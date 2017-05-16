package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import server.element.CartaSviluppo;
import server.element.Partita;


/*
*Classe per l'implementazione dei metodi utilizzati nelle classi RMIServer e ThreadSocketServer, meodoti richiamati e utilizzati per giocare
*dai giocatori durante una partita.
**/
public class ImplementServerInterface extends UnicastRemoteObject implements ServerInterface{

	private static final long serialVersionUID = 1L;
	private StartServer commonServer;
	
	public ImplementServerInterface(StartServer commonServer) throws RemoteException{
		this.commonServer=commonServer;
	}

	public String login(String username, String pw1) throws RemoteException {
		return commonServer.addClient(username, pw1);
	}

	
	public boolean register(String username, String pw1, String pw2, String email) throws RemoteException {
		if(!pw1.equals(pw2))
			return false;
		return commonServer.registerNewClient(username, pw1, email);
	}

	public int createNewLobby(String lobby, String account) throws RemoteException {
		commonServer.addGame(lobby, account);
		commonServer.setCards(commonServer.getLobbyByName(lobby),account);
		return commonServer.getIndicePartita(lobby);
		
	}

	public int startPartita(String account, int game) throws RemoteException{
			commonServer.getLobbyByNumber(game).start(account);
			return commonServer.getLobbyByNumber(game).numberOfPlayer();
	}

	public ArrayList<Partita> getLobby() throws RemoteException{
		return commonServer.getLobbies();
	}
	
	public int selectLobby(String lobby, String account, String color) throws RemoteException{
		int numberGame=commonServer.getIndicePartita(lobby);
		commonServer.addGamer(numberGame, color, account);
		return numberGame;
	}

	public String[] getColors(String lobby) throws RemoteException{
		return commonServer.getLobbyByName(lobby).getColors();
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
	
	
	
}

