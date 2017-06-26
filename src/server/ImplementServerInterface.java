
package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.scene.image.Image;
import server.database.ConnectionDatabase;
import server.element.CartaSviluppo;
import server.element.Dado;
import server.element.Flag;
import server.element.Giocatore;
import server.element.Partita;
import server.element.Portafoglio;
import server.element.Posizioni;
import server.element.TesseraScomunica;

/*
*Classe per l'implementazione dei metodi utilizzati nelle classi RMIServer e ThreadSocketServer, meodoti richiamati e utilizzati per giocare
*dai giocatori durante una partita.
**/
public class ImplementServerInterface extends UnicastRemoteObject implements ServerInterface {

	private static final long serialVersionUID = 1L;
	private StartServer commonServer;

	public ImplementServerInterface(StartServer commonServer) throws RemoteException {
		this.commonServer = commonServer;
	}

	/**
	 * Metodo utilizzato dall'utente per irchiedere un login di sistema
	 * 
	 * @param String
	 *            pw1 password dell'utente
	 * @param String
	 *            username nome del giocatore che ha richiesto il login
	 */
	public String login(String username, String pw1) throws RemoteException {
		System.out.println("Classe implementazione interfaccia");
		return commonServer.addClient(username, pw1);
	}

	/**
	 * Metodo di restrazione di una nuovo utente al sistema viene in prima
	 * battuta controllata la correttezza dei dati iseriti dall'utente ossia che
	 * la email abbia i campi richiesti, le due password siano uguali, che
	 * l'utente non sia gia' registrato al sistema. Il sistema in caso di
	 * successo o insuccesso per qualsiasi dei motivi esposti in precedenza
	 * notifica con un particolare messaggio l'utente
	 * 
	 * @param String
	 *            account nuovo nome dell'utente che si vuole restistrare al
	 *            sistema
	 * @param String
	 *            pw1 prima password inserita dall'utente
	 * @param String
	 *            pw2 secondo password richiesta per il confronto del corretto
	 *            inserimento con la prima pw
	 * @param String
	 *            email emai richiesta per la registrazione a sistema
	 * @return String stringa di riscontro a secondo dei differenti casi dal
	 *         server che comunichera' la riuscita o meno della nuova
	 *         registrazione
	 * @throws RemoteException
	 */
	public String register(String username, String pw1, String pw2, String email) throws RemoteException, SQLException {
		if (!pw1.equals(pw2))
			return "The passwords are not equal";
		return commonServer.registerNewClient(username, pw1, email);
	}

	/**
	 * Metodo di creazione di una nuova lobby richiesta da un utente
	 * 
	 * @param String
	 *            lobby ossia il nome che l'utente dara' alla propria partita
	 * @param String
	 *            color ossia il colore scelto dall'utente
	 * @return int restituisce l'indice della lobby all'inteerno dell'ArrayList
	 *         di partite conetnure all'interno del commonServer
	 * @throws RemoteException
	 * 
	 */
	public int createNewLobby(String lobby, String account, String color) throws RemoteException, SQLException {
		System.out.println("Creo la partita nell'arraylist partite");
		commonServer.addGame(lobby, account);
		System.out.println(account);
		System.out.println(commonServer.getLobbyByName(lobby));
		System.out.println("Recupero dal db le carte");
		commonServer.setCards(commonServer.getLobbyByName(lobby), account);
		System.out.println("SetCarteScomunica");
		commonServer.getLobbyByName(lobby).setCardsScomunica(commonServer.getDBConnection(), account);
		System.out.println("Aggiungo il giocatore alla partita creata");
		Giocatore g = new Giocatore(color, commonServer.getLobbyByName(lobby), account,
				commonServer.getIndicePartita(lobby));
		System.out.println("Il giocatore aggiunto " + commonServer.getLobbyByName(lobby).getGiocatoreByName(account));
		commonServer.getLobbyByName(lobby).addGiocatore(g);
		System.out.println("Aggiorno i colori disponibili");
		commonServer.getLobbyByName(lobby).changeColors(color);
		System.out.println("Acquisisco la comunicazione del giocatore");
		commonServer.getLobbyByName(lobby).getGiocatoreByName(account).setFlag(new Flag(color, commonServer, account));
		return commonServer.getIndicePartita(lobby);
	}

