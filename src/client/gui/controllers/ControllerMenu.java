package client.gui.controllers;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import client.gui.StartClientGui;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
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
	@FXML
	public Button startGame;
	@FXML
	public Label label;
	@FXML
	public MenuItem about;
	@FXML
	public MenuItem close;
	@FXML
	public MenuItem exitToGame;

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
	public void enterInALobby() throws IOException {
		ArrayList<Partita> arrayLobbies = new ArrayList<Partita>();
		arrayLobbies = start.getClient().lobbiesView();
		ObservableList<String> nameLobbies = FXCollections.observableArrayList();
		for (Partita mom : arrayLobbies){
			nameLobbies.add(mom.getLobbyName());
		}
		lobbies.setItems(nameLobbies);
		lobbies.setOpacity(1);
		lobbies.setDisable(false);
		back.setDisable(false);
		back.setOpacity(1);
	}

	@FXML
	public void back() {
		lobbies.setItems(FXCollections.observableArrayList(""));
		lobbies.setOpacity(0);
		lobbies.setDisable(true);
		back.setOpacity(0);
		back.setDisable(true);
	}

	@FXML
	public void selectLobby(MouseEvent e) {
		lobby = "";
		if (e.getClickCount() == 2){
			lobby = (String) lobbies.getSelectionModel().getSelectedItem();
			colorSelect();
		}
		e.consume();
	}

	@FXML
	public void createANewLobby() {
		Stage popup = new Stage();
		popup.setTitle("Write name for a new Lobby");
		popup.setResizable(false);
		VBox box = new VBox();
		Button confirm = new Button("Confirm");
		TextField textLobby = new TextField();
		confirm.setOnAction(event -> {
			popup.close();
			colorSelectFirstTime();
			if(!textLobby.getText().equals(""))
				lobby = textLobby.getText();
				
		});
		box.getChildren().addAll(new Label("Write e confirm lobby's name"), new Label(), confirm, textLobby);
		Scene scene = new Scene(box, 200, 100);
		popup.centerOnScreen();
		popup.setScene(scene);
		popup.show();
	}
	
	@FXML
	public void startGame() throws IOException{
		start.getClient().startGame();
		start.changeStage(4);
	}

	@FXML
	public void close() throws IOException{
		if(!exitToGame.isDisable())
			start.getClient().exitToTheGame(lobby, start.getColor());
		System.exit(0);
	}
	
	@FXML
	public void help(){
		
	}
	
	@FXML
	public void extiToTheGame() throws IOException{
		exitToGame.setDisable(true);
		start.getClient().exitToTheGame(lobby, start.getColor());
	}
	
	private void colorSelectFirstTime() {
		Stage popup = new Stage();
		popup.setTitle("Select Colors");
		VBox box = new VBox();
		HBox boxColors = new HBox();
		HBox boxButton = new HBox();
		Circle circle;
		Button b;
		circle = new Circle();
		circle.setFill(Color.BLUE);
		circle.setRadius(50.0);
		boxColors.getChildren().add(circle);
		b = new Button("Blue");
		b.setOnAction(event -> {
			start.setColor("black");
			try {
				System.out.println(start.getClient().createANewLobby(lobby, start.getColor()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			viewStartButton();
			popup.close();
			event.consume();
		});
		boxButton.getChildren().add(b);
		circle = new Circle();
		circle.setFill(Color.ORANGERED);
		circle.setRadius(50.0);
		boxColors.getChildren().add(circle);
		b = new Button("Orange");
		b.setOnAction(event -> {
			start.setColor("orange");
			try {
				System.out.println(start.getClient().createANewLobby(lobby, start.getColor()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			viewStartButton();
			popup.close();
			event.consume();
		});
		boxButton.getChildren().add(b);
		circle = new Circle();
		circle.setFill(Color.ANTIQUEWHITE);
		circle.setRadius(50.0);
		boxColors.getChildren().add(circle);
		b = new Button("White");
		b.setOnAction(event -> {
			start.setColor("white");
			try {
				System.out.println(start.getClient().createANewLobby(lobby, start.getColor()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			viewStartButton();
			popup.close();
			event.consume();
		});
		boxButton.getChildren().add(b);
		circle = new Circle();
		circle.setFill(Color.DARKGREEN);
		circle.setRadius(50.0);
		boxColors.getChildren().add(circle);
		b = new Button("Green");
		b.setOnAction(event -> {
			start.setColor("white");
			try {
				System.out.println(start.getClient().createANewLobby(lobby, start.getColor()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			viewStartButton();
			popup.close();
			event.consume();
		});
		boxButton.getChildren().add(b);
		box.getChildren().addAll(boxColors,boxButton);
		popup.centerOnScreen();
		Scene scene = new Scene(box, 400, 150);
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
			for (int i = 0; i < 4; i++) {
				if (colors[i] != null) {
					switch (colors[i]) {
					case "blue":
						circle = new Circle();
						circle.setFill(Color.BLUE);
						circle.setRadius(50.0);
						boxColors.getChildren().add(circle);
						b = new Button("blue");
						b.setOnAction(event -> {
							start.setColor("blue");
							try {
								start.getClient().enterInALobby(lobby, start.getColor());
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							popup.close();
							viewStartButton();
							event.consume();
						});
						boxButton.getChildren().add(b);
						break;
					case "orange":
						circle = new Circle();
						circle.setFill(Color.ORANGERED);
						circle.setRadius(50.0);
						boxColors.getChildren().add(circle);
						b = new Button("Orange");
						b.setOnAction(event -> {
							start.setColor("orange");
							try {
								start.getClient().enterInALobby(lobby, start.getColor());
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							popup.close();
							viewStartButton();
							event.consume();
						});
						boxButton.getChildren().add(b);
						break;
					case "white":
						circle = new Circle();
						circle.setFill(Color.ANTIQUEWHITE);
						circle.setRadius(50.0);
						boxColors.getChildren().add(circle);
						b = new Button("White");
						b.setOnAction(event -> {
							start.setColor("white");
							try {
								start.getClient().enterInALobby(lobby, start.getColor());
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							popup.close();
							viewStartButton();
							event.consume();
						});
						boxButton.getChildren().add(b);
						break;
					case "green":
						circle = new Circle();
						circle.setFill(Color.DARKGREEN);
						circle.setRadius(50.0);
						boxColors.getChildren().add(circle);
						b = new Button("Green");
						b.setOnAction(event -> {
							start.setColor("green");
							try {
								start.getClient().enterInALobby(lobby, start.getColor());
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							popup.close();
							viewStartButton();
							event.consume();
						});
						boxButton.getChildren().add(b);
						break;
					}
				}
			}
			box.getChildren().addAll(new Label("Selezione il colore che vuoi per le tue pedine"), boxColors,boxButton);
			Scene scene = new Scene(box, 400, 150);
			popup.centerOnScreen();
			popup.setScene(scene);
			popup.show();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private void viewStartButton() {
		back.setOpacity(0);
		back.setDisable(true);
		exitToGame.setDisable(false);
		lobbies.setOpacity(0);
		lobbies.setDisable(true);
		startGame.setOpacity(1);
		startGame.setDisable(false);
		label.setOpacity(1);
		create.setDisable(true);
		create.setOpacity(0);
		enter.setDisable(true);
		enter.setOpacity(0);
	}

}
