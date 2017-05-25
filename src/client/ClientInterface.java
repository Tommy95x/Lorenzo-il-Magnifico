package client;

import java.util.ArrayList;

import server.element.Partita;

public interface ClientInterface {

	public String login(String account, String pw);
	public String register(String account, String pw, String pw2, String email);
	public boolean createANewLobby(String lobby);
	public ArrayList<Partita> lobbiesView();
	public void enterInALobby(String lobby);
	public void selectColorGamer(String color);
	public void lanciaDadi();
	public void posizionareFamiliare(String color, int x, int y);
	public void spendereRisorse(String risorsa, int qta);
	public void startGame();
	public void sostegnoChiesa(boolean flag);
	public void richiestaRegistrazione();
	public void takeCards(String name);
	
}
