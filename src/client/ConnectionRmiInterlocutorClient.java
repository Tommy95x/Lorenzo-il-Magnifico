package client;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.SQLException;

import client.gui.StartClientGui;
import client.gui.controllers.ControllerGame;
import javafx.scene.control.Tooltip;
import server.RMIClientInterface;
import server.element.CartaSviluppo;
import server.element.Giocatore;
import server.element.Portafoglio;

public class ConnectionRmiInterlocutorClient implements RMIClientInterface, client.RMIClientInterface, Serializable{
	
	
	private ControllerGame guiGame;
	private StartClientGui start;
	private int positionGame;
	private String name;

	public ConnectionRmiInterlocutorClient(String name, int positionGame, StartClientGui start){
		this.name = name;
		this.positionGame = positionGame;
		this.start = start;
		
	}
	
	public void notifyStartGame() throws RemoteException {
		System.out.println("Notifica inizio partita avvenuta");
		if(start == null)
			System.out.println("Stage null");
		System.out.println("Cambio Stage");
		start.changeStage(5);
		System.out.println("Prova");
	}

	@Override
	public void moveDisco(double x, double y, String colorPlayer, String colorDisco) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveDiscoFede(double x, double y, String colorPlayer, String colorDisco) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveFamiliareAvv(double x, double y, String colorPlayer, String colorDisco) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void richestaSostegnoChiesa() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restTabellone() throws RemoteException {
		try {
			guiGame.resetTabellon();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void endGame(Giocatore[] giocatoriPartita) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addScomunica(int nScomuniche, Tooltip tooltip) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	public void notifyAddCard(CartaSviluppo carta, String nameAvv,Portafoglio portafoglio) throws RemoteException {
		guiGame.notifyAddCardAvv(carta, nameAvv,portafoglio);
		
	}
	
	public void notifyTurno() throws RemoteException, SQLException {
		guiGame.enableGame();
	}

	public void setGuiGame(ControllerGame guiGame){
		this.guiGame = guiGame;
		
	}
	
}
