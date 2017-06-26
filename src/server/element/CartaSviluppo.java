package server.element;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import javafx.scene.control.Tooltip;

/*
*Classe base delle carte presenti nel gioco. Possiedera' tutti gli attributi e metodi comuni a tutti i tipi di carte sviluppo all'interno del gioco stesso.
*Questa classe per il settaggio di una generica carta comunichera' con il database che contiene tutti i tipi di carte presenti.
*/
public class CartaSviluppo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String image;
	private String name;
	private int costoAzione;
	@SuppressWarnings("unused")
	private ArrayList<Effetto> effetti;
	private int costoMoneta;
	private int costoLegno;
	private int costoPietra;
	private int costoServitori;
	private Risorsa[] spendiRisorse = new Risorsa[3];
	private Risorsa[] prendiRisorse = new Risorsa[2];
	private ArrayList<Risorsa> acquisisciPunti;
	private String perognicarta;
	int scontoAzione = 3;
	private int costoPuntiMilitari;
	private int puntiMilitariRichiesti;
	private int puntiVittoria;
	private String tooltip;
	private String ID;

	public CartaSviluppo(String string, String string2) {
		this.tooltip = string;
		this.image = string2;
	}

	public CartaSviluppo() {
		// TODO Auto-generated constructor stub
	}

	public void setCarta(Connection connection, String query) {

	}

	public String getImage() {
		return image;
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

	public ArrayList<Risorsa> getAcquisisciPunti() {
		return acquisisciPunti;
	}

	public String getPerOgniCarta() {
		return perognicarta;
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

	public int getCostoAzione() {
		return costoAzione;
	}

	public Tooltip getTooltip() {
		return new Tooltip(tooltip);
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
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

	public void setCostoAzione(int costoAzione) {
		this.costoAzione = costoAzione;
	}

	public void setPerOgniCarta(String perognicarta) {
		this.perognicarta = perognicarta;
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

	public void setImage(String url) {
		image = url;
	}

	public String getTooltipString() {
		return tooltip;
	}

	public ArrayList<Effetto> getEffetti() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setId(String ID) {
		this.ID = ID;
	}

	public String getId() {
		return ID;
	}

	public int getScontoAzione() {
		return scontoAzione;
	}

	public Risorsa[] getSpendiRisorsa() {
		return spendiRisorse;
	}

	public Risorsa[] getPrendiRisorsa() {
		return prendiRisorse;
	}
}