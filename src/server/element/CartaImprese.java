package server.element;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.control.Tooltip;

public class CartaImprese extends CartaSviluppo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Le carte impresa hanno due differenti costi impresa e il giocatore pu�
	// decidere quale dei due costi scegliere tra risorse o punti militari
	private int costoLegno;
	private int costoPietra;
	private int costoMoneta;
	private int costoPuntiMilitari;
	private int puntiMilitariRichiesti;
	private int costoServitori;
	private String name;
	private String ID;
	private String nomeffetto;
	private int qtaeffetto;
	private ArrayList<Effetto> effetti = new ArrayList<Effetto>();
	private int puntiVittoria;
	private String image;
	private String tooltip;

	public CartaImprese() {

	}

	public void setCarta(Connection connection, String query) {
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				ID=rs.getString("ID");
				name = rs.getString("NOME");
				costoLegno = rs.getInt("COSTOLEGNO");
				costoPietra = rs.getInt("COSTOPIETRA");
				costoMoneta = rs.getInt("COSTOMONETA");
				costoServitori = rs.getInt("COSTOSERVITORI");
				costoPuntiMilitari = rs.getInt("COSTOPUNTIMILITARI");
				puntiMilitariRichiesti = rs.getInt("REQUISITOPUNTIMILITARI");
				nomeffetto = rs.getString("EFFETTOIMMEDIATO1");
				qtaeffetto = rs.getInt("QTAEFFETTOIMMEDIATO1");
				effetti.add(new Effetto(nomeffetto, qtaeffetto, true, rs.getInt("TIPO"), rs.getInt("TIPOPERMANENTE")));
				nomeffetto = rs.getString("EFFETTOIMMEDIATO2");
				qtaeffetto = rs.getInt("QTAEFFETTOIMMEDIATO2");
				effetti.add(new Effetto(nomeffetto, qtaeffetto, true, rs.getInt("TIPO"), rs.getInt("TIPOPERMANENTE")));
				nomeffetto = rs.getString("EFFETTOIMMEDIATO3");
				qtaeffetto = rs.getInt("QTAEFFETTOIMMEDIATO3");
				effetti.add(new Effetto(nomeffetto, qtaeffetto, true, rs.getInt("TIPO"), rs.getInt("TIPOPERMANENTE")));
				puntiVittoria = rs.getInt("PUNTIVITTORIA");
				effetti.add(new Effetto(rs.getString("AZIONEIMMEDIATA").toLowerCase(), rs.getInt("VALOREAZIONEIMMEDIATA"), true, rs.getInt("TIPO"), rs.getInt("TIPOPERMANENTE")));
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

	public int getCostoLegno() {
		return costoLegno;
	}

	public int getCostoPietra() {
		return costoPietra;
	}

	public int getCostoServitori() {
		return costoServitori;
	}

	public int getCostoPuntiMilitari() {
		return costoPuntiMilitari;
	}

	public int getPuntiMilitariRichiesti() {
		return puntiMilitariRichiesti;
	}
	
	public int getPuntiVittoria() {
		return puntiVittoria;
	}

	public Tooltip getTooltip() {
		Tooltip tooltip = new Tooltip("");
		tooltip.setText(this.tooltip);
		return tooltip;
	}

	public String getImage() {
		return image;
	}
	
	public ArrayList<Effetto> getEffetti() {
		return effetti;
	}

	public void setNameCard(String name) {
		this.name = name;
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

	public void setCostoPuntiMilitari(int costoPuntiMilitari) {
		this.costoPuntiMilitari = costoPuntiMilitari;
	}

	public void setPuntiMilitariRichiesti(int puntiMilitariRichiesti) {
		this.puntiMilitariRichiesti = puntiMilitariRichiesti;
	}

	public void setPuntiVittoria(int puntiVittoria) {
		this.puntiVittoria = puntiVittoria;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public String getTooltipString() {
		return tooltip;
	}
	
	public void setId(String ID){
		this.ID=ID;
	}
	
	public String getId(){
		return ID;
	}
}