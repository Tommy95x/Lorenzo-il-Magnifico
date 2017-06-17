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
	private ArrayList<Effetto> effetti = new ArrayList<Effetto>();
	private String perognicarta;
	int scontoAzione=3;
	private String image;
	private String tooltip;
	private int tipo;
	private int tipopermanente;

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
				System.out.println("Carta personaggio preso nome");
				ID=rs.getString("ID");
				System.out.println("Carta personaggio preso id");
				costoMoneta = rs.getInt("COSTOMONETA");
				System.out.println("Carta personaggio preso costo moneta");
				setImage(rs.getString("IMMAGINE"));
				System.out.println("Carta personaggio presa immagine");
				setTooltip(rs.getString("DESCRIZIONE"));
				System.out.println("Carta personaggio preso tooltip");
				nomeffetto=rs.getString("EFFETTOIMMEDIATO").toLowerCase();
				System.out.println("Nome Effetto: "+nomeffetto);
				qtaeffetto=rs.getInt("QTAEFFETTOIMMEDIATO");
				System.out.println("Quantità Effetto: "+qtaeffetto);
				tipo=rs.getInt("TIPO");
				System.out.println("Tipo carta azione immediata: "+tipo);
				tipopermanente=rs.getInt("TIPOPERMANENTE");
				System.out.println("Tipo carta azione permanente: "+tipopermanente);
				effetti.add(new Effetto(nomeffetto, qtaeffetto, true, tipo, tipopermanente));
				System.out.println("Carta personaggio preso effetto 1");
				perognicarta = rs.getString("PEROGNICARTA");
				System.out.println("Carta personaggio preso perognicarta");
				effetti.add(new Effetto(rs.getString("AZIONEIMMEDIATA").toLowerCase(), rs.getInt("VALOREAZIONEIMMEDIATA"), true, rs.getInt("TIPO"), rs.getInt("TIPOPERMANENTE")));
				System.out.println("Carta personaggio presa azione immediata");
				effetti.add(new Effetto(rs.getString("AZIONEPERMANENTE").toLowerCase(), rs.getInt("VALOREAZIONEPERMANENTE"), false, rs.getInt("TIPO"), rs.getInt("TIPOPERMANENTE")));
				System.out.println("Carta personaggio presa azione permanente");
			}
			rs.close();
			stmt.close();
			System.out.println("chiuso statement");
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
