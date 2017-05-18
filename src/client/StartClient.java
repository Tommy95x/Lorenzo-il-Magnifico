package client;

import java.util.Scanner;

import client.gui.StartClientGui;


public class StartClient {
	
	private static ConnectionClient client;
	
	public static void main(String[] args) {
		System.out.println("Welcome to the login game");
		StartClientGui startgui=new StartClientGui(args);
		decision();
	}

	private static void decision(){
		Scanner in=new Scanner(System.in);
		System.out.println("Do you want to create a RMI connction or a Socket connection?");
		String decision=in.nextLine();
		if(decision.equals("RMI")||decision.equals("rmi")||decision.equals("Rmi"))
			client = new ConnectionRmiClient();
		else 
			client = new ConnectionSocketClient();
	}
	
	
}
