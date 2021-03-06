package server.element;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.control.Tooltip;

public class CartaEdifici extends CartaSviluppo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	private ArrayList<Effetto> effetti = new ArrayList<Effetto>();
	private Risorsa[] spendiRisorse = new Risorsa[3];
	private Risorsa[] prendiRisorse = new Risorsa[2];
	private ArrayList<Risorsa> acquisisciPunti = new ArrayList<Risorsa>();
	private String perognicarta;
	private String image;
	private String tooltip;

	public CartaEdifici() {
		super();
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
				effetti.add(new Effetto(nomeffetto, qtaeffetto, true, rs.getInt("TIPO"), rs.getInt("TIPOPERMANENTE")));
				nomeffetto = rs.getString("EFFETTOIMMEDIATO2").toLowerCase();
				qtaeffetto = rs.getInt("QTAEFFETTOIMMEDIATO2");
				effetti.add(new Effetto(nomeffetto, qtaeffetto, true, rs.getInt("TIPO"), rs.getInt("TIPOPERMANENTE")));
				spendiRisorse[0]=(new Risorsa(rs.getString("SPENDIRISORSA1"), rs.getInt("QTASPENDIRISORSA1")));
				spendiRisorse[1]=(new Risorsa(rs.getString("SPENDIRISORSA2"), rs.getInt("QTASPENDIRISORSA2")));
				spendiRisorse[2]=(new Risorsa(rs.getString("SPENDIRISORSA2"), rs.getInt("QTASPENDIRISORSA2")));
				prendiRisorse[0]=(new Risorsa(rs.getString("PRENDIRISORSA1"), rs.getInt("QTAPRENDIRISORSA1")));
				prendiRisorse[1]=(new Risorsa(rs.getString("PRENDIRISORSA2"), rs.getInt("QTAPRENDIRISORSA2")));
				costoAzione = rs.getInt("COSTOAZIONE");
				acquisisciPunti.add(new Risorsa(rs.getString("ACQUISISCIPUNTI"), rs.getInt("QTAACQUISISCIPUNTI")));
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

	public void setPerOgniCarta(String perognicarta) {
		this.perognicarta = perognicarta;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	
	public void setId(String ID){
		this.ID=ID;
	}
	
	public String getId(){
		return ID;
	}

	public ArrayList<Effetto> getEffetti() {
		return effetti;
	}
	
	public ArrayList<Risorsa> getAcquisisciPunti(){
		return acquisisciPunti;
	}
	
	public String getTooltipString() {
		return tooltip;
	}
	
	public Risorsa[] getSpendiRisorsa(){
		return spendiRisorse;
	}
	
	public Risorsa[] getPrendiRisorsa(){
		return prendiRisorse;
	}
}