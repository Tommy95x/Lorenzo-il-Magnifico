package client;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

import client.gui.StartClientGui;
import client.gui.controllers.ControllerGame;
import client.gui.controllers.ControllerWaitingRoom;
import javafx.application.Platform;
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

	public ConnectionRmiInterlocutorClient() throws RemoteException{}

	public void setName(String name){
		this.name = name;
	}
	
	public void notifyStartGame() throws RemoteException {
		System.out.println("Notifica inizio partita avvenuta");
		Platform.runLater(() -> {
			start.changeStage(5);
		});
		System.out.println("Prova");
	}

	public void moveFamiliareAvv(double x, double y, String colorPlayer, String colorFamiliare) throws RemoteException {
		Platform.runLater(() -> {
		guiGame.moveFamAvv(colorPlayer, colorFamiliare, x, y);
		});
	}


	public void addScomunica(int nScomuniche, String tooltip) throws RemoteException {
		Platform.runLater(() -> {
		guiGame.addScomunica(nScomuniche, new Tooltip(tooltip));
		});
	}

	@Override
	public void restTabellone() throws RemoteException {
		
	}

	@Override
	public void endGame(Giocatore[] giocatoriPartita) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	public void notifyAddCardAvv(CartaSviluppo carta, String string) throws RemoteException {
		Platform.runLater(() -> {
		guiGame.notifyAddCardAvv(carta, string);
		});
	}
	
	public void notifyTurno(int turno) throws RemoteException, SQLException {
		System.out.println("Notifico il turno al giocatore");
		Platform.runLater(()->{
			guiGame.enableGame(turno);
		});
		
	}

	public void setGuiGame(ControllerGame guiGame){
		Platform.runLater(() -> {
		this.guiGame = guiGame;
		});
	}

	@Override
	public void richestaSostegnoChiesa() throws RemoteException {
		// TODO Auto-generated method stub
		
	}


	public void notifySpostamentoPuntiMilitari(double x, double y, String color) {
		Platform.runLater(() ->{
		this.guiGame.notifySpostamentoPuntiMilitari(x,y,color);
		});
	}

	public void notifySpostamentoPuntiVittoria(double x, double y, String color) {
		Platform.runLater(() -> {
		this.guiGame.notifySpostamentoPuntiVittoria(x, y, color);
		});
	}

	
	public void notifySpostamentoPuntiFede(double x, double y, String color) {
		Platform.runLater(() -> {
		this.guiGame.notifySpostamentoPuntiFede(x, y, color);
		});
	}

	public void notifyPergamena(int i) throws RemoteException {
		Platform.runLater(() -> {
		this.guiGame.notifyPergamena(i);
		});
	}

	public void notifyTutteCarte(int i) throws RemoteException {
		Platform.runLater(() -> {
		this.guiGame.notifyTutteCarte(i);
		});
	}

	public int getPositionGame() {
		return positionGame;
	}

	public void setPositionGame(int positionGame) {
		Platform.runLater(() -> {
		this.positionGame = positionGame;
		});
	}

	public void notifyUnTipoCarta(int tipo, int qta, int scontoAzioneImmediata1) throws RemoteException {
		Platform.runLater(() -> {
		this.guiGame.notifyUnTipoCarta( tipo,  qta,  scontoAzioneImmediata1);
		});		
	}

	public void setStart(StartClientGui start2) {
		this.start = start2;
		
	}
	
}
