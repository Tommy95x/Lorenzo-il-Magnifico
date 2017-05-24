package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;

import server.ServerInterface;
import server.element.CartaSviluppo;
import server.element.Partita;

/*
 * Classe di implementazione 
 */
public class ConnectionRmiClient extends ConnectionClient implements ClientInterface{

	private String ip;
	private int port;
	private ServerInterface serverMethods;
	private int positionGame;
	private String name;
	private int numberOfGamers;
	
	public ConnectionRmiClient(){
		System.out.println("New Rmi client create");
		connect();
		//loginMom();
	}
	
	//Metodo di test per il login
	/*private void loginMom() {
		Scanner input = new Scanner(System.in);
		System.out.println("Inserisci il nome dell'account");
		String account = input.nextLine();
		System.out.println("Inserisci la password");
		String pw = input.nextLine();
		try {
			System.out.println(serverMethods.login(account, pw));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/
	
	private void connect() {
		try {
			Registry registry = LocateRegistry.getRegistry(port);
			System.out.println("Get registry from Server");
			String [] e = registry.list();
			for(String mom:e)
				System.out.println(mom);
			String remoteInterface="ServerInterface";
			serverMethods = (ServerInterface) registry.lookup(remoteInterface);
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
			return serverMethods.login(account, pw);
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
			return String.valueOf(serverMethods.register(account, pw, pw2, email));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "false";
		}
		
	}



	public boolean createANewLobby(String lobby) {
		try {
			positionGame=serverMethods.createNewLobby(lobby, name);
			return true;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}


	public ArrayList<Partita> lobbiesView() {
		try {
			return serverMethods.getLobby();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public void enterInALobby(String lobby) {
		try {
			//Metodo grafico per richiedere il colore al giocatore passandogli i colori disponibili
			serverMethods.getColors(lobby);
			String color = null;
			positionGame=serverMethods.selectLobby(lobby, name, color);
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
			numberOfGamers=serverMethods.startPartita(name, positionGame);
			ArrayList<CartaSviluppo> carte = serverMethods.getCards(positionGame);
			for(int i=0;i<carte.size();i++){
				//CHiamare il metodo grafico per settare le carte
				carte.get(i).getNameCard();
				carte.get(i).getImage();
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void lanciaDadi() {
		//Risposta al metodo grafico con il lancio di un metodo grafico che modificher� il tabellone 
		try {
			serverMethods.showDiceValues(positionGame, name);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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