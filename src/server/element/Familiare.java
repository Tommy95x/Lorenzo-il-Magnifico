package server.element;

//Classe del familiare che verrà  piazzato dal giocatore sul tabellone per eseguire le azioni possibili 
public class Familiare extends FamiliareNeutro{

	public String diceColor;
	public int puntiAzione;
	
	public Familiare(String diceColor){
		this.diceColor=diceColor;
		setZeroPuntiAzione();
	}
	
	public void setZeroPuntiAzione(){
		puntiAzione=0;
	}
	
	public void setPuntiAzione(int puntiAzione){
		this.puntiAzione=puntiAzione;
	}
	
	public int getPuntiAzione(){
		return puntiAzione;
	}
}
