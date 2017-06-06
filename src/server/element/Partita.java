package server.element;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import server.database.ConnectionDatabase;

/**
 * @author Tommy
 *
 *Classe che rappresentera' la partita. Puo' contenere al massimo 4 giocatori, tutte le carte giocate (sempre rappresentate da degli array) e il turno che far� terminare la partita una volta che raggiungera' il 
 *valore pari a 6.
 */
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
	
	/**
	 * 
	 * 
	 * @param lobby
	 * @param namePlayer
	 * @param positionGame
	 * @param connection
	 * @throws SQLException
	 */
	public Partita(String lobby, String namePlayer, int positionGame, Connection connection) throws SQLException{
		this.setLobby(lobby);
		colors[0] = "blue"; 
		colors[1] = "orange";
		colors[2] = "white";
		colors[3] = "green"; 
		String queryterritorio = "CREATE VIEW "+lobby.toUpperCase()+"CARTETERRITORIOPARTITA AS (SELECT * FROM CARTATERRITORIO ORDER BY RAND())";
		String queryimpresa = "CREATE VIEW "+lobby.toUpperCase()+"CARTEIMPRESAPARTITA AS (SELECT * FROM CARTAIMPRESA ORDER BY RAND())";
		String querypersonaggio = "CREATE VIEW "+lobby.toUpperCase()+"CARTEPERSONAGGIOPARTITA AS (SELECT * FROM CARTAPERSONAGGIO ORDER BY RAND())";
		String queryedificio = "CREATE VIEW "+lobby.toUpperCase()+"CARTEEDIFICIOPARTITA AS (SELECT * FROM CARTAEDIFICIO ORDER BY RAND())";
		connection.createStatement().execute(queryterritorio);
		connection.createStatement().execute(queryimpresa);
		connection.createStatement().execute(querypersonaggio);
		connection.createStatement().execute(queryedificio);
		queryterritorio="SELECT * FROM "+lobby.toUpperCase()+"CARTETERRITORIOPARTITA ORDER BY PERIODO";
		queryimpresa="SELECT * FROM "+lobby.toUpperCase()+"CARTEIMPRESAPARTITA ORDER BY PERIODO";
		querypersonaggio="SELECT * FROM "+lobby.toUpperCase()+"CARTEPERSONAGGIOPARTITA ORDER BY PERIODO";
		queryedificio="SELECT * FROM "+lobby.toUpperCase()+"CARTEEDIFICIOPARTITA ORDER BY PERIODO";
		connection.createStatement().executeQuery(queryterritorio);
		connection.createStatement().executeQuery(queryimpresa);
		connection.createStatement().executeQuery(querypersonaggio);
		connection.createStatement().executeQuery(queryedificio);
		for(int i=0;i<DIM;i++){
			start[i]=false;
		}
	}
	
	
	private void startPartita() throws RemoteException, SQLException{
		turno=1;
		beShuffled();
		for(int i = 0; i<4; i++){
			try {
				giocatori[i].notifyStartGame();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//Vedi regole e assegna a seconda della posizione le risorse di posizione
		try {
			giocatori[giocatore].notifyTurno();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean checkBoolean(int dim){
		for(int i=0;i<dim;i++){
			if(!start[i])
				return false;
		}
		return true;
	}
	
	/**
	 * This method take the array of the gamers and shuffle it to decide the start 
	 * order of the first turn of the game.
	 * 
	 * @author Mattia
	 */
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
			if(turno == 2 || turno == 4)
				sostegnoChiesa();
			turno++;
			return true;
		}else{
			sostegnoChiesa();
			return false;
		}
	}


	private void sostegnoChiesa() {
		// TODO Auto-generated method stub
		
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
		int dim = 0;
		for(Giocatore g : giocatori)
			if(g != null)
				dim++;
				else
					break;
		if(checkBoolean(dim))
			startPartita();
		else
			return;
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
	
	/**
	 * This method is used to set all the cards that will be used during the game.
	 * 
	 * @author Mattia
	 * @param connection
	 * @throws SQLException
	 */
	public void setCards(Connection connection) throws SQLException{
		String query;
		String queryelimina;
		
		for(CartaSviluppo c:carteTerritori){
			query="SELECT * FROM "+name.toUpperCase()+"CARTETERRITORIOPARTITA LIMIT 1";//Scrivere la query in modo che cerchi differenti carte in generale
			c.setCarta(connection,query);
			queryelimina="DELETE TOP 1 FROM "+name.toUpperCase()+"CARTETERRITORIOPARTITA";
			connection.createStatement().executeUpdate(queryelimina);
		}
		for(CartaSviluppo c:cartePersonaggio){
			query="SELECT * FROM "+name.toUpperCase()+"CARTEPERSONAGGIOPARTITA LIMIT 1";//Scrivere la query in modo che cerchi differenti carte in generale
			c.setCarta(connection,query);
			queryelimina="DELETE TOP 1 FROM "+name.toUpperCase()+"CARTEPERSONAGGIOPARTITA";
			connection.createStatement().executeUpdate(queryelimina);
		}
		for(CartaSviluppo c:carteEdifici){
			query="SELECT * FROM "+name.toUpperCase()+"CARTEEDIFICIOPARTITA LIMIT 1";//Scrivere la query in modo che cerchi differenti carte in generale
			c.setCarta(connection,query);
			queryelimina="DELETE TOP 1 FROM "+name.toUpperCase()+"CARTEEDIFICIOPARTITA";
			connection.createStatement().executeUpdate(queryelimina);
		}
		for(CartaSviluppo c:carteImprese){
			query="SELECT * FROM "+name.toUpperCase()+"CARTEIMPRESAPARTITA LIMIT 1";//Scrivere la query in modo che cerchi differenti carte in generale
			c.setCarta(connection,query);
			queryelimina="DELETE TOP 1 FROM "+name.toUpperCase()+"CARTEIMPRESAPARTITA";
			connection.createStatement().executeUpdate(queryelimina);
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
			try {
				giocatori[giocatore].notifyTurno();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}


	public void notifySpostamento(String color, Giocatore giocatoreByName, double x, double y) {
		for(Giocatore g : giocatori){
			if(!g.equals(giocatoreByName)){
				try {
					g.notifySpostamento(color,giocatoreByName.getColor(),x,y);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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


	public TesseraScomunica[] getCardsScomunica() {
		return tessereScomunica;
	}

	public Giocatore[] getGiocatori() {
		return giocatori;
	}


	public void notifyAddCardGiocatore(String name, CartaSviluppo carta) throws RemoteException {
		for(Giocatore g : giocatori){
			if(!g.getName().equals(name) &&  g!=null){
				g.notifyAddCard(carta);
			}
		}
	}
	
}
