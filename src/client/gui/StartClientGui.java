package client.gui;

import java.io.IOException;
import client.ConnectionClient;
import client.gui.controllers.ControllerConnection;
import client.gui.controllers.ControllerGame;
import client.gui.controllers.ControllerLogin;
import client.gui.controllers.ControllerMenu;
import client.gui.controllers.ControllerRegister;
import client.gui.controllers.ControllerWaitingRoom;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * @author Tommy
 *
 *Claase che viene avviata direttamente dall'utente all'avvio del gioco, main dell'applicazione lato client inizialmente di pura grafica successivamente diventa anche di logica nel momento in cui l'utente decider�
 *in quale modalit� giocare. Le modalit� disponibili sono mediante una grafica con una connessione RMI o Socket, o da Console con una sola connessione RMI.
 */
public class StartClientGui extends Application{

	private FXMLLoader loader = new FXMLLoader(); ;
	private Parent root;
	private Stage primaryStage;
	private ConnectionClient client;
	private String color;
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	public void start(Stage primaryStage) throws IOException {
		
		this.primaryStage = primaryStage;
		
		loader.setLocation(StartClientGui.class.getResource("controllers/ConnectionGui.fxml"));
		root = loader.load();
		primaryStage.setResizable(false);
		primaryStage.setTitle("Lorenzo il Magnifico Connection");
		primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("logo.png")));
		ControllerConnection connection = loader.getController();
		connection.getStartClientGui(this);
		
		root.setId("pane");
		Scene scene = new Scene(root, 600, 400);
		scene.getStylesheets().addAll(this.getClass().getResource("controllers/pane.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}


	public void changeStage(int numberOfStage){
		
		switch(numberOfStage){
			case 1:
				try {
					loader = new FXMLLoader();
					loader.setLocation(StartClientGui.class.getResource("controllers/LoginGui.fxml"));
					root = loader.load();
					
					primaryStage.setTitle("Lorenzo il Magnifico Login");
					ControllerLogin login = loader.getController();
					login.getStartClientGui(this);
					root.setId("pane");
					Scene scene = new Scene(root, 600, 400);
					scene.getStylesheets().addAll(this.getClass().getResource("controllers/pane.css").toExternalForm());
					primaryStage.setScene(scene);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 2:
				try {
					loader = new FXMLLoader();
					loader.setLocation(this.getClass().getResource("controllers/RegisterGui.fxml"));
					root = loader.load();
					primaryStage.setTitle("Lorenzo il Magnifico Register");
					ControllerRegister register = loader.getController();
					register.getStartClientGui(this);
					root.setId("pane");
					Scene scene = new Scene(root, 600, 400);
					scene.getStylesheets().addAll(this.getClass().getResource("controllers/pane.css").toExternalForm());
					primaryStage.setScene(scene);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 3:
				try {
					loader = new FXMLLoader();
					loader.setLocation(this.getClass().getResource("controllers/MenuGui.fxml"));
					root = loader.load();
					primaryStage.setTitle("Lorenzo il Magnifico");
					ControllerMenu menu = loader.getController();
					menu.getStartClient(this);
					root.setId("pane");
					Scene scene = new Scene(root, 600, 400);
					scene.getStylesheets().addAll(this.getClass().getResource("controllers/pane.css").toExternalForm());
					primaryStage.setScene(scene);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 4:
				try {
					loader = new FXMLLoader();
					loader.setLocation(this.getClass().getResource("controllers/LoadingGui.fxml"));
					root = loader.load();
					ControllerWaitingRoom waitingRoom = loader.getController();
					primaryStage.setResizable(true);
					waitingRoom.getStartClientGui(this);
					primaryStage.setScene(new Scene(root));
					//getClient().waitStartGame(this);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 5:
				try {
					loader = new FXMLLoader();
					loader.setLocation(this.getClass().getResource("controllers/GameGui.fxml"));
					root = loader.load();
					ControllerGame game = loader.getController();
					try {
						game.getStartClient(this);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					root.setId("back");
					Scene scene = new Scene(root);
					scene.getStylesheets().addAll(this.getClass().getResource("controllers/gameBackGround.css").toExternalForm());
					primaryStage.setWidth(1366);
					primaryStage.setHeight(768);
					primaryStage.setScene(scene);
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


	public ConnectionClient getClient() {
		return client;
	}

	public void setClient(ConnectionClient mom) {
		this.client = mom;
	}


	public void closeStageForPlayWithConsole() {
		primaryStage.close();
	}


	public Stage getStage() {
		return primaryStage;
	}


	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}
	
}
