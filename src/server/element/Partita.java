package server.element;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import server.database.ConnectionDatabase;

/*Parte condivisa dai vari giocatori e che possiederà tutte le azioni che un giocatore può eseguire. Ogni azione della partita
  sarà un metodo synchronized*/
public class Partita implements Serializable{

	private final int DIM=4;
	private final int NUMCARTE=4;
	private int turno;
	private String name;
	private Giocatore[] giocatori = new Giocatore[DIM];
	private boolean[] start = new boolean[DIM];
	private CartaPersonaggi[] cartePersonaggio = new CartaPersonaggi[NUMCARTE];
	private CartaImprese[] carteImprese = new CartaImprese[NUMCARTE];
	private CartaEdifici[] carteEdifici = new CartaEdifici[NUMCARTE];
	private CartaTerritori[] carteTerritori = new CartaTerritori[NUMCARTE];
	private TesseraScomunica[] tessereScomunica = new TesseraScomunica[3];
	private String[] colors = new String[DIM];
	private int giocatore = 0;
	
	public Partita(String lobby, String namePlayer, int positionGame){
		this.setLobby(lobby);
		colors[0] = "blue"; 
		colors[1] = "orange";
		colors[2] = "white";
		colors[3] = "green"; 
		for(int i=0;i<DIM;i++){
			start[i]=false;
		}
	}
	
	
	private void startPartita() throws RemoteException, SQLException{
		turno=1;
		beShuffled();
		for(int i = 0; i<4; i++){
			giocatori[i].notifyStartGame();
		}
		giocatori[giocatore].notifyTurno();
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


	public String getLobbyName() {
		return name;
	}

	public void addGiocatore(Giocatore giocatore) {
		for(int i=0;i<giocatori.length;i++){
			if(giocatori[i]==null){
				giocatori[i]=giocatore;
				return;
			}
		}
	}

	public Giocatore getGiocatoreByName(String name){
		for(Giocatore g: giocatori){
			if(g.getName().equals(name))
				return g;
		}
		return null;
	}
	
	public int numberOfPlayer(){
		return giocatori.length;
	}
	
	public void setLobby(String lobby) {
		this.name = lobby;
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

	public void start(String account) throws RemoteException, SQLException {
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
		return colors;	
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


	public void changeColors(String color) {
		for(int i = 0; i<DIM;i++){
			if(colors[i].equals(color) || colors[i]==null){
				colors[i] = null;
				break;
			}
		}
		
	}


	public void setCardsScomunica(ConnectionDatabase connectionDatabase, String account) throws SQLException {
		for(TesseraScomunica mom : tessereScomunica){
			mom.setTessera(connectionDatabase.getConnection(account));
		}
		
	}


	public void changeGamer() throws RemoteException, SQLException {
		giocatore++;
		if(giocatore>4){
			addTurno();
		}else{
			giocatori[giocatore].notifyTurno();
		}
		
	}


	public void notifySpostamento(String color, Giocatore giocatoreByName, double x, double y) {
		for(Giocatore g : giocatori){
			if(!g.equals(giocatoreByName)){
				g.notifySpostamento(color,giocatoreByName.getColor(),x,y);
			}
		}
		
	}


	public String getNamePosition(double x, double y, Connection connection) {
		// Scrivere query che fornisce il nome della posizione tipo primo piano palazzo...
		return null;
	}


	public void exitToGame(String name, String color) {
		int i;
		for(i=0;i<giocatori.length;i++){
			if(giocatori[i].getName().equals(name)){
				giocatori[i]=null;
			}
			for( i =0;i<colors.length;i++){
				if(colors[i] == null){
					colors[i] = color;
					break;
				}
			}
		}
	}
	
}
