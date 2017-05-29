package client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

import javafx.scene.control.Tooltip;
import server.element.Giocatore;

public interface RMIClientInterface extends Remote{
	public void notifyStartGame() throws RemoteException;
	public void moveDisco(double x, double y, String colorPlayer, String colorDisco) throws RemoteException;
	public void moveDiscoFede(double x, double y, String colorPlayer, String colorDisco) throws RemoteException;
	public void moveFamiliareAvv(double x, double y, String colorPlayer, String colorDisco) throws RemoteException;
	public void richestaSostegnoChiesa() throws RemoteException;
	public void restTabellone() throws RemoteException;
	public void endGame(Giocatore[] giocatoriPartita) throws RemoteException;
	public void addScomunica(int nScomuniche, Tooltip tooltip) throws RemoteException;
	public void notifyTurno() throws RemoteException, SQLException;
}
