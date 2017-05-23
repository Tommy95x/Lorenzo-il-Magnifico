package client.gui.controllers;

import client.ConnectionClient;
import client.ConnectionRmiClient;
import client.ConnectionSocketClient;
import client.gui.StartClientGui;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;

public class ControllerConnection {

	private StartClientGui start;
	private ConnectionClient client;
	
	@FXML
	Button rmi;
	@FXML
	Button socket;
	
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
		start.setClient(new ConnectionRmiClient());
		start.changeStage(1);
	}
	
	@FXML
	public void pressSocket(){ 
		System.out.println("Premuto bottone socket");
		start.changeStage(1);
		start.setClient(new ConnectionSocketClient());
		
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
}
