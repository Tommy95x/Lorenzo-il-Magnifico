package server.element;
import java.io.Serializable;
import java.sql.Connection;

import javafx.scene.image.Image;

/*
*Classe che verra' istanziata ad ogni partita in un array di 3 elementi. Il  dado iteragisce con il giocatore che lo lancia fornendogli
*il valore casuale tra 1 e 6 che acquistera' dal rispettivo metodo.
*/
public class Dado implements Serializable{

	private String color;
	private int valore;
	private String immagineValore;
	
	public Dado(String color) {
		this.color=color;
	}
	
	//Metodi per il get del colore
	public String getColor() {
		return color;
	}
	
	//Set del valore dopo il lancio del primo giocatore del turno
	public void setValue(Connection connection) {
		 valore = (int) (Math.random()*6);
		 //creare una query d'interrogazione per avre l'immagine corrispondente dei dadi
	}
	
	public int getValore() {
		return valore;
	}

	public String getImage() {
		return immagineValore;
	}
	
}
