package client;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.ArrayList;

import client.gui.StartClientGui;
import client.gui.controllers.ControllerGame;
import server.ServerInterface;
import server.element.CartaSviluppo;
import server.element.Dado;
import server.element.Giocatore;
import server.element.Partita;
import server.element.Portafoglio;
import server.element.TesseraScomunica;

/**
 * Classe utilizzata dal client per l'istanzia e utlizzo di una connessione client/server di tipo RMI
 * Classe che estende la classe ConnectionClient viene istanziata dall'utente che decide e seleziona la connessione richiesta
 * 
 * @author Lorenzo il Magnifico
 *
 */
public class ConnectionRmiClient extends ConnectionClient implements ClientInterface {

	@SuppressWarnings("unused")
	private int port;
	private ServerInterface serverMethods;
	private int positionGame;
	private String name;
	private int numberOfGamers;
	@SuppressWarnings("unused")
	private ControllerGame guiGame;
	private ConnectionRmiInterlocutorClient interlocutor;
	private String color;

	public ConnectionRmiClient() throws RemoteException {
		System.out.println("New Rmi client create");
		connect();
	}

	/**
	 * Metodo per la conenssione del client RMI con il ServerRMI
	 * 
	 * @throws RemoteException
	 */
	private void connect() throws RemoteException {
		try {
			Registry registry = LocateRegistry.getRegistry();
			System.out.println("Get registry from Server");
			String[] e = registry.list();
			for (String mom : e)
				System.out.println(mom);
			String remoteInterface = "ServerInterface";
			serverMethods = (ServerInterface) registry.lookup(remoteInterface);
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Error connection not create");
			e.printStackTrace();
		}
	}

	/**
	 * Metodo di login chiamato dalla grafica dall'utente per effettuare il
	 * login di sistema
	 * 
	 * @param String
	 *            account nome dell'utente che si collega al sistema
	 * @param String
	 *            pw password dedicata dell'utente
	 * @return String risposta del server a seconda dei possibili riscontri con
	 *         i dati ricevuti
	 * @throws RemoteException
	 */
	public String login(String account, String pw) throws RemoteException {
		try {
			String mom = serverMethods.login(account, pw);
			return mom;
		} catch (RemoteException e) {
			// Gestire l'eccezione
			e.printStackTrace();
			return "Error, lost server connection";
		}

	}

	/**
	 * Metodo di restrazione di una nuovo utente che si collega al sistema
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
	public String register(String account, String pw, String pw2, String email) throws RemoteException {
		try {
			return serverMethods.register(account, pw, pw2, email);
		} catch (RemoteException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "false";
		}

	}

	/**
	 * Metodo di creazione di una nuova lobby ossia di una nuova partita che
	 * sarà pronta per essere giocata
	 * 
	 * @param String
	 *            lobby ossia il nome che l'utente dara' alla propria partita
	 * @param String
	 *            color ossia il colore scelto dall'utente
	 * @return boolean restituisce true se la crazione della lobby avviene con
	 *         successo false altrimenti
	 * @throws RemoteException
	 * 
	 */
	public boolean createANewLobby(String lobby, String color) throws RemoteException, SQLException {
		System.out.println("lobby = " + lobby + " name=" + name + " color=" + color + " this=" + this + "");
		System.out.println(positionGame);
		System.out.println("Momentaneo");
		positionGame = serverMethods.createNewLobby(lobby, name, color);
		System.out.println("provaprova");
		System.out.println(positionGame);
		this.color = color;
		return true;
	}

