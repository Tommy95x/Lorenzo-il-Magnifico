package server.element;

public class DiscoMilitare extends Disco{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Disco che servirà per il conto dei punti militari accumulati
	private int puntiMilitari;
	
	public DiscoMilitare(String color) {
		super(color);
		puntiMilitari=0;
	}

	public void setPuntiMilitari(int punti) {
		puntiMilitari=puntiMilitari+punti;
	}
	
	public int getPuntiMilitari() {
		return puntiMilitari;
	}
}
