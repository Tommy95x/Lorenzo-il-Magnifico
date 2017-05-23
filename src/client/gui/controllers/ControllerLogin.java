package client.gui.controllers;

import client.gui.StartClientGui;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ControllerLogin {

	private StartClientGui start;
	
	@FXML
	Button singIn;
	@FXML
	TextField name;
	@FXML
	PasswordField pw;
	@FXML
	Label clickHere;
	
	
	public void getStartClientGui(StartClientGui startClientGui) {
		setStart(startClientGui);
	}

	public StartClientGui getStart() {
		return start;
	}

	private void setStart(StartClientGui start) {
		this.start = start;
	}

	
	@FXML
	public void pressSingIn(){
		
		
		
	}
	
	@FXML
	public void pressRegister(){
		
	}
	
	@FXML
	public void inClickHere(){
		clickHere.setCursor(Cursor.HAND);
		//clickHere
	}
	
	@FXML
	public void outClickHere(){
		clickHere.setCursor(Cursor.DEFAULT);
	}
	
	@FXML
	public void mouseIn(){
		clickHere.setCursor(Cursor.HAND);
	}
	
	@FXML
	public void mouseOut(){
		clickHere.setCursor(Cursor.DEFAULT);
	}
	
}