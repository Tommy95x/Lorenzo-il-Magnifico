package server.element;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;

public class CartaImprese extends CartaSviluppo{

	//Le carte impresa hanno due differenti costi impresa e il giocatore può decidere quale dei due costi scegliere tra risorse o punti militari
	private int costoLegno;
	private int costoPietra;
	private int costoMoneta;
	private int costoPuntiMilitari;
	private int puntiMilitariRichiesti;
	private int costoServitori;
	private String name; 
	private String nomeffetto;
	private int qtaeffetto;
	private HashMap<String, Integer> effettoimmediato1 = new HashMap<String, Integer>();
	private HashMap<String, Integer> effettoimmediato2 = new HashMap<String, Integer>();
	private HashMap<String, Integer> effettoimmediato3 = new HashMap<String, Integer>();
	private HashMap<String, Integer> azioneimmediata = new HashMap<String, Integer>();
	private int puntiVittoria;
	private javafx.scene.image.Image image;
	private Tooltip tooltip;
	
		public void setCarta(Connection connection, String query ) {
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				name=rs.getString("NOME");
				costoLegno=rs.getInt("COSTOLEGNO");
				costoPietra=rs.getInt("COSTOPIETRA");
				costoMoneta=rs.getInt("COSTOMONETA");
				costoServitori=rs.getInt("COSTOSERVITORI");
				costoPuntiMilitari=rs.getInt("COSTOPUNTIMILITARI");
				puntiMilitariRichiesti=rs.getInt("REQUISITOPUNTIMILITARI");
				nomeffetto=rs.getString("EFFETTOIMMEDIATO1");
				qtaeffetto=rs.getInt("QTAEFFETTOIMMEDIATO1");
				effettoimmediato1.put(nomeffetto, qtaeffetto);
				nomeffetto=rs.getString("EFFETTOIMMEDIATO2");
				qtaeffetto=rs.getInt("QTAEFFETTOIMMEDIATO2");
				effettoimmediato2.put(nomeffetto, qtaeffetto);
				nomeffetto=rs.getString("EFFETTOIMMEDIATO3");
				qtaeffetto=rs.getInt("QTAEFFETTOIMMEDIATO3");
				effettoimmediato3.put(nomeffetto, qtaeffetto);
				azioneimmediata.put(rs.getString("AZIONEIMMEDIATA"),rs.getInt("VALOREAZIONEIMMEDIATA"));
				puntiVittoria=rs.getInt("PUNTIVITTORIA");
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
