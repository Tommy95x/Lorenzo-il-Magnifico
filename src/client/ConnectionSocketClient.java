package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.image.Image;
import server.element.Dado;
import server.element.Partita;

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
			//inputSocketObject = new ObjectInputStream(socket.getInputStream());
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
		outputSocket.println("login");
		outputSocket.flush();

		outputSocket.println(account);
		outputSocket.flush();
		outputSocket.println(pw);
		outputSocket.flush();
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

	public boolean createANewLobby(String lobby) {
		outputSocket.println(lobby);
		outputSocket.flush();
		positionGame=inputSocket.nextInt();
		selectColorGamer(/*Metodo grafico che restituisce il colore scelto dal giocatore*/);
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

	public void enterInALobby(String lobby) {
		outputSocket.println("enter in a lobby");
		outputSocket.flush();
		outputSocket.println(lobby);
		outputSocket.flush();
		positionGame=inputSocket.nextInt();
		/*Chiamata al metodo grafico che stampa tutti i colori che deve essere messo all'interno del metodo sottostante*/inputSocketObject.readObject();
		selectColorGamer(/*Metodo grafico che restituisce il colore scelto dal giocatore*/);
		//L'unica cosa che manca è il metodo per il cambio di stage
	}

	public void selectColorGamer(String color) {
		outputSocket.println(color);
		outputSocket.flush();
	}


	@Override
	public void startGame() {
		outputSocket.println("start");
		outputSocket.flush();
		
		//Richiamo al metodo grafico per iniziare a comporre il tabellone e a settare il numero corretto di plance, nel metodo metto la riga sotto che rappresenta il numero dei gicatori
		numberOfGamers=inputSocket.nextInt();
		String mom=inputSocket.nextLine();
		while(!mom.equals("endCards")){
			try {
				Image im=(Image) inputSocketObject.readObject();
			} catch (ClassNotFoundException e) {
				//Gestire eccezione
			} catch (IOException e) {
			}
			//L'oggetto immagine ricevuto sopra, insieme al nome della carta, verrano inserite nel metodo che posiziona graficamente la carta
			mom=inputSocket.nextLine();
		}
	}


	@Override
	public void lanciaDadi() {
		outputSocket.println("dices");
		outputSocket.flush();
		//chiamata al metodo grafico che mi ritorna i dadi lanciati
		try {
			Dado[] momd = (Dado[]) inputSocketObject.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}


	@Override
	public void posizionareFamiliare(String color, int x, int y) {
		outputSocket.println("mossa familiare");
		outputSocket.flush();
		//Dovr� rinviare indietro le modifiche da apportare
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
	
}
