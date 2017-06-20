
package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.image.Image;
import server.element.CartaSviluppo;
import server.element.Dado;
import server.element.Flag;
import server.element.Giocatore;
import server.element.Partita;
import server.element.Portafoglio;
import server.element.TesseraScomunica;
import shared.RMIClientInterface;
import shared.ServerInterface;

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

	public String login(String username, String pw1) throws RemoteException {
		System.out.println("Classe implementazione interfaccia");
		return commonServer.addClient(username, pw1);
	}

	public String register(String username, String pw1, String pw2, String email) throws RemoteException, SQLException {
		if (!pw1.equals(pw2))
			return "The passwords are not equal";
		return commonServer.registerNewClient(username, pw1, email);
	}

	public int createNewLobby(String lobby, String account, String color) throws RemoteException, SQLException {
		System.out.println("Creo la partita nell'arraylist partite");
		commonServer.addGame(lobby, account);
		System.out.println("Recupero dal db le carte");
		commonServer.setCards(commonServer.getLobbyByName(lobby), account);
		System.out.println("SetCarteScomunica");
		commonServer.getLobbyByName(lobby).setCardsScomunica(commonServer.getDBConnection(), account);
		System.out.println("Aggiungo il giocatore alla partita creata");
		Giocatore g = new Giocatore(color, commonServer.getLobbyByName(lobby), account,
				commonServer.getIndicePartita(lobby));
		commonServer.getLobbyByName(lobby).addGiocatore(g);
		System.out.println("Aggiorno i colori disponibili");
		commonServer.getLobbyByName(lobby).changeColors(color);
		System.out.println("Acquisisco la comunicazione del giocatore");
		commonServer.getLobbyByName(lobby).getGiocatoreByName(account).setFlag(new Flag(color, commonServer, account));
		return commonServer.getIndicePartita(lobby);
	}

	public void setClientInterface(int positionGame, String account, RMIClientInterface connectionRmiClient)
			throws RemoteException {
		commonServer.getLobbyByNumber(positionGame).getGiocatoreByName(account).getClient(connectionRmiClient);
	}

	public void startPartita(String account, int game) throws RemoteException, SQLException {
		System.out.println("Ricevuto " + account + " utente pronto");
		System.out.println("Ritorno da metodo notifica partita");
		commonServer.getLobbyByNumber(game).start(account);
	}

	public ArrayList<String> getLobby() throws RemoteException {
		System.out.println(commonServer.getLobbies().size());
		ArrayList<String> mom = new ArrayList<String>();
		for (Partita p : commonServer.getLobbies())
			mom.add(p.getLobbyName());
		return mom;
	}

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

	public String[] getColors(int positionGame) throws RemoteException {
		return commonServer.getLobbyByNumber(positionGame).getColors();
	}

	public CartaSviluppo[] getCards(int positionGame) throws RemoteException {
		return commonServer.getLobbyByNumber(positionGame).getCards();
	}

	public Dado[] showDiceValues(int positionGame, String name) throws RemoteException, SQLException {

		return commonServer.getLobbyByNumber(positionGame).getGiocatoreByName(name)
				.setDadi(commonServer.getDBConnection().getConnection(name));
	}

	public String[] getColors(String lobby) throws RemoteException {
		return commonServer.getLobbyByName(lobby).getColors();
	}

	public String controlloPosizionamento(String color, int posisitionGame, String name, double x, double y, int agg)
			throws RemoteException, SQLException {
		System.out.println(name);
		return commonServer.getLobbyByNumber(posisitionGame).getGiocatoreByName(name).controlloPosizionamento(color, x,
				y, commonServer.getDBConnection().getConnection(name), agg);
	}

	public void changeGamer(int positionGame) throws RemoteException, SQLException {
		commonServer.getLobbyByNumber(positionGame).changeGamer();
		if(commonServer.getLobbyByNumber(positionGame).getNumberOfPlayers() == 4){
			
		}
	}

	public void notifySpostamento(String color, double x, double y, String name, int positionGame)
			throws RemoteException {
		commonServer.getLobbyByNumber(positionGame).notifySpostamento(color,
				commonServer.getLobbyByNumber(positionGame).getGiocatoreByName(name), x, y);
	}

	public String getNamePosition(double x, double y, int positionGame, String name)
			throws RemoteException, SQLException {
		return commonServer.getLobbyByNumber(positionGame).getNamePosition(x, y,
				commonServer.getDBConnection().getConnection(name));
	}

	public void exitToTheGame(String lobby, String color, String name) throws RemoteException {
		commonServer.getLobbyByName(lobby).exitToGame(name, color);
	}

	public ArrayList<CartaSviluppo> getCardsGamer(int positionGame, String name) throws RemoteException {
		return commonServer.getLobbyByNumber(positionGame).getGiocatoreByName(name).getCardsGamer();
	}

	public void giveCard(CartaSviluppo carta, String name, int positionGame, int tipo, int piano)
			throws RemoteException, SQLException {
		commonServer.getLobbyByNumber(positionGame).getGiocatoreByName(name).addCard(carta, tipo,
				commonServer.getDBConnection().getConnection(name));
		System.out.println("Pronto a notificare i giocatori");
		commonServer.getLobbyByNumber(positionGame).notifyAddCardGiocatoreAvv(name, carta, piano);
	}

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
	
	public void notifySpostamentoPunti(int positionGame, String name, String tipo, String color) throws RemoteException, SQLException {
		commonServer.getLobbyByNumber(positionGame).notifySpostamentoPunti(tipo,
				commonServer.getLobbyByNumber(positionGame).getGiocatoreByName(name).getRisorse().getPunti(tipo), commonServer.getDBConnection().getConnection(name), color);

	}

	public void produzione(int positionGame, String name, int qta) throws RemoteException, SQLException {
		commonServer.getLobbyByNumber(positionGame).getGiocatoreByName(name).produzione(qta, commonServer.getDBConnection().getConnection(name));
	}

	public void raccolto(int positionGame, String name, int qta) throws RemoteException, SQLException {
		commonServer.getLobbyByNumber(positionGame).getGiocatoreByName(name).raccolto(qta, commonServer.getDBConnection().getConnection(name));
		
	}

	@Override
	public void addRisorse(int positionGame, String name, String tipo, int qta) throws RemoteException, SQLException {
		commonServer.getLobbyByNumber(positionGame).getGiocatoreByName(name).addRis(tipo, qta, commonServer.getDBConnection().getConnection(name));
	}

	
	public void addPunti(int positionGame, String name, String tipo, int qta) throws RemoteException, SQLException {
		commonServer.getLobbyByNumber(positionGame).getGiocatoreByName(name).addPunti(tipo, qta, commonServer.getDBConnection().getConnection(name));
	}

	public void pergamene(int posizionGame, String name,int qta) throws RemoteException {
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
}
