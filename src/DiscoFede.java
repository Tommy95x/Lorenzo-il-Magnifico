
public class DiscoFede extends Disco{

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
