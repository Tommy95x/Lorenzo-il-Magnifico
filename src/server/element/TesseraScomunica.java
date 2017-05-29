package server.element;

import java.sql.Connection;

import javafx.scene.image.ImageView;

/*Tessera che verr� posizionata sul tabellone a inizio partita, possiede un periodo e un effetto che viene attivato
  su ogni giocatore scomunicato*/
public class TesseraScomunica {

	private String oggettoPenalita;
	private int valorePenalita;
	private int periodo;
	private ImageView image;
	
	//Vengono settati i parametri della tessera scomunica
	public TesseraScomunica(){
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

	public void setTessera(Connection connection) {
		//Scrivere query e riempire i campi delle tessere scomunica
		
	}

}
