package server.element;

import java.util.ArrayList;

/*Classe che verr� posseduta da ogni giocatore e che conterr� tutti i punti per categoria di risorsa che il 
  giocatore accumuler� durante la partita*/
public class Portafoglio {

	private ArrayList<Legno> legno = new ArrayList<Legno>();
	private ArrayList<Pietra> pietra = new ArrayList<Pietra>();
	private ArrayList<Monete> monete = new ArrayList<Monete>();
	private ArrayList<Servitori> servitori = new ArrayList<Servitori>();
	
	public Portafoglio(){
		int i;
		for(i=0;i<2;i++)
			legno.add(new Legno(false));
		for(i=0;i<2;i++)
			pietra.add(new Pietra(false));
		for(i=0;i<3;i++)
			monete.add(new Monete(false));
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
	
	public int getDimRisorse(String risorsa){
		int dim = 0;
		int i;
		switch(risorsa){
			case "legno":
				for(i=0;i<legno.size();i++){
					dim=dim+legno.get(i).getValue();
				}
				break;
			case "monete":
				for(i=0;i<monete.size();i++){
					dim=dim+monete.get(i).getValue();
				}
				break;
			case "pietra":
				for(i=0;i<pietra.size();i++){
					dim=dim+pietra.get(i).getValue();
				}
				break;
			case "servitori":
				for(i=0;i<servitori.size();i++){
					dim=dim+servitori.get(i).getValue();
				}
				break;
		}
		return dim;
	}
	
	private int getDimPortafoglio(){
		int dim=0;
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
