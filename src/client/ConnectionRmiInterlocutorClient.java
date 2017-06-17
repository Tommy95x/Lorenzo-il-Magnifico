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

	public ConnectionRmiInterlocutorClient(String name) throws RemoteException{
		this.name = name;
	}

	public void notifyStartGame() throws RemoteException {
		System.out.println("Notifica inizio partita avvenuta");
		start.changeStage(5);
		System.out.println("Prova");
	}

	public void moveFamiliareAvv(double x, double y, String colorPlayer, String colorFamiliare) throws RemoteException {
		guiGame.moveFamAvv(colorPlayer, colorFamiliare, x, y);
		
	}


	public void addScomunica(int nScomuniche, Tooltip tooltip) throws RemoteException {
		guiGame.addScomunica(nScomuniche, tooltip);
		
	}

	@Override
	public void restTabellone() throws RemoteException {
		
	}

	@Override
	public void endGame(Giocatore[] giocatoriPartita) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	public void notifyAddCard(CartaSviluppo carta, String nameAvv,Portafoglio portafoglio) throws RemoteException {
		guiGame.notifyAddCardAvv(carta, nameAvv,portafoglio);
		
	}
	
	public void notifyTurno(int turno) throws RemoteException, SQLException {
		guiGame.enableGame( turno);
	}

	public void setGuiGame(ControllerGame guiGame){
		this.guiGame = guiGame;
	}

	public void setStart(StartClientGui start) {
		this.start = start;
		
	}

	@Override
	public void richestaSostegnoChiesa() throws RemoteException {
		// TODO Auto-generated method stub
		
	}


	public void notifySpostamentoPuntiMilitari(double x, double y, String color) {
		this.guiGame.notifySpostamentoPuntiMilitari(x,y,color);
		
	}

	public void notifySpostamentoPuntiVittoria(double x, double y, String color) {
		this.guiGame.notifySpostamentoPuntiVittoria(x, y, color);
		
	}

	
	public void notifySpostamentoPuntiFede(double x, double y, String color) {
		this.guiGame.notifySpostamentoPuntiFede(x, y, color);
		
	}

	public void notifyPergamena(int i) throws RemoteException {
		this.guiGame.notifyPergamena(i);
		
	}

	public void notifyTutteCarte(int i) throws RemoteException {
		this.guiGame.notifyTutteCarte(i);
		
	}

	public int getPositionGame() {
		return positionGame;
	}

	public void setPositionGame(int positionGame) {
		this.positionGame = positionGame;
	}

	@Override
	public void ciao() throws RemoteException {
		System.out.println("Ciao");
		
	}

	public void notifyUnTipoCarta(int tipo, int qta, int scontoAzioneImmediata1) throws RemoteException {
		this.guiGame.notifyUnTipoCarta( tipo,  qta,  scontoAzioneImmediata1);
		
	}
	
}
