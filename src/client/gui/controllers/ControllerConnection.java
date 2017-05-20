package client.gui.controllers;

import client.ConnectionClient;
import client.ConnectionRmiClient;
import client.ConnectionSocketClient;
import client.gui.StartClientGui;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class ControllerConnection {

	private StartClientGui start;
	
	@FXML
	CheckBox rmi;
	@FXML
	CheckBox socket;
	@FXML
	Button confirm;
	
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
	public void pressConfirm(){
		ConnectionClient client;
		if(rmi.isSelected()){
			client = new ConnectionRmiClient();
		}else{
			client = new ConnectionSocketClient();
		}
		start.getClient(client);
		start.changeStage(1);
	}
	
	
}
