package fade;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FadeParallel extends Application {
	public void start(Stage primaryStage) {
		Stage s = new Stage();
		BorderPane pane = new BorderPane();
		HBox box = new HBox();
		HBox box2 =new HBox();
		Button start = new Button("Start");
		ImageView im = new ImageView();
		ImageView im2 = new ImageView();
		Label label = new Label("FadeOut");
		im.setImage(new Image(getClass().getResourceAsStream("2e728641c90ce6024365f0f5cb477deb.jpg")));
		im2.setImage(new Image(getClass().getResourceAsStream("LIMBO-Wallpaper-Logo.jpg")));
		box2.getChildren().addAll(im,im2);
		box.getChildren().addAll(start,label);
		pane.setTop(box);
		pane.setCenter(box2);
		im2.setOpacity(0);
		Scene scene = new Scene(pane, 600,400);
		start.setOnAction((actionEvent)->{
			FadeTransition fadeTransition2  = new FadeTransition(Duration.millis(3000), im);
			 fadeTransition2.setFromValue(1.0);
			 fadeTransition2.setToValue(0.0);
			 fadeTransition2.play();
			 FadeTransition fadeTransition = new FadeTransition(Duration.millis(3000), im2);
			 fadeTransition.setFromValue(0.0);
			 fadeTransition.setToValue(1.0);
			 fadeTransition.play();
			 label.setText("FadeIn");
		});
		s.setScene(scene);
		s.show();
		
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
