package client;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

import client.gui.StartClientGui;
import client.gui.controllers.ControllerGame;
import server.element.CartaSviluppo;
import server.element.Dado;
import server.element.Giocatore;
import server.element.Partita;
import server.element.Portafoglio;
import server.element.TesseraScomunica;

public class ConnectionClient implements ClientInterface {

	
	@Override
	public String login(String account, String pw) throws ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String register(String account, String pw, String pw2, String email) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createANewLobby(String lobby, String color) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Partita> lobbiesView() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void enterInALobby(String lobby, String color) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectColorGamer(String color) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startGame() throws IOException {
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
	public void richiestaRegistrazione() throws IOException {
		// TODO Auto-generated method stub
		
	}

	public void waitStartGame(StartClientGui start) throws ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void takeCards(String name) {
		// TODO Auto-generated method stub
		
	}

	public Dado[] lanciaDadi() throws SQLException, RemoteException, ClassNotFoundException, IOException {
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

	public void notifySpostamento(String color, double x, double y) throws RemoteException, IOException {
		// TODO Auto-generated method stub
		
	}

	public String getNamePosition(double x, double y) throws RemoteException, SQLException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public void exitToTheGame(String lobby, String color) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public void waitTurno() throws ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<CartaSviluppo> getCardsGamer() throws ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setCardGiocatore(CartaSviluppo carta) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<CartaSviluppo> getCardsGame() throws ClassNotFoundException, IOException{
		// TODO Auto-generated method stub
		return null;
	}

	public Giocatore[] getGiocatori() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public TesseraScomunica[] getCardsScomunica() throws RemoteException, ClassNotFoundException, IOException {
		return null;
	}

	public Portafoglio getRisorse() throws RemoteException, ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteView() throws RemoteException {
		// TODO Auto-generated method stub
		
	}
}
