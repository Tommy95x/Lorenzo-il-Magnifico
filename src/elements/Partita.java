package elements;

/*Parte condivisa dai vari giocatori e che possiederà tutte le azioni che un giocatore può eseguire. Ogni azione della partita
  sarà un metodo synchronized*/
public class Partita {

	private int turno;
	private String lobby;
	
	public Partita(String lobby){
		this.setLobby(lobby);
	}
	
	public void startPartita(){
		turno=1;
		
	}

	public String getLobby() {
		return lobby;
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
