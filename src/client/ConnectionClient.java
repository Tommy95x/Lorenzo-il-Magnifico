package client;

import java.util.ArrayList;

import client.gui.StartClientGui;
import server.element.Partita;

public class ConnectionClient implements ClientInterface{

	@Override
	public String login(String account, String pw) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String register(String account, String pw, String pw2, String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createANewLobby(String lobby) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Partita> lobbiesView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void enterInALobby(String lobby) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectColorGamer(String color) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void lanciaDadi() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void posizionareFamiliare(String color, int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void spendereRisorse(String risorsa, int qta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sostegnoChiesa(boolean flag) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void richiestaRegistrazione() {
		// TODO Auto-generated method stub
		
	}

	public void waitStartGame(StartClientGui start) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void takeCards(String name) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
