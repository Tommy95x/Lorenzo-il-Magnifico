package server.element;
import java.io.Serializable;

//Classe generica delle risorse 
public class Risorse implements Serializable{

	private boolean big;
	private String tipo;
	
	public Risorse(boolean big){
		this.big=big;
	}
	
	public int getValue(){
		if(big)
			return 5;
		else
			return 1;
	}
	
	public String getTipo(){
		return tipo;
	}
}
