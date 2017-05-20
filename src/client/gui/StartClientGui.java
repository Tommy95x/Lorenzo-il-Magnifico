package client.gui;

import java.io.IOException;

import client.ConnectionClient;
import client.gui.controllers.ControllerConnection;
import client.gui.controllers.ControllerGame;
import client.gui.controllers.ControllerLogin;
import client.gui.controllers.ControllerRegister;
import client.gui.controllers.ControllerWaitingRoom;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartClientGui extends Application{

	private FXMLLoader loader;
	private Parent root;
	private Stage primaryStage;
	private ConnectionClient client;
	
	
	
	public StartClientGui(String[] args){
		launch(args);
		loader = new FXMLLoader();
	}
	
	@SuppressWarnings("static-access")
	public void start(Stage primaryStage) throws IOException {
		root = loader.load(this.getClass().getResource(""));
		primaryStage.setResizable(false);
		primaryStage.setTitle("Lorenzo il Magnifico Connection");
		//primaryStage.getIcons().add(e);
		ControllerConnection connection = loader.getController();
		connection.getStartClientGui(this);
		primaryStage.setScene(new Scene(root, 600, 400));
		primaryStage.show();
	}
	
	
	@SuppressWarnings("static-access")
	public void changeStage(int numberOfStage){
		
		switch(numberOfStage){
			case 1:
				try {
					root = loader.load(this.getClass().getResource(""));
					primaryStage.setTitle("Lorenzo il Magnifico Login");
					ControllerLogin login = loader.getController();
					login.getStartClientGui(this);
					primaryStage.setScene(new Scene(root,600,400));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 2:
				try {
					root = loader.load(this.getClass().getResource(""));
					primaryStage.setTitle("Lorenzo il Magnifico Register");
					ControllerRegister register = loader.getController();
					register.getStartClientGui(this);
					primaryStage.setScene(new Scene(root,600,400));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 3:
				try {
					root = loader.load(this.getClass().getResource(""));
					primaryStage.setResizable(true);
					primaryStage.setTitle("Lorenzo il Magnifico");
					ControllerWaitingRoom waitingRoom = loader.getController();
					waitingRoom.getStartClientGui(this);
					primaryStage.setScene(new Scene(root));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 4:
				try {
					root = loader.load(this.getClass().getResource(""));
					ControllerGame game = loader.getController();
					game.getStartClient(this);
					primaryStage.setScene(new Scene(root));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
		}
	}
	
	public void exit(){
		primaryStage.close();
		System.exit(0);
	}

	public void getClient(ConnectionClient client) {
		this.client=client;
	}

	public ConnectionClient getClient() {
		return client;
	}

	public void setClient(ConnectionClient client) {
		this.client = client;
	}
	
}
