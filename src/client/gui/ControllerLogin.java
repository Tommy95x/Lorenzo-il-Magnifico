package client.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ControllerLogin {

	private StartClientGui start;
	
	@FXML
	Button loginbutton;
	@FXML
	TextField name;
	@FXML
	TextField pw;
	
	
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
	
}