package server.element;

public class Disco {

	/*Attributi e metodi comuni dei differenti dischi oltre che ad essere il disco utilizzato per settare l'ordine di gioco
	  dei giocatori*/
	private String color;
	private int ordine;
	
	public Disco(String color) {
		this.color=color;
	}
	
	public String getColor() {
		return color;
	}
	
	public int getOrdine() {
		return ordine;
	}
	
	public void setOrdine() {
		
	}
	
}
