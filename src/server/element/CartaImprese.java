package server.element;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;

public class CartaImprese extends CartaSviluppo{

	//Le carte impresa hanno due differenti costi impresa e il giocatore pu� decidere quale dei due costi scegliere tra risorse o punti militari
	private int costoLegno;
	private int costoPietra;
	private int costoMoneta;
	private int costoPuntiMilitari;
	private int puntiMilitariRichiesti;
	private int costoServitori;
	private String name; 
	private String nomeffetto;
	private int qtaeffetto;
	private HashMap<String, Integer> effettoimmediato1 = new HashMap<String, Integer>();
	private HashMap<String, Integer> effettoimmediato2 = new HashMap<String, Integer>();
	private HashMap<String, Integer> effettoimmediato3 = new HashMap<String, Integer>();
	private HashMap<String, Integer> azioneimmediata = new HashMap<String, Integer>();
	private int puntiVittoria;
	private String image;
	private String tooltip;
	
		public void setCarta(Connection connection, String query ) {
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				name=rs.getString("NOME");
				costoLegno=rs.getInt("COSTOLEGNO");
				costoPietra=rs.getInt("COSTOPIETRA");
				costoMoneta=rs.getInt("COSTOMONETA");
				costoServitori=rs.getInt("COSTOSERVITORI");
				costoPuntiMilitari=rs.getInt("COSTOPUNTIMILITARI");
				puntiMilitariRichiesti=rs.getInt("REQUISITOPUNTIMILITARI");
				nomeffetto=rs.getString("EFFETTOIMMEDIATO1");
				qtaeffetto=rs.getInt("QTAEFFETTOIMMEDIATO1");
				effettoimmediato1.put(nomeffetto, qtaeffetto);
				nomeffetto=rs.getString("EFFETTOIMMEDIATO2");
				qtaeffetto=rs.getInt("QTAEFFETTOIMMEDIATO2");
				effettoimmediato2.put(nomeffetto, qtaeffetto);
				nomeffetto=rs.getString("EFFETTOIMMEDIATO3");
				qtaeffetto=rs.getInt("QTAEFFETTOIMMEDIATO3");
				effettoimmediato3.put(nomeffetto, qtaeffetto);
				azioneimmediata.put(rs.getString("AZIONEIMMEDIATA"),rs.getInt("VALOREAZIONEIMMEDIATA"));
				puntiVittoria=rs.getInt("PUNTIVITTORIA");
				setImage(rs.getString("IMMAGINE"));
				setTooltip(rs.getString("DESCRIZIONE"));
				}
				rs.close();
				stmt.close();
				connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
		public void setImage(String url){
			image = url;
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
		
		public int getCostoPuntiMilitari(){
			return costoPuntiMilitari;
		}
		
		public int getPuntiMilitariRichiesti(){
			return puntiMilitariRichiesti;
		}
		
		public HashMap<String, Integer> getEffettoimmediato1(){
			return effettoimmediato1;
		}
		
		public HashMap<String, Integer> getEffettoimmediato2(){
			return effettoimmediato2;
		}
		
		public HashMap<String, Integer> getEffettoimmediato3(){
			return effettoimmediato3;
		}
		
		public HashMap<String, Integer> getAzioneImmediata(){
			return azioneimmediata;
		}
		
		public int getPuntiVittoria(){
			return puntiVittoria;
		}
		
		public Tooltip getTooltip(){
			Tooltip tooltip = new Tooltip("");
			tooltip.setText(this.tooltip);
			return tooltip;
		}
		
		public String getImage(){
			return image;
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
		
		public void setCostoPuntiMilitari(int costoPuntiMilitari){
			this.costoPuntiMilitari=costoPuntiMilitari;
		}
		
		public void setPuntiMilitariRichiesti(int puntiMilitariRichiesti){
			this.puntiMilitariRichiesti=puntiMilitariRichiesti;
		}
		
		public void setEffettoImmediato1(HashMap<String, Integer> effettoimmediato1){
			this.effettoimmediato1=effettoimmediato1;
		}
		
		public void setEffettoImmediato2(HashMap<String, Integer> effettoimmediato2){
			this.effettoimmediato2=effettoimmediato2;
		}
		
		public void setEffettoImmediato3(HashMap<String, Integer> effettoimmediato3){
			this.effettoimmediato3=effettoimmediato3;
		}
		
		public void setAzioneImmediata(HashMap<String, Integer> azioneimmediata){
			this.azioneimmediata=azioneimmediata;
		}
		
		public void setPuntiVittoria(int puntiVittoria){
			this.puntiVittoria=puntiVittoria;
		}
		
		public void setTooltip(String tooltip) {
			this.tooltip = tooltip;
		}
}
