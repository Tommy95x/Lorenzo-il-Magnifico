package server.element;

import java.io.Serializable;

public class Risorsa implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String risorsa;
	private int qta;
	
	public Risorsa(String risorsa, int qtaeffetto) {
		this.risorsa=risorsa;
		this.qta = qtaeffetto;
	}
	public String getRisorsa() {
		return risorsa;
	}
	@SuppressWarnings("unused")
	private void setRisorsa(String risorsa) {
		this.risorsa = risorsa;
	}
	public int getQta() {
		return qta;
	}
	@SuppressWarnings("unused")
	private void setQta(int qta) {
		this.qta = qta;
	}
}
