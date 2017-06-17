package prova;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import prova.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
		Scene scene = new Scene(root, 1366, 768);
		scene.getStylesheets().addAll(this.getClass().getResource("gameBackGround.css").toExternalForm());
		primaryStage.setScene(scene);
		game.enableGame();
		game.parentsProperties();
		game.setFlag("blue");
		System.out.println("ProvaProvaProva");
		// getClient().waitTurno();
		primaryStage.setScene(scene);
		primaryStage.show();
		
		System.out.println("Sono i thread audio");
		File f = new File("src/prova/Suono-monete-effetto-di-suono.wav");
		AudioInputStream audioIn;
		try {
			audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start();
			clip.loop(1);
		} catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
