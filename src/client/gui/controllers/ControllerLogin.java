package client.gui.controllers;

import java.io.IOException;

import client.gui.StartClientGui;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;

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
		pw.setText("");
		name.setText("");
	}

	public StartClientGui getStart() {
		return start;
	}

	private void setStart(StartClientGui start) {
		this.start = start;
	}

	@FXML
	public void pressSingIn() throws ClassNotFoundException, IOException {
		if (pw.getText().equals("") || name.getText().equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(start.getStage());
			alert.setTitle("Invalid Login");
			alert.setContentText("Manca un campo obbligatorio per il Login");
			alert.showAndWait();
		} else {
			String mom = start.getClient().login(name.getText(), pw.getText());
			if (mom.equals("Welcome to the game"))
				start.changeStage(3);
			else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.initOwner(start.getStage());
				alert.setTitle("Invalid Login");
				alert.setContentText(mom);
				alert.showAndWait();
			}
		}
	}

	@FXML
	public void pressRegister() {
		start.changeStage(2);
	}

	@FXML
	public void inClickHere() {
		clickHere.setCursor(Cursor.HAND);
	}

	@FXML
	public void mouseIn() {
		singIn.setCursor(Cursor.HAND);
	}

}