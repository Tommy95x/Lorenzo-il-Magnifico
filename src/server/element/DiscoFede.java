package server.element;

public class DiscoFede extends Disco{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Disco che sar� piazzato nel tabellone sulla linea della fede
	private int puntiFede;
	
	public DiscoFede(String color) {
		super(color);
		puntiFede=0;
	}

	public void setPuntiFede(int punti) {
		puntiFede=puntiFede+punti;
	}
	
	public int getPuntiFede() {
		return puntiFede;
	}
}
