package elements;

//Classe specifica delle monete
public class Monete extends Risorse{

	private String tipo;
	
	public Monete(boolean big) {
		super(big);
		setTipo("monete");
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
