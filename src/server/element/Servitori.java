package server.element;

/**
 * @author Tommy
 * 
 *Classe che rappresenta le risorse servitore, ogni servitore speso durante la partita aumenta i punti per eseguire una mossa possibile
 */
public class Servitori extends Risorse{

	private String tipo;
	
	public Servitori() {
		setTipo("servitori");
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
