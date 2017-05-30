package client.gui.controllers;

import java.rmi.RemoteException;
import java.util.ArrayList;
import client.gui.StartClientGui;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import server.element.Partita;

public class ControllerMenu {

	private StartClientGui start;
	private String lobby;
	private String color;

	@FXML
	public Button create;
	@FXML
	public Button enter;
	@FXML
	public ListView<String> lobbies;
	@FXML
	public Button back;
	@FXML
	public Label title;
	
	
	public void getStartClient(StartClientGui start) {
		this.setClient(start);
		Font.loadFont(getClass().getResourceAsStream("Sketch Gothic School.ttf"), 0);
	}

	public StartClientGui getClient() {
		return start;
	}

	public void setClient(StartClientGui start) {
		this.start = start;
	}
	
	@FXML
	public void enterInALobby(){
		ArrayList<Partita> arrayLobbies = start.getClient().lobbiesView();
		ArrayList<String> nameLobbies = new ArrayList<String>();
		for(Partita mom : arrayLobbies)
			nameLobbies.add(mom.getLobby());
		javafx.collections.ObservableList<String> mom = FXCollections.observableArrayList(nameLobbies);
		lobbies.setItems(mom);
		lobbies.setOpacity(1);
		lobbies.setDisable(false);
		back.setDisable(false);
		back.setOpacity(0);
	}
	
	@FXML
	public void back(){
		lobbies.setItems(FXCollections.observableArrayList(""));
		lobbies.setOpacity(0);
		lobbies.setDisable(true);
		back.setOpacity(0);
		back.setDisable(true);
	}
	
	@FXML
	public void selectLobby(MouseEvent e){
		lobby = null;
		if(e.getClickCount() == 2)
			lobby = (String) lobbies.getSelectionModel().getSelectedItem();
		colorSelect();
		start.getClient().enterInALobby(lobby,color);
		start.changeStage(4);
		e.consume();
	}

	@FXML
	public void createANewLobby(){
		Stage popup = new Stage();
		popup.setTitle("Write name for a new Lobby");
		popup.setResizable(false);
		VBox box = new VBox();
		Button confirm = new Button("Confirm");
		TextField textLobby = new TextField();
		confirm.setOnAction(event -> {
			colorSelect();
			start.getClient().createANewLobby(textLobby.getText(), color);
			popup.close();
		});
		box.getChildren().addAll(new Label("Write e confirm lobby's name"),new Label(),confirm, textLobby);
		Scene scene = new Scene(box,200,200);
		popup.centerOnScreen();
		popup.setScene(scene);
		popup.show();
	}

	private void colorSelect() {
		try {
			
			Stage popup = new Stage();
			popup.setTitle("Select Colors");
			VBox box = new VBox();
			HBox boxColors = new HBox();
			HBox boxButton = new HBox();
			Circle circle;
			Button b;
			String[] colors = start.getClient().getColors();
			for(int i=0; i<4;i++){
				if(colors[i] != null){
					switch(colors[i]){
					case "black":
						circle = new Circle();
						circle.setFill(Color.BLACK);
						boxColors.getChildren().add(circle);
						b = new Button("Black");
						b.setOnAction(event ->{
							color = "black";
							event.consume();
						});
						boxButton.getChildren().add(b);
						break;
					case "orange":
						circle = new Circle();
						circle.setFill(Color.ORANGERED);
						boxColors.getChildren().add(circle);
						b = new Button("Orange");
						b.setOnAction(event ->{
							color = "orange";
							event.consume();
						});
						boxButton.getChildren().add(b);
						break;
					case "white":
						circle = new Circle();
						circle.setFill(Color.ANTIQUEWHITE);
						boxColors.getChildren().add(circle);
						b = new Button("White");
						b.setOnAction(event ->{
							color = "white";
							event.consume();
						});
						boxButton.getChildren().add(b);
						break;
					case "green":
						circle = new Circle();
						circle.setFill(Color.DARKGREEN);
						boxColors.getChildren().add(circle);
						b = new Button("Green");
						b.setOnAction(event ->{
							color = "green";
							event.consume();
						});
						boxButton.getChildren().add(b);
						break;
					}
				}
			}
			box.getChildren().addAll(new Label("Selezione il colore che vuoi per le tue pedine") ,boxColors);
			Scene scene = new Scene(box,200,200);
			popup.centerOnScreen();
			popup.setScene(scene);
			popup.show();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
}
