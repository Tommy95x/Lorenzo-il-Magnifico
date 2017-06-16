package client;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.ArrayList;

import client.gui.StartClientGui;
import client.gui.controllers.ControllerGame;
import javafx.scene.control.Tooltip;
import server.element.CartaSviluppo;
import server.element.CartaTerritori;
import server.element.Dado;
import server.element.Giocatore;
import server.element.Partita;
import server.element.Portafoglio;
import server.element.TesseraScomunica;
import shared.ServerInterface;

/*
 * Classe di implementazione 
 */

public class ConnectionRmiClient extends ConnectionClient implements ClientInterface{

	private int port;
	private ServerInterface serverMethods;
	private int positionGame;
	private String name;
	private int numberOfGamers;
	private ControllerGame guiGame;
	private ConnectionRmiInterlocutorClient interlocutor;
	private String color;
	
	public ConnectionRmiClient() throws RemoteException{
		System.out.println("New Rmi client create");
		connect();
	}
	
	private void connect() throws RemoteException{
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


	
	public String login(String account, String pw) throws RemoteException{
		name=account;
		try {
			String mom = serverMethods.login(account, pw);
			return mom;
		} catch (RemoteException e) {
			//Gestire l'eccezione
			e.printStackTrace();
			return "Error, lost server connection";
		}
		
	}
	
	public String register(String account, String pw, String pw2, String email)throws RemoteException {
		try {
			return serverMethods.register(account, pw, pw2, email);
		} catch (RemoteException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "false";
		}
		
	}



	public boolean createANewLobby(String lobby,String color) {
		System.out.println("lobby = "+lobby+" name="+name+" color="+color+" this="+this+"");
		try {
			System.out.println(positionGame);
			System.out.println("Momentaneo");
			interlocutor = new ConnectionRmiInterlocutorClient(name);
			serverMethods.setClientInterface(lobby, name, interlocutor);
			positionGame=serverMethods.createNewLobby(lobby, name, color);
			System.out.println("provaprova");
			System.out.println(positionGame);
			this.color=color;
			return true;
		} catch (RemoteException | SQLException e) {
			System.out.println("Error rmi");
			e.printStackTrace();
			return false;
		}
	}


	public ArrayList<Partita> lobbiesView() throws RemoteException{
		try {
			ArrayList<Partita> mom = new ArrayList<Partita>();
			for(String name :serverMethods.getLobby())
				mom.add(new Partita(name));
			return mom;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public int enterInALobby(String lobby, String color) throws RemoteException{
		try {
			interlocutor = new ConnectionRmiInterlocutorClient(name);
			positionGame=serverMethods.selectLobby(lobby, name, color);
			interlocutor.setPositionGame(positionGame);
		} catch (RemoteException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return numberOfGamers;
		
	}

	public String[] getColors(String lobby) throws RemoteException, IOException, ClassNotFoundException {
		return serverMethods.getColors(positionGame);
	}

	public TesseraScomunica[] getCardsScomunica() throws RemoteException{
		return serverMethods.getCardsScomunica(positionGame);
	}

	public void startGame() throws RemoteException{
		try {
			try {
				System.out.println(name+" "+positionGame+" "+ serverMethods.toString());
				serverMethods.startPartita(name, positionGame);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Ritorno dal metodo notifica rmiClient");
	}

	public Dado[] lanciaDadi(int positionGame) throws SQLException, RemoteException {
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

	public int getNumberOfGamers() {
		return numberOfGamers;
	}

	public void setNumberOfGamers(int numberOfGamers) {
		this.numberOfGamers = numberOfGamers;
	}
	
	public String controlloPosizionamento(String color, double x, double y, Integer agg) throws RemoteException {
		try {
			return serverMethods.controlloPosizionamento(color, positionGame, name, x, y,agg);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public void setGuiGame(ControllerGame guiGame){
		this.guiGame = guiGame;
		interlocutor.setGuiGame(guiGame);
	}
	
	

	public void setStage(StartClientGui start) {
		interlocutor.setStart(start);
	}

	public String getNamePosition(double x, double y) throws RemoteException, SQLException {
		return serverMethods.getNamePosition(x,y,positionGame,name);
	}
	
	public void exitToTheGame(String lobby,String color) {
		try {
			serverMethods.exitToTheGame(lobby, color, name);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<CartaSviluppo> getCardsGamer() {
		try {
			return serverMethods.getCardsGamer(positionGame,name);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void setCardGiocatore(CartaSviluppo carta, int i) {
		try {
			serverMethods.giveCard(carta,name,positionGame,i);
		} catch (RemoteException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public CartaSviluppo[] getCardsGame() {
		try {
			return serverMethods.getCards(positionGame);
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Portafoglio getRisorse() throws RemoteException {
		return serverMethods.getRisorse(positionGame, name);
	}

	public Giocatore[] getGiocatori() throws RemoteException {
		return serverMethods.getGiocatori(positionGame);
	}
	
	public String getName() {
		return name;
	}
	
	public void deleteView() throws RemoteException {
		serverMethods.deleteView(positionGame);
		
	}
	
	public void removeAccount() throws RemoteException {
		serverMethods.removeAccount(name);
	}
	
	public int getPlayers() throws RemoteException{
		return serverMethods.getNumberOfPlayer(positionGame);
	}
	
	public void notifySpostamento(String color, double x, double y) throws RemoteException {
		serverMethods.notifySpostamento(color,x,y,name,positionGame);
	}
	
	public String controlloPosizionamento(String color, double x, double y, int integer) throws RemoteException, IOException, ClassNotFoundException, SQLException {
		return serverMethods.controlloPosizionamento(color, positionGame, name, x, y, integer);
	}
	
	public String getColor(){
		return this.color;
	}
	
	public void notifySpostamentoPunti(String tipo) throws RemoteException {
		try {
			serverMethods.notifySpostamentoPunti(positionGame, name, tipo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void produzione(int qta) throws RemoteException {
		serverMethods.produzione(positionGame, name,qta);
		
	}
	
	public void raccolto(int qta) throws RemoteException {
		serverMethods.raccolto(positionGame, name,qta);
	}
	
	public void addRisorse(String tipo, int qta) throws RemoteException, SQLException {
		serverMethods.addRisorse(positionGame, name, tipo, qta);
		
	}
	
	public void addPunti(String tipo, int qta) throws RemoteException, SQLException {
		serverMethods.addPunti(positionGame, name, tipo, qta);
	}
	
	public void addPergamene(int qta) throws RemoteException{
		serverMethods.pergamene(positionGame, name,qta);
		
	}
	
	@Override
	public void sostegnoChiesa(boolean flag) {
		// TODO Auto-generated method stub
		
	}
}