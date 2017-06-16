package server.element;


import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;

/*
*Classe base delle carte presenti nel gioco. Possiedera' tutti gli attributi e metodi comuni a tutti i tipi di carte sviluppo all'interno del gioco stesso.
*Questa classe per il settaggio di una generica carta comunichera' con il database che contiene tutti i tipi di carte presenti.
*/
public class CartaSviluppo implements Serializable{

	private String image;
	private String name;
	private int costoAzione;
	private ArrayList<Effetto> effetti;
	private int costoMoneta;
	private int costoLegno;
	private int costoPietra;
	private int costoServitori;
	private ArrayList<Risorsa> spendiRisorse;
	private ArrayList<Risorsa> prendiRisorse;
	private ArrayList<Risorsa> acquisisciPunti;
	private String perognicarta;
	private ArrayList<Azione> azioni;
	private ArrayList<Effetto> scontoAzioni;
	private int costoPuntiMilitari;
	private int puntiMilitariRichiesti;
	private int puntiVittoria;
	private Tooltip tooltip;
	private String ID;
	
	public void setCarta(Connection connection, String query) {
	
	}

	public String getImage() {
		return image;
	}

	public String getNameCard() {
		return name;
	}
	
	public int getCostoMoneta(){
		return costoMoneta;
	}
	
	public int getCostoLegno(){
		return costoLegno;
	}
	
	public int getCostoPietra(){
		return costoPietra;
	}
	
	public int getCostoServitori(){
		return costoServitori;
	}
	
	public ArrayList<Risorsa> getAcquisisciPunti(){
		return acquisisciPunti;
	}
	
	public String getPerOgniCarta(){
		return perognicarta;
	}
	
	public int getCostoPuntiMilitari(){
		return costoPuntiMilitari;
	}
	
	public int getPuntiMilitariRichiesti(){
		return puntiMilitariRichiesti;
	}
	
	public int getPuntiVittoria(){
		return puntiVittoria;
	}
	
	public int getCostoAzione(){
		return costoAzione;
	}

	public Tooltip getTooltip() {
		return tooltip;
	}

	public void setTooltip(Tooltip tooltip) {
		this.tooltip = tooltip;
	}

	public void setNameCard(String name){
		this.name=name;
	}
	
	public void setCostoMoneta(int costoMoneta){
		this.costoMoneta=costoMoneta;
	}
	
	public void setCostoPietra(int costoPietra){
		this.costoPietra=costoPietra;
	}
	
	public void setCostoLegno(int costoLegno){
		this.costoLegno=costoLegno;
	}
	
	public void setCostoServitori(int costoServitori){
		this.costoServitori=costoServitori;
	}
		
	public void setCostoAzione(int costoAzione){
		this.costoAzione=costoAzione;
	}
	
	public void setPerOgniCarta(String perognicarta){
		this.perognicarta=perognicarta;
	}
	
	public void setCostoPuntiMilitari(int costoPuntiMilitari){
		this.costoPuntiMilitari=costoPuntiMilitari;
	}
	
	public void setPuntiMilitariRichiesti(int puntiMilitariRichiesti){
		this.puntiMilitariRichiesti=puntiMilitariRichiesti;
	}
	
	public void setPuntiVittoria(int puntiVittoria){
		this.puntiVittoria=puntiVittoria;
	}
	
	public void setImage(String url){
		image =  url;
	}

	public String getTooltipString() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Effetto> getEffetti() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ArrayList<Effetto> getScontoAzioni() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ArrayList<Azione> getAzione() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setId(String ID){
		this.ID=ID;
	}
	
	public String getId(){
		return ID;
	}

}