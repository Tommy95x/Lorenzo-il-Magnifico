package server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/*
*Classe utilizzata utilizzata per creare una connessione passiva RMI che permette agli utenti di connettersi e giocare con una connessione 
*differente dai Socket.
*/
public class RMIServer extends Thread{

	private int port;
	private StartServer commonServer;
	
	public RMIServer(int port, StartServer commonServer){
		this.port=port;
		this.commonServer=commonServer;
	}
	
	private void startConnectionRMI(){
		Registry registry;
		try {
			registry = LocateRegistry.createRegistry(port);
			registry.bind("ServerInterface", new ImplementServerInterface(commonServer));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("RMI Server Start");
		System.out.println("Waiting for invocations from clients");
	}
	
	public void run(){
		this.startConnectionRMI();
	}
	
}
