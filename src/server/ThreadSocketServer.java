package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;
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
	private String pw2;
	private String action;
	private String email;
	private String lobby;
	private String color;
	private int positionGame;
	private ImplementServerInterface actionsServer;
	
	public ThreadSocketServer(Socket executorSocket, StartServer commonServer) {
		this.commonServer=commonServer;
		this.socket=executorSocket;
		try {
			actionsServer=new ImplementServerInterface(commonServer);
		} catch (RemoteException e) {
			System.out.println("Error not create a new ImplementInterface");
			e.printStackTrace();
		} 
	}

	
	public void closeSocket(){
		output.println("Gioco finito");
		input.close();
		output.close();
	}
	
	private void outArray(String[] colors, PrintWriter output2) {
		for(String s:colors)
			output.println(s);
	}
	
	private void closeAGamer(){
		actionsServer.adviseOtherGamers(account,positionGame);
		
	}
	
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
						output.println(actionsServer.login(account, pw));
						output.flush();
						break;
					case "register":
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
						actionsServer.createNewLobby(lobby, account);
						break;
					case "get lobbies":
						output.println(actionsServer.getLobby());
						break;
					case "enter in a lobby":
						lobby=input.nextLine();
						//account=input.nextLine();
						outArray(actionsServer.getColors(lobby),output);
						color=input.nextLine();
						actionsServer.selectLobby(lobby, account, color);
						break;
					case "start":
						//account=input.nextLine();
						positionGame=input.nextInt();	
						actionsServer.startPartita(account, positionGame);
						break;
					case "play":
						break;
					case "quit":
						closeSocket();
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
