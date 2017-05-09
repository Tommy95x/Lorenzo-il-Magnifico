package elements;

//Classe specifica della risorsa del legno
public class Legno extends Risorse{

	private String tipo;
	
	public Legno(boolean big) {
		super(big);
		setTipo("legno");
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	
}
