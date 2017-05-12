package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServerInterface extends Remote {

	public boolean login(String username, String pw) throws RemoteException;
	public boolean register(String username, String pw1, String email) throws RemoteException;
	public void createNewLobby(String lobby) throws RemoteException;
}
