package server.element;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Tommy
 *
 *Classe che verra' posseduta da ogni giocatore e che conterra' tutti i punti per categoria di risorsa che il giocatore accumulera' durante la partita
 */
public class Portafoglio implements Serializable{

	private ArrayList<Legno> legno = new ArrayList<Legno>();
	private ArrayList<Pietra> pietra = new ArrayList<Pietra>();
	private ArrayList<Monete> monete = new ArrayList<Monete>();
	private ArrayList<Servitori> servitori = new ArrayList<Servitori>();
	private ArrayList<CartaSviluppo> carte = new ArrayList<CartaSviluppo>();
	private int puntiTot;
	private int puntiMilitari;
	private int puntiFede;
	
	public Portafoglio(){
		int i;
		for(i=0;i<2;i++)
			legno.add(new Legno());
		for(i=0;i<2;i++)
			pietra.add(new Pietra());
		for(i=0;i<3;i++)
			servitori.add(new Servitori());
		for(i=0;i<5;i++)
			monete.add(new Monete());
		puntiTot=0;
		puntiMilitari=0;
		puntiFede=0;
	}
	
	public void addServitori(Servitori s){
		servitori.add(s);
	}
	
	public void addMonete(Monete m){
		monete.add(m);
	}
	
	public void addLengo(Legno l){
		legno.add(l);
	}
	
	public void addPietra(Pietra p){
		pietra.add(p);
	}
	
	public void addCarta(CartaSviluppo c){
		carte.add(c);
	}
	
	public int getDimRisorse(String risorsa){
		int dim = 0;
		int i;
		switch(risorsa){
			case "legno":
				dim=legno.size();
				break;
			case "monete":
				for(i=0;i<monete.size();i++){
					dim=monete.size();
				}
				break;
			case "pietra":
				for(i=0;i<pietra.size();i++){
					dim=pietra.size();
				}
				break;
			case "servitori":
				for(i=0;i<servitori.size();i++){
					dim=servitori.size();
				}
				break;
		}
		return dim;
	}
	
	public void addRis(Risorse ris) {
		switch(ris.getTipo()){
		case "legno":
			legno.add((Legno) ris);
			break;
		case "monete":
			monete.add((Monete) ris);
			break;
		case "pietra":
			pietra.add((Pietra) ris);
			break;
		case "servitori":
			servitori.add((Servitori) ris);
			break;
	}
		
	}

	
}
