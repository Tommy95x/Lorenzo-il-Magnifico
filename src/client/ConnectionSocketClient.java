package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ConnectionSocketClient extends ConnectionClient{

	private Socket socket;
	private Scanner inputSocket;
	private PrintWriter outputSocket;
	private String ip="localhost";
	private int port=3000;
	
	public ConnectionSocketClient(){
		System.out.println("Start Socket Client");
		connect();
		
	}
	
	
	private void connect(){
		try {
			socket=new Socket(ip,port);
			//Creo i canali di comunicazione
			inputSocket = new Scanner(socket.getInputStream());
			outputSocket = new PrintWriter(socket.getOutputStream());
			System.out.println("Create a new connection");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
