package server.element;

public class Giocatore extends Thread{

	private String color;
	private Disco[] discoGiocatore;
	private Portafoglio risorse;
	private FamiliareNeutro[] familiari = new FamiliareNeutro[4];
	private CuboScomunica[] cubiScomunica = new CuboScomunica[3];
	private Partita partita;
	
	public Giocatore(String color, Partita partita){
		int i;
		this.setColor(color);
		discoGiocatore[0]=new Disco(color);
		discoGiocatore[1]=new DiscoFede(color);
		discoGiocatore[2]=new DiscoMilitare(color);
		discoGiocatore[3]=new DiscoVittoria(color);
		risorse=new Portafoglio();
		for(i=0;i<4;i++){
			switch(i){
				case 0:
					familiari[i]=new FamiliareNeutro();
					break;
				case 1:
					familiari[i]=new Familiare("black");
					break;
				case 2:
					familiari[i]=new Familiare("orange");
					break;
				case 3:
					familiari[i]=new Familiare("white");
			}
		}
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Portafoglio getRisorse() {
		return risorse;
	}

	public void setRisorse(Risorse ris) {
		risorse.addRis(ris);
	}

	
	
}
