package client.gui.controllers;

import client.ConnectionClient;
import client.ConnectionRmiClient;
import client.ConnectionSocketClient;
import client.gui.StartClientGui;
import javafx.fxml.FXML;
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
		client = new ConnectionRmiClient();
		start.getClient(client);
		start.changeStage(1);
	}
	
	@FXML
	public void pressSocket(){
		client = new ConnectionSocketClient();
		start.getClient(client);
		start.changeStage(1);
	}
	
}
