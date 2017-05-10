package server.element;
import java.io.Serializable;

public class Dado implements Serializable{

	private String color;
	private int valore;
	
	public Dado(String color) {
		this.color=color;
	}
	
	//Metodi per il get del colore
	public String getColor() {
		return color;
	}
	
	//Set del valore dopo il lancio del primo giocatore del turno
	public void setValore() {
		valore = (int) (Math.random()*6);
	}
	
	public int getValore() {
		return valore;
	}
	
}
