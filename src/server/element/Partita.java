package server.element;

import java.io.Serializable;

/*Parte condivisa dai vari giocatori e che possiederà tutte le azioni che un giocatore può eseguire. Ogni azione della partita
  sarà un metodo synchronized*/
public class Partita implements Serializable{

	private final int DIM=4;
	private int turno;
	private String lobby;
	private Giocatore[] giocatori=new Giocatore[DIM];
	private boolean[] start=new boolean[DIM];
	
	public Partita(String lobby, String namePlayer, int positionGame){
		this.setLobby(lobby);
		addGiocatore(new Giocatore(lobby, this,namePlayer,positionGame));
	}
	
	
	private void startPartita(){
		turno=1;
	}

	public String getLobby() {
		return lobby;
	}

	public void addGiocatore(Giocatore giocatore) {
		if(giocatori.length<DIM)
			giocatori[(giocatori.length)+1]= giocatore;
	}

	public int numberOfPlayer(){
		return giocatori.length;
	}
	
	public void setLobby(String lobby) {
		this.lobby = lobby;
	}

	public boolean addTurno() {
		if(turno<7){
			turno++;
			return true;
		}else
			return false;
	}
	
	public String getCreator(){
		return giocatori[0].getName();
	}

	public void start(String account) {
		for(int i=0;i<DIM;i++){
			if(start[i]!=true)
				start[i]=true;
		}
		if(start.length==giocatori.length){
			for(boolean flag:start){
				if(!flag)
					return;
			}
			startPartita();
		}else
			return;
	}
	
	public String[] getColors(){
		String[] colors=new String[DIM];
		for(int i=0;i<DIM;i++){
			if(giocatori[i]!=null)
				colors[i]=giocatori[i].getColor();
		}
		return colors;	
	}

	public void adviseGamers() {
		//Chiedere al prof come referenziare per avvisare gli altri giocatori
		
	}
	
	
	
}
