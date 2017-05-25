package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import server.element.Dado;
import server.element.Giocatore;

public interface RMIClientInterface extends Remote{
	public void notifyStartGame();
	//public void moveDisco(int x, int y, /*Aggiungere elemento grafico utilizzato per il disco*/, String nameMove) throws RemoteException;
	//public void moveFamiliareAvv(int x, int y, String nameMove, /*Aggiungere elemento grafico che rappresenta il familiare*/) throws RemoteException;
	public void richestaSostegnoChiesa() throws RemoteException;
	public void restTabellone() throws RemoteException;
	public void endGame(Giocatore[] giocatoriPartita) throws RemoteException;
	public Dado[] showDiceValues(int positionGame, String name) throws RemoteException;
}
