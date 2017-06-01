package client;

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
import server.ServerInterface;
import server.element.CartaSviluppo;
import server.element.CartaTerritori;
import server.element.Dado;
import server.element.Giocatore;
import server.element.Partita;

/*
 * Classe di implementazione 
 */
public class ConnectionRmiClient extends ConnectionClient implements ClientInterface, RMIClientInterface,Serializable{

	private int port;
	private ServerInterface serverMethods;
	private int positionGame;
	private String name;
	private int numberOfGamers;
	private ControllerGame guiGame;
	private StartClientGui start;
	
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



	public boolean createANewLobby(String lobby,String color) {
		try {
			positionGame=serverMethods.createNewLobby(lobby, name, color , this);
			return true;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
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
			positionGame=serverMethods.selectLobby(lobby, name, color, (client.RMIClientInterface) this);
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
			try {
				setNumberOfGamers(serverMethods.startPartita(name, positionGame));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
	public void notifyStartGame() throws RemoteException {
		start.changeStage(4);
		ArrayList<CartaSviluppo> carte = serverMethods.getCards(positionGame);
		for(int i=0;i<carte.size();i++){
			//CHiamare il metodo grafico per settare le carte
			carte.get(i).getNameCard();
			carte.get(i).getImage();
		}
		guiGame.setCards(carte);
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
	}
	
	public void moveDisco(double x, double y, String colorPlayer, String colorDisco) throws RemoteException {
		guiGame.movePunti(colorDisco, x, y);
		
	}

	public void moveFamiliareAvv(double x, double y, String colorPlayer, String colorFamiliare) throws RemoteException {
		guiGame.moveFamAvv(colorPlayer, colorFamiliare, x, y);
		
	}

	@Override
	public void moveDiscoFede(double x, double y, String colorPlayer, String colorDisco) throws RemoteException {
		guiGame.movePuntiFede(colorDisco, x, y);
		
	}

	public void addScomunica(int nScomuniche, Tooltip tooltip) throws RemoteException {
		guiGame.addScomunica(nScomuniche, tooltip);
		
	}

	public StartClientGui getStart() {
		return start;
	}

	public void setStart(StartClientGui start) {
		this.start = start;
	}

	public void notifyTurno() throws RemoteException, SQLException {
		guiGame.enableGame();
	}
	
	public void notifySpostamento(String color, double x, double y) throws RemoteException {
		serverMethods.notifySpostamento(color,x,y,name,positionGame);
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
	
	public void setCardGiocatore(CartaSviluppo carta) {
		try {
			serverMethods.giveCard(carta,name,positionGame);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}