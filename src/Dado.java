import java.io.Serializable;

public class Dado implements Serializable{

	private String color;
	private int valore;
	
	public Dado(String colore) {
		this.color=color;
	}
	
	public String getColor() {
		return color;
	}
	
	public void setValore() {
		valore = (int) (Math.random()*6);
	}
	
	public int getValore() {
		return valore;
	}
	
}
