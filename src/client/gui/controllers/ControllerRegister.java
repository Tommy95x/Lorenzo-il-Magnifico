package client.gui.controllers;

import client.gui.StartClientGui;

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
	@FXML
	
	
	public void getStartClientGui(StartClientGui startClientGui) {
		
		this.setStart(startClientGui);
		
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
