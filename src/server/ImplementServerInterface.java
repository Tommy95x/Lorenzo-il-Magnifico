
package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

import client.ConnectionRmiClient;
import javafx.scene.image.Image;
import server.element.CartaSviluppo;
import server.element.Dado;
import server.element.Flag;
import server.element.Giocatore;
import server.element.Partita;


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

	
	public String register(String username, String pw1, String pw2, String email) throws RemoteException {
		if(!pw1.equals(pw2))
			return "The passwords are not equal";
		return commonServer.registerNewClient(username, pw1, email);
	}

	public int createNewLobby(String lobby, String account, String color, ConnectionRmiClient connectionRmiClient) throws RemoteException, SQLException {
		commonServer.addGame(lobby, account);
		commonServer.setCards(commonServer.getLobbyByName(lobby),account);
		commonServer.getLobbyByName(lobby).setCardsScomunica(commonServer.getDBConnection(), account);
		commonServer.getLobbyByName(lobby).addGiocatore(new Giocatore(color,commonServer.getLobbyByName(lobby), account, commonServer.getIndicePartita(lobby)));
		commonServer.getLobbyByName(lobby).changeColors(color);
		commonServer.getLobbyByName(lobby).getGiocatoreByName(account).getClient(connectionRmiClient);
		commonServer.getLobbyByName(lobby).getGiocatoreByName(account).setFlag(new Flag(color, commonServer, account));
		return commonServer.getIndicePartita(lobby);
	}

	public int startPartita(String account, int game) throws RemoteException{
			commonServer.getLobbyByNumber(game).start(account);
			return commonServer.getLobbyByNumber(game).numberOfPlayer();
	}

	public ArrayList<Partita> getLobby() throws RemoteException{
		return commonServer.getLobbies();
	}
	
	public int selectLobby(String lobby, String account, String color, ConnectionRmiClient connectionRmiClient) throws RemoteException{
		int numberGame=commonServer.getIndicePartita(lobby);
		commonServer.addGamer(numberGame, color, account);
		commonServer.getLobbyByName(lobby).changeColors(color);
		commonServer.getLobbyByName(lobby).getGiocatoreByName(account).getClient(connectionRmiClient);
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

	public void mossa(String account, int positionGame, String color, int x, int y) throws RemoteException{
		
		
	}

	/*@Override
	public void moveDisco(int x, int y) throws RemoteException{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveFamiliareAvv(int x, int y, String nameMove) throws RemoteException{
		// TODO Auto-generated method stub
		
	}*/

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

	@Override
	public Dado[] showDiceValues(int positionGame, String name) throws RemoteException, SQLException {
		
		return commonServer.getLobbyByNumber(positionGame).getGiocatoreByName(name).setDadi(commonServer.getDBConnection().getConnection(name));
	}

	public String[] getColors(String lobby) throws RemoteException {
		return commonServer.getLobbyByName(lobby).getColors();
	}

	public String controlloPosizionamento(String color, int posisitionGame, String name, double x, double y) throws RemoteException, SQLException {	
		return commonServer.getLobbyByNumber(posisitionGame).getGiocatoreByName(name).controlloPosizionamento(color, x,y, commonServer.getDBConnection().getConnection(name));
	}
}

