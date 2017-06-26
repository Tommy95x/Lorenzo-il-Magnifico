package server.element;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Tommy
 *
 *         Classe che verra' posseduta da ogni giocatore e che conterra' tutti i
 *         punti per categoria di risorsa che il giocatore accumulera' durante
 *         la partita
 */
public class Portafoglio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int legno;
	private int pietra;
	private int monete;
	private int servitori;
	private ArrayList<CartaSviluppo> carte = new ArrayList<CartaSviluppo>();
	private int puntiTot;
	private int puntiMilitari;
	private int puntiFede;

	public Portafoglio() {
		int i;
		for (i = 0; i < 2; i++) {
			legno++;
			pietra++;
		}
		for (i = 0; i < 3; i++)
			servitori++;
		for (i = 0; i < 5; i++)
			monete++;
		puntiTot = 0;
		puntiMilitari = 0;
		puntiFede = 0;
	}

	private boolean addServitori(int incr) {
		/*if (servitori == 0 && incr <= 0) {
			return false;
		}*/
		servitori += incr;
		if(servitori<0)
			servitori=0;
		return true;
	}

	// Rendere public solo per la verifica di test
	// public boolean addMonete(int incr){
	private boolean addMonete(int incr) {
		/*if (monete == 0 && incr <= 0) {
			return false;
		}else{*/
		monete += incr;
		if(monete<0)
			monete=0;
		return true;
		//}
	}

	private boolean addLengo(int incr) {
		/*if (legno <= 0 ) {
			return false;
		}else{*/
		legno += incr;
		if(legno<0)
			legno=0;
		return true;
		//}
	}

	private boolean addPietra(int incr) {
		/*if (pietra == 0 && incr <= 0) {
			return false;
		}else{*/
		pietra += incr;
		if(pietra<0)
			pietra=0;
		return true;
		//}
	}

	public void addCarta(CartaSviluppo c) {
		carte.add(c);
	}

	public int getDimRisorse(String risorsa) {
		switch (risorsa) {
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
		switch (ris) {
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

	public void addPunti(String tipo, int qta) {
		switch (tipo) {
		case "militari":
			puntiMilitari += qta;
			if(puntiMilitari<=0)
				puntiMilitari =0;
			break;
		case "vittoria":
			puntiTot += qta;
			if(puntiTot<=0)
				puntiTot =0;
			break;
		case "fede":
			puntiFede += qta;
			if(puntiFede<=0)
				puntiFede =0;
			break;
		}
	}

	public int getPunti(String tipo) {
		switch (tipo) {
		case "militari":
			return puntiMilitari;
		case "vittoria":
			return puntiTot;
		case "fede":
			return puntiFede;
		}
		return 0;
	}

}
