package client.gui.controllers;

import java.io.IOException;

import client.gui.StartClientGui;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
	public void registerConfirm() throws IOException, ClassNotFoundException {
		if (username.getText().equals("") || pw1.getText().equals("") || pw2.getText().equals("")
				|| email.getText().equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(start.getStage());
			alert.setTitle("Invalid Registered");
			alert.setContentText("Manca un campo obbligatorio per la registrazione");
			alert.showAndWait();
		} else if (!email.getText().contains("@") && !email.getText().contains(".com") || !email.getText().contains(".it")){
			//Chiedere a Campi
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(start.getStage());
			alert.setTitle("Invalid Registered");
			alert.setContentText("Email non valida");
			alert.showAndWait();
		} else {
			start.getClient().richiestaRegistrazione();
			String mom = start.getClient().register(username.getText(), pw1.getText(), pw2.getText(), email.getText());
			System.out.println(mom);
			if(mom.equals("You are now registered!"))
				start.changeStage(3);
			else{
				Alert alert = new Alert(AlertType.WARNING);
				alert.initOwner(start.getStage());
				alert.setTitle("Invalid Registered");
				alert.setContentText(mom);
				alert.showAndWait();
			}
		}
	}

}