	/**
	 * Quesnto metodo serve al server per settare all'interno di tutti i
	 * giocatori collegati con collegamento RMI l'attributo utilizzato per
	 * notificare il client stesso
	 * 
	 * @param int
	 *            posiotionGame posizione della partita all'interno
	 *            dell'ArrayList di partite
	 * @param String
	 *            account nome dell'utente che vuole aggiungere all'interno
	 *            della "propria" calsse Giocatore l'attributo interlocutor che
	 *            rapprensente il client di comunicazione utilizzato dal server
	 *            per notificare il client stesso
	 * @param RMIClientInterface
	 *            connectionClient
	 */
	public void setClientInterface(int positionGame, String account, RMIClientInterface connectionRmiClient)
			throws RemoteException {
		commonServer.getLobbyByNumber(positionGame).getGiocatoreByName(account).getClient(connectionRmiClient);
	}

	/**
	 * Metodo di notifica da parte di un utente pronto per iniziare la propria
	 * partita
	 * 
	 * @param String
	 *            account nome del giocatore che notifica
	 * @param int
	 *            game posizione della propria partita all'interno dell'array di
	 *            parite
	 */
	public void startPartita(String account, int game) throws RemoteException, SQLException {
		System.out.println("Ricevuto " + account + " utente pronto");
		System.out.println("Ritorno da metodo notifica partita");
		commonServer.getLobbyByNumber(game).start(account);
	}

	/**
	 * Metodi d'invio delle partite attive all'interno del commonServer contente
	 * tutte le partite.
	 * 
	 * @return ArrayList<Partita> arrayList di partite pronte per essere giocate
	 *         e scelte dall'utente che ha richiesto le lobbies disponibili
	 * @throws RemoteException
	 */
	public ArrayList<String> getLobby() throws RemoteException {
		System.out.println(commonServer.getLobbies().size());
		ArrayList<String> mom = new ArrayList<String>();
		for (Partita p : commonServer.getLobbies())
			mom.add(p.getLobbyName());
		return mom;
	}

	/**
	 * Metodo di richiesta di una selazione di una lobby da parte di un
	 * giocatore
	 * 
	 * @param String
	 *            lobby nome della lobby scelta dal giocatore
	 * @param String
	 *            color colore scelto dal giocatore
	 * @return int numero della lobby all'interno dell'ArrayList di partite
	 *         contenute nel commonServer in caso di qualche possibile errore il
	 *         server notifica restituendo -1
	 * @throws RemoteException
	 */
	public int selectLobby(String lobby, String account, String color) throws RemoteException, SQLException {
		int numberGame = commonServer.getIndicePartita(lobby);
		if (commonServer.getLobbyByNumber(numberGame).getNumberOfPlayers() < 4) {
			commonServer.addGamer(numberGame, color, account);
			commonServer.getLobbyByName(lobby).changeColors(color);
			// Commentare per i test altrimenti lancia un null pointer la riga
			// successiva
			commonServer.getLobbyByName(lobby).getGiocatoreByName(account)
					.setFlag(new Flag(color, commonServer, account));
			return numberGame;
		} else
			return -1;
	}

	/**
	 * Metodo che fornisce i colori disponibili a seconda della lobby
	 * selezionata dall'utente
	 * 
	 * @param String
	 *            nome della lobby selezionata dall'utente
	 * @return String[] array di colori disponibili
	 * @throws RemoteException
	 */
	public String[] getColors(int positionGame) throws RemoteException {
		return commonServer.getLobbyByNumber(positionGame).getColors();
	}

	public CartaSviluppo[] getCards(int positionGame) throws RemoteException {
		return commonServer.getLobbyByNumber(positionGame).getCards();
	}

	/**
	 * Metodo che invia i dadi dedicati al giocatore e che vengono generati in
	 * modo casuale all'interno della classe giocatore
	 * 
	 * @param positionGame
	 * @param String
	 *            name nome del giocatore che richiede i dadi
	 * @return Dado[] array dei tre dadi dedicati ad ogni giocatore
	 * @throws SQLException
	 * @throws RemoteException
	 */
	public Dado[] showDiceValues(int positionGame, String name) throws RemoteException, SQLException {

		return commonServer.getLobbyByNumber(positionGame).getGiocatoreByName(name)
				.setDadi(commonServer.getDBConnection().getConnection(name));
	}

	/**
	 * Invio dei colori disponibili a seconda della lobby selezionata
	 * dall'utente
	 * 
	 * @param String
	 *            nome della lobby selezionata dall'utente
	 * @return String[] array di colori disponibili
	 * @throws RemoteException
	 */
	public String[] getColors(String lobby) throws RemoteException {
		return commonServer.getLobbyByName(lobby).getColors();
	}

	public String controlloPosizionamento(String color, int posisitionGame, String name, double x, double y, int agg)
			throws RemoteException, SQLException {
		System.out.println(name);
		return commonServer.getLobbyByNumber(posisitionGame).getGiocatoreByName(name).controlloPosizionamento(color, x,
				y, commonServer.getDBConnection(), agg);
	}

	public void changeGamer(int positionGame) throws RemoteException, SQLException {
		commonServer.getLobbyByNumber(positionGame).changeGamer();
	}

	public void notifySpostamento(String color, double x, double y, String colorAvv, int positionGame)
			throws RemoteException {
		commonServer.getLobbyByNumber(positionGame).notifySpostamento(color, colorAvv, x, y);
	}

	public String getNamePosition(double x, double y, int positionGame, String name)
			throws RemoteException, SQLException {
		return commonServer.getLobbyByNumber(positionGame).getNamePosition(x, y, commonServer.getDBConnection(), name);

	}

	/**
	 * Notifica d'uscita di un utente da una partita
	 * 
	 * @param String
	 *            lobby nome della partita che viene abbandonata
	 * @param String
	 *            color colore posseduto in precedenza dal giocatore
	 * @param String
	 *            name nome del giocatore che lascia la partita
	 * @throws RemoteException
	 */
	public void exitToTheGame(String lobby, String color, String name) throws RemoteException {
		commonServer.getLobbyByName(lobby).exitToGame(name, color);
	}

	public ArrayList<CartaSviluppo> getCardsGamer(int positionGame, String name) throws RemoteException {
		return commonServer.getLobbyByNumber(positionGame).getGiocatoreByName(name).getCardsGamer();
	}

	/**
	 * Metodo utilizzato per aggiungere di una carta ad un giocatore
	 * successivamente ad uan sua mossa e a tutti i controlli di sistema pre
	 * eseguiti
	 * 
	 * @param CartaSviluppo
	 *            carta ossai la carta acquisita dal giocatore
	 * @param int
	 *            posizionGame posizione della partita del giocaotre su cui
	 *            operare
	 * @param int
	 *            tipo tipo di carta acquisita
	 * @param int
	 *            piano ossia la posizione della carta sul corrispondente piano
	 *            del palazzo corrispondente
	 * @throws RemoteException
	 */
	public void giveCard(CartaSviluppo carta, String name, int positionGame, int tipo, int piano)
			throws RemoteException, SQLException {
		commonServer.getLobbyByNumber(positionGame).getGiocatoreByName(name).addCard(carta, tipo,
				commonServer.getDBConnection(), name);
		System.out.println("Pronto a notificare i giocatori");
		commonServer.getLobbyByNumber(positionGame).notifyAddCardGiocatoreAvv(name, carta, piano);
	}

	/**
	 * Metodo d'invio da parte dell'utente in fase d'istanzia graficamente delle
	 * TessereScomunica selelzionate dal server in modo casuale in fase di
	 * crezione della partita
	 * 
	 * @param int
	 *            positionGame posizione della lobby richiesta all'interno
	 *            dell'ArrayList delle parite
	 * @return TessereScomunica[] array di tre tessere selezionate dal server e
	 *         presenti nella partita corrispondente contenuta nel commonServer
	 * @throws RemoteException
	 */
	public TesseraScomunica[] getCardsScomunica(int positionGame) throws RemoteException {
		return commonServer.getLobbyByNumber(positionGame).getCardsScomunica();
	}

	public Portafoglio getRisorse(int positionGame, String name) throws RemoteException {
		return commonServer.getLobbyByNumber(positionGame).getGiocatoreByName(name).getRisorse();
	}

	public Giocatore[] getGiocatori(int positionGame) throws RemoteException {
		return commonServer.getLobbyByNumber(positionGame).getGiocatori();
	}

