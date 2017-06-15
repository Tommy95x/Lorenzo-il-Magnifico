package server;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import server.database.ConnectionDatabase;
import server.element.Giocatore;
import server.element.Partita;

/*
*Classe utilizzata per l'avvio del server. Si istanziano due differenti Threads che avviano in corrispondenza un RMIServer per la
*connessione dei Clients che utilizzeranno RMI e un SocketServer per tutti quei Clients che si collegheranno mediante Socket.
*/
public class StartServer implements Serializable{

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
	
	/**
	 * This method is used to control that the user who wants to play is already registered,
	 * if he is, he can start the game, else he has to register an account.
	 * 
	 * @author Mattia
	 * @param account
	 * @param pw
	 * @return
	 */
	public String addClient(String account, String pw){
		System.out.println(account);
		for(int i=0; i<utente.size();i++){
			if(account.equals(utente.get(i)))
				return "Player already login";
		}
		String query = "SELECT COUNT(*) AS C FROM UTENTE WHERE (NOMEUTENTE='"+account.toLowerCase()+"' AND PASSWORD='"+pw.toLowerCase()+"')";
		try {
			Connection connection = DB.getConnection(account);
			Statement stmt = connection.createStatement();
			ResultSet res = stmt.executeQuery(query);
			res.next();
			int conta = res.getInt("C");
			if(conta>0){
				utente.add(account);
				res.close();
				stmt.close();
				connection.close();
				return "Welcome to the game";
			}else{
				res.close();
				stmt.close();
				connection.close();
				return "For player must register a new account";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "For player must register a new account";
		}
			
	}
	
	/**
	 * Method used to register a new user, if that user is already registered, 
	 * the system warns him.
	 * If he is not registered, the method store his credentials in the database 
	 * 
	 * @author Mattia
	 * @param account
	 * @param pw
	 * @param email
	 * @return Return one of two strings, it advise you if you are already registered or if you has been registered
	 * @throws SQLException 
	 */
	public String registerNewClient(String account, String pw, String email) throws SQLException {
		// Scrivere la query per aggiungere un nuovo utente al sistema
		String query = "SELECT COUNT(*) AS C FROM UTENTE WHERE (NOMEUTENTE='"+account.toLowerCase()+"' AND PASSWORD='"+pw.toLowerCase()+"')";
		Connection connection=DB.getConnection(account);
		try {
			Statement stmt = connection.createStatement();
			ResultSet res = stmt.executeQuery(query);
			res.next();
				int conta = res.getInt("C");
			res.close();
			stmt.close();
			connection.close();
			if(conta>0){
			    return "You are registered yet! You have to login!";
			}else{
				Connection connection2 = DB.getConnection(account);
				connection2.createStatement().executeUpdate("INSERT INTO UTENTE (NOMEUTENTE, EMAIL, PASSWORD) VALUES ('"+account.toLowerCase()+"','"+email.toLowerCase()+"','"+pw.toLowerCase()+"')");
				connection2.close();
				return "You are now registered!";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Database crash!";
		}
	}
	
	/**
	 * Method used to create a new game where other users can enter to play, it also 
	 * controls that all the lobbies have different names.
	 * 
	 * @author Mattia
	 * @param partita
	 * @param account
	 * @return
	 * @throws SQLException 
	 */
	public String addGame(String partita,String account) throws SQLException{
		for(int i=0;i<getDimLobbies();i++){
			if(partita.equals(lobbies.get(i).getLobbyName()))
				return "Sorry, but the name of the game is already use, change name";
		}
		lobbies.add(new Partita(partita,account,getDimLobbies(), DB.getConnection(account)));
		for(Partita mom : lobbies){
			System.out.println(mom.getLobbyName());
		}
		return "Welcome to the lobby";
	}

	
	public int getIndicePartita(String name){
		int i=0;
		while(true){
			if(!lobbies.get(i).getLobbyName().equals(name))
				i++;
			else
				break;
		}
		return i;
	}

	public int getDimLobbies() {
		return lobbies.size();
	}
	
	public String getNameLobby(int i){
		return lobbies.get(i).getLobbyName();
	}

	public void addGamer(int positionGame, String color, String account) {
		Giocatore g = new Giocatore(color,lobbies.get(positionGame),account, positionGame);
		lobbies.get(positionGame).addGiocatore(g);
	}

	public ArrayList<Partita> getLobbies() {
		return lobbies;
	}
	
	public Partita getLobbyByNumber(int number){
		return lobbies.get(number);
	}
	
	public Partita getLobbyByName(String lobby){
		for(Partita p:lobbies)
			if(p.getLobbyName().equals(lobby))
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

	public void deletLobby(int positionGame) {
		lobbies.remove(positionGame);	
	}

	public void removeAccount(String name) {
		for(int i=0;i<utente.size();i++)
			if(utente.get(i).equals(name))
				utente.remove(i);
		System.out.println("Utente "+name+" rimosso");
	}
	
}
