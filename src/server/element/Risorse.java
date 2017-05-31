package server.element;
import java.io.Serializable;

/**
 * @author Tommy
 *
 *Superclasse generica delle risorse, classe che di conseguenza verrà estaesa da tutte le classi risorse specifiche. Implementa Serializable perchè viene inviata ai client durante la partita
 */

public class Risorse implements Serializable{

	private String tipo;
	
	public String getTipo(){
		return tipo;
	}
}
