package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.control.Tooltip;
import server.element.CartaSviluppo;
import server.element.Giocatore;

/*
*Classe che comunica con un SocketClient che ha istanziato e creato una nuva connessione inprecedenza con il ServerSocket, la classe
*di conseguenza implementa l'interfaccia Runnable che verra' eseguito da un Executor istanziato in precedenza alla creazione di una 
*connessione da parte di un client.
**/
public class ThreadSocketServer implements Runnable {

	private StartServer commonServer;
	private Socket socket;
	private Scanner input;
	private PrintWriter output;
	private String account;
	private String pw;
	private String pw2;
	private String action;
	private String email;
	private String lobby;
	private String color;
	private int positionGame;
	private ImplementServerInterface actionsServer;
	private int x;
	private int y;
	private ObjectInputStream inputObject;

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

	public void closeSocket() {
		output.println("Gioco finito");
		input.close();
		output.close();
	}

	private void outArray(String[] colors, PrintWriter output2) {
		for (String s : colors) {
			output.println(s);
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

	private void play() throws RemoteException, SQLException {
		action = input.nextLine();
		while (true) {
			switch (action) {
			case "dices":
				output.println(actionsServer.showDiceValues(positionGame, account));
				output.flush();
				break;
			case "controllo posizionamento":
				color = input.nextLine();
				double x = input.nextDouble();
				double y = input.nextDouble();
				positionGame = input.nextInt();
				account = input.nextLine();
				int agg = input.nextInt();
				output.println(
						commonServer.getLobbyByNumber(positionGame).getGiocatoreByName(account).controlloPosizionamento(
								color, x, y, commonServer.getDBConnection().getConnection(account), agg));
				output.flush();
				break;
			case "getCardsGamer":
				lobby = input.nextLine();
				account = input.nextLine();
				try {
					commonServer.getLobbyByName(lobby).getGiocatoreByName(account).addCard((CartaSviluppo) inputObject.readObject());
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				break;
			case "quit":
				closeSocket();
				break;
			}
		}
	}

	public void run() {
		try {
			input = new Scanner(socket.getInputStream());
			output = new PrintWriter(socket.getOutputStream());
			inputObject = new ObjectInputStream(socket.getInputStream());
			while (true) {
				action = input.nextLine();
				System.out.println(action);
				switch (action) {
				case "login":
					System.out.println("Richiesto login");
					account = input.nextLine();
					pw = input.nextLine();
					output.println(actionsServer.login(account, pw));
					output.flush();
					break;
				case "register":
					System.out.println("Richiesta registrazione");
					account = input.nextLine();
					pw = input.nextLine();
					pw2 = input.nextLine();
					email = input.nextLine();
					output.println(actionsServer.register(account, pw, pw2, email));
					output.flush();
					break;
				case "create new lobby":
					lobby = input.nextLine();
					// account=input.nextLine();
					color = input.nextLine();
					output.println(actionsServer.createNewLobby(lobby, account, color, null));
					output.flush();
					commonServer.getLobbyByName(lobby).getGiocatoreByName(account).getSocket(this);
					break;
				case "get lobbies":
					output.println(actionsServer.getLobby());
					break;
				case "enter in a lobby":
					lobby = input.nextLine();
					color = input.nextLine();
					commonServer.getLobbyByName(lobby).addGiocatore(new Giocatore(color,
							commonServer.getLobbyByName(lobby), account, commonServer.getIndicePartita(lobby)));
					output.println(commonServer.getIndicePartita(lobby));
					output.flush();
					break;
				case "getColors":
					lobby = input.nextLine();
					output.println(commonServer.getLobbyByName(lobby).getColors());
					output.flush();
					break;
				case "exitToTheGame":
					lobby = input.nextLine();
					color = input.nextLine();
					commonServer.getLobbyByName(lobby).exitToGame(account, color);
					break;
				case "start":
					positionGame = input.nextInt();
					output.println(actionsServer.startPartita(account, positionGame));
					output.flush();
					break;
				}
			}
		} catch (IOException | SQLException e) {
			System.err.println("Error lost socket connection");
			output.println("Error lost socket connection");
			output.close();
			input.close();
			e.printStackTrace();
		}
	}

	private void getCards(PrintWriter output) {
		ArrayList<CartaSviluppo> mom = null;
		try {
			mom = actionsServer.getCards(positionGame);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (CartaSviluppo c : mom) {
			output.println(c.getNameCard());
			output.flush();
			output.println(c.getImage());
			output.flush();
		}
		output.println("endCards");
	}

	public void notifyStartGame() {
		output.println("gioca");
		output.flush();
		try {
			play();
		} catch (RemoteException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void playGamer() {
		// TODO Auto-generated method stub

	}

	public void addScomunica(int nScomuniche, Tooltip tooltip) {
		// TODO Auto-generated method stub

	}

	public void notifyTurno() {
		// TODO Auto-generated method stub

	}

	public void moveFamiliareAvv(double x2, double y2, String colorPlayer, String color2) {
		// TODO Auto-generated method stub

	}
}
