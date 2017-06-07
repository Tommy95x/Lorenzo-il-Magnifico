package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import client.gui.StartClientGui;
import client.gui.controllers.ControllerGame;
import javafx.scene.control.Tooltip;
import server.element.CartaSviluppo;
import server.element.Dado;
import server.element.Partita;
import server.element.Portafoglio;
import server.element.TesseraScomunica;

/*
 * Classe che implementa il socket
 */

public class ConnectionSocketClient extends ConnectionClient implements ClientInterface{

	private Socket socket;
	private ObjectInputStream inputSocket;
	private ObjectOutputStream outputSocket;
	private String ip="127.0.0.1";
	private int port=3000;
	private int positionGame;
	private int numberOfGamers;
	private String name;
	private ControllerGame guiGame;
	private StartClientGui start;
	private String lobby;
	
	public ConnectionSocketClient(){
		System.out.println("Start Socket Client");
		connect();
	}


	private void connect(){
		try {
			socket = new Socket(ip,port);
			System.out.println("Creato nuovo socket");
			//Creo i canali di comunicazione
			outputSocket = new ObjectOutputStream(socket.getOutputStream());
			outputSocket.flush();
			inputSocket = new ObjectInputStream(socket.getInputStream());
			System.out.println("Create a new connection");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String login(String account, String pw) throws ClassNotFoundException, IOException {
		System.out.println("Login");
		outputSocket.writeObject("login");
		outputSocket.flush();

		outputSocket.writeObject(account);
		outputSocket.flush();
		name=account;
		
		outputSocket.writeObject(pw);
		outputSocket.flush();
		
		System.out.println("Inviati i dati di login");
		return inputSocket.readObject().toString();
	}

	
	public void richiestaRegistrazione() throws IOException {
		outputSocket.writeObject("register");
		outputSocket.flush();
	}

	public String register(String account, String pw, String pw2, String email) throws IOException, ClassNotFoundException {
		outputSocket.writeObject(account);
		outputSocket.flush();
		outputSocket.writeObject(pw);
		outputSocket.flush();
		outputSocket.writeObject(pw2);
		outputSocket.flush();
		outputSocket.writeObject(email);
		outputSocket.flush();
		return inputSocket.readObject().toString();
				
	}

	public boolean createANewLobby(String lobby, String color) throws IOException {
		outputSocket.writeObject("create new lobby");
		outputSocket.flush();
		outputSocket.writeObject(lobby);
		outputSocket.flush();
		outputSocket.writeObject(color);
		outputSocket.flush();
		try {
			setPositionGame(((Integer) inputSocket.readObject()).intValue());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public ArrayList<Partita> lobbiesView() throws IOException {
		outputSocket.writeObject("get lobbies");
		outputSocket.flush();
		try {
			return (ArrayList)inputSocket.readObject();
		} catch (ClassNotFoundException e) {
			// Gestire le eccezioni
			e.printStackTrace();
		} catch (IOException e) {
			// Gestire le eccezioni
			e.printStackTrace();
		}
		return null;
	}

	public void enterInALobby(String lobby, String color) throws IOException {
		this.lobby = lobby;
		outputSocket.writeObject("enter in a lobby");
		outputSocket.flush();
		outputSocket.writeObject(lobby);
		outputSocket.flush();
		outputSocket.writeObject(color);
		outputSocket.flush();
		setPositionGame(inputSocket.readInt());
	}

	public TesseraScomunica[] getCardsScomunica() throws ClassNotFoundException, IOException{
		outputSocket.writeObject("getTessereScomunica");
		outputSocket.flush();
		return (TesseraScomunica[]) inputSocket.readObject();
	}
	
	public void startGame() throws IOException {
		outputSocket.writeObject("start");
		outputSocket.flush();
		outputSocket.writeObject(positionGame);
		outputSocket.flush();
		setNumberOfGamers(inputSocket.readInt());
	}

	public Dado[] lanciaDadi() throws ClassNotFoundException, IOException {
		outputSocket.writeObject("dices");
		outputSocket.flush();
		return (Dado[]) inputSocket.readObject();
	}
	
	public String[] getColors(String lobby) throws IOException {
		outputSocket.writeObject("getColors");
		outputSocket.flush();
		outputSocket.writeObject(lobby);
		outputSocket.flush();
		try {
			return (String[]) inputSocket.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getNumberOfGamers() {
		return numberOfGamers;
	}
	
	public void setNumberOfGamers(int numberOfGamers) {
		this.numberOfGamers = numberOfGamers;
	}
	
	public void selectColorGamer(String color) throws IOException {
		outputSocket.writeObject(color);
		outputSocket.flush();
	}

	public String controlloPosizionamento(String color, double x, double y, int agg) throws IOException, ClassNotFoundException{
		outputSocket.writeObject("controllo posizionamento");
		outputSocket.flush();
		outputSocket.writeObject(color);
		outputSocket.flush();
		outputSocket.writeObject(x);
		outputSocket.flush();
		outputSocket.writeObject(y);
		outputSocket.flush();
		outputSocket.writeObject(positionGame);
		outputSocket.flush();
		outputSocket.writeObject(name);
		outputSocket.flush();
		outputSocket.writeObject(agg);
		outputSocket.flush();
		return inputSocket.readObject().toString();
	}
	
	public void setGuiGame(ControllerGame guiGame){
		this.guiGame = guiGame;
	}

	public void notifyTurno() throws ClassNotFoundException, IOException {
		double x;
		double y;
		String colorPlayer; 
		String color;
		if(inputSocket.readObject().toString().equals("move")){
			switch(inputSocket.readObject().toString()){
				case "disco":
					x = inputSocket.readDouble();
					y = inputSocket.readDouble();
					colorPlayer = inputSocket.readObject().toString();
					color = inputSocket.readObject().toString();
					guiGame.movePunti(color, x, y);
					break;
				case "familiareAvv":
					x = inputSocket.readDouble();
					y = inputSocket.readDouble();
					colorPlayer = inputSocket.readObject().toString();
					color = inputSocket.readObject().toString();
					guiGame.moveFamAvv(colorPlayer, color, x, y);
					break;
				case "discoFede":
					x = inputSocket.readDouble();
					y = inputSocket.readDouble();
					colorPlayer = inputSocket.readObject().toString();
					color = inputSocket.readObject().toString();
					guiGame.movePuntiFede(color, x, y);
					break;
				case "startTurno":
					guiGame.enableGame();
			}		
		}
	}
	
	public void notifySpostamento(String color, double x, double y) throws IOException{
		outputSocket.writeObject("notifySpostamento");
		outputSocket.flush();
		outputSocket.writeObject(color);
		outputSocket.flush();
		outputSocket.writeObject(x);
		outputSocket.flush();
		outputSocket.writeObject(y);
		outputSocket.flush();
	}
	
	public String getNamePosition(double x, double y) throws IOException {
		outputSocket.writeObject("getNamePosition");
		outputSocket.flush();
		outputSocket.writeObject(x);
		outputSocket.flush();
		outputSocket.writeObject(y);
		return null;
	}
	
	public void exitToTheGame(String lobby, String color) throws IOException {
		outputSocket.writeObject("exitToTheGame");
		outputSocket.flush();
		outputSocket.writeObject(lobby);
		outputSocket.flush();
		outputSocket.writeObject(color);
		outputSocket.flush();
	}
	
	public ArrayList<CartaSviluppo> getCardsGamer() throws ClassNotFoundException, IOException{
		outputSocket.writeObject("getCardsGamer");
		outputSocket.flush();
		return (ArrayList<CartaSviluppo>) inputSocket.readObject();
		
	}
	
	public void setCardGiocatore(CartaSviluppo carta) throws IOException {
		outputSocket.writeObject("getCardsGamer");
		outputSocket.flush();
		outputSocket.writeObject(lobby);
		outputSocket.flush();
		outputSocket.writeObject(name);
		outputSocket.writeObject(carta);
		outputSocket.flush();
	}
	
	public ArrayList<CartaSviluppo> getCardsGame() throws ClassNotFoundException, IOException {
		outputSocket.writeObject("getCardsGame");
		outputSocket.flush();
		return (ArrayList<CartaSviluppo>) inputSocket.readObject();
	}
	
	public Portafoglio getRisorse() throws ClassNotFoundException, IOException{
		outputSocket.writeObject("getPortafoglio");
		outputSocket.flush();
		return (Portafoglio) inputSocket.readObject();
	}
	
	public int getPositionGame() {
		return positionGame;
	}


	public void waitStartGame(StartClientGui start) throws ClassNotFoundException, IOException {
		if(inputSocket.readObject().toString().equals("start"))
				start.changeStage(4);
	}
	
	public void setPositionGame(int positionGame) {
		this.positionGame = positionGame;
	}

	public void setStart(StartClientGui start) {
		this.start = start;
	}
	
	public StartClientGui getStart() {
		return start;
	}
	
	public void waitTurno() throws ClassNotFoundException, IOException {
		if(inputSocket.readObject().toString().equals("gioca"))
			guiGame.enableGame();
	}
	
	public void deleteView() {
		
		
	}
	
	@Override
	public void spendereRisorse(String risorsa, int qta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void takeCards(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sostegnoChiesa(boolean flag) {
		// TODO Auto-generated method stub
		
	}
	
	public void getCard(int positionGame, String name, CartaSviluppo carta) throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	
	public void addScomunica(int nScomuniche, Tooltip tooltip){
	}
}
