package server.element;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	private int NumberOfPlayers = 0;
	
	/**
	 * Questo metodo inizializza la partita creando nuove tabelle contenti le carte del gioco.
	 * Queste tabelle potranno essere utilizzate per essere modicate durante la partita.
	 * Alla fine della partita verranno eliminate.
	 * 
	 * @author Mattia & Tommy
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
		String queryterritorio = "CREATE TABLE "+lobby.toUpperCase()+"CARTETERRITORIOPARTITARANDOM AS SELECT * FROM CARTATERRITORIO ORDER BY RAND()";
		String queryimpresa = "CREATE TABLE "+lobby.toUpperCase()+"CARTEIMPRESAPARTITARANDOM AS SELECT * FROM CARTAIMPRESA ORDER BY RAND()";
		String querypersonaggio = "CREATE TABLE "+lobby.toUpperCase()+"CARTEPERSONAGGIOPARTITARANDOM AS SELECT * FROM CARTAPERSONAGGIO ORDER BY RAND()";
		String queryedificio = "CREATE TABLE "+lobby.toUpperCase()+"CARTEEDIFICIOPARTITARANDOM AS SELECT * FROM CARTAEDIFICIO ORDER BY RAND()";
		connection.createStatement().execute(queryterritorio);
		connection.createStatement().execute(queryimpresa);
		connection.createStatement().execute(querypersonaggio);
		connection.createStatement().execute(queryedificio);
		String queryterritorio1 = "CREATE TABLE "+lobby.toUpperCase()+"CARTETERRITORIOPARTITA AS SELECT * FROM "+lobby.toUpperCase()+"CARTETERRITORIOPARTITARANDOM ORDER BY PERIODO";
		String queryimpresa1 = "CREATE TABLE "+lobby.toUpperCase()+"CARTEIMPRESAPARTITA AS SELECT * FROM "+lobby.toUpperCase()+"CARTEIMPRESAPARTITARANDOM ORDER BY PERIODO";
		String querypersonaggio1 = "CREATE TABLE "+lobby.toUpperCase()+"CARTEPERSONAGGIOPARTITA AS SELECT * FROM "+lobby.toUpperCase()+"CARTEPERSONAGGIOPARTITARANDOM ORDER BY PERIODO";
		String queryedificio1 = "CREATE TABLE "+lobby.toUpperCase()+"CARTEEDIFICIOPARTITA AS SELECT * FROM "+lobby.toUpperCase()+"CARTEEDIFICIOPARTITARANDOM ORDER BY PERIODO";
		connection.createStatement().execute(queryterritorio1);
		connection.createStatement().execute(queryimpresa1);
		connection.createStatement().execute(querypersonaggio1);
		connection.createStatement().execute(queryedificio1);
		String queryterritorio2 = "DROP TABLE "+lobby.toUpperCase()+"CARTETERRITORIOPARTITARANDOM";
		String queryimpresa2 = "DROP TABLE "+lobby.toUpperCase()+"CARTEIMPRESAPARTITARANDOM";
		String querypersonaggio2 = "DROP TABLE "+lobby.toUpperCase()+"CARTEPERSONAGGIOPARTITARANDOM";
		String queryedificio2 = "DROP TABLE "+lobby.toUpperCase()+"CARTEEDIFICIOPARTITARANDOM";
		connection.createStatement().executeUpdate(queryterritorio2);
		connection.createStatement().executeUpdate(queryimpresa2);
		connection.createStatement().executeUpdate(querypersonaggio2);
		connection.createStatement().executeUpdate(queryedificio2);
		connection.close();
		for(int i=0;i<DIM;i++){
			start[i]=false;
		}
	}
	
	public Partita(String name){
		this.name=name;
	}
	
	
	private void startPartita() throws RemoteException, SQLException{
		turno=1;
		/*for(int i = 0; i<4; i++){
			
				if(giocatori[i] != null){
					System.out.println("notifico giocatori per l'inizio partita");
					System.out.println(giocatori[i]);
					giocatori[i].notifyStartGame();
				}
			
		}*/
		try {
		System.out.println("Notificato giocatore 1\n\n\n");
		if(giocatori[0] != null)
			giocatori[0].notifyStartGame();
		System.out.println("Notificato giocatore 2\n\n\n");
		if(giocatori[1] != null)
			giocatori[1].notifyStartGame();
		System.out.println("Notificato giocatore 3\n\n\n");
		if(giocatori[2] != null)
			giocatori[2].notifyStartGame();
		System.out.println("Notificato giocatore 4\n\n\n");
		if(giocatori[3] != null)
			giocatori[3].notifyStartGame();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("shuffle giocatori ");
		beShuffled();
		System.out.println("Sistemato ordine gioco");
		this.NumberOfPlayers = 0;
		//Vedi regole e assegna a seconda della posizione le risorse di posizione
		/*try {
	
			giocatori[i].notifyTurno();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	//Mettere a public per verificare con il metodo di test
	//public boolean checkBoolean(int dim){
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
				System.out.println("Posizione giocatore"+i);
				this.NumberOfPlayers++;
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
		/*Deve scorrere i giocatori e vedere quanti punti fede si è accoumulati in base al turno di gioco.
		 * Se minori da quanto richiesto attribuisco scomunica, altrimenti chiedi se sostenere o no
		 */
		
	}


	public String getCreator(){
		//Metodo sbagliato soprattutto dopo lo shuffle
		return giocatori[0].getName();
	}

	public void start(String account) throws RemoteException, SQLException {
		//Riguardare metodo che e' errato in quanto la partita parte solo e soltanto per 4 giocatori.
		System.out.println("Setto giocatore pronto");
		for(int i=0;i<DIM;i++){
			if(giocatori[i].getName().equals(account)){
				start[i]=true;
				break;
			}
		}
		System.out.println("Verifico quanti giocatori pronti");
		int dim = 0;
		for(Giocatore g : giocatori)
			if(g != null)
				dim++;
				else
					break;
		System.out.println("Se minori di due giocatori pronti ritorno");
		if(dim<2)
			return;
		else
			if(checkBoolean(dim))
				startPartita();
			else
				return;
	}
	
	public String[] getColors(){
		return colors;	
	}

	public CartaSviluppo[] getCards() {
		System.out.println("Allinterno del metodo");
		CartaSviluppo[] mom = new  CartaSviluppo[16];
		for(int i=0; i<NUMCARTE;i++)
			mom[i] = carteTerritori[i];
		for(int i=0; i<NUMCARTE;i++)
			mom[i+4]= cartePersonaggio[i];
		System.out.println("CarteEdificio");
		for(int i=0; i<NUMCARTE;i++)
			mom[i+8]= carteEdifici[i];
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
		int i;
		System.out.println("All'interno della partita setto le carte");
		for(i=0;i<NUMCARTE;i++){
			System.out.println("CarteTerritori "+i+"");
			query="SELECT * FROM "+name.toUpperCase()+"CARTETERRITORIOPARTITA LIMIT 1";//Scrivere la query in modo che cerchi differenti carte in generale
			carteTerritori[i] = new CartaTerritori();
			carteTerritori[i].setCarta(connection,query);
			queryelimina="DELETE TOP 1 FROM "+name.toUpperCase()+"CARTETERRITORIOPARTITA";
			connection.createStatement().executeUpdate(queryelimina);
		}
		for(i=0;i<NUMCARTE;i++){
			System.out.println("CartePersonaggio "+i+"");
			query="SELECT * FROM "+name.toUpperCase()+"CARTEPERSONAGGIOPARTITA LIMIT 1";//Scrivere la query in modo che cerchi differenti carte in generale
			cartePersonaggio[i] = new CartaPersonaggi();
			cartePersonaggio[i].setCarta(connection,query);
			queryelimina="DELETE TOP 1 FROM "+name.toUpperCase()+"CARTEPERSONAGGIOPARTITA";
			connection.createStatement().executeUpdate(queryelimina);
		}
		for(i=0;i<NUMCARTE;i++){
			System.out.println("CarteEdificio "+i+"");
			query="SELECT * FROM "+name.toUpperCase()+"CARTEEDIFICIOPARTITA LIMIT 1";//Scrivere la query in modo che cerchi differenti carte in generale
			carteEdifici[i] = new CartaEdifici();
			carteEdifici[i].setCarta(connection,query);
			queryelimina="DELETE TOP 1 FROM "+name.toUpperCase()+"CARTEEDIFICIOPARTITA";
			connection.createStatement().executeUpdate(queryelimina);
		}
		for(i=0;i<NUMCARTE;i++){
			System.out.println("CarteImpresa "+i+"");
			query="SELECT * FROM "+name.toUpperCase()+"CARTEIMPRESAPARTITA LIMIT 1";//Scrivere la query in modo che cerchi differenti carte in generale
			carteImprese[i] = new CartaImprese();
			carteImprese[i].setCarta(connection,query);
			queryelimina="DELETE TOP 1 FROM "+name.toUpperCase()+"CARTEIMPRESAPARTITA";
			connection.createStatement().executeUpdate(queryelimina);
		}
		connection.close();
	}

	public void changeColors(String color) {
		for(int i = 0; i<DIM;i++){
			if(colors[i].equals(color) || colors[i].equals("")){
				colors[i] = "";
				break;
			}
		}
		
	}


	public void setCardsScomunica(ConnectionDatabase connectionDatabase, String account) throws SQLException {
			System.out.println("Carta1");
			for(int i=0;i<3;i++){
				tessereScomunica[i] = new TesseraScomunica();
			}
			tessereScomunica[0].setTesseraPrimoPeriodo(connectionDatabase.getConnection(account));
			System.out.println("Carta2");
			tessereScomunica[1].setTessereSecondoPeriodo(connectionDatabase.getConnection(account));
			System.out.println("Carta3");
			tessereScomunica[2].setTesseraTerzoPeriodo(connectionDatabase.getConnection(account));
	}


	public void changeGamer() throws RemoteException, SQLException {
		this.NumberOfPlayers++;
		if(NumberOfPlayers>4){
			addTurno();
		}else{
			try {
				giocatori[NumberOfPlayers].notifyTurno();
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


	public String getNamePosition(double x, double y, Connection connection) throws SQLException {
		String nomeposizione;
		String query="SELECT NOME FROM POSIZIONETABELLONE WHERE POSX="+x+" AND POSY="+y+"";
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(query);// Scrivere query che fornisce il nome della posizione tipo primo piano palazzo...
		rs.next();
			nomeposizione=rs.getString("NOME");
		rs.close();
		stmt.close();
		return nomeposizione;
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

	public void deleteView(Connection connection) throws SQLException {
		String querydroppersonaggi = "DROP TABLE "+name.toUpperCase()+"CARTEPERSONAGGIOPARTITA";
		String querydropedifici = "DROP TABLE "+name.toUpperCase()+"CARTEEDIFICIOPARTITA";
		String querydropterritori = "DROP TABLE "+name.toUpperCase()+"CARTETERRITORIOPARTITA";
		String querydropimprese = "DROP TABLE "+name.toUpperCase()+"CARTEIMPRESAPARTITA";
		connection.createStatement().execute(querydroppersonaggi);
		connection.createStatement().execute(querydropedifici);
		connection.createStatement().execute(querydropterritori);
		connection.createStatement().execute(querydropimprese);
		connection.close();
	}

	public int getNumberOfPlayers() {
		// TODO Auto-generated method stub
		return this.NumberOfPlayers;
	}
	
}
