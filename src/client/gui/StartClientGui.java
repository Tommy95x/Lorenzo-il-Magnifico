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
	
	@SuppressWarnings("static-access")
	public void start(Stage primaryStage) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(this.getClass().getResource(""));
		primaryStage.setResizable(false);
		primaryStage.setTitle("Lorenzo il Magnifico Login");
		//primaryStage.getIcons().add(e);
		ControllerLogin login = loader.getController();
		login.getStartClientGui(this, primaryStage);
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
	
	
	public void changeStage(Stage oldStage, Stage newStage){
		
	}
	
	
}
