package server.element;

public class Risorsa {
	private String risorsa;
	private int qta;
	
	public Risorsa(String risorsa, int qtaeffetto) {
		this.risorsa=risorsa;
		this.qta = qtaeffetto;
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
}
