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
		beShuffled();
		//Chiedere come notificare che è iniziata la partita ai giocatori
	}

	private void beShuffled() {
		Giocatore[] mom=new Giocatore[DIM];
		Giocatore[] ordine=new Giocatore[DIM];
		boolean flag=false;
		for(int i=0;i<DIM;i++){
			mom[i]=giocatori[i];
		}
		int con=DIM;
		for(int i=0;i<DIM;i++){
			int casuale=(int) (Math.random()*con);
			if(mom[casuale]!=null){
				ordine[i]=mom[casuale];
				mom[casuale]=null;
				con--;
			}else{
				for(int j=0;j<DIM;j++){
					if(mom[i]!=null)
						flag=false;
					else
						flag=true;
				}
				if(!flag)
					i--;
				else
					break;
			}	
		}
		for(int i=0;i<DIM;i++)
			giocatori[i]=ordine[i];
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
		int casuale= ((int) Math.random()*24)+1;
		String query;
		for(CartaSviluppo c:carteTerritori){
			query="";//Scrivere la query in modo che cerchi differenti carte in generale
			c.setCarta(connection,query);
		}
		for(CartaSviluppo c:cartePersonaggio){
			query="";//Scrivere la query in modo che cerchi differenti carte in generale
			c.setCarta(connection,query);
		}
		for(CartaSviluppo c:carteEdifici){
			query="";//Scrivere la query in modo che cerchi differenti carte in generale
			c.setCarta(connection,query);
		}
		for(CartaSviluppo c:carteImprese){
			query="";//Scrivere la query in modo che cerchi differenti carte in generale
			c.setCarta(connection,query);
		}
	}
	
}
