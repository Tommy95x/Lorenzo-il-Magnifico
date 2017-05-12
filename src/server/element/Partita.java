package server.element;

/*Parte condivisa dai vari giocatori e che possiederà tutte le azioni che un giocatore può eseguire. Ogni azione della partita
  sarà un metodo synchronized*/
public class Partita {

	private int turno;
	private String lobby;
	private Giocatore[] giocatori=new Giocatore[4];
	
	public Partita(String lobby, String namePlayer, int positionGame){
		this.setLobby(lobby);
		addGiocatore(new Giocatore(lobby, this,namePlayer,positionGame));
	}
	
	public void startPartita(){
		turno=1;
	}

	public String getLobby() {
		return lobby;
	}

	public void addGiocatore(Giocatore giocatore) {
		if(giocatori.length<4)
			giocatori[(giocatori.length)+1]= giocatore;
	}

	public int numberOfPlayer(){
		return giocatori.length;
	}
	
	public void setLobby(String lobby) {
		this.lobby = lobby;
	}

	public int getTurno() {
		return turno;
	}

	public boolean addTurno() {
		if(turno<7){
			turno++;
			return true;
		}else
			return false;
	}
}
