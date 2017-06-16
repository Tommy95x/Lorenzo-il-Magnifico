package server.element;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;

public class CartaTerritori extends CartaSviluppo{
	
	//Le carte territorio non hanno alcun costo
	private int costoAzione;
	private String name = new String(); 
	private String nomeffetto = new String();
	private int qtaeffetto;
	private ArrayList<Effetto> effetti = new ArrayList<Effetto>();
	private String image = new String();
	private String tooltip = new String();
	
	public CartaTerritori(){
		
	}
	
	public CartaTerritori(String name, String image, String tooltip, HashMap<String, Integer> effettoimmediato1, HashMap<String, Integer> effettoimmediato2, HashMap<String, Integer> effettopermanente1, HashMap<String, Integer> effettopermanente2, HashMap<String, Integer> effettopermanente3){
		this.image=image;
		this.name=name;
		this.tooltip=tooltip;
	}
	
	
	public void setCarta(Connection connection, String query ){
	try{
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next()){
			name=rs.getString("NOME");
			nomeffetto=rs.getString("EFFETTOIMMEDIATO1").toLowerCase();
			qtaeffetto=rs.getInt("QTAEFFETTOIMMEDIATO1");
			effetti.add(new Effetto(nomeffetto, qtaeffetto, true));
			nomeffetto=rs.getString("EFFETTOIMMEDIATO2").toLowerCase();
			qtaeffetto=rs.getInt("QTAEFFETTOIMMEDIATO2");
			effetti.add(new Effetto(nomeffetto, qtaeffetto, true));
			nomeffetto=rs.getString("EFFETTOPERMANENTE1").toLowerCase();
			qtaeffetto=rs.getInt("QTAEFFETTOPERMANENTE1");
			effetti.add(new Effetto(nomeffetto, qtaeffetto, false));
			nomeffetto=rs.getString("EFFETTOPERMANENTE2").toLowerCase();
			qtaeffetto=rs.getInt("QTAEFFETTOPERMANENTE2");
			effetti.add(new Effetto(nomeffetto, qtaeffetto, false));
			nomeffetto=rs.getString("EFFETTOPERMANENTE3").toLowerCase();
			qtaeffetto=rs.getInt("QTAEFFETTOPERMANENTE3");
			effetti.add(new Effetto(nomeffetto, qtaeffetto, false));
			costoAzione=rs.getInt("COSTOAZIONE");
			setImage(rs.getString("IMMAGINE"));
			setTooltip(rs.getString("DESCRIZIONE"));
			}
			rs.close();
			stmt.close();
	}catch(SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setImage(String url){
		image = url;
	}
	
	public String getNameCard() {
		return name;
	}
	
	public int getCostoAzione(){
		return costoAzione;
	}
	
	public String getImage(){
		return image;
	}
	
	public Tooltip getTooltip(){
		Tooltip tooltip = new Tooltip("");
		tooltip.setText(this.tooltip);
		return tooltip;
	}
	
	public void setNameCard(String name){
		this.name=name;
	}
	
	public void setCostoAzione(int costoAzione){
		this.costoAzione=costoAzione;
	}
	
	public void setTooltip(String string) {
		this.tooltip = string;
	}
	
	public String getTooltipString() {
		return tooltip;
	}
	
	public ArrayList<Effetto> getEffetti() {
		return effetti;
	}
}
