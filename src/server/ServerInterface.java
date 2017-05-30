package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

import client.ConnectionRmiClient;
import javafx.scene.image.Image;
import server.element.CartaSviluppo;
import server.element.Dado;
import server.element.Partita;

public interface ServerInterface extends Remote {
	
	public String login(String username, String pw1) throws RemoteException;
	String register(String username, String pw1, String pw2, String email) throws RemoteException;
	public int createNewLobby(String lobby, String account, String color, ConnectionRmiClient connectionRmiClient) throws RemoteException, SQLException;
	public int startPartita(String account, int game) throws RemoteException, SQLException;
	public ArrayList<Partita> getLobby() throws RemoteException;
	public int selectLobby(String lobby, String account, String color, ConnectionRmiClient connectionRmiClient) throws RemoteException;
	public String[] getColors(int positionGame) throws RemoteException;
	public ArrayList<CartaSviluppo> getCards(int positionGame) throws RemoteException;
	public void mossa(String account, int positionGame, String color, int x, int y) throws RemoteException;
	public void showCards(Image card, String nameCard) throws RemoteException;
	public void getTurno(String name) throws RemoteException;
	public void giveResources(String resource, int qta) throws RemoteException;
	public void givePunti(String gamer, int qta, String tipoPunti) throws RemoteException;
	public Dado[] showDiceValues(int positionGame, String name) throws RemoteException, SQLException;
	public String[] getColors(String lobby)throws RemoteException;
	public String controlloPosizionamento(String color,int posisitionGame, String name, double x, double y)throws RemoteException, SQLException;
	public void changeGamer(int positionGame) throws RemoteException, SQLException;
	public void notifySpostamento(String color, double x, double y, String name, int positionGame)throws RemoteException;
	public String getNamePosition(double x, double y, int positionGame, String name)throws RemoteException, SQLException;
	public void getCard(int positionGame, String name, CartaSviluppo carta)throws RemoteException;
}
