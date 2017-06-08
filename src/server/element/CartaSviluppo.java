package server.element;

import java.awt.Image;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javafx.scene.control.Tooltip;

/*
*Classe base delle carte presenti nel gioco. Possiedera' tutti gli attributi e metodi comuni a tutti i tipi di carte sviluppo all'interno del gioco stesso.
*Questa classe per il settaggio di una generica carta comunichera' con il database che contiene tutti i tipi di carte presenti.
*/
public class CartaSviluppo implements Serializable{

	//Le carte sviluppo possiedono differenti costi variabili a seconnda della carta estratta
	private int costoAzione;
	private String name; 
	private String nomeffetto;
	private int qtaeffetto;
	private HashMap<String, Integer> effettoimmediato1 = new HashMap<String, Integer>();
	private HashMap<String, Integer> effettoimmediato2 = new HashMap<String, Integer>();
	private HashMap<String, Integer> effettoimmediato3 = new HashMap<String, Integer>();
	private HashMap<String, Integer> effettopermanente1 = new HashMap<String, Integer>();
	private HashMap<String, Integer> effettopermanente2 = new HashMap<String, Integer>();
	private HashMap<String, Integer> effettopermanente3 = new HashMap<String, Integer>();
	private HashMap<String, Integer> azioneimmediata = new HashMap<String, Integer>();
	private HashMap<String, Integer> azionepermanente = new HashMap<String, Integer>();
	private javafx.scene.image.Image image;
	private Tooltip tooltip;
	
	//A seconda della carta estratta dal DB verranno settati i costi della carta stessa
	public void setCarta(Connection connection, String query ) {
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				name=rs.getString("NOME");
				nomeffetto=rs.getString("EFFETTOIMMEDIATO1");
				qtaeffetto=rs.getInt("QTAEFFETTOIMMEDIATO1");
				effettoimmediato1.put(nomeffetto, qtaeffetto);
				nomeffetto=rs.getString("EFFETTOIMMEDIATO2");
				qtaeffetto=rs.getInt("QTAEFFETTOIMMEDIATO2");
				effettoimmediato2.put(nomeffetto, qtaeffetto);
				nomeffetto=rs.getString("EFFETTOIMMEDIATO3");
				qtaeffetto=rs.getInt("QTAEFFETTOIMMEDIATO3");
				effettoimmediato3.put(nomeffetto, qtaeffetto);
				nomeffetto=rs.getString("EFFETTOPERMANENTE1");
				qtaeffetto=rs.getInt("QTAEFFETTOPERMANENTE1");
				effettopermanente1.put(nomeffetto, qtaeffetto);
				nomeffetto=rs.getString("EFFETTOPERMANENTE2");
				qtaeffetto=rs.getInt("QTAEFFETTOPERMANENTE2");
				effettopermanente2.put(nomeffetto, qtaeffetto);
				nomeffetto=rs.getString("EFFETTOPERMANENTE3");
				qtaeffetto=rs.getInt("QTAEFFETTOPERMANENTE3");
				effettopermanente3.put(nomeffetto, qtaeffetto);
				costoAzione=rs.getInt("COSTOAZIONE");
				}
				rs.close();
				stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public javafx.scene.image.Image getImage() {
		return image;
	}

	public String getNameCard() {
		return name;
	}

	public Tooltip getTooltip() {
		return tooltip;
	}

	public void setTooltip(Tooltip tooltip) {
		this.tooltip = tooltip;
	}
	
}
