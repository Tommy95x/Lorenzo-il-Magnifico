package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

import client.ConnectionRmiInterlocutorClient;
import javafx.scene.image.Image;
import server.element.CartaSviluppo;
import server.element.Dado;
import server.element.Giocatore;
import server.element.Partita;
import server.element.Portafoglio;
import server.element.TesseraScomunica;

public interface ServerInterface extends Remote {
	
	public String login(String username, String pw1) throws RemoteException;
	String register(String username, String pw1, String pw2, String email) throws RemoteException, SQLException;
	public int createNewLobby(String lobby, String account, String color) throws RemoteException, SQLException;
	public void startPartita(String account, int game) throws RemoteException, SQLException;
	public ArrayList<String> getLobby() throws RemoteException;
	public int selectLobby(String lobby, String account, String color) throws RemoteException, SQLException;
	public String[] getColors(int positionGame) throws RemoteException;
	public CartaSviluppo[] getCards(int positionGame) throws RemoteException;
	public void showCards(Image card, String nameCard) throws RemoteException;
	public void getTurno(String name) throws RemoteException;
	public void giveResources(String resource, int qta) throws RemoteException;
	public void givePunti(String gamer, int qta, String tipoPunti) throws RemoteException;
	public Dado[] showDiceValues(int positionGame, String name) throws RemoteException, SQLException;
	public String[] getColors(String lobby)throws RemoteException;
	public String controlloPosizionamento(String color,int posisitionGame, String name, double x, double y, Integer agg)throws RemoteException, SQLException;
	public void changeGamer(int positionGame) throws RemoteException, SQLException;
	public void notifySpostamento(String color, double x, double y, String name, int positionGame)throws RemoteException;
	public String getNamePosition(double x, double y, int positionGame, String name)throws RemoteException, SQLException;
	public void exitToTheGame(String lobby, String color, String name) throws RemoteException;
	public ArrayList<CartaSviluppo> getCardsGamer(int positionGame, String name)throws RemoteException;
	public void giveCard(CartaSviluppo carta, String name, int positionGame)throws RemoteException;
	public TesseraScomunica[] getCardsScomunica(int positionGame) throws RemoteException;
	public Portafoglio getRisorse(int positionGame, String name)throws RemoteException;
	public Giocatore[] getGiocatori(int positionGame)throws RemoteException;
	public void deleteView(int positionGame)throws RemoteException;
	public void removeAccount(String name)throws RemoteException;
	public int getNumberOfPlayer(int positionGame) throws RemoteException;
	public void setClientInterface(String lobby, String account, RMIClientInterface connectionRmiClient)throws RemoteException;
}
