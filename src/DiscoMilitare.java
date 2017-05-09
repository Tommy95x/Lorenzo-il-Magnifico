
public class DiscoMilitare extends Disco{

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
