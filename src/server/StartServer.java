package server;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import server.database.ConnectionDatabase;
import server.element.Giocatore;
import server.element.Partita;

/*
*Classe utilizzata per l'avvio del server. Si istanziano due differenti Threads che avviano in corrispondenza un RMIServer per la
*connessione dei Clients che utilizzeranno RMI e un SocketServer per tutti quei Clients che si collegheranno mediante Socket.
*/
public class StartServer {

	private ArrayList<String> utente= new ArrayList<String>();
	private ArrayList<Partita> lobbies = new ArrayList <Partita>();
	private ConnectionDatabase DB = new ConnectionDatabase(20, 10);
	
	public static void main(String[] args) {
		StartServer server = new StartServer();
		RMIServer rmi = new RMIServer(1099, server);
		SocketServer ss = new SocketServer(3000,server);
		rmi.start();
		ss.start();
	}

	public String addClient(String account, String pw){
		String mom=account+pw;
		System.out.println(mom);
		for(int i=0; i<utente.size();i++){
			if((mom).equals(utente.get(i)))
				return "Player alredy login";
		}
		String query = "SELECT CASE WHEN EXISTS( SELECT * FROM UTENTE WHERE (NOMEUTENTE='"+account.toLowerCase()+"' AND PASSWORD='"+pw.toLowerCase()+"'))THEN CAST (1 AS BIT) ELSE CAST(0 AS BIT) END";
		try {
			if(DB.getConnection(account).createStatement().execute(query))//Verificare se effettivamente � cos� che si accetta un risultato di uan query boolean
				return "Welcome to the game";
			else
				return "For player must register a new account";
		} catch (SQLException e) {
			e.printStackTrace();
			return "For player must register a new account";
		}
			
	}
	
	public String registerNewClient(String account, String pw, String email) {
		// Scrivere la query per aggiungere un nuovo utente al sistema
		String query = "SELECT CASE WHEN EXISTS( SELECT * FROM UTENTE WHERE (NOMEUTENTE='"+account.toLowerCase()+"' AND PASSWORD='"+pw.toLowerCase()+"'))THEN CAST (1 AS BIT) ELSE CAST(0 AS BIT) END";
		try {
			if(!(DB.getConnection(account).createStatement().execute(query))){
				DB.getConnection(account).createStatement().executeUpdate("INSERT INTO UTENTE" + "VALUES('2','"+account.toLowerCase()+"','"+email.toLowerCase()+"','"+pw.toLowerCase()+"')");
			    return "You are now registered!";
			}else{
				return "You are registered yet!";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Database crash!";
		}
	}
	
	public String addGame(String partita,String account){
		for(int i=0;i<getDimLobbies();i++){
			if(partita.equals(lobbies.get(i)))
				return "Sorry, but the name of the game is already use, change name";
		}
		lobbies.add(new Partita(partita,account,getDimLobbies()));
		return "Welcome to the lobby";
	}

	
	public int getIndicePartita(String name){
		int i=0;
		while(!name.equals(lobbies.get(i))){
			i++;
		}
		return i;
	}

	public int getDimLobbies() {
		return lobbies.size();
	}
	
	public String getNameLobby(int i){
		return lobbies.get(i).getLobby();
	}

	public void addGamer(int positionGame, String color, String account) {
		lobbies.get(positionGame).addGiocatore(new Giocatore(color,lobbies.get(positionGame),account, positionGame));
	}

	public ArrayList<Partita> getLobbies() {
		return lobbies;
	}
	
	public Partita getLobbyByNumber(int number){
		return lobbies.get(number);
	}
	
	public Partita getLobbyByName(String lobby){
		for(Partita p:lobbies)
			if(p.getLobby().equals(lobby))
				return p;
		return null;
	}


	public void setCards(Partita partita, String account) {
		try {
			partita.setCards(DB.getConnection(account));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public ConnectionDatabase getDBConnection(){
		return DB;
	}
	
}
