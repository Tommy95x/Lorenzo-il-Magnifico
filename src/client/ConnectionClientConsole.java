package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.sun.glass.events.KeyEvent;

import server.ServerInterface;
import server.element.Dado;
import server.element.Partita;

@SuppressWarnings("restriction")
public class ConnectionClientConsole extends ConnectionRmiClient{

	private int port;
	private ServerInterface serverMethods;
	private int positionGame;
	private String name;
	private int numberOfGamers;
	private Scanner input;
	private String account;
	
	public ConnectionClientConsole(){
		
		connect();
		login();
		
	}

	private void connect() {
		
		try {
			Registry registry = LocateRegistry.getRegistry(port);
			System.out.println("Get registry from Server");
			String [] e = registry.list();
			for(String mom:e)
				System.out.println(mom);
			String remoteInterface="ServerInterface";
			serverMethods = (ServerInterface) registry.lookup(remoteInterface);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			System.out.println("Error connection not create");
			e.printStackTrace();
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			
			e1.printStackTrace();
		}
		
	}
	
	//Metodo di test per il login
		private void login() {
			input = new Scanner(System.in);
			System.out.println("Inserisci il nome dell'account o scrivi Register per creare un nuovo account");
			String account = input.nextLine();
			if(account.equals("Register"))
				registerStart();
			System.out.println("Inserisci la password");
			String pw = input.nextLine();
			try {
				System.out.println(serverMethods.login(account, pw));
				try {
					startMenu();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	private void registerStart() {
		
		System.out.println("Inserisci il nome dell'account che vuoi creare");
		account = input.nextLine();
		System.out.println("Inserisci la password");
		String pw1 = input.nextLine();
		System.out.println("Ri-inserisci la password");
		String pw2 = input.nextLine();
		System.out.println("Inserisci la email");
		String email = input.nextLine();
		try {
			System.out.println(serverMethods.register(account, pw1, pw2, email));
			login();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void startMenu() throws SQLException {
		String lobby;
		String[] colors = null;
		String color;
		System.out.println("Premi 1 se vuoi creare una nuova lobby di gioco o 2 se vuoi entrare in una lobby gia' esistente");
		int menu = input.nextInt();
		input.nextLine();
		switch(menu){
			case 1: 
				System.out.println("Inserisci il nome della nuova lobby");
				lobby = input.nextLine();
				System.out.println("Questi sono i colori che puoi scegliere: blue, orange, white e green");
				System.out.println("Inserisci il colore che vorrai avere");
				color = input.nextLine();
				try {
					setPositionGame(serverMethods.createNewLobby(lobby, account, color, this));
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 2:
				ArrayList<Partita> lobbies = null;
				try {
					lobbies = serverMethods.getLobby();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(int i=0; i<lobbies.size();i++){
					System.out.println(lobbies.get(i).getLobbyName());
				}
				System.out.println("Scrivi il nome della lobby in cui vuoi entrare");
				lobby = input.nextLine();
				try {
					colors = serverMethods.getColors(lobby);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(int i=0;i<colors.length;i++){
					System.out.println(colors);
				}
				System.out.println("Inserisci il colore che vorrai avere");
				color = input.nextLine();
				try {
					serverMethods.selectLobby(lobby, account, color, this);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				waitGamer();
				break;
		}
	}

	private void waitGamer() {
		System.out.println("Premi enter se sei pronto a entrare nel gioco o attendi");
		while(true)
			if(keyPressed(new KeyEvent())){
				break;
			}
		System.out.println("In attesa degli altri giocatori...");
	}
	

	private boolean keyPressed(KeyEvent e) {
		if(e.equals(KeyEvent.VK_ENTER)){
			return true;
		}
		return false;
	}
	
	private void setName(String name) {
		this.name = name;
	}

	private void setPositionGame(int positionGame) {
		this.positionGame = positionGame;
	}
	
	public int getPositionGame() {
		return positionGame;
	}

	public String getName() {
		return name;
	}

	public void startGame(String name){
		System.out.println("Il gioco e' iniziato");
		System.out.println("Inizia il turno "+name);
	}
	
	public void notifyTurno() throws RemoteException, SQLException{
		int mosseDisponibili = 4;
		System.out.println("Fai le tue mosse nel seguente ordine:\n1)Lancia i dadi(verranno lanciati all'inizio del tuo turno mostrando i loro valori)\n2)Scrivi il colore del familiare che vuoi spostare (ricorda i familiari sono di colore Arancio, Nero, Bianco, Neutro)\n3)Scrivi la posizione numerica nel tabellone in cui vuoi inserire il familiare (scrivendo back de-selezioni il familiare selezionato)\n4)Acquisisci carte e vedi i relativi effetti\n5)Verifichi il tuo punteggio, scrivendo punteggio o personal score\n");
		Dado[] dadi = serverMethods.showDiceValues(positionGame, account);
		for(int i=0; i<3;i++){
			System.out.println("Il dado "+dadi[i].getColor()+" vale "+dadi[i].getValore());
		}
		do{
			String action = input.nextLine();
			switch(action.toLowerCase()){
			case "nero":
				System.out.println("Inserisci la posizione o scrivi back per tornare indietro");
				action = input.nextLine();
				if(action.equals("back"))
					break;
				else{
					double y = input.nextDouble();
					input.nextLine();
					String mom = serverMethods.controlloPosizionamento("black", positionGame, name, Double.valueOf(action), y);
					if(mom.equals("OK")){
						System.out.println("Familiare posizionato");
					}else{
						System.out.println(mom);
						mosseDisponibili++;
					}
					serverMethods.notifySpostamento("black",Double.valueOf(action) , y, name, positionGame);  
					mosseDisponibili--;
				}
				break;
			case "black":
				System.out.println("Inserisci la posizione o scrivi back per tornare indietro");
				action = input.nextLine();
				if(action.equals("back"))
					break;
				else{
					double y = input.nextDouble();
					input.nextLine();
					String mom = serverMethods.controlloPosizionamento("black", positionGame, name, Double.valueOf(action), y);
					if(mom.equals("OK")){
						System.out.println("Familiare posizionato");
					}else{
						System.out.println(mom);
						mosseDisponibili++;
					}
					serverMethods.notifySpostamento("black",Double.valueOf(action) , y, name, positionGame);
					mosseDisponibili--;
				}
				break;
			case "orange":
				System.out.println("Inserisci la posizione o scrivi back per tornare indietro");
				action = input.nextLine();
				if(action.equals("back"))
					break;
				else{
					double y = input.nextDouble();
					input.nextLine();
					String mom = serverMethods.controlloPosizionamento("orange", positionGame, name, Double.valueOf(action), y);
					if(mom.equals("OK")){
						System.out.println("Familiare posizionato");
					}else{
						System.out.println(mom);
						mosseDisponibili++;
					}
					serverMethods.notifySpostamento("orange",Double.valueOf(action) , y, name, positionGame); 
					mosseDisponibili--;
				}
				break;
			case "arancione":
				System.out.println("Inserisci la posizione o scrivi back per tornare indietro");
				action = input.nextLine();
				if(action.equals("back"))
					break;
				else{
					double y = input.nextDouble();
					input.nextLine();
					String mom = serverMethods.controlloPosizionamento("orange", positionGame, name, Double.valueOf(action), y);
					if(mom.equals("OK")){
						System.out.println("Familiare posizionato");
					}else{
						System.out.println(mom);
						mosseDisponibili++;
					}
					serverMethods.notifySpostamento("orange",Double.valueOf(action) , y, name, positionGame); 
					mosseDisponibili--;
				}
				break;
			case "white":
				System.out.println("Inserisci la posizione o scrivi back per tornare indietro");
				action = input.nextLine();
				if(action.equals("back"))
					break;
				else{
					double y = input.nextDouble();
					input.nextLine();
					String mom = serverMethods.controlloPosizionamento("white", positionGame, name, Double.valueOf(action), y);
					if(mom.equals("OK")){
						System.out.println("Familiare posizionato");
					}else{
						System.out.println(mom);
						mosseDisponibili++;
					}
					serverMethods.notifySpostamento("white",Double.valueOf(action) , y, name, positionGame); 
					mosseDisponibili--;
				}
				break;
			case "bianco":
				System.out.println("Inserisci la posizione x e poi o scrivi back per tornare indietro");
				action = input.nextLine();
				if(action.equals("back"))
					break;
				else{
					double y = input.nextDouble();
					input.nextLine();
					String mom = serverMethods.controlloPosizionamento("white", positionGame, name, Double.valueOf(action), y);
					if(mom.equals("OK")){
						System.out.println("Familiare posizionato");
					}else{
						System.out.println(mom);
						mosseDisponibili++;
					}
					serverMethods.notifySpostamento("white",Double.valueOf(action) , y, name, positionGame); 
					mosseDisponibili--;
				}
				break;
			case "neutro":
				System.out.println("Inserisci la posizione o scrivi back per tornare indietro");
				action = input.nextLine();
				if(action.equals("back"))
					break;
				else{
					double y = input.nextDouble();
					input.nextLine();
					String mom = serverMethods.controlloPosizionamento("neutro", positionGame, name, Double.valueOf(action), y);
					if(mom.equals("OK")){
						System.out.println("Familiare posizionato");
					}else{
						System.out.println(mom);
						mosseDisponibili++;
					}
					serverMethods.notifySpostamento("neutro",Double.valueOf(action) , y, name, positionGame); 
					mosseDisponibili--;
				}
				break;
			case "neutral":
				System.out.println("Inserisci la posizione o scrivi back per tornare indietro");
				action = input.nextLine();
				if(action.equals("back"))
					break;
				else{
					double y = input.nextDouble();
					input.nextLine();
					String mom = serverMethods.controlloPosizionamento("neutro", positionGame, name, Double.valueOf(action), y);
					if(mom.equals("OK")){
						System.out.println("Familiare posizionato");
					}else{
						System.out.println(mom);
						mosseDisponibili++;
					}
					serverMethods.notifySpostamento("neutro",Double.valueOf(action) , y, name, positionGame); 
					mosseDisponibili--;
				}
				break;
			case "mostra le posizioni":
				//serverMethods.getPositions();
				//Guardare le hash maps per utilizzare direttamente quelle
				break;
			case "personal score":
				//serverMethods.showPersonalScore(account,positionGame);
				//Guardare le hash maps per utilizzare direttamente quelle
				break;
			case "punteggio":
				//serverMethods.showPersonalScore(account,positionGame);
				//Guardare le hash maps per utilizzare direttamente quelle
				break;
			}
		}while(mosseDisponibili>0);
		serverMethods.changeGamer(positionGame);
	}
	
	public void moveDisco(double x, double y, String colorPlayer, String colorDisco) throws RemoteException{
		System.out.println("Il giocatore di colore "+colorPlayer+ "ha totalizzato dei punti il suo disco dei punti vittoria si muove nella posizione "+x+" "+y);
	}
	public void moveDiscoFede(double x, double y, String colorPlayer, String colorDisco) throws RemoteException{
		System.out.println("Il giocatore di colore "+colorPlayer+ "sostiene il credo e la chiesa il suo disco fede si muove nella posizione "+x+" "+y);
	}
	public void moveFamiliareAvv(double x, double y, String colorPlayer, String colorFam) throws RemoteException{
		System.out.println("Il giocatore di colore "+colorPlayer+ "ha mosso il suo familiare di colore "+colorFam+" nella posizione "+x+" "+y);
	}
	
}
