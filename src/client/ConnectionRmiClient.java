package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.ArrayList;
import server.ServerInterface;
import server.element.CartaSviluppo;
import server.element.Dado;
import server.element.Giocatore;
import server.element.Partita;

/*
 * Classe di implementazione 
 */
public class ConnectionRmiClient extends ConnectionClient implements ClientInterface, RMIClientInterface{

	private int port;
	private ServerInterface serverMethods;
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
			return serverMethods.register(account, pw, pw2, email);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "false";
		}
		
	}



	public boolean createANewLobby(String lobby) {
		try {
			String color = null;
			positionGame=serverMethods.createNewLobby(lobby, name, color ,this);
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
	
	public void enterInALobby(String lobby, String color) {
		try {
			positionGame=serverMethods.selectLobby(lobby, name, color, this);
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
			setNumberOfGamers(serverMethods.startPartita(name, positionGame));
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
	public Dado[] lanciaDadi(int positionGame, String name) throws SQLException {
		//Risposta al metodo grafico con il lancio di un metodo grafico che modificher� il tabellone 
		try {
			return serverMethods.showDiceValues(this.positionGame, this.name);
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public String[] getColors() throws RemoteException {
		
		return serverMethods.getColors(positionGame);
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
	public void sostegnoChiesa(boolean flag) {
		// TODO Auto-generated method stub
		
	}

	public int getNumberOfGamers() {
		return numberOfGamers;
	}

	public void setNumberOfGamers(int numberOfGamers) {
		this.numberOfGamers = numberOfGamers;
	}

	@Override
	public void notifyStartGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void richestaSostegnoChiesa() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restTabellone() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endGame(Giocatore[] giocatoriPartita) throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	
	
}