package server.element;

import java.awt.Image;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
*Classe base delle carte presenti nel gioco. Possiedera' tutti gli attributi e metodi comuni a tutti i tipi di carte sviluppo all'interno del gioco stesso.
*Questa classe per il settaggio di una generica carta comunichera' con il database che contiene tutti i tipi di carte presenti.
*/
public class CartaSviluppo {

	//Le carte sviluppo possiedono differenti costi variabili a seconnda della carta estratta
	private int costoAzione;
	private String name; 
	private String descrizioneEffettoPermanente;
	private String descrizioneEffettoImmediato;
	private int qtaEffettoPermanente; 
	private int qtaEffettoImmediato;
	private Image image;
	
	//A seconda della carta estratta dal DB verranno settati i costi della carta stessa
	public void setCarta(Connection connection, String query ) {
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				//Riempire i campi con i risultati della query
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public Image getImage() {
		return image;
	}

	public String getNameCard() {
		return name;
	}
	
}
