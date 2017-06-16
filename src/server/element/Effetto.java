package server.element;

public class Effetto {

	private String risorsa;
	private int qta;
	private boolean immediato;
	
	public Effetto(String risorsa, int qtaeffetto, boolean b) {
		this.risorsa=risorsa;
		this.qta = qtaeffetto;
		this.immediato = b;
	}
	public String getRisorsa() {
		return risorsa;
	}
	private void setRisorsa(String risorsa) {
		this.risorsa = risorsa;
	}
	public int getQta() {
		return qta;
	}
	private void setQta(int qta) {
		this.qta = qta;
	}
	public boolean isImmediato() {
		return immediato;
	}
	private void setImmediato(boolean immediato) {
		this.immediato = immediato;
	}
	
}
