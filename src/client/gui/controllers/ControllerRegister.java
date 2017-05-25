package client.gui.controllers;

import client.gui.StartClientGui;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class ControllerRegister {

	private StartClientGui start;
	
	@FXML
	public Button register;
	@FXML
	public TextField username;
	@FXML
	public PasswordField pw1;
	@FXML
	public PasswordField pw2;
	@FXML
	public TextField email;
	
	public void getStartClientGui(StartClientGui startClientGui) {
		
		this.setStart(startClientGui);
		Font.loadFont(getClass().getResourceAsStream("Sketch Gothic School.ttf"), 50.0);
		
	}

	public StartClientGui getStart() {
		return start;
	}

	private void setStart(StartClientGui start) {
		this.start = start;
	}
	
	@FXML
	public void registerConfirm(){
		
	}
	
}
