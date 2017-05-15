package server;

import java.rmi.RemoteException;


/*
*Classe per l'implementazione dei metodi utilizzati nelle classi RMIServer e ThreadSocketServer, meodoti richiamati e utilizzati per giocare
*dai giocatori durante una partita.
**/
public class ImplementsServerInterface implements RMIServerInterface{

	@Override
	public boolean login(String username, String pw) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean register(String username, String pw1, String email) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createNewLobby(String lobby) throws RemoteException {
		// TODO Auto-generated method stub
		
	}


}

@Override
public boolean login(String username, String pw) throws RemoteException {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean register(String username, String pw1, String email) throws RemoteException {
	// TODO Auto-generated method stub
	return false;
}

@Override
public void createNewLobby(String lobby) throws RemoteException {
	// TODO Auto-generated method stub
	
}
