package server.element;

public class Azione {
	private String azione;
	private int valore;
	private boolean immediato;
	private int tipo;
	private int tipoPermanente;
	
	public Azione(String azione, int valore, boolean b,int tipo, int tipoPermanente) {
		this.azione=azione;
		this.valore = valore;
		this.immediato = b;
		this.tipo=tipo;
		this.tipoPermanente=tipoPermanente;
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
	
	public int getTipoPermanente() {
		return tipoPermanente;
	}
	public void setTipoPermanente(int tipoPermanente) {
		this.tipoPermanente = tipoPermanente;
	}
	
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	
}
