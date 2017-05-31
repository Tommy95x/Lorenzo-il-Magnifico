package server.element;

//Classe specifica delle monete
public class Monete extends Risorse{

	private String tipo;
	
	public Monete() {
		setTipo("monete");
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
