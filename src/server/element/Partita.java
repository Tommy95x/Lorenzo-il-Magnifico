package server.element;

import java.sql.Connection;
import java.util.ArrayList;

import server.database.ConnectionDatabase;

/*Parte condivisa dai vari giocatori e che possiederà tutte le azioni che un giocatore può eseguire. Ogni azione della partita
  sarà un metodo synchronized*/
public class Partita{

	private final int DIM=4;
	private final int NUMCARTE=4;
	private int turno;
	private String lobby;
	private Giocatore[] giocatori = new Giocatore[DIM];
	private boolean[] start = new boolean[DIM];
	private CartaPersonaggi[] cartePersonaggio = new CartaPersonaggi[NUMCARTE];
	private CartaImprese[] carteImprese = new CartaImprese[NUMCARTE];
	private CartaEdifici[] carteEdifici = new CartaEdifici[NUMCARTE];
	private CartaTerritori[] carteTerritori = new CartaTerritori[NUMCARTE];
	
	public Partita(String lobby, String namePlayer, int positionGame){
		this.setLobby(lobby);
		addGiocatore(new Giocatore(lobby, this,namePlayer,positionGame));
	}
	
	
	private void startPartita(){
		turno=1;
		//Chiedere come notificare che è iniziata la partita ai giocatori
		//Cercare e implementare metodo per primo ordine casuale di gioco -> trovato su una pagina ubuntu
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
		//Riguardare metodo che e' errato in quanto la partita parte solo e soltanto per 4 giocatori.
		for(int i=0;i<DIM;i++){
			if(giocatori[i].getName().equals(account)){
				start[i]=true;
				break;
			}
		}
		
		for(int i=0;i<DIM;i++){
			if(!start[i])
				return;
		else
			startPartita();
		}
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

	public ArrayList<CartaSviluppo> getCards() {
		ArrayList<CartaSviluppo> mom = new ArrayList<CartaSviluppo>();
		for(int i=0; i<NUMCARTE;i++){
			mom.add(carteTerritori[i]);
		}
		for(int i=0; i<NUMCARTE;i++){
			mom.add(carteImprese[i]);
		}
		for(int i=0; i<NUMCARTE;i++){
			mom.add(carteEdifici[i]);
		}
		for(int i=0; i<NUMCARTE;i++){
			mom.add(cartePersonaggio[i]);
		}
		return mom;
	}
	
	//Crea la connesione al Db e il metodo per otterene tutti le carte del gioco
	public void setCards(Connection connection){
		
	}
	
}
