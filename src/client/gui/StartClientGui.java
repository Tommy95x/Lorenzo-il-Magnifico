package client.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartClientGui extends Application{

	public StartClientGui(String[] args){
		launch(args);
	}
	
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource(""));
		primaryStage.setResizable(false);
		primaryStage.setTitle("Lorenzo il Magnifico Login");
		primaryStage.setScene(new Scene(root));
	}
	
}
