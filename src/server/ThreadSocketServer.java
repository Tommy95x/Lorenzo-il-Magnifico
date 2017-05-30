package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.control.Tooltip;
import server.element.CartaSviluppo;

/*
*Classe che comunica con un SocketClient che ha istanziato e creato una nuva connessione inprecedenza con il ServerSocket, la classe
*di conseguenza implementa l'interfaccia Runnable che verra' eseguito da un Executor istanziato in precedenza alla creazione di una 
*connessione da parte di un client.
**/
public class ThreadSocketServer implements Runnable{

	private StartServer commonServer;
	private Socket socket;
	private Scanner input;
	private PrintWriter output;
	private String account;
	private String pw;
	private String pw2;
	private String action;
	private String email;
	private String lobby;
	private String color;
	private int positionGame;
	private ImplementServerInterface actionsServer;
	private int x;
	private int y;
	
	public ThreadSocketServer(Socket executorSocket, StartServer commonServer) {
		this.commonServer=commonServer;
		this.socket=executorSocket;
		try {
			actionsServer=new ImplementServerInterface(commonServer);
		} catch (RemoteException e) {
			System.out.println("Error not create a new ImplementInterface");
			e.printStackTrace();
		}
		System.out.println("Creato nuovo executor di gestione client");
	}

	
	public void closeSocket(){
		output.println("Gioco finito");
		input.close();
		output.close();
	}
	
	private void outArray(String[] colors, PrintWriter output2) {
		for(String s:colors){
			output.println(s);
			output.flush();
		}
			
	}
	
	/*private void closeAGamer(){
		try {
			actionsServer.adviseOtherGamers(account,positionGame);
		} catch (Exception e) {
			// Pensare per la gestione dell'eccezione
			e.printStackTrace();
		}
		
	}*/
	
	private void play(PrintWriter output, Scanner input) {
		action=input.nextLine();
		for(int i=0;i<4;i++){
				color=input.nextLine();
				x=input.nextInt();
				y=input.nextInt();
				try {
					actionsServer.mossa(account, positionGame, color, x, y);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			action=input.nextLine();
	}

	
	public void run() {
		try {
			input = new Scanner(socket.getInputStream());
			output = new PrintWriter(socket.getOutputStream());
			while(true){
				action=input.nextLine();
				System.out.println(action);
				switch(action){
					case "login":
						System.out.println("Richiesto login");
						account=input.nextLine();
						pw=input.nextLine();
						output.println(actionsServer.login(account, pw));
						output.flush();
						break;
					case "register":
						System.out.println("Richiesta registrazione");
						account=input.nextLine();
						pw=input.nextLine();
						pw2=input.nextLine();
						email = input.nextLine();
						output.println(actionsServer.register(account, pw, pw2,email));
						output.flush();
						break;
					case "create new lobby":
						lobby=input.nextLine();
						//account=input.nextLine();
						color = input.nextLine();
						output.println(actionsServer.createNewLobby(lobby, account,color,null));
						commonServer.getLobbyByName(lobby).getGiocatoreByName(account).getSocket(this);
						break;
					case "get lobbies":
						output.println(actionsServer.getLobby());
						break;
					case "enter in a lobby":
						lobby=input.nextLine();
						//account=input.nextLine();
						positionGame=commonServer.getIndicePartita(lobby);
						output.println(positionGame);
						output.flush();
						output.println(actionsServer.getColors(lobby));
						output.flush();
						color=input.nextLine();
						actionsServer.selectLobby(lobby, account, color,null);
						commonServer.getLobbyByName(lobby).getGiocatoreByName(account).getSocket(this);
						break;
					case "start":
						//account=input.nextLine();
						positionGame=input.nextInt();	
						output.println(actionsServer.startPartita(account, positionGame));
						output.flush();
						break;
					case "play":
						play(output,input);
						break;
					/*case "dices":
						output.println(actionsServer.showDiceValues(positionGame, account));
						output.flush();
						break;*/
					case "mossa familiare":
						//Discutere con Mattia per vedere come implementare il tutto
						break;
					case "quit":
						closeSocket();
						break;
				}
			}
		} catch (IOException | SQLException e) {
			System.err.println("Error lost socket connection");
			output.println("Error lost socket connection");
			output.close();
			input.close();
			e.printStackTrace();
		}	
	}


	private void getCards(PrintWriter output) {
		ArrayList<CartaSviluppo> mom = null;
		try {
			mom = actionsServer.getCards(positionGame);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(CartaSviluppo c:mom){
			output.println(c.getNameCard());
			output.flush();
			output.println(c.getImage());
			output.flush();
		}
		output.println("endCards");
	}


	public void notifyStartGame() {
		
		//Una volta ricevuto la notifica l'utente richiede le carte
	}


	public void playGamer() {
		// TODO Auto-generated method stub
		
	}


	public void addScomunica(int nScomuniche, Tooltip tooltip) {
		// TODO Auto-generated method stub
		
	}


	public void notifyTurno() {
		// TODO Auto-generated method stub
		
	}


	public void moveFamiliareAvv(double x2, double y2, String colorPlayer, String color2) {
		// TODO Auto-generated method stub
		
	}	
}
