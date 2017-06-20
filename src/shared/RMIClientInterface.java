package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

import server.element.CartaSviluppo;
import server.element.Giocatore;

public interface RMIClientInterface extends Remote{
	public void notifyStartGame() throws RemoteException;
	public void moveFamiliareAvv(double x, double y, String colorPlayer, String colorDisco) throws RemoteException;
	public void richestaSostegnoChiesa() throws RemoteException;
	public void restTabellone() throws RemoteException;
	public void endGame(Giocatore[] giocatoriPartita) throws RemoteException;
	public void addScomunica(int nScomuniche, String name) throws RemoteException;
	public void notifyTurno(int turno) throws RemoteException, SQLException;
	public void notifyAddCardAvv(CartaSviluppo carta, String string, String tipo)throws RemoteException;
	public void notifySpostamentoPuntiMilitari(double x, double y, String string)throws RemoteException;
	public void notifySpostamentoPuntiVittoria(double x, double y, String color2)throws RemoteException;
	public void notifySpostamentoPuntiFede(double x, double y, String color2)throws RemoteException;
	public void notifyPergamena(int i)throws RemoteException;
	public void notifyTutteCarte(int i)throws RemoteException;
	public void notifyUnTipoCarta(int tipo, int qta, int scontoAzioneImmediata1)throws RemoteException;
	public void notifyAddRisorse(String name, String tipo, int qta)throws RemoteException;
	public void notifyAskSostegnoChiesa()throws RemoteException;
}
