package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javafx.scene.image.Image;
import server.element.CartaSviluppo;
import server.element.Giocatore;
import server.element.Partita;

public interface ServerInterface extends Remote {
	
	public String login(String username, String pw1) throws RemoteException;
	boolean register(String username, String pw1, String pw2, String email) throws RemoteException;
	public int createNewLobby(String lobby, String account) throws RemoteException;
	public int startPartita(String account, int game) throws RemoteException;
	public ArrayList<Partita> getLobby() throws RemoteException;
	public int selectLobby(String lobby, String account, String color) throws RemoteException;
	public String[] getColors(String lobby) throws RemoteException;
	public ArrayList<CartaSviluppo> getCards(int positionGame) throws RemoteException;
	public void mossa(String account, int positionGame, String color, int x, int y) throws RemoteException;
	//public void moveDisco(int x, int y, /*Aggiungere elemento grafico utilizzato per il disco*/, String nameMove) throws RemoteException;
	//public void moveFamiliareAvv(int x, int y, String nameMove, /*Aggiungere elemento grafico che rappresenta il familiare*/) throws RemoteException;
	public void showDiceValues(int values) throws RemoteException;
	public void showCards(Image card, String nameCard) throws RemoteException;
	public void richestaSostegnoChiesa() throws RemoteException;
	public void restTabellone() throws RemoteException;
	public void getTurno(String name) throws RemoteException;
	public void giveResources(String resource, int qta) throws RemoteException;
	public void givePunti(String gamer, int qta, String tipoPunti) throws RemoteException;
	public void endGame(Giocatore[] giocatoriPartita) throws RemoteException;
	
}
