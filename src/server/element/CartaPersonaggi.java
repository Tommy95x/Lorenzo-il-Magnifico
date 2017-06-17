package server.element;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;

public class CartaPersonaggi extends CartaSviluppo {

	// Le carte personaggio possiedono solo e soltanto costo in moneta
	private int costoMoneta;
	private String name;
	private String ID;
	private String nomeffetto;
	private int qtaeffetto;
	private ArrayList<Effetto> effetti;
	private String perognicarta;
	int scontoAzione=3;
	private String image;
	private String tooltip;

	public CartaPersonaggi(int costoMoneta, String name, String perognicarta, String image, String tooltip, 
			HashMap<String, Integer> effettoimmediato1, HashMap<String, Integer> azioneimmediata,
			HashMap<String, Integer> scontoazioneimmediata1, HashMap<String, Integer> scontoazioneimmediata2,
			HashMap<String, Integer> azionepermanente, HashMap<String, Integer> scontoazionepermanente1,
			HashMap<String, Integer> scontoazionepermanente2) {
		this.costoMoneta = costoMoneta;
		this.name=name;
		this.tooltip=tooltip;
		this.image=image;
		this.perognicarta=perognicarta;
	}

	public CartaPersonaggi() {

	}

	public void setCarta(Connection connection, String query) {
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				name = rs.getString("NOME");
				ID=rs.getString("ID");
				nomeffetto=rs.getString("EFFETTOIMMEDIATO1").toLowerCase();
				qtaeffetto=rs.getInt("QTAEFFETTOIMMEDIATO1");
				effetti.add(new Effetto(nomeffetto, qtaeffetto, true, rs.getInt("TIPO"), rs.getInt("TIPOPERMANENTE")));
				perognicarta = rs.getString("PEROGNICARTA");
				effetti.add(new Effetto(rs.getString("AZIONEIMMEDIATA").toLowerCase(), rs.getInt("VALOREAZIONEIMMEDIATA"), true, rs.getInt("TIPO"), rs.getInt("TIPOPERMANENTE")));
				effetti.add(new Effetto(rs.getString("AZIONEPERMANENTE").toLowerCase(), rs.getInt("VALOREAZIONEPERMANENTE"), false, rs.getInt("TIPO"), rs.getInt("TIPOPERMANENTE")));
				costoMoneta = rs.getInt("COSTOMONETA");
				setImage(rs.getString("IMMAGINE"));
				setTooltip(rs.getString("DESCRIZIONE"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setImage(String url) {
		image = url;
	}

	public String getNameCard() {
		return name;
	}

	public int getCostoMoneta() {
		return costoMoneta;
	}
	
	public ArrayList<Effetto> getEffetti() {
		return effetti;
	}
	
	public int getScontoAzione() {
		return scontoAzione;
	}

	public String getPerOgniCarta() {
		return perognicarta;
	}

	public Tooltip getTooltip() {
		Tooltip tooltip = new Tooltip("");
		tooltip.setText(this.tooltip);
		return tooltip;
	}

	public String getImage() {
		return image;
	}

	public void setNameCard(String name) {
		this.name = name;
	}

	public void setCostoMoneta(int costoMoneta) {
		this.costoMoneta = costoMoneta;
	}

	public void setTooltip(String string) {
		this.tooltip = string;
	}
	
	public String getTooltipString(){
		return tooltip;
	}
	
	public void setId(String ID){
		this.ID=ID;
	}
	
	public String getId(){
		return ID;
	}
}
