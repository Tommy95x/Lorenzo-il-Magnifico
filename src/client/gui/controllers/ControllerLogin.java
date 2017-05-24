package client.gui.controllers;

import client.gui.StartClientGui;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

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
	@FXML
	Label login;
	
	
	public void getStartClientGui(StartClientGui startClientGui) {
		setStart(startClientGui);
		Font.loadFont(getClass().getResourceAsStream("Sketch Gothic School.ttf"), 50.0);
	}

	public StartClientGui getStart() {
		return start;
	}

	private void setStart(StartClientGui start) {
		this.start = start;
	}

	
	@FXML
	public void pressSingIn(){
		if(start.getClient().login(name.getText(), pw.getText()).equals("Welcome to the game"))
			start.changeStage(3);
		
	}
	
	@FXML
	public void pressRegister(){
		start.changeStage(2);
	}
	
	@FXML
	public void inClickHere(){
		clickHere.setCursor(Cursor.HAND);
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