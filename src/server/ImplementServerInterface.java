
package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

import client.ConnectionRmiClient;
import client.RMIClientInterface;
import javafx.scene.image.Image;
import server.element.CartaSviluppo;
import server.element.Dado;
import server.element.Flag;
import server.element.Giocatore;
import server.element.Partita;
import server.element.Portafoglio;
import server.element.TesseraScomunica;


/*
*Classe per l'implementazione dei metodi utilizzati nelle classi RMIServer e ThreadSocketServer, meodoti richiamati e utilizzati per giocare
*dai giocatori durante una partita.
**/
public class ImplementServerInterface extends UnicastRemoteObject implements ServerInterface{

	private static final long serialVersionUID = 1L;
	private StartServer commonServer;
	
	public ImplementServerInterface(StartServer commonServer) throws RemoteException {
		this.commonServer=commonServer;
	}

	public String login(String username, String pw1) throws RemoteException {
		System.out.println("Classe implementazione interfaccia");
		return commonServer.addClient(username, pw1);
	}

	
	public String register(String username, String pw1, String pw2, String email) throws RemoteException, SQLException {
		if(!pw1.equals(pw2))
			return "The passwords are not equal";
		return commonServer.registerNewClient(username, pw1, email);
	}

	public int createNewLobby(String lobby, String account, String color, RMIClientInterface connectionRmiClient) throws RemoteException, SQLException {
		commonServer.addGame(lobby, account);
		//commonServer.setCards(commonServer.getLobbyByName(lobby),account);
		//commonServer.getLobbyByName(lobby).setCardsScomunica(commonServer.getDBConnection(), account);
		commonServer.getLobbyByName(lobby).addGiocatore(new Giocatore(color,commonServer.getLobbyByName(lobby), account, commonServer.getIndicePartita(lobby)));
		commonServer.getLobbyByName(lobby).changeColors(color);
		commonServer.getLobbyByName(lobby).getGiocatoreByName(account).getClient(connectionRmiClient);
		//commonServer.getLobbyByName(lobby).getGiocatoreByName(account).setFlag(new Flag(color, commonServer, account));
		return commonServer.getIndicePartita(lobby);
	}

	public int startPartita(String account, int game) throws RemoteException, SQLException{
			commonServer.getLobbyByNumber(game).start(account);
			return commonServer.getLobbyByNumber(game).numberOfPlayer();
	}

	public ArrayList<Partita> getLobby() throws RemoteException{
		System.out.println(commonServer.getLobbies().size());
		return commonServer.getLobbies();
	}
	
	public int selectLobby(String lobby, String account, String color, RMIClientInterface client) throws RemoteException{
		int numberGame=commonServer.getIndicePartita(lobby);
		commonServer.addGamer(numberGame, color, account);
		commonServer.getLobbyByName(lobby).changeColors(color);
		commonServer.getLobbyByName(lobby).getGiocatoreByName(account).getClient(client);
		commonServer.getLobbyByName(lobby).getGiocatoreByName(account).setFlag(new Flag(color, commonServer, account));
		return numberGame;
	}

	public String[] getColors(int positionGame) throws RemoteException{
		return commonServer.getLobbyByNumber(positionGame).getColors();
	}

	//Chiedere per sentire come gestire al meglio il caso 
	/*public void adviseOtherGamers(String account, int positionGame) throws RemoteException {
		commonServer.getLobbyByNumber(positionGame).adviseGamers();		
	}*/

	public ArrayList<CartaSviluppo> getCards(int positionGame) throws RemoteException{
		return commonServer.getLobbyByNumber(positionGame).getCards();
	}

	@Override
	public void showCards(Image card, String nameCard) throws RemoteException{
		
		
	}

	@Override
	public void getTurno(String name) throws RemoteException{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void giveResources(String resource, int qta) throws RemoteException{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void givePunti(String gamer, int qta, String tipoPunti) throws RemoteException{
		// TODO Auto-generated method stub
		
	}

	public Dado[] showDiceValues(int positionGame, String name) throws RemoteException, SQLException {
		
		return commonServer.getLobbyByNumber(positionGame).getGiocatoreByName(name).setDadi(commonServer.getDBConnection().getConnection(name));
	}

	public String[] getColors(String lobby) throws RemoteException {
		return commonServer.getLobbyByName(lobby).getColors();
	}

	public String controlloPosizionamento(String color, int posisitionGame, String name, double x, double y, Integer agg) throws RemoteException, SQLException {	
		return commonServer.getLobbyByNumber(posisitionGame).getGiocatoreByName(name).controlloPosizionamento(color, x,y, commonServer.getDBConnection().getConnection(name), agg);
	}

	public void changeGamer(int positionGame) throws RemoteException, SQLException {
		commonServer.getLobbyByNumber(positionGame).changeGamer();
		
	}

	public void notifySpostamento(String color, double x, double y, String name, int positionGame) throws RemoteException {
		commonServer.getLobbyByNumber(positionGame).notifySpostamento(color,commonServer.getLobbyByNumber(positionGame).getGiocatoreByName(name),x,y);
	}

	public String getNamePosition(double x, double y, int positionGame, String name) throws RemoteException, SQLException {
		return commonServer.getLobbyByNumber(positionGame).getNamePosition(x,y,commonServer.getDBConnection().getConnection(name));
	}

	public void getCard(int positionGame, String name, CartaSviluppo carta) throws RemoteException {
		commonServer.getLobbyByNumber(positionGame).getGiocatoreByName(name).addCard(carta);
		
	}

	public void exitToTheGame(String lobby, String color, String name) throws RemoteException{
		commonServer.getLobbyByName(lobby).exitToGame(name,color);
	}

	public ArrayList<CartaSviluppo> getCardsGamer(int positionGame, String name) throws RemoteException{
		return commonServer.getLobbyByNumber(positionGame).getGiocatoreByName(name).getCardsGamer();
	}

	public void giveCard(CartaSviluppo carta, String name, int positionGame) throws RemoteException {
		commonServer.getLobbyByNumber(positionGame).getGiocatoreByName(name).addCard(carta);
		commonServer.getLobbyByNumber(positionGame).notifyAddCardGiocatore(name,carta);
		
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

}

