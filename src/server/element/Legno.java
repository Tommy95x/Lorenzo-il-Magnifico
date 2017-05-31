package server.element;

//Classe specifica della risorsa del legno
public class Legno extends Risorse{

	private String tipo;
	
	public Legno() {
		setTipo("legno");
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	
}
