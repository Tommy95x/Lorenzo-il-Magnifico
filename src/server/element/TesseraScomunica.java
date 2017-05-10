package server.element;

/*Tessera che verrà posizionata sul tabellone a inizio partita, possiede un effetto e un turno in cui può essere attivata
  su ogni giocatore scomunicato*/
public class TesseraScomunica {

	private String oggettoPenalita;
	private int valorePenalita;
	private int periodo;
	
	public TesseraScomunica(String oggettoPenalita, int valorePenalita, int periodo){
		this.setOggettoPenalita(oggettoPenalita);
		this.setValorePenalita(valorePenalita);
		this.setTurno(periodo);
	}

	public String getOggettoPenalita() {
		return oggettoPenalita;
	}

	public void setOggettoPenalita(String oggettoPenalita) {
		this.oggettoPenalita = oggettoPenalita;
	}

	public int getValorePenalita() {
		return valorePenalita;
	}

	public void setValorePenalita(int valorePenalita) {
		this.valorePenalita = valorePenalita;
	}

	public int getTurno() {
		return periodo;
	}

	public void setTurno(int turno) {
		this.periodo = turno;
	}

}
