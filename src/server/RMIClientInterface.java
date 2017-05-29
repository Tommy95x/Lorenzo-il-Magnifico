package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import javafx.scene.control.Tooltip;
import server.element.Giocatore;

public interface RMIClientInterface extends Remote{
	public void notifyStartGame();
	public void moveDisco(double x, double y, String colorPlayer, String colorDisco) throws RemoteException;
	public void moveFamiliareAvv(double x, double y, String colorPlayer, String colorDisco) throws RemoteException;
	public void richestaSostegnoChiesa() throws RemoteException;
	public void restTabellone() throws RemoteException;
	public void endGame(Giocatore[] giocatoriPartita) throws RemoteException;
	public void addScomunica(int nScomuniche, Tooltip tooltip) throws RemoteException;
	public void notifyTurno() throws RemoteException;
}
