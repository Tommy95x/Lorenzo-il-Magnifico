package server.element;

//Classe risorsa servitore
public class Servitori extends Risorse{

	private String tipo;
	
	public Servitori(boolean big) {
		super(big);
		setTipo("servitori");
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
