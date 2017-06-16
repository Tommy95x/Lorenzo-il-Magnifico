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
	private HashMap<String, Integer> effettoimmediato1 = new HashMap<String, Integer>();
	private HashMap<String, Integer> effettoimmediato2 = new HashMap<String, Integer>();
	private HashMap<String, Integer> effettopermanente1 = new HashMap<String, Integer>();
	private HashMap<String, Integer> effettopermanente2 = new HashMap<String, Integer>();
	private HashMap<String, Integer> effettopermanente3 = new HashMap<String, Integer>();
	private int costoMoneta;
	private int costoLegno;
	private int costoPietra;
	private int costoServitori;
	private HashMap<String, Integer> spendirisorsa1 = new HashMap<String, Integer>();
	private HashMap<String, Integer> spendirisorsa2 = new HashMap<String, Integer>();
	private HashMap<String, Integer> spendirisorsa3 = new HashMap<String, Integer>();
	private HashMap<String, Integer> prendirisorsa1 = new HashMap<String, Integer>();
	private HashMap<String, Integer> prendirisorsa2 = new HashMap<String, Integer>();
	private HashMap<String, Integer> acquisiscipunti = new HashMap<String, Integer>();
	private String perognicarta;
	private HashMap<String, Integer> azioneimmediata = new HashMap<String, Integer>();
	private HashMap<String, Integer> scontoazioneimmediata1 = new HashMap<String, Integer>();
	private HashMap<String, Integer> scontoazioneimmediata2 = new HashMap<String, Integer>();
	private HashMap<String, Integer> azionepermanente = new HashMap<String, Integer>();
	private HashMap<String, Integer> scontoazionepermanente1 = new HashMap<String, Integer>();
	private HashMap<String, Integer> scontoazionepermanente2 = new HashMap<String, Integer>();
	private int costoPuntiMilitari;
	private int puntiMilitariRichiesti;
	private HashMap<String, Integer> effettoimmediato3 = new HashMap<String, Integer>();
	private int puntiVittoria;
	private Tooltip tooltip;
	
	public void setCarta(Connection connection, String query ) {
	
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
	
	public HashMap<String, Integer> getEffettoimmediato1(){
		return effettoimmediato1;
	}
	
	public HashMap<String, Integer> getEffettoimmediato2(){
		return effettoimmediato2;
	}
	
	public HashMap<String, Integer> getEffettopermanente1(){
		return effettopermanente1;
	}
	
	public HashMap<String, Integer> getEffettopermanente2(){
		return effettopermanente2;
	}
	
	public HashMap<String, Integer> getEffettopermanente3(){
		return effettopermanente3;
	}
	
	public HashMap<String, Integer> getSpendiRisorsa1(){
		return spendirisorsa1;
	}
	
	public HashMap<String, Integer> getSpendiRisorsa2(){
		return spendirisorsa2;
	}
	
	public HashMap<String, Integer> getSpendiRisorsa3(){
		return spendirisorsa3;
	}
	
	public HashMap<String, Integer> getPrendiRisorsa1(){
		return prendirisorsa1;
	}
	
	public HashMap<String, Integer> getPrendiRisorsa2(){
		return prendirisorsa2;
	}
	
	public HashMap<String, Integer> getAcquisisciPunti(){
		return acquisiscipunti;
	}
	
	public String getPerOgniCarta(){
		return perognicarta;
	}
	
	public HashMap<String, Integer> getAzioneImmediata(){
		return azioneimmediata;
	}
	
	public HashMap<String, Integer> getScontoAzioneImmediata1(){
		return scontoazioneimmediata1;
	}
	
	public HashMap<String, Integer> getScontoAzioneImmediata2(){
		return scontoazioneimmediata2;
	}
	
	public HashMap<String, Integer> getAzionePermanente(){
		return azionepermanente;
	}
	
	public HashMap<String, Integer> getScontoAzionepermanente1(){
		return scontoazionepermanente1;
	}
	
	public HashMap<String, Integer> getScontoAzionepermanente2(){
		return scontoazionepermanente2;
	}
	
	public int getCostoPuntiMilitari(){
		return costoPuntiMilitari;
	}
	
	public int getPuntiMilitariRichiesti(){
		return puntiMilitariRichiesti;
	}
	
	public HashMap<String, Integer> getEffettoimmediato3(){
		return effettoimmediato3;
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
	
	public void setEffettoImmediato1(HashMap<String, Integer> effettoimmediato1){
		this.effettoimmediato1=effettoimmediato1;
	}
	
	public void setEffettoImmediato2(HashMap<String, Integer> effettoimmediato2){
		this.effettoimmediato2=effettoimmediato2;
	}
	
	public void setEffettoPermanente1(HashMap<String, Integer> effettopermanente1){
		this.effettopermanente1=effettopermanente1;
	}
	
	public void setEffettoPermanente2(HashMap<String, Integer> effettopermanente2){
		this.effettopermanente2=effettopermanente2;
	}
	
	public void setEffettoPermanente3(HashMap<String, Integer> effettopermanente3){
		this.effettopermanente3=effettopermanente3;
	}
	
	public void setCostoAzione(int costoAzione){
		this.costoAzione=costoAzione;
	}
	
	public void setSpendiRisorsa1(HashMap<String, Integer> spendirisorsa1){
		this.spendirisorsa1=spendirisorsa1;
	}
	
	public void setSpendiRisorsa2(HashMap<String, Integer> spendirisorsa2){
		this.spendirisorsa2=spendirisorsa2;
	}
	
	public void setSpendiRisorsa3(HashMap<String, Integer> spendirisorsa3){
		this.spendirisorsa3=spendirisorsa3;
	}
	
	public void setprendiRisorsa1(HashMap<String, Integer> prendirisorsa1){
		this.prendirisorsa1=prendirisorsa1;
	}
	
	public void setprendiRisorsa2(HashMap<String, Integer> prendirisorsa2){
		this.prendirisorsa2=prendirisorsa2;
	}
	
	public void setAcquisisciPunti(HashMap<String, Integer> acquisiscipunti){
		this.acquisiscipunti=acquisiscipunti;
	}
	
	public void setPerOgniCarta(String perognicarta){
		this.perognicarta=perognicarta;
	}
	
	public void setAzioneImmediata(HashMap<String, Integer> azioneimmediata){
		this.azioneimmediata=azioneimmediata;
	}
	
	public void setScontoAzioneImmediata1(HashMap<String, Integer> scontoazioneimmediata1){
		this.scontoazioneimmediata1=scontoazioneimmediata1;
	}
	
	public void setScontoAzioneImmediata2(HashMap<String, Integer> scontoazioneimmediata2){
		this.scontoazioneimmediata2=scontoazioneimmediata2;
	}
	
	public void setAzionePermanente(HashMap<String, Integer> azionepermanente){
		this.azionepermanente=azionepermanente;
	}
	
	public void setScontoAzionePermanente1(HashMap<String, Integer> scontoazionepermanente1){
		this.scontoazionepermanente1=scontoazionepermanente1;
	}
	
	public void setScontoAzionePermanente2(HashMap<String, Integer> scontoazionepermanente2){
		this.scontoazionepermanente2=scontoazionepermanente2;
	}
	
	public void setCostoPuntiMilitari(int costoPuntiMilitari){
		this.costoPuntiMilitari=costoPuntiMilitari;
	}
	
	public void setPuntiMilitariRichiesti(int puntiMilitariRichiesti){
		this.puntiMilitariRichiesti=puntiMilitariRichiesti;
	}
	
	public void setEffettoImmediato3(HashMap<String, Integer> effettoimmediato3){
		this.effettoimmediato3=effettoimmediato3;
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

}