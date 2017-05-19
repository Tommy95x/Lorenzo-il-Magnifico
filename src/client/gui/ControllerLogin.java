package client.gui;

import javafx.stage.Stage;

public class ControllerLogin {

	private Stage stage;
	private StartClientGui start;
	
	public void getStartClientGui(StartClientGui startClientGui, Stage primaryStage) {
		setStage(primaryStage);
		setStart(startClientGui);
	}

	public Stage getStage() {
		return stage;
	}

	private void setStage(Stage stage) {
		this.stage = stage;
	}

	public StartClientGui getStart() {
		return start;
	}

	private void setStart(StartClientGui start) {
		this.start = start;
	}

}
