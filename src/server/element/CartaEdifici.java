package server.element;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;

public class CartaEdifici extends CartaSviluppo {

	// Le carte edifici possiedono un costo che deve essere pagato con le
	// risorse del giocatore che intende possederle
	private int costoMoneta;
	private int costoLegno;
	private int costoPietra;
	private int costoServitori;
	private int costoAzione;
	private String name;
	private String ID;
	private String nomeffetto;
	private int qtaeffetto;
	private ArrayList<Effetto> effetti;
	private ArrayList<Risorsa> spendiRisorse;
	private ArrayList<Risorsa> prendiRisorse;
	private HashMap<String, Integer> spendirisorsa1 = new HashMap<String, Integer>();
	private HashMap<String, Integer> spendirisorsa2 = new HashMap<String, Integer>();
	private HashMap<String, Integer> spendirisorsa3 = new HashMap<String, Integer>();
	private HashMap<String, Integer> prendirisorsa1 = new HashMap<String, Integer>();
	private HashMap<String, Integer> prendirisorsa2 = new HashMap<String, Integer>();
	private HashMap<String, Integer> acquisiscipunti = new HashMap<String, Integer>();
	private String perognicarta;
	private String image;
	private String tooltip;

	public CartaEdifici(int costoMoneta, int costoLegno, int costoPietra, int costoServitori, int costoAzione,
			String name, HashMap<String, Integer> effettoimmediato1, HashMap<String, Integer> effettoimmediato2,
			HashMap<String, Integer> spendirisorsa1, HashMap<String, Integer> spendirisorsa2,
			HashMap<String, Integer> spendirisorsa3, HashMap<String, Integer> prendirisorsa1,
			HashMap<String, Integer> prendirisorsa2, HashMap<String, Integer> acquisiscipunti, String perognicarta,
			String image, String tooltip) {
		this.costoMoneta = costoMoneta;
		this.costoLegno = costoLegno;
		this.costoPietra = costoPietra;
		this.costoServitori = costoServitori;
		this.costoAzione = costoAzione;
		this.name = name;
		this.spendirisorsa1 = spendirisorsa1;
		this.spendirisorsa2 = spendirisorsa2;
		this.spendirisorsa3 = spendirisorsa3;
		this.prendirisorsa1 = prendirisorsa1;
		this.prendirisorsa2 = prendirisorsa2;
		this.acquisiscipunti = acquisiscipunti;
		this.perognicarta = perognicarta;
		this.image = image;
		this.tooltip = tooltip;
	}

	public CartaEdifici() {

	}

	public void setCarta(Connection connection, String query) {
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				costoMoneta = rs.getInt("COSTOMONETA");
				costoLegno = rs.getInt("COSTOLEGNO");
				costoPietra = rs.getInt("COSTOPIETRA");
				costoServitori = rs.getInt("COSTOSERVITORI");
				name = rs.getString("NOME");
				ID=rs.getString("ID");
				nomeffetto = rs.getString("EFFETTOIMMEDIATO1").toLowerCase();
				qtaeffetto = rs.getInt("QTAEFFETTOIMMEDIATO1");
				effetti.add(new Effetto(nomeffetto, qtaeffetto, true));
				nomeffetto = rs.getString("EFFETTOIMMEDIATO2").toLowerCase();
				qtaeffetto = rs.getInt("QTAEFFETTOIMMEDIATO2");
				effetti.add(new Effetto(nomeffetto, qtaeffetto, true));
				spendiRisorse.add(new Risorsa(rs.getString("SPENDIRISORSA1"), rs.getInt("QTASPENDIRISORSA1")));
				spendiRisorse.add(new Risorsa(rs.getString("SPENDIRISORSA2"), rs.getInt("QTASPENDIRISORSA2")));
				spendiRisorse.add(new Risorsa(rs.getString("SPENDIRISORSA2"), rs.getInt("QTASPENDIRISORSA2")));
				prendiRisorse.add(new Risorsa(rs.getString("PRENDIRISORSA1"), rs.getInt("QTAPRENDIRISORSA1")));
				prendiRisorse.add(new Risorsa(rs.getString("PRENDIRISORSA2"), rs.getInt("QTAPRENDIRISORSA2")));
				costoAzione = rs.getInt("COSTOAZIONE");
				acquisiscipunti.put(rs.getString("ACQUISISCIPUNTI"), rs.getInt("QTAACQUISISCIPUNTI"));
				perognicarta = rs.getString("PEROGNICARTA");
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

	public int getCostoAzione() {
		return costoAzione;
	}

	public int getCostoMoneta() {
		return costoMoneta;
	}

	public int getCostoLegno() {
		return costoLegno;
	}

	public int getCostoPietra() {
		return costoPietra;
	}

	public int getCostoServitori() {
		return costoServitori;
	}

	public HashMap<String, Integer> getSpendiRisorsa1() {
		return spendirisorsa1;
	}

	public HashMap<String, Integer> getSpendiRisorsa2() {
		return spendirisorsa2;
	}

	public HashMap<String, Integer> getSpendiRisorsa3() {
		return spendirisorsa3;
	}

	public HashMap<String, Integer> getPrendiRisorsa1() {
		return prendirisorsa1;
	}

	public HashMap<String, Integer> getPrendiRisorsa2() {
		return prendirisorsa2;
	}

	public HashMap<String, Integer> getAcquisisciPunti() {
		return acquisiscipunti;
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

	public void setCostoAzione(int costoAzione) {
		this.costoAzione = costoAzione;
	}

	public void setCostoMoneta(int costoMoneta) {
		this.costoMoneta = costoMoneta;
	}

	public void setCostoPietra(int costoPietra) {
		this.costoPietra = costoPietra;
	}

	public void setCostoLegno(int costoLegno) {
		this.costoLegno = costoLegno;
	}

	public void setCostoServitori(int costoServitori) {
		this.costoServitori = costoServitori;
	}

	public void setSpendiRisorsa1(HashMap<String, Integer> spendirisorsa1) {
		this.spendirisorsa1 = spendirisorsa1;
	}

	public void setSpendiRisorsa2(HashMap<String, Integer> spendirisorsa2) {
		this.spendirisorsa2 = spendirisorsa2;
	}

	public void setSpendiRisorsa3(HashMap<String, Integer> spendirisorsa3) {
		this.spendirisorsa3 = spendirisorsa3;
	}

	public void setprendiRisorsa1(HashMap<String, Integer> prendirisorsa1) {
		this.prendirisorsa1 = prendirisorsa1;
	}

	public void setprendiRisorsa2(HashMap<String, Integer> prendirisorsa2) {
		this.prendirisorsa2 = prendirisorsa2;
	}

	public void setAcquisisciPunti(HashMap<String, Integer> acquisiscipunti) {
		this.acquisiscipunti = acquisiscipunti;
	}

	public void setPerOgniCarta(String perognicarta) {
		this.perognicarta = perognicarta;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	
	public void setID(String ID){
		this.ID=ID;
	}
	
	public String getID(){
		return ID;
	}

	public String getTooltipString() {
		return tooltip;
	}
}