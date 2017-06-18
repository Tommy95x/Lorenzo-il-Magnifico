package client;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

import client.gui.controllers.ControllerGame;
import server.element.CartaSviluppo;
import server.element.Dado;
import server.element.Partita;

/**
 * 
 * @author tommy
 *
 */
public interface ClientInterface {

	public String login(String account, String pw) throws ClassNotFoundException, IOException;
	public String register(String account, String pw, String pw2, String email) throws IOException, ClassNotFoundException;
	public boolean createANewLobby(String lobby, String color) throws IOException;
	public ArrayList<Partita> lobbiesView() throws IOException, ClassNotFoundException;
	public void selectColorGamer(String color) throws IOException;
	public void posizionareFamiliare(String color, double x, double y);
	public void spendereRisorse(String risorsa, int qta);
	public void startGame() throws IOException, ClassNotFoundException;
	public void sostegnoChiesa(boolean flag);
	public void richiestaRegistrazione() throws IOException;
	public void takeCards(String name);
	public int enterInALobby(String lobby, String string) throws IOException, ClassNotFoundException;
	public Dado[] lanciaDadi() throws SQLException, RemoteException, ClassNotFoundException, IOException;
	public String[] getColors(String lobby) throws RemoteException, IOException, ClassNotFoundException;
	public String controlloPosizionamento(String color, double x, double y, int integer) throws RemoteException, IOException, ClassNotFoundException, SQLException;
	public void setGuiGame(ControllerGame guiGame);
	public void notifySpostamento(String color, double x, double y) throws RemoteException, IOException;
	public String getNamePosition(double x, double y) throws RemoteException, SQLException, IOException, ClassNotFoundException;
	public void exitToTheGame(String lobby, String color) throws IOException;
	
}