	/**
	 * Metodi di richiesta delle partite attive all'interno del commonServer
	 * contente tutte le partite.
	 * 
	 * @return ArrayList<Partita> arrayList di partite pronte per essere giocate
	 *         e scelte dall'utente che ha richiesto le lobbies disponibili
	 * @throws RemoteException
	 */
	public ArrayList<Partita> lobbiesView() throws RemoteException {
		try {
			ArrayList<Partita> mom = new ArrayList<Partita>();
			for (String name : serverMethods.getLobby())
				mom.add(new Partita(name));
			return mom;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Metodo di selezione di una lobby da parte di un giocatore
	 * 
	 * @param String
	 *            lobby nome della lobby scelta dal giocatore
	 * @param String
	 *            color colore scelto dal giocatore
	 * @return int numero della lobby all'interno dell'ArrayList di partite
	 *         contenute nel commonServer
	 * @throws RemoteException
	 */
	public int enterInALobby(String lobby, String color) throws RemoteException {
		try {
			positionGame = serverMethods.selectLobby(lobby, name, color);
			this.color = color;
		} catch (RemoteException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return numberOfGamers;

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
	public String[] getColors(String lobby) throws RemoteException, IOException, ClassNotFoundException {
		return serverMethods.getColors(positionGame);
	}

	/**
	 * Metodo di richiesta da parte dell'utente in fase d'istanzia graficamente
	 * delle TessereScomunica selelzionate dal server in modo casuale in fase di
	 * crezione della partita
	 * 
	 * @return TessereScomunica[] array di tre tessere selezionate dal server e
	 *         presenti nella partita corrispondente contenuta nel commonServer
	 * @throws RemoteException
	 */
	public TesseraScomunica[] getCardsScomunica() throws RemoteException {
		return serverMethods.getCardsScomunica(positionGame);
	}

	/**
	 * Metodo che serve all'utente per notificare al sistema di essere pronto ad
	 * iniziare la partita
	 * 
	 * @throws RemoteException
	 */
	public void startGame() throws RemoteException {
		try {
			try {
				System.out.println(name + " " + positionGame + " " + serverMethods.toString());
				serverMethods.startPartita(name, positionGame);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Ritorno dal metodo notifica rmiClient");
	}

	/**
	 * Metodo che richiede i dadi dedicati al giocatore e che vengono generati
	 * in modo casuale all'interno della classe giocatore
	 * 
	 * @param positionGame
	 * @return
	 * @throws SQLException
	 * @throws RemoteException
	 */
	public Dado[] lanciaDadi(int positionGame) throws SQLException, RemoteException {
		try {
			return serverMethods.showDiceValues(this.positionGame, this.name);
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}

	}

	public String[] getColors() throws RemoteException {

		return serverMethods.getColors(positionGame);
	}

	public int getNumberOfGamers() {
		return numberOfGamers;
	}

	public void setNumberOfGamers(int numberOfGamers) {
		this.numberOfGamers = numberOfGamers;
	}

	/**
	 * Metodo utilizzato per il corretto posizionamento da parte dell'utente di
	 * una mossa di gioco.
	 * 
	 * @param String
	 *            color colore del familiare mosso dal giocatore
	 * @param double
	 *            x posizione x di posizionamento del familiare
	 * @param double
	 *            y posizione y del posizionamento del familiare
	 * @param int
	 *            agg possibile aggiunta di servitori da parte del giocatore per
	 *            aumentare il proprio valore d'azione
	 * @return String restituzione a seocnda dei differenti casi del riscontro
	 *         da parte del server
	 * @throws RemoteException
	 */
	public String controlloPosizionamento(String color, double x, double y, Integer agg) throws RemoteException {
		try {
			return serverMethods.controlloPosizionamento(color, positionGame, name, x, y, agg);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public void setGuiGame(ControllerGame guiGame) {
		this.guiGame = guiGame;
		interlocutor.setGuiGame(guiGame);
	}

	public String getNamePosition(double x, double y) throws RemoteException, SQLException {
		return serverMethods.getNamePosition(x, y, positionGame, name);
	}

	/**
	 * Metodo utilizzato per il corretto exit dal gioco
	 * 
	 * @throws RemoteException
	 */
	public void exitToTheGame(String lobby, String color) {
		try {
			serverMethods.exitToTheGame(lobby, color, name);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * public ArrayList<CartaSviluppo> getCardsGamer() { try { return
	 * serverMethods.getCardsGamer(positionGame,name); } catch (RemoteException
	 * e) { // TODO Auto-generated catch block e.printStackTrace(); } return
	 * null; }
	 */

	/**
	 * Metodo che viene invocato ad ogni aggiunta di una carta da parte del
	 * giocatore successivamente ad una propria mossa di giocao
	 * 
	 * @throws RemoteException
	 */
	public void setCardGiocatore(CartaSviluppo carta, int tipo, int piano) {
		try {
			serverMethods.giveCard(carta, name, positionGame, tipo, piano);
		} catch (RemoteException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Metodo che restituisce le carte di gioco selezionate in modo casuale
	 * durante la creazioen della partita
	 * 
	 * @throws RemoteException
	 */
	public CartaSviluppo[] getCardsGame() {
		try {
			return serverMethods.getCards(positionGame);
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Metodo utirlizzato per reperire le risorse del giocatore
	 * 
	 * @throws RemoteException
	 */
	public Portafoglio getRisorse() throws RemoteException {
		return serverMethods.getRisorse(positionGame, name);
	}

	public Giocatore[] getGiocatori() throws RemoteException {
		return serverMethods.getGiocatori(positionGame);
	}

	public String getName() {
		return name;
	}

	/**
	 * Metodo invocato dall'utente al momento dall'uscita del sistema per
	 * l'eliminazione della partita dal DB (questo metodo viene eseguito solo
	 * dall'utente che ha creato la partita)
	 * 
	 * @throws RemoteException
	 */
	public void deleteView() throws RemoteException {
		serverMethods.deleteView(positionGame);

	}

	public void removeAccount() throws RemoteException {
		serverMethods.removeAccount(name);
	}

	public int getPlayers() throws RemoteException {
		return serverMethods.getNumberOfPlayer(positionGame);
	}

	/**
	 * Metodo per la notifica di una propria pedian agli avversari
	 * 
	 * @throws RemoteException
	 */
	public void notifySpostamento(String color, double x, double y) throws RemoteException {
		serverMethods.notifySpostamento(color, x, y, this.color, positionGame);
	}

	/**
	 * Metodo utilizzato per il corretto posizionamento da parte dell'utente di
	 * una mossa di gioco.
	 * 
	 * @param String
	 *            color colore del giocatore che esegue la mossa
	 * @param double
	 *            x posizione x della pedina
	 * @param double
	 *            y posizione y della pedina
	 * @param int
	 * @throws RemoteException
	 */
	public String controlloPosizionamento(String color, double x, double y, int integer)
			throws RemoteException, IOException, ClassNotFoundException, SQLException {
		System.out.println(name);
		return serverMethods.controlloPosizionamento(color, positionGame, name, x, y, integer);
	}

	public String getColor() {
		return this.color;
	}

	/**
	 * Metodo utilizzato per la notifica dello spostamento dei punti da parte di
	 * un giocatore che ha appena eseguito una mossa
	 * 
	 * @param String
	 *            tipo e' la tipologia di punteggio da notrificare agli
	 *            avversari
	 * @throws RemoteException
	 */
	public void notifySpostamentoPunti(String tipo) throws RemoteException {
		try {
			serverMethods.notifySpostamentoPunti(positionGame, name, tipo, color);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Azione di produzione chiamato in caso di acquisizione di carte
	 * particolare o in caso di posizionamente di un proprio familiare su una
	 * posizione specifica del tabellone
	 * 
	 * @param int
	 *            qta forza della produzione
	 * @throws RemoteException
	 */
	public void produzione(int qta) throws RemoteException {
		try {
			serverMethods.produzione(positionGame, name, qta);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Azione di raccolro chiamato in caso di acquisizione di carte particolare
	 * o in caso di posizionamente di un proprio familiare su una posizione
	 * specifica del tabellone
	 * 
	 * @param int
	 *            qta forza del racconto
	 * @throws RemoteException
	 */
	public void raccolto(int qta) throws RemoteException {
		try {
			serverMethods.raccolto(positionGame, name, qta);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Metodo che aggiorna le risorse del giocatore all'interno del proprio
	 * portafoglio risorse
	 * 
	 * @param String
	 *            tipo di risorsa da aggiornare
	 * @param int
	 *            qta quantita' di risorsa da aggiungere alle risore gia'
	 *            presenti nel tabellone
	 * @throws RemoteException
	 */
	public void addRisorse(String tipo, int qta) throws RemoteException, SQLException {
		serverMethods.addRisorse(positionGame, name, tipo, qta);

	}

	/**
	 * Metodo di aggiunta e aggiornamento dei punti conenuti all'interno del
	 * portafoglio di un giocatore
	 * 
	 * @param Stirng
	 *            tipo tipo di risorsa da aggiornare
	 * @param int
	 *            qta quantita' da aggiugere alle risorse presenti nel
	 *            portafoglio
	 * @throws RemoteException
	 */
	public void addPunti(String tipo, int qta) throws RemoteException, SQLException {
		serverMethods.addPunti(positionGame, name, tipo, qta);
	}

	/**
	 * Notifica di aggiunta di una pergamente
	 * 
	 * @param int
	 *            qta quantita' di pergamene da attivare al giocatore
	 * @throws RemoteException
	 */
	public void addPergamene(int qta) throws RemoteException {
		serverMethods.pergamene(positionGame, name, qta);

	}

	public void sendClient(StartClientGui start) {
		try {
			interlocutor = new ConnectionRmiInterlocutorClient();
			interlocutor.setName(name);
			interlocutor.setPositionGame(positionGame);
			interlocutor.setStart(start);
			serverMethods.setClientInterface(positionGame, name, interlocutor);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setName(String text) {
		this.name = text;

	}

	public Dado[] lanciaDadi() throws SQLException, RemoteException, ClassNotFoundException, IOException {
		return serverMethods.showDiceValues(positionGame, name);
	}

	public boolean getPosPalLibero(String string, int i) {
		try {
			return serverMethods.getPosPalLibero(string, i, positionGame, name);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Metodo chiamato a fine del turno per il cambio delle carte di gioco
	 * 
	 * @throws RemoteException
	 */
	public void changeCards() {
		try {
			serverMethods.changeCards(positionGame);
		} catch (RemoteException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addScomunica() throws RemoteException {
		serverMethods.addScomunica(positionGame, name);

	}

	public int getDado(String string) throws RemoteException {
		return serverMethods.getDado(string, positionGame, name);
	}

	public void changeGamer() throws RemoteException, SQLException {
		serverMethods.changeGamer(positionGame);
	}

	public void notifyDecisionChiesa(boolean b) throws RemoteException {
		serverMethods.notifyDecisionChiesa(b);
	}

	/**
	 * Metodo di notifica del cambiamento di risorse da parte dell'avversario
	 * 
	 * @param String
	 *            tipo ossia il tipo di risorsa aggiunta
	 * @param int
	 *            qta quantita' acquisita dall'avversario
	 */
	public void notifyRisorse(String tipo, int qta) {
		try {
			serverMethods.notifyAddRisorse(positionGame, name, tipo, qta);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getNumberOfGamer() throws RemoteException {
		return serverMethods.getNumberOfPlayer(positionGame);
	}

	/**
	 * Metodo di conferma di ricezione di una scomunica da parte della chiesa
	 * 
	 * @param int
	 *            i
	 * @return int periodo di scomunica ricevuto
	 * @throws RemoteException
	 */
	public int scomunicato(int i) throws RemoteException {

		return serverMethods.scomunicato(i, positionGame, name);
	}

	/**
	 * Metodo di scambio all'interno di un turno della possibilita' di
	 * posizionare i propri familiare
	 * 
	 * @throws RemoteException
	 */
	public void scambio() throws RemoteException {
		serverMethods.scambio(positionGame);

	}

	public void sistemaPosizioni() {
		try {
			serverMethods.sistemaPosizioni(positionGame, name);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Commentare quando non si testa
	/*
	 * public boolean testConnetcion() { String mom = null; try { mom =
	 * serverMethods.login("prova", "prova"); } catch (RemoteException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); } if(mom != null)
	 * return true; else return false; }
	 */

}