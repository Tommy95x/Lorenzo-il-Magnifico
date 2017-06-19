package client.gui.controllers;

import java.rmi.RemoteException;

import client.ConnectionClient;
import client.ConnectionClientConsole;
import client.ConnectionRmiClient;
import client.ConnectionSocketClient;
import client.gui.StartClientGui;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

public class ControllerConnection {

	private StartClientGui start;
	private ConnectionClient client;
	
	@FXML
	Button rmi = new Button();
	@FXML
	Button socket = new Button();
	@FXML
	MenuItem close = new MenuItem();
	@FXML
	MenuItem playWithConsole = new MenuItem();
	
	public void getStartClientGui(StartClientGui startClientGui) {
		this.setStart(startClientGui);
	}

	public StartClientGui getStart() {
		return start;
	}

	public void setStart(StartClientGui start) {
		this.start = start;
	}
	
	@FXML
	public void pressRMI(){
		System.out.println("Premuto bottone rmi");
		try {
			start.setClient(new ConnectionRmiClient());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		start.changeStage(1);
	}
	
	@FXML
	public void pressSocket(){ 
		System.out.println("Premuto bottone socket");
		start.changeStage(1);
		try {
			start.setClient(new ConnectionSocketClient());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void mouseInRMI(){
		rmi.setCursor(Cursor.HAND);
	}
	
	@FXML
	public void mouseOutRMI(){
		rmi.setCursor(Cursor.DEFAULT);
	}
	
	@FXML
	public void mouseInSocket(){
		socket.setCursor(Cursor.HAND);
	}
	
	@FXML
	public void mouseOutSocket(){
		socket.setCursor(Cursor.DEFAULT);
	}
	
	@FXML
	public void exit(){
		System.exit(0);
	}

	@FXML
	public void playWithConsole(){
		start.closeStageForPlayWithConsole();
		try {
			start.setClient(new ConnectionClientConsole());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ConnectionClient getClient() {
		return client;
	}

	public void setClient(ConnectionClient client) {
		this.client = client;
	}
}
