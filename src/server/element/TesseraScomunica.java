package server.element;

import java.io.Serializable;
import java.sql.Connection;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Mattia
 *Tessera che verra' posizionata sul tabellone a inizio partita, possiede un periodo e un effetto che viene attivato
  su ogni giocatore scomunicato
 */
public class TesseraScomunica implements Serializable{

	private String oggettoPenalita;
	private int valorePenalita;
	private int periodo;
	private Image image = new Image(getClass().getResourceAsStream("Zecca.png"));
	private String tooltip;
	
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

	

	public Tooltip getTooltip() {
		Tooltip tooltip = new Tooltip();
		tooltip.setText(this.tooltip);
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * Method that with a DB connection are set all parameters
	 * 
	 * @param connection
	 */
	public void setTesseraPrimoPeriodo(Connection connection) {
		// TODO Auto-generated method stub
		
	}

	public void setTesseraTerzoPeriodo(Connection connection) {
		// TODO Auto-generated method stub
		
	}

	public void setTessereSecondoPeriodo(Connection connection) {
		// TODO Auto-generated method stub
		
	}

}
