package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import server.ServerInterface;
import server.element.Partita;

/*
 * Classe di implementazione 
 */
public class ConnectionRmiClient extends ConnectionClient implements ClientInterface{

	private String ip;
	private int port;
	private ServerInterface serverMethod;
	private int positionGame;
	private String name;
	private int numberOfGamers;
	
	public ConnectionRmiClient(){
		System.out.println("New Rmi client create");
		connect();
	}


	private void connect() {
		try {
			Registry registry = LocateRegistry.getRegistry(port);
			System.out.println("Get registry from Server");
			String [] e = registry.list();
			for(String mom:e)
				System.out.println(mom);
			String remoteInterface="ServerInterface";
			serverMethod = (ServerInterface) registry.lookup(remoteInterface);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			System.out.println("Error connection not create");
			e.printStackTrace();
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			
			e1.printStackTrace();
		}
	}


	
	public String login(String account, String pw) {
		name=account;
		try {
			return serverMethod.login(account, pw);
		} catch (RemoteException e) {
			//Gestire l'eccezione
			e.printStackTrace();
			return "Error, lost server connection";
		}
		
	}


	public void richiestaRegistrazione() {
		// TODO Auto-generated method stub
		
	}
	
	
	public String register(String account, String pw, String pw2, String email) {
		try {
			return String.valueOf(serverMethod.register(account, pw, pw2, email));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "false";
		}
		
	}



	public boolean createANewLobby(String lobby) {
		try {
			positionGame=serverMethod.createNewLobby(lobby, name);
			return true;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}


	public ArrayList<Partita> lobbiesView() {
		try {
			return serverMethod.getLobby();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public void enterInALobby(String lobby) {
		try {
			//Metodo grafico per richiedere il colore al giocatore passandogli i colori disponibili
			serverMethod.getColors(lobby);
			String color = null;
			positionGame=serverMethod.selectLobby(lobby, name, color);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	@Override
	public void selectColorGamer(String color) {
		
		
	}


	@Override
	public void startGame() {
		try {
			numberOfGamers=serverMethod.startPartita(name, positionGame);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void lanciaDadi() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void posizionareFamiliare(String color, int x, int y) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void spendereRisorse(String risorsa, int qta) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void takeCards(String name) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void sostegnoChiesa(boolean flag) {
		// TODO Auto-generated method stub
		
	}
	
}