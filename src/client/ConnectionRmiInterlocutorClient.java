package client;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

import client.gui.StartClientGui;
import client.gui.controllers.ControllerGame;
import javafx.application.Platform;
import javafx.scene.control.Tooltip;
import server.element.CartaSviluppo;
import server.element.Giocatore;
import shared.RMIClientInterface;

public class ConnectionRmiInterlocutorClient  extends UnicastRemoteObject implements RMIClientInterface{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private StartClientGui start;
	private ControllerGame guiGame;
	private int positionGame;
	@SuppressWarnings("unused")
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


	public void addScomunica(int nScomuniche, String name) throws RemoteException {
		Platform.runLater(() -> {
		guiGame.addScomunica(nScomuniche, name);
		});
	}

	@Override
	public void restTabellone() throws RemoteException {
		Platform.runLater(() -> {
			try {
				guiGame.resetTabellon();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	@Override
	public void endGame(Giocatore[] giocatoriPartita) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	public void notifyAddCardAvv(String string,String tipo, int piano) throws RemoteException {
		Platform.runLater(() ->{
		System.out.println("Notifica carta Avv clientRMI");
		guiGame.notifyAddCardAvv(string, tipo, piano);
		});
	}
	
	public void notifyTurno(int turno) throws RemoteException, SQLException {
		System.out.println("Notifico il turno al giocatore");
		Platform.runLater(() -> {
		guiGame.enableGame(turno);
		});
	}

	public void setGuiGame(ControllerGame guiGame){
		this.guiGame = guiGame;
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
		System.out.println("Classe di comunicazione RMI");
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

	public void notifyAddRisorse(String name, String tipo, int qta) throws RemoteException {
		Platform.runLater(() -> {
			this.guiGame.notifyAddRisorse(name, tipo, qta);
		});
		
	}

	public void notifyAskSostegnoChiesa() throws RemoteException {
		Platform.runLater(() -> {
			this.guiGame.notifyAskSostegnoChiesa();
		});
	}
	
}
