package client.gui.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;

import client.ConnectionRmiInterlocutorClient;
import client.gui.StartClientGui;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class ControllerWaitingRoom {

	private StartClientGui start;
	
	@FXML
	public ImageView background = new ImageView();
	@FXML
	public Label text = new Label();

	private BufferedReader fileReader;
	
	public void getStartClientGui(StartClientGui startClientGui) {
		this.setStart(startClientGui);
		//Rivedo perche' forse non funziona, forse c'e' da cambiare posizione o creare una funzione di lancio apposita
			try {
				fade();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}
	
	public StartClientGui getStart() {
		return start;
	}

	private void setStart(StartClientGui start) {
		this.start = start;
	}
	
	public void fade() throws IOException{
		FileReader file = new FileReader("src/client/gui/controllers/Images.txt");//creo il file da leggere
		fileReader = new BufferedReader(file);
		int random = (int) (Math.random()*4);//devo moltiplicare per il numero massimo di immagini che inserisco
		System.out.println(random);
		for(int i = 0;i<random-1;i++){
			fileReader.readLine();
		}
		String mom = fileReader.readLine();
		background.setImage(new Image(getClass().getResourceAsStream(mom)));
		FadeTransition fadeTransition = new FadeTransition(Duration.millis(2500), background);
		fadeTransition.setFromValue(0.0);
		fadeTransition.setToValue(1.0);
		fadeTransition.play();
		
		file = new FileReader("src/client/gui/controllers/mom.txt");//creo il file da leggere
		fileReader = new BufferedReader(file);
		random = (int) (Math.random()*4);//devo moltiplicare per il numero massimo di immagini che inserisco
		System.out.println(random);
		for(int i = 0;i<random-1;i++){
			fileReader.readLine();
		}
		mom = fileReader.readLine();
		text.setText(mom);
		FadeTransition fadeTransition2 = new FadeTransition(Duration.millis(2500), text);
		fadeTransition2.setFromValue(0.0);
		fadeTransition2.setToValue(1.0);
		fadeTransition2.play();
	}
}
