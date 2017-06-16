package server.element;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	public void setValue(Connection connection) throws SQLException {
		//Chiedere bene
		 valore = (int) (Math.random()*6);
		 //Commentare quando si testa
		 Statement stmt=connection.createStatement();
		 ResultSet res = stmt.executeQuery("SELECT IMMAGINE FROM DADO WHERE VALORE="+valore+" and COLORE = '"+color+"'");
		 res.next();
		 immagineValore=res.getString("IMMAGINE");
		 res.close();
		 stmt.close();
		 connection.close();
	}
	
	public int getValore() {
		return valore;
	}

	public String getImage() {
		return immagineValore;
	}
	
}
