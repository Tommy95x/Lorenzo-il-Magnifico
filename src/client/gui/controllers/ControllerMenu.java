package client.gui.controllers;

import java.util.ArrayList;

import ObservableList;
import client.gui.StartClientGui;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import server.element.Partita;

public class ControllerMenu {

	private StartClientGui start;

	@FXML
	public Button create;
	@FXML
	public Button enter;
	@FXML
	public ListView lobbies;
	
	
	public void getStartClient(StartClientGui start) {
		this.setClient(start);
	}

	public StartClientGui getClient() {
		return start;
	}

	public void setClient(StartClientGui client) {
		this.start = start;
	}
	
	@FXML
	public void enterInALobby(){
		ArrayList<Partita> arrayLobbies = start.getClient().lobbiesView();
		ArrayList<String> nameLobbies;
		for(Partita mom : arrayLobbies)
			nameLobbies.add(mom.getLobby());
		ObservableList<String> mom = FXCollections.observableArrayList(nameLobbies);
		lobbies.setItems(mom);
	}

}
