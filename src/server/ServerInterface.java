package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import server.element.Partita;

public interface ServerInterface extends Remote {

	public int createNewLobby(String lobby, String account) throws RemoteException;
	public String login(String username, String pw1) throws RemoteException;
	boolean register(String username, String pw1, String pw2, String email) throws RemoteException;
	public int selectLobby(String lobby, String account, String color) throws RemoteException;
	public ArrayList<Partita> getLobby() throws RemoteException;
	public int startPartita(String account, int game) throws RemoteException;
}
