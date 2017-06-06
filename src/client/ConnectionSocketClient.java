package client;

import java.io.IOException;
import java.io.ObjectInputStream;
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
	private Scanner inputSocket;
	private ObjectInputStream inputSocketObject;
	private PrintWriter outputSocket;
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
			inputSocket = new Scanner(socket.getInputStream());
			outputSocket = new PrintWriter(socket.getOutputStream());
			inputSocketObject = new ObjectInputStream(socket.getInputStream());
			System.out.println("Create a new connection");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String login(String account, String pw) {
		System.out.println("Login");
		outputSocket.println("login");
		outputSocket.flush();

		outputSocket.println(account);
		outputSocket.flush();
		name=account;
		
		outputSocket.println(pw);
		outputSocket.flush();
		
		System.out.println("Inviati i dati di login");
		return inputSocket.nextLine();
	}

	
	public void richiestaRegistrazione() {
		outputSocket.println("register");
		outputSocket.flush();
	}

	public String register(String account, String pw, String pw2, String email) {
		outputSocket.println(account);
		outputSocket.flush();
		outputSocket.println(pw);
		outputSocket.flush();
		outputSocket.println(pw2);
		outputSocket.flush();
		outputSocket.println(email);
		outputSocket.flush();
		return inputSocket.nextLine();
				
	}

	public boolean createANewLobby(String lobby, String color) {
		outputSocket.println("create new lobby");
		outputSocket.flush();
		outputSocket.println(lobby);
		outputSocket.flush();
		outputSocket.println(color);
		outputSocket.flush();
		setPositionGame(inputSocket.nextInt());
		return true;
	}

	public ArrayList<Partita> lobbiesView() {
		outputSocket.println("get lobbies");
		outputSocket.flush();
		try {
			return (ArrayList)inputSocketObject.readObject();
		} catch (ClassNotFoundException e) {
			// Gestire le eccezioni
			e.printStackTrace();
		} catch (IOException e) {
			// Gestire le eccezioni
			e.printStackTrace();
		}
		return null;
	}

	public void enterInALobby(String lobby, String color) {
		this.lobby = lobby;
		outputSocket.println("enter in a lobby");
		outputSocket.flush();
		outputSocket.println(lobby);
		outputSocket.flush();
		outputSocket.print(color);
		outputSocket.flush();
		setPositionGame(Integer.parseInt( inputSocket.nextLine()));
	}

	public TesseraScomunica[] getCardsScomunica() throws ClassNotFoundException, IOException{
		outputSocket.println("getTessereScomunica");
		outputSocket.flush();
		return (TesseraScomunica[]) inputSocketObject.readObject();
	}
	
	public void startGame() {
		outputSocket.println("start");
		outputSocket.flush();
		outputSocket.print(positionGame);
		outputSocket.flush();
		setNumberOfGamers(inputSocket.nextInt());
	}

	public Dado[] lanciaDadi() throws ClassNotFoundException, IOException {
		outputSocket.println("dices");
		outputSocket.flush();
		return (Dado[]) inputSocketObject.readObject();
	}
	
	public String[] getColors(String lobby) throws RemoteException {
		outputSocket.println("getColors");
		outputSocket.flush();
		outputSocket.print(lobby);
		outputSocket.flush();
		try {
			return (String[]) inputSocketObject.readObject();
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
	
	public void selectColorGamer(String color) {
		outputSocket.println(color);
		outputSocket.flush();
	}

	public String controlloPosizionamento(String color, double x, double y, int agg){
		outputSocket.println("controllo posizionamento");
		outputSocket.flush();
		outputSocket.print(color);
		outputSocket.flush();
		outputSocket.println(x);
		outputSocket.flush();
		outputSocket.println(y);
		outputSocket.flush();
		outputSocket.println(positionGame);
		outputSocket.flush();
		outputSocket.println(name);
		outputSocket.flush();
		outputSocket.println(agg);
		outputSocket.flush();
		return inputSocket.nextLine();
	}
	
	public void setGuiGame(ControllerGame guiGame){
		this.guiGame = guiGame;
	}

	public void notifyTurno() {
		double x;
		double y;
		String colorPlayer; 
		String color;
		if(inputSocket.nextLine().equals("move")){
			switch(inputSocket.nextLine()){
				case "disco":
					x = inputSocket.nextDouble();
					y = inputSocket.nextDouble();
					colorPlayer = inputSocket.nextLine();
					color = inputSocket.nextLine();
					guiGame.movePunti(color, x, y);
					break;
				case "familiareAvv":
					x = inputSocket.nextDouble();
					y = inputSocket.nextDouble();
					colorPlayer = inputSocket.nextLine();
					color = inputSocket.nextLine();
					guiGame.moveFamAvv(colorPlayer, color, x, y);
					break;
				case "discoFede":
					x = inputSocket.nextDouble();
					y = inputSocket.nextDouble();
					colorPlayer = inputSocket.nextLine();
					color = inputSocket.nextLine();
					guiGame.movePuntiFede(color, x, y);
					break;
				case "startTurno":
					guiGame.enableGame();
			}		
		}
	}
	
	public void notifySpostamento(String color, double x, double y){
		outputSocket.println("notifySpostamento");
		outputSocket.flush();
		outputSocket.println(color);
		outputSocket.flush();
		outputSocket.println(x);
		outputSocket.flush();
		outputSocket.println(y);
		outputSocket.flush();
	}
	
	public String getNamePosition(double x, double y) throws RemoteException {
		outputSocket.println("getNamePosition");
		outputSocket.flush();
		outputSocket.println(x);
		outputSocket.println(y);
		return null;
	}
	
	public void exitToTheGame(String lobby, String color) {
		outputSocket.println("exitToTheGame");
		outputSocket.flush();
		outputSocket.print(lobby);
		outputSocket.flush();
		outputSocket.println(color);
		outputSocket.flush();
	}
	
	public ArrayList<CartaSviluppo> getCardsGamer() throws ClassNotFoundException, IOException{
		outputSocket.println("getCardsGamer");
		outputSocket.flush();
		return (ArrayList<CartaSviluppo>) inputSocketObject.readObject();
		
	}
	
	public void setCardGiocatore(CartaSviluppo carta) {
		outputSocket.println("getCardsGamer");
		outputSocket.flush();
		outputSocket.println(lobby);
		outputSocket.flush();
		outputSocket.println(name);
		outputSocket.println(carta);
		outputSocket.flush();
	}
	
	public ArrayList<CartaSviluppo> getCardsGame() throws ClassNotFoundException, IOException {
		outputSocket.println("getCardsGame");
		outputSocket.flush();
		return (ArrayList<CartaSviluppo>) inputSocketObject.readObject();
	}
	
	public Portafoglio getRisorse() throws ClassNotFoundException, IOException{
		outputSocket.println("getPortafoglio");
		outputSocket.flush();
		return (Portafoglio) inputSocketObject.readObject();
	}
	
	public int getPositionGame() {
		return positionGame;
	}


	public void waitStartGame(StartClientGui start) {
		if(inputSocket.nextLine().equals("start"))
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
	
	public void waitTurno() {
		if(inputSocket.nextLine().equals("gioca"))
			guiGame.enableGame();
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
