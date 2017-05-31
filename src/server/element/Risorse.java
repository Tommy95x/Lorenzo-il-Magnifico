package server.element;
import java.io.Serializable;

/**
 * @author Tommy
 *
 *Superclasse generica delle risorse, classe che di conseguenza verr� estaesa da tutte le classi risorse specifiche. Implementa Serializable perch� viene inviata ai client durante la partita
 */

public class Risorse implements Serializable{

	private String tipo;
	
	public String getTipo(){
		return tipo;
	}
}
