package server.element;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;

public class CartaTerritori extends CartaSviluppo{
	
	//Le carte territorio non hanno alcun costo
	private int costoAzione;
	private String name; 
	private String nomeffetto;
	private int qtaeffetto;
	private HashMap<String, Integer> effettoimmediato1 = new HashMap<String, Integer>();
	private HashMap<String, Integer> effettoimmediato2 = new HashMap<String, Integer>();
	private HashMap<String, Integer> effettopermanente1 = new HashMap<String, Integer>();
	private HashMap<String, Integer> effettopermanente2 = new HashMap<String, Integer>();
	private HashMap<String, Integer> effettopermanente3 = new HashMap<String, Integer>();
	private javafx.scene.image.Image image;
	private Tooltip tooltip;
	
	public CartaTerritori(){
		
	}
	
	public void setCarta(Connection connection, String query ){
	try{
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
			setImage(rs.getString("IMMAGINE"));
			}
			rs.close();
			stmt.close();
	}catch(SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setImage(String url){
		image = new Image(getClass().getResourceAsStream(url));
	}
	
}
