package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/*
*Classe che comunica con un SocketClient che ha istanziato e creato una nuva connessione inprecedenza con il ServerSocket, la classe
*di conseguenza implementa l'interfaccia Runnable che verra' eseguito da un Executor istanziato in precedenza alla creazione di una 
*connessione da parte di un client.
**/

public class ThreadSocketServer implements Runnable {

	private StartServer commonServer;
	private Socket socket;
	private Scanner input;
	private PrintWriter output;
	private String account;
	private String pw;
	private String action;
	private String email;
	private String lobby;
	private int positionGame;
	
	
	public ThreadSocketServer(Socket executorSocket, StartServer commonServer) {
		this.commonServer=commonServer;
		this.socket=executorSocket;
	}

	
	public void closeSocket(){
		output.println("Gioco finito");
		//Bisognerà scrivere la classifica della partita
		input.close();
		output.close();
	}
	
	public void play(Scanner input, PrintWriter output) {
		// TODO Auto-generated method stub
	}
	
	private void sendLobbies(PrintWriter output){
		for(int i=0;i<commonServer.getDimLobbies();i++){
			output.println(commonServer.getNameLobby(i));
		}
	}
	
	private void listOfLobbies(PrintWriter output){
		for(int i=0;i<commonServer.getDimLobbies();i++){
			output.println(commonServer.getNameLobby(i));
		}
	}
	
	@Override
	public void run() {
		try {
			input = new Scanner(socket.getInputStream());
			output = new PrintWriter(socket.getOutputStream());
			action=input.nextLine();
			while(true){
				switch(action){
					case "login":
						account=input.nextLine();
						pw=input.nextLine();
						output.println(commonServer.addClient(account, pw));
						break;
					case "register":
						account=input.nextLine();
						pw=input.nextLine();
						email = input.nextLine();
						commonServer.registerNewClient(account,pw,email);
						break;
					case "create new lobby":
						lobby=input.nextLine();
						commonServer.addGame(lobby,account);
						break;
					case "enter in a lobby":
<<<<<<< HEAD
						sendLobbies(output);
=======
						listOfLobbies(output);
>>>>>>> branch 'develop' of https://github.com/Tommy95x/Lorenzo-il-Magnifico.git
						break;
					case "select a game":
						lobby=input.nextLine();
						positionGame=commonServer.getIndicePartita(lobby);
						String color=input.nextLine();
						commonServer.addGamer(positionGame,color,account);
						break;
					case "play":
						play(input,output);
						break;
				}
			}
		} catch (IOException e) {
			System.err.println("Error lost socket connection");
			output.println("Error lost socket connection");
			output.close();
			input.close();
			e.printStackTrace();
		}	
	}
	
}
