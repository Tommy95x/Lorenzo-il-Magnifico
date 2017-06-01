package client;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

import client.gui.StartClientGui;
import client.gui.controllers.ControllerGame;
import server.element.CartaSviluppo;
import server.element.CartaTerritori;
import server.element.Dado;
import server.element.Partita;

public class ConnectionClient implements ClientInterface {

	
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
	public boolean createANewLobby(String lobby, String color) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Partita> lobbiesView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void enterInALobby(String lobby, String color) {
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

	public Dado[] lanciaDadi(int positionGame, String name) throws SQLException, RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] getColors() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	public String controlloPosizionamento(String color, double x, double y, Integer integer) throws RemoteException {
		return color;
	}
	
	public void setGuiGame(ControllerGame guiGame){
	}

	public void setStage(StartClientGui start) {
		// TODO Auto-generated method stub
		
	}

	public void notifySpostamento(String color, double x, double y) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	public String getNamePosition(double x, double y) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public void exitToTheGame(String lobby, String color) {
		// TODO Auto-generated method stub
		
	}

	public void waitTurno() {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<CartaSviluppo> getCardsGamer() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setCardGiocatore(CartaSviluppo carta) {
		// TODO Auto-generated method stub
		
	}
}
