package server.element;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;

public class CartaEdifici extends CartaSviluppo{

	//Le carte edifici possiedono un costo che deve essere pagato con le risorse del giocatore che intende possederle
	private int costoMoneta;
	private int costoLegno;
	private int costoPietra;
	private int costoServitori;
	private int costoAzione;
	private String name; 
	private String nomeffetto;
	private int qtaeffetto;
	private HashMap<String, Integer> effettoimmediato1 = new HashMap<String, Integer>();
	private HashMap<String, Integer> effettoimmediato2 = new HashMap<String, Integer>();
	private HashMap<String, Integer> spendirisorsa1 = new HashMap<String, Integer>();
	private HashMap<String, Integer> spendirisorsa2 = new HashMap<String, Integer>();
	private HashMap<String, Integer> spendirisorsa3 = new HashMap<String, Integer>();
	private HashMap<String, Integer> prendirisorsa1 = new HashMap<String, Integer>();
	private HashMap<String, Integer> prendirisorsa2 = new HashMap<String, Integer>();
	private HashMap<String, Integer> acquisiscipunti = new HashMap<String, Integer>();
	private String perognicarta;
	private javafx.scene.image.Image image;
	private Tooltip tooltip;
	
	public void setCarta(Connection connection, String query ) {
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				costoMoneta=rs.getInt("COSTOMONETA");
				costoLegno=rs.getInt("COSTOLEGNO");
				costoPietra=rs.getInt("COSTOPIETRA");
				costoServitori=rs.getInt("COSTOSERVITORI");
				name=rs.getString("NOME");
				nomeffetto=rs.getString("EFFETTOIMMEDIATO1");
				qtaeffetto=rs.getInt("QTAEFFETTOIMMEDIATO1");
				effettoimmediato1.put(nomeffetto, qtaeffetto);
				nomeffetto=rs.getString("EFFETTOIMMEDIATO2");
				qtaeffetto=rs.getInt("QTAEFFETTOIMMEDIATO2");
				effettoimmediato2.put(nomeffetto, qtaeffetto);
				spendirisorsa1.put(rs.getString("SPENDIRISORSA1"), rs.getInt("QTASPENDIRISORSA1"));
				spendirisorsa2.put(rs.getString("SPENDIRISORSA2"), rs.getInt("QTASPENDIRISORSA2"));
				spendirisorsa3.put(rs.getString("SPENDIRISORSA3"), rs.getInt("QTASPENDIRISORSA3"));
				prendirisorsa1.put(rs.getString("PRENDIRISORSA1"), rs.getInt("QTAPRENDIRISORSA1"));
				prendirisorsa2.put(rs.getString("PRENDIRISORSA2"), rs.getInt("QTAPRENDIRISORSA2"));
				costoAzione=rs.getInt("COSTOAZIONE");
				acquisiscipunti.put(rs.getString("ACQUISISCIPUNTI"),rs.getInt("QTAACQUISISCIPUNTI"));
				perognicarta=rs.getString("PEROGNICARTA");
				setImage(rs.getString("IMMAGINE"));
				}
				rs.close();
				stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setImage(String url){
		image = new Image(getClass().getResourceAsStream(url));
	}
	
	
}
