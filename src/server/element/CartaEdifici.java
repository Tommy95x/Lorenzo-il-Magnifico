package server.element;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;

public class CartaEdifici extends CartaSviluppo{

	//Le carte edifici possiedono un costo che deve essere pagato con le risorse del giocatore che intende possederle
	private int costoMoneta;
	private int costoLegno;
	private int costoPietra;
	private int costoServitori;
	private int costoAzione;
	private String name; 
	private String nomeffetto;
	private int qtaeffetto;
	private HashMap<String, Integer> effettoimmediato1 = new HashMap<String, Integer>();
	private HashMap<String, Integer> effettoimmediato2 = new HashMap<String, Integer>();
	private HashMap<String, Integer> spendirisorsa1 = new HashMap<String, Integer>();
	private HashMap<String, Integer> spendirisorsa2 = new HashMap<String, Integer>();
	private HashMap<String, Integer> spendirisorsa3 = new HashMap<String, Integer>();
	private HashMap<String, Integer> prendirisorsa1 = new HashMap<String, Integer>();
	private HashMap<String, Integer> prendirisorsa2 = new HashMap<String, Integer>();
	private HashMap<String, Integer> acquisiscipunti = new HashMap<String, Integer>();
	private String perognicarta;
	private javafx.scene.image.Image image;
	private Tooltip tooltip;
	
	public void setCarta(Connection connection, String query ) {
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				costoMoneta=rs.getInt("COSTOMONETA");
				costoLegno=rs.getInt("COSTOLEGNO");
				costoPietra=rs.getInt("COSTOPIETRA");
				costoServitori=rs.getInt("COSTOSERVITORI");
				name=rs.getString("NOME");
				nomeffetto=rs.getString("EFFETTOIMMEDIATO1");
				qtaeffetto=rs.getInt("QTAEFFETTOIMMEDIATO1");
				effettoimmediato1.put(nomeffetto, qtaeffetto);
				nomeffetto=rs.getString("EFFETTOIMMEDIATO2");
				qtaeffetto=rs.getInt("QTAEFFETTOIMMEDIATO2");
				effettoimmediato2.put(nomeffetto, qtaeffetto);
				spendirisorsa1.put(rs.getString("SPENDIRISORSA1"), rs.getInt("QTASPENDIRISORSA1"));
				spendirisorsa2.put(rs.getString("SPENDIRISORSA2"), rs.getInt("QTASPENDIRISORSA2"));
				spendirisorsa3.put(rs.getString("SPENDIRISORSA3"), rs.getInt("QTASPENDIRISORSA3"));
				prendirisorsa1.put(rs.getString("PRENDIRISORSA1"), rs.getInt("QTAPRENDIRISORSA1"));
				prendirisorsa2.put(rs.getString("PRENDIRISORSA2"), rs.getInt("QTAPRENDIRISORSA2"));
				costoAzione=rs.getInt("COSTOAZIONE");
				acquisiscipunti.put(rs.getString("ACQUISISCIPUNTI"),rs.getInt("QTAACQUISISCIPUNTI"));
				perognicarta=rs.getString("PEROGNICARTA");
				setImage(rs.getString("IMMAGINE"));
				tooltip.setText(rs.getString("DESCRIZIONE"));
				}
				rs.close();
				stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setImage(String url){
		image = new Image(getClass().getResourceAsStream(url));
	}
	
	public String getNameCard() {
		return name;
	}
	
	public int getCostoAzione(){
		return costoAzione;
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
	
	public Tooltip getTooltip(){
		return tooltip;
	}
	
	public Image getImage(){
		return image;
	}
	
	public void setNameCard(String name){
		this.name=name;
	}
	
	public void setCostoAzione(int costoAzione){
		this.costoAzione=costoAzione;
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
	
	public void setTooltip(Tooltip tooltip) {
		this.tooltip = tooltip;
	}
}