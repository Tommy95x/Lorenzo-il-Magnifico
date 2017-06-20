package server.element;

public class DiscoVittoria extends Disco{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Disco per il conteggio dei punti totali accumulati durante i turni
	private int punti;
	
	public DiscoVittoria(String color) {
		super(color);
		punti = 0;
	}

	public void setPuntiVittoria(int puntiTurno) {
		punti=punti+puntiTurno;
	}
	
	public int getPuntiVittoria() {
		return punti;
	}
	
}
