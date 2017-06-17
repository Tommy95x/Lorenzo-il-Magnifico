package server.element;

public class Effetto {

	private String risorsa;
	private int qta;
	private boolean immediato;
	private int tipo;
	private int tipoPermanente;
	
	public Effetto(String risorsa, int qtaeffetto, boolean b, int tipo, int tipoPermanente) {
		this.risorsa=risorsa;
		this.qta = qtaeffetto;
		this.immediato = b;
		this.setTipo(tipo);
		this.tipoPermanente=tipoPermanente;
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
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	
	public int getTipoPermanente() {
		return tipoPermanente;
	}
	public void setTipoPermanente(int tipoPermanente) {
		this.tipoPermanente = tipoPermanente;
	}
	
}
