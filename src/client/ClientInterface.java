package client;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import server.element.CartaSviluppo;
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
	public ArrayList<Partita> lobbiesView() throws IOException;
	public void selectColorGamer(String color) throws IOException;
	public void posizionareFamiliare(String color, int x, int y);
	public void spendereRisorse(String risorsa, int qta);
	public void startGame() throws IOException, ClassNotFoundException;
	public void sostegnoChiesa(boolean flag);
	public void richiestaRegistrazione() throws IOException;
	public void takeCards(String name);
	public void enterInALobby(String lobby, String string) throws IOException, ClassNotFoundException;
}
