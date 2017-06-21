
package client.gui;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

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
 *         Claase che viene avviata direttamente dall'utente all'avvio del
 *         gioco, main dell'applicazione lato client inizialmente di pura
 *         grafica successivamente diventa anche di logica nel momento in cui
 *         l'utente decider� in quale modalit� giocare. Le modalit� disponibili
 *         sono mediante una grafica con una connessione RMI o Socket, o da
 *         Console con una sola connessione RMI.
 */
public class StartClientGui extends Application {

	private FXMLLoader loader = new FXMLLoader();;
	private Parent root;
	private Stage primaryStage;
	private ConnectionClient client = new ConnectionClient();
	private String color;
	// Mettere public per i test
	// public boolean create = false;
	private boolean create = false;

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

	public void changeStage(int numberOfStage) {
		switch (numberOfStage) {
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
				primaryStage.setOnCloseRequest(event -> exit(primaryStage));
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
			loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("controllers/LoadingGui.fxml"));
			try {
				root = loader.load();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			ControllerWaitingRoom waitingRoom = loader.getController();
			primaryStage.setResizable(true);
			primaryStage.setScene(new Scene(root));
			waitingRoom.getStartClientGui(this);
			try {
				getClient().startGame();
				getClient().waitStartGame(this);
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 5:
			try {
				System.out.println("ProvaProva");
				loader = new FXMLLoader();
				loader.setLocation(this.getClass().getResource("controllers/GameGui.fxml"));
				root = loader.load();
				ControllerGame game = new ControllerGame();
				game = loader.getController();
				try {
					game.setGUI(this);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					getClient().setGuiGame(game);
					game.setCards(getClient().getCardsGame());
					game.setCardsScomunica(getClient().getCardsScomunica());
					game.setColorsParents(getColor());
					game.setColorCubiScomunica(getColor());
					game.setRisorse(getClient().getRisorse());
					game.parentsProperties();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				root.setId("back");
				Scene scene = new Scene(root, 1366, 768);
				scene.getStylesheets()
						.addAll(this.getClass().getResource("controllers/gameBackGround.css").toExternalForm());
				primaryStage.setScene(scene);
				File f = new File("src/client/gui/Madrigale XIV secolo.wav");
				AudioInputStream audioIn;
				try {
					audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
					Clip clip = AudioSystem.getClip();
					clip.open(audioIn);
					clip.start();
					clip.loop(Clip.LOOP_CONTINUOUSLY);
				} catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("ProvaProvaProva");
				getClient().waitQualcosa();
			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
	}

	public void exit(Stage primaryStage) {
		if (create)
			try {
				getClient().deleteView();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		try {
			getClient().removeAccount();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public void setCreate(boolean b) {
		create = b;
	}

	public void mom() {
		changeStage(5);

	}

}
