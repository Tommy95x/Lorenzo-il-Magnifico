package server.element;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;

public class CartaPersonaggi extends CartaSviluppo{

	//Le carte personaggio possiedono solo e soltanto costo in moneta
	private int costoMoneta;
	private String name; 
	private String nomeffetto;
	private int qtaeffetto;
	private HashMap<String, Integer> effettoimmediato = new HashMap<String, Integer>();
	private String perognicarta;
	private HashMap<String, Integer> azioneimmediata = new HashMap<String, Integer>();
	private HashMap<String, Integer> scontoazioneimmediata1 = new HashMap<String, Integer>();
	private HashMap<String, Integer> scontoazioneimmediata2 = new HashMap<String, Integer>();
	private HashMap<String, Integer> azionepermanente = new HashMap<String, Integer>();
	private HashMap<String, Integer> scontoazionepermanente1 = new HashMap<String, Integer>();
	private HashMap<String, Integer> scontoazionepermanente2 = new HashMap<String, Integer>();
	private javafx.scene.image.Image image;
	private Tooltip tooltip;
	
		public void setCarta(Connection connection, String query ) {
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				name=rs.getString("NOME");
				nomeffetto=rs.getString("EFFETTOIMMEDIATO1");
				qtaeffetto=rs.getInt("QTAEFFETTOIMMEDIATO1");
				effettoimmediato.put(nomeffetto, qtaeffetto);
				perognicarta=rs.getString("PEROGNICARTA");
				azioneimmediata.put(rs.getString("AZIONEIMMEDIATA"), rs.getInt("VALOREAZIONEIMMEDIATA"));
				scontoazioneimmediata1.put(rs.getString("SCONTOAZIONEIMMEDIATA1"), rs.getInt("QTASCONTOAZIONEIMMEDIATA1"));
				scontoazioneimmediata2.put(rs.getString("SCONTOAZIONEIMMEDIATA2"), rs.getInt("QTASCONTOAZIONEIMMEDIATA2"));
				azionepermanente.put(rs.getString("AZIONEPERMANENTE"), rs.getInt("VALOREAZIONEPERMANENTE"));
				scontoazionepermanente1.put(rs.getString("SCONTOAZIONEPERMANENTE1"), rs.getInt("QTASCONTOAZIONEPERMANENTE1"));
				scontoazionepermanente2.put(rs.getString("SCONTOAZIONEPERMANENTE2"), rs.getInt("QTASCONTOAZIONEPERMANANTE2"));
				costoMoneta=rs.getInt("COSTOMONETA");
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
