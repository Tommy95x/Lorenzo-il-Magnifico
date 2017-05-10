package server.element;

public class CuboScomunica {

	//Possiederà degli attributi che contraddistingueranno il cubo in caso d'istanza di mancato sostegno della chiesa
	private String color;
	private int periodo;
	private TesseraScomunica scomunica;
	
	public CuboScomunica(String color, int periodo, TesseraScomunica scomunica) {
		this.setColor(color);
		this.setPeriodo(periodo);
		//Verifica per vedere se il match tra cubo e la tessera scomunica è corretto
		try{
			if(scomunica.getTurno()==periodo)
				this.setScomunica(scomunica);
		}catch(Exception e){
			System.err.println("Match di verifica turno per scomunica errato");
		}
	}

	//Getter e Setter delle varie variabili aggiunti per una corretta programmazione ad oggetti
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getPeriodo() {
		return periodo;
	}

	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}

	public TesseraScomunica getScomunica() {
		return scomunica;
	}

	public void setScomunica(TesseraScomunica scomunica) {
		this.scomunica = scomunica;
	}

	
}
