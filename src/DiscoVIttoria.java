
public class DiscoVIttoria extends Disco{

	private int punti;
	
	public DiscoVIttoria(String color) {
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
