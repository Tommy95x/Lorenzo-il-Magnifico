package server.element;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Tommy
 *
 *Classe che verra' posseduta da ogni giocatore e che conterra' tutti i punti per categoria di risorsa che il giocatore accumulera' durante la partita
 */
public class Portafoglio implements Serializable{

	private int legno;
	private int pietra;
	private int monete;
	private int servitori;
	private ArrayList<CartaSviluppo> carte = new ArrayList<CartaSviluppo>();
	private int puntiTot;
	private int puntiMilitari;
	private int puntiFede;
	
	public Portafoglio(){
		int i;
		for(i=0;i<2;i++){
			legno++;
			pietra++;
		}
		for(i=0;i<3;i++)
			servitori++;
		for(i=0;i<5;i++)
			monete++;
		puntiTot=0;
		puntiMilitari=0;
		puntiFede=0;
	}
	
	public void addServitori(int incr){
		servitori+=incr;
	}
	
	private void addMonete(int incr){
		monete+=incr;
	}
	
	private void addLengo(int incr){
		legno+=incr;
	}
	
	private void addPietra(int incr){
		pietra+=incr;
	}
	
	public void addCarta(CartaSviluppo c){
		carte.add(c);
	}
	
	public int getDimRisorse(String risorsa){
		switch(risorsa){
			case "legno":
				return legno;
			case "monete":
				return monete;
			case "pietra":
				return pietra;
			case "servitori":
				return servitori;
		}
		return 0;
	}
	
	public void addRis(String ris, int incr) {
		switch(ris){
		case "legno":
			addLengo(incr);
			break;
		case "monete":
			addMonete(incr);
			break;
		case "pietra":
			addPietra(incr);
			break;
		case "servitori":
			addServitori(incr);
			break;
	}
		
	}

	
}
