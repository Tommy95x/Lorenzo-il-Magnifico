package server;

import java.util.ArrayList;

import server.element.Giocatore;
import server.element.Partita;

/*
*Classe utilizzata per l'avvio del server. Si istanziano due differenti Threads che avviano in corrispondenza un RMIServer per la
*connessione dei Clients che utilizzeranno RMI e un SocketServer per tutti quei Clients che si collegheranno mediante Socket.
*/
public class StartServer {

	private ArrayList<String> utente= new ArrayList<String>();
	private ArrayList<Partita> lobbies = new ArrayList <Partita>();
	
	
	public static void main(String[] args) {
		StartServer server = new StartServer();
		RMIServer rmi = new RMIServer(1099, server);
		SocketServer ss = new SocketServer(3000,server);
		
		rmi.start();
		ss.start();
	}

	public String addClient(String account, String pw){
		String mom=account+pw;
		for(int i=0; i<utente.size();i++){
			if((mom).equals(utente.get(i)))
				return "Player alredy login";
		}
		if(/*Ritorna la presenza o meno del giocatore all'interno del db gli vanno passati sia l'account che la pw*/){
			return "Welcome to the game";
		}else
			return "For player must register a new account";
	}
	
	public boolean registerNewClient(String account, String pw, String email) {
		return false;
		// Metodo d'aggiunta di un nuovo utente al sistema
		
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
}
