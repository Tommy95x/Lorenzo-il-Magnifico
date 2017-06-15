package prova;

import java.io.IOException;

import prova.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import server.element.Portafoglio;

public class Mom extends Application {

	
	public void start(Stage primaryStage) throws IOException, ClassNotFoundException {
		System.out.println("ProvaProva");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("GameGui.fxml"));
		Parent root = loader.load();
		Controller game = new Controller();
		game = loader.getController();
		game.setCards(null);
		game.setCardsScomunica(null);
		game.setColorsParents("blue");
		game.setColorCubiScomunica("blue");
		game.setRisorse(new Portafoglio());
		game.setPosizioni();
		root.setId("back");
		Scene scene = new Scene(root,1366,768);
		scene.getStylesheets().addAll(this.getClass().getResource("gameBackGround.css").toExternalForm());
		primaryStage.setScene(scene);
		game.enableGame();
		game.parentsProperties();
		System.out.println("ProvaProvaProva");
		//getClient().waitTurno();
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
