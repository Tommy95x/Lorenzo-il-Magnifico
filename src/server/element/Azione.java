package server.element;

public class Azione {
	private String azione;
	private int valore;
	private boolean immediato;
	
	public Azione(String azione, int valore, boolean b) {
		this.azione=azione;
		this.valore = valore;
		this.immediato = b;
	}
	public String getAzione() {
		return azione;
	}
	private void setAzione(String azione) {
		this.azione = azione;
	}
	public int getValore() {
		return valore;
	}
	private void setValore(int Valore) {
		this.valore = valore;
	}
	public boolean isImmediato() {
		return immediato;
	}
	private void setImmediato(boolean immediato) {
		this.immediato = immediato;
	}
}
