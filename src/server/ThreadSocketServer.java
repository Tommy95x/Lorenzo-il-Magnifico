package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Tooltip;
import server.element.CartaSviluppo;
import server.element.Giocatore;
import server.element.Partita;
import server.element.Portafoglio;

/*
*Classe che comunica con un SocketClient che ha istanziato e creato una nuva connessione inprecedenza con il ServerSocket, la classe
*di conseguenza implementa l'interfaccia Runnable che verra' eseguito da un Executor istanziato in precedenza alla creazione di una 
*connessione da parte di un client.
**/
public class ThreadSocketServer implements Runnable{

	private StartServer commonServer;
	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private String account;
	private String pw;
	private String pw2;
	private String action;
	private String email;
	private String lobby;
	private String color;
	private int positionGame;
	private ImplementServerInterface actionsServer;

	public ThreadSocketServer(Socket executorSocket, StartServer commonServer) {
		this.commonServer = commonServer;
		this.socket = executorSocket;
		try {
			actionsServer = new ImplementServerInterface(commonServer);
		} catch (RemoteException e) {
			System.out.println("Error not create a new ImplementInterface");
			e.printStackTrace();
		}
		System.out.println("Creato nuovo executor di gestione client");
	}

	public void closeSocket() throws IOException {
		output.writeObject("Gioco finito");
		input.close();
		output.close();
	}

	private void outArray(String[] colors, PrintWriter output2) throws IOException {
		for (String s : colors) {
			output.writeObject(s);
			output.flush();
		}

	}

	/*
	 * private void closeAGamer(){ try {
	 * actionsServer.adviseOtherGamers(account,positionGame); } catch (Exception
	 * e) { // Pensare per la gestione dell'eccezione e.printStackTrace(); }
	 * 
	 * }
	 */

