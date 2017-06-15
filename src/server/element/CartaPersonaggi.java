package server.element;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;

public class CartaPersonaggi extends CartaSviluppo {

	// Le carte personaggio possiedono solo e soltanto costo in moneta
	private int costoMoneta;
	private String name;
	private String nomeffetto;
	private int qtaeffetto;
	private HashMap<String, Integer> effettoimmediato1 = new HashMap<String, Integer>();
	private String perognicarta;
	private HashMap<String, Integer> azioneimmediata = new HashMap<String, Integer>();
	private HashMap<String, Integer> scontoazioneimmediata1 = new HashMap<String, Integer>();
	private HashMap<String, Integer> scontoazioneimmediata2 = new HashMap<String, Integer>();
	private HashMap<String, Integer> azionepermanente = new HashMap<String, Integer>();
	private HashMap<String, Integer> scontoazionepermanente1 = new HashMap<String, Integer>();
	private HashMap<String, Integer> scontoazionepermanente2 = new HashMap<String, Integer>();
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
		this.scontoazioneimmediata1=scontoazioneimmediata1;
		this.azionepermanente=azionepermanente;
		this.scontoazioneimmediata2=scontoazioneimmediata2;
		this.scontoazionepermanente1=scontoazionepermanente1;
		this.scontoazionepermanente2=scontoazionepermanente2;
		this.effettoimmediato1=effettoimmediato1;
	}

	public CartaPersonaggi() {

	}

	public void setCarta(Connection connection, String query) {
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				name = rs.getString("NOME");
				nomeffetto = rs.getString("EFFETTOIMMEDIATO");
				qtaeffetto = rs.getInt("QTAEFFETTOIMMEDIATO");
				effettoimmediato1.put(nomeffetto, qtaeffetto);
				perognicarta = rs.getString("PEROGNICARTA");
				azioneimmediata.put(rs.getString("AZIONEIMMEDIATA"), rs.getInt("VALOREAZIONEIMMEDIATA"));
				scontoazioneimmediata1.put(rs.getString("SCONTOAZIONEIMMEDIATA1"),
						rs.getInt("QTASCONTOAZIONEIMMEDIATA1"));
				scontoazioneimmediata2.put(rs.getString("SCONTOAZIONEIMMEDIATA2"),
						rs.getInt("QTASCONTOAZIONEIMMEDIATA2"));
				azionepermanente.put(rs.getString("AZIONEPERMANENTE"), rs.getInt("VALOREAZIONEPERMANENTE"));
				scontoazionepermanente1.put(rs.getString("SCONTOAZIONEPERMANENTE1"),
						rs.getInt("QTASCONTOAZIONEPERMANENTE1"));
				scontoazionepermanente2.put(rs.getString("SCONTOAZIONEPERMANENTE2"),
						rs.getInt("QTASCONTOAZIONEPERMANENTE2"));
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

	public HashMap<String, Integer> getEffettoimmediato1() {
		return effettoimmediato1;
	}

	public String getPerOgniCarta() {
		return perognicarta;
	}

	public HashMap<String, Integer> getAzioneImmediata() {
		return azioneimmediata;
	}

	public HashMap<String, Integer> getScontoAzioneImmediata1() {
		return scontoazioneimmediata1;
	}

	public HashMap<String, Integer> getScontoAzioneImmediata2() {
		return scontoazioneimmediata2;
	}

	public HashMap<String, Integer> getAzionePermanente() {
		return azionepermanente;
	}

	public HashMap<String, Integer> getScontoAzionepermanente1() {
		return scontoazionepermanente1;
	}

	public HashMap<String, Integer> getScontoAzionepermanente2() {
		return scontoazionepermanente2;
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

	public void setEffettoImmediato1(HashMap<String, Integer> effettoimmediato1) {
		this.effettoimmediato1 = effettoimmediato1;
	}

	public void setAzioneImmediata(HashMap<String, Integer> azioneimmediata) {
		this.azioneimmediata = azioneimmediata;
	}

	public void setScontoAzioneImmediata1(HashMap<String, Integer> scontoazioneimmediata1) {
		this.scontoazioneimmediata1 = scontoazioneimmediata1;
	}

	public void setScontoAzioneImmediata2(HashMap<String, Integer> scontoazioneimmediata2) {
		this.scontoazioneimmediata2 = scontoazioneimmediata2;
	}

	public void setAzionePermanente(HashMap<String, Integer> azionepermanente) {
		this.azionepermanente = azionepermanente;
	}

	public void setScontoAzionePermanente1(HashMap<String, Integer> scontoazionepermanente1) {
		this.scontoazionepermanente1 = scontoazionepermanente1;
	}

	public void setScontoAzionePermanente2(HashMap<String, Integer> scontoazionepermanente2) {
		this.scontoazionepermanente2 = scontoazionepermanente2;
	}

	public void setTooltip(String string) {
		this.tooltip = string;
	}
	
	public String getTooltipString(){
		return tooltip;
	}
}
