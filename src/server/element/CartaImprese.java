package server.element;

public class CartaImprese extends CartaSviluppo{

	//Le carte impresa hanno due differenti costi impresa e il giocatore pu� decidere quale dei due costi scegliere tra risorse o punti militari
	private int costoLegno;
	private int costoPietra;
	private int costoMoneta;
	private int costoPuntiMilitari;
	private int costoServitore;
	
	//A seconda della carta estratta dal DB verranno settati i costi della carta stessa
	public void setCosti() {
			
	}
	
}