	private void play() throws SQLException, IOException, ClassNotFoundException {
		double x;
		double y;
		action = input.readObject().toString();
		while (true) {
			switch (action) {
			case "dices":
				output.writeObject(actionsServer.showDiceValues(positionGame, account));
				output.flush();
				break;
			case "controllo posizionamento":
				color = input.readObject().toString();
				x = input.readDouble();
				y = input.readDouble();
				positionGame = input.readInt();
				account = input.readObject().toString();
				int agg = input.readInt();
				output.writeObject(
						commonServer.getLobbyByNumber(positionGame).getGiocatoreByName(account).controlloPosizionamento(
								color, x, y, commonServer.getDBConnection().getConnection(account), agg));
				output.flush();
				break;
			case "getCardsGamer":
				lobby = input.readObject().toString();
				account = input.readObject().toString();
				try {
					commonServer.getLobbyByName(lobby).getGiocatoreByName(account).addCard((CartaSviluppo) input.readObject());
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "getNamePosition":
				output.writeObject(commonServer.getLobbyByNumber(positionGame).getNamePosition(input.readDouble(), input.readDouble(),commonServer.getDBConnection().getConnection(account)));
				output.flush();
				break;
			case "getPortafoglio":
				output.writeObject(commonServer.getLobbyByNumber(positionGame).getGiocatoreByName(account).getRisorse());
				output.flush();
				break;
			case "getTessereScomunica":
				output.writeObject(commonServer.getLobbyByNumber(positionGame).getCardsScomunica());
				output.flush();
				break;
			case "getCardsGame":
				CartaSviluppo[] mom = commonServer.getLobbyByNumber(positionGame).getCards();
				output.writeObject(mom);
				output.flush();
				break;
			case "notifySpostamento":
				String color = input.readObject().toString();
				x = input.readDouble();
				y = input.readDouble();
				commonServer.getLobbyByNumber(positionGame).notifySpostamento(color, commonServer.getLobbyByNumber(positionGame).getGiocatoreByName(account), x, y);
			case "quit":
				closeSocket();
				break;
			}
		}
	}

	public void run() {
		try {
			input = new ObjectInputStream(socket.getInputStream());
			output = new ObjectOutputStream(socket.getOutputStream());
			output.flush();
			while (true) {
				action = input.readObject().toString();
				System.out.println(action);
				switch (action) {
				case "login":
					System.out.println("Richiesto login");
					account = input.readObject().toString();
					pw = input.readObject().toString();
					output.writeObject(actionsServer.login(account, pw));
					output.flush();
					break;
				case "register":
					System.out.println("Richiesta registrazione");
					account = input.readObject().toString();
					pw = input.readObject().toString();
					pw2 = input.readObject().toString();
					email = input.readObject().toString();
					output.writeObject(actionsServer.register(account, pw, pw2, email));
					output.flush();
					break;
				case "create new lobby":
					lobby = input.readObject().toString();
					color = input.readObject().toString();
					System.out.println("Set partita");
					output.writeObject(actionsServer.createNewLobby(lobby, account, color, null));
					output.flush();
					commonServer.getLobbyByName(lobby).getGiocatoreByName(account).getSocket(this);
					break;
				case "get lobbies":
					for(Partita mom : commonServer.getLobbies()){
						output.writeObject(mom.getLobbyName());
						output.flush();
					}
					output.writeObject("stop");
					output.flush();
					break;
				case "enter in a lobby":
					lobby = input.readObject().toString();
					color = input.readObject().toString();
					commonServer.getLobbyByName(lobby).addGiocatore(new Giocatore(color,
							commonServer.getLobbyByName(lobby), account, commonServer.getIndicePartita(lobby)));
					output.writeObject(commonServer.getIndicePartita(lobby));
					output.flush();
					commonServer.getLobbyByName(lobby).getGiocatoreByName(account).getSocket(this);
					break;
				case "getColors":
					lobby = (String) input.readObject();
					output.writeObject(commonServer.getLobbyByName(lobby).getColors());
					output.flush();
					break;
				case "exitToTheGame":
					lobby = input.readObject().toString();
					color = input.readObject().toString();
					commonServer.getLobbyByName(lobby).exitToGame(account, color);
					break;
				case "start":
					positionGame = (int) input.readObject();
					actionsServer.startPartita(account, positionGame);
					break;
				case "deleteView":
					positionGame = (int) input.readObject();
					commonServer.deletLobby(positionGame);
					break;
				case "exitAccount":
					commonServer.removeAccount((String) input.readObject());
					socket.close();
					break;
				}
			}
		} catch (IOException | SQLException e) {
			System.err.println("Error lost socket connection");
			try {
				input.close();
				output.writeObject("Error lost socket connection");
				output.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void notifyStartGame() throws IOException, ClassNotFoundException {
		System.out.println("notifica partita");
		output.writeObject("start");
		output.flush();
		try {
			play();
		} catch (RemoteException | SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void notifyTurno() throws IOException {
		output.writeObject("startTurno");
		output.flush();

	}

	public void moveFamiliareAvv(double x, double y, String colorPlayer, String color) throws IOException {
		output.writeObject("familiareAvv");
		output.flush();
		output.writeObject(x);
		output.flush();
		output.writeObject(y);
		output.flush();
		output.writeObject(colorPlayer);
		output.flush();
		output.writeObject(color);
		output.flush();
	}
	
	public void moveDisco(double x, double y, String colorPlayer, String colorDisco) throws IOException {
		output.writeObject("disco");
		output.flush();
		output.writeObject(x);
		output.flush();
		output.writeObject(y);
		output.flush();
		output.writeObject(colorPlayer);
		output.flush();
		output.writeObject(colorDisco);
		output.flush();
	}
	
	public void moveDiscoFede(double x, double y, String colorPlayer, String colorDisco) throws IOException {
		output.writeObject("discoFede");
		output.flush();
		output.writeObject(x);
		output.flush();
		output.writeObject(y);
		output.flush();
		output.writeObject(colorPlayer);
		output.flush();
		output.writeObject(colorDisco);
		output.flush();
	}

	public void addScomunica(int nScomuniche, Tooltip tooltip) {
				
	}

	public void notifyAddCard(CartaSviluppo carta, String string, Portafoglio portafoglio) {
		// TODO Auto-generated method stub
		
	}
	
}
