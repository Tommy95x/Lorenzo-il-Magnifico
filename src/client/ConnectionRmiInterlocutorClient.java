package client;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

import client.gui.StartClientGui;
import client.gui.controllers.ControllerGame;
import javafx.scene.control.Tooltip;
import server.element.CartaSviluppo;
import server.element.Giocatore;
import server.element.Portafoglio;
import shared.RMIClientInterface;

public class ConnectionRmiInterlocutorClient  extends UnicastRemoteObject implements RMIClientInterface{
	
	private StartClientGui start;
	private ControllerGame guiGame;
	private int positionGame;
	private String name;

	public ConnectionRmiInterlocutorClient(String name, int positionGame) throws RemoteException{
		this.name = name;
		this.positionGame = positionGame;
	}
	
	public void notifyStartGame() throws RemoteException {
		System.out.println("Notifica inizio partita avvenuta");
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

	public void setStart(StartClientGui start) {
		this.start = start;
		
	}
	
}
