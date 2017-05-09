package elements;

//Classe specifica della risorsa pietra
public class Pietra extends Risorse{

	private String tipo;
	
	public Pietra(boolean big) {
		super(big);
		setTipo("pietra");
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