	public void deleteView(int positionGame) throws RemoteException {
		try {
			commonServer.getLobbyByNumber(positionGame)
					.deleteView(commonServer.getDBConnection().getConnection("Server"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		commonServer.deletLobby(positionGame);
	}

	public void removeAccount(String name) throws RemoteException {
		commonServer.removeAccount(name);

	}

	public int getNumberOfPlayer(int positionGame) throws RemoteException {
		return commonServer.getLobbyByNumber(positionGame).numberOfPlayer();
	}

	@Override
	public void showCards(Image card, String nameCard) throws RemoteException {

	}

	@Override
	public void getTurno(String name) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void giveResources(String resource, int qta) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void givePunti(String gamer, int qta, String tipoPunti) throws RemoteException {
		// TODO Auto-generated method stub

	}

	/**
	 * Metodo di notifica dello spostamento dei punti da parte di un giocatore
	 * che ha appena eseguito una mossa a tutti gli altri giocatori avversari
	 * 
	 * @param String
	 *            tipo e' la tipologia di punteggio da notrificare agli
	 *            avversari
	 * @param int
	 *            positionGame posizione della partita all'interno
	 *            dell'ArrayList delle partita
	 * @param String
	 *            tipo tipo di punti acquisiti dal giocaotre durante la mossa
	 * @param String
	 *            color ossia il colore del giocatore
	 * @throws RemoteException
	 */
	public void notifySpostamentoPunti(int positionGame, String name, String tipo, String color)
			throws RemoteException, SQLException {
		commonServer.getLobbyByNumber(positionGame).notifySpostamentoPunti(tipo,
				commonServer.getLobbyByNumber(positionGame).getGiocatoreByName(name).getRisorse().getPunti(tipo),
				commonServer.getDBConnection(), color);

	}

	/**
	 * Azione di produzione richiesta da un giocatore
	 * 
	 * @param int
	 *            qta forza della produzione
	 * @param int
	 *            positionGame posizione della partita all'interno
	 *            dell'ArrayList delle partita
	 * @param String
	 *            tipo tipo di punti acquisiti dal giocaotre durante la mossa
	 * @throws RemoteException
	 */
	public void produzione(int positionGame, String name, int qta) throws RemoteException, SQLException {
		commonServer.getLobbyByNumber(positionGame).getGiocatoreByName(name).produzione(qta,
				commonServer.getDBConnection());
	}

	/**
	 * Azione di raccolro richiesto da un giocaotore
	 * 
	 * @param int
	 *            qta forza del racconto
	 * @param int
	 *            positionGame posizione della partita all'interno
	 *            dell'ArrayList delle partita
	 * @param String
	 *            tipo tipo di punti acquisiti dal giocaotre durante la mossa
	 * @throws RemoteException
	 */
	public void raccolto(int positionGame, String name, int qta) throws RemoteException, SQLException {
		commonServer.getLobbyByNumber(positionGame).getGiocatoreByName(name).raccolto(qta,
				commonServer.getDBConnection());

	}

	/**
	 * Metodo che aggiorna le risorse del giocatore
	 * 
	 * @param String
	 *            tipo di risorsa da aggiornare
	 * @param int
	 *            qta quantita' di risorsa da aggiungere alle risore gia'
	 *            presenti nel tabellone
	 * @param int
	 *            positionGame posizione della partita all'interno
	 *            dell'ArrayList delle partita
	 * @param String
	 *            tipo tipo di punti acquisiti dal giocaotre durante la mossa
	 * @throws RemoteException
	 */
	@Override
	public void addRisorse(int positionGame, String name, String tipo, int qta) throws RemoteException, SQLException {
		commonServer.getLobbyByNumber(positionGame).getGiocatoreByName(name).addRis(tipo, qta,
				commonServer.getDBConnection());
	}

	/**
	 * Metodo di aggiunta e aggiornamento i putni di un giocatore
	 * 
	 * @param Stirng
	 *            tipo tipo di risorsa da aggiornare
	 * @param int
	 *            qta quantita' da aggiugere alle risorse presenti nel
	 *            portafoglio
	 * @param int
	 *            positionGame posizione della partita all'interno
	 *            dell'ArrayList delle partita
	 * @param String
	 *            tipo tipo di punti acquisiti dal giocaotre durante la mossa
	 * @throws RemoteException
	 */
	public void addPunti(int positionGame, String name, String tipo, int qta) throws RemoteException, SQLException {
		commonServer.getLobbyByNumber(positionGame).getGiocatoreByName(name).addPunti(tipo, qta,
				commonServer.getDBConnection());
	}

	/**
	 * Notifica di aggiunta di una pergamente da parte di un giocatore
	 * 
	 * @param int
	 *            qta quantita' di pergamene da attivare al giocatore *
	 * @param int
	 *            positionGame posizione della partita all'interno
	 *            dell'ArrayList delle partita
	 * @param String
	 *            tipo tipo di punti acquisiti dal giocaotre durante la mossa
	 * 
	 * @throws RemoteException
	 */
	public void pergamene(int posizionGame, String name, int qta) throws RemoteException {
		commonServer.getLobbyByNumber(posizionGame).getGiocatoreByName(name).notifyPergamena(qta);

	}

	public boolean getPosPalLibero(String string, int i, int positionGame, String name) throws RemoteException {
		return commonServer.getLobbyByNumber(positionGame).getGiocatoreByName(name).getPosPalLibero(string, i);
	}

	public int getDado(String string, int positionGame, String name) throws RemoteException {
		return commonServer.getLobbyByNumber(positionGame).getGiocatoreByName(name).getDado(string);
	}

	@Override
	public void notifyDecisionChiesa(boolean b) throws RemoteException {
		// TODO Auto-generated method stub

	}

	public void addScomunica(int positionGame, String name) throws RemoteException {
		commonServer.getLobbyByNumber(positionGame).getGiocatoreByName(name).addScomunica();

	}

	@Override
	public void changeCards(int positionGame) throws RemoteException, SQLException {
		commonServer.getLobbyByNumber(positionGame).setCards(commonServer.getDBConnection().getConnection("Server"));

	}

	/**
	 * Metodo di notifica di aggiunta di alcune risorse a tutti i giocatori
	 * avversari del benificiario
	 * 
	 * @param String
	 *            tipo ossia il tipo di risorsa aggiunta
	 * @param int
	 *            qta quantita' acquisita dall'avversario
	 * @param int
	 *            positionGame posizione della partita all'interno
	 *            dell'ArrayList delle partita
	 * @param String
	 *            tipo tipo di punti acquisiti dal giocaotre durante la mossa
	 * @throws RemoteException
	 */
	@Override
	public void notifyAddRisorse(int positionGame, String name, String tipo, int qta) throws RemoteException {
		commonServer.getLobbyByNumber(positionGame).notifyAddRisorse(name, tipo, qta);

	}

	/**
	 * Metodo utilizzato solo dal ConnectionClientConsole per stampare a CLi
	 * tutte le posizioni disponibili di gioco
	 * 
	 * @param String
	 *            name nome della poszione richiesta
	 * @return ArrayList<Posizioni> tutte le posizioni contenute all'iterno del
	 *         tabellone di gioco con i relativi nomi e posizioni x y
	 * @throws RemoteException
	 */
	@Override
	public ArrayList<Posizioni> getPositions(String name) throws RemoteException, SQLException {
		Connection con;
		ArrayList<Posizioni> mom = new ArrayList<Posizioni>();
		String query = "SELECT * FROM POSIZIONETABELLONE";
		con = commonServer.getDBConnection().getConnection(name);
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while (rs.next()) {
			mom.add(new Posizioni(rs.getDouble("POSX"), rs.getDouble("POSY"), rs.getString("NOME")));
		}
		rs.close();
		stmt.close();
		commonServer.getDBConnection().releaseConnection(con);
		return mom;
	}

	/**
	 * Notifica di scomunica di un giocatore
	 * 
	 * @param int
	 *            i
	 * @param int
	 *            positionGame posizione della partita all'interno
	 *            dell'ArrayList delle partita
	 * @param String
	 *            tipo tipo di punti acquisiti dal giocaotre durante la mossa
	 * @return int periodo di scomunica ricevuto
	 * @throws RemoteException
	 */
	public int scomunicato(int i, int positionGame, String name) throws RemoteException {
		return commonServer.getLobbyByNumber(positionGame).getGiocatoreByName(name).activateBanCards();
	}

	/**
	 * Metodo di "rimbalzo" di mosse fra un giocatore e l'altro
	 * 
	 * @param int
	 *            positionGame posizione della partita all'interno
	 *            dell'ArrayList delle partita
	 * 
	 * @throws RemoteException
	 */
	@Override
	public void scambio(int positionGame) throws RemoteException {
		commonServer.getLobbyByNumber(positionGame).scambio();

	}

	@Override
	public void sistemaPosizioni(int positionGame, String name) throws RemoteException {
		commonServer.getLobbyByNumber(positionGame).sistemaPosizioni(name);

	}
}
