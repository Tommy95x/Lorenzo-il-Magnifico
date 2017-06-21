package client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import client.gui.StartClientGui;
import client.gui.controllers.ControllerGame;
import javafx.scene.control.Tooltip;
import server.element.CartaEdifici;
import server.element.CartaImprese;
import server.element.CartaPersonaggi;
import server.element.CartaSviluppo;
import server.element.CartaTerritori;
import server.element.Dado;
import server.element.Giocatore;
import server.element.Partita;
import server.element.Portafoglio;
import server.element.TesseraScomunica;

/*
 * Classe che implementa il socket
 */

public class ConnectionSocketClient extends ConnectionClient implements ClientInterface {

	private Socket socket;
	private ObjectInputStream inputSocket;
	private ObjectOutputStream outputSocket;
	private String ip = "127.0.0.1";
	private int port = 3000;
	private int positionGame;
	private int numberOfGamers;
	private String name;
	private ControllerGame guiGame;
	private StartClientGui start;
	@SuppressWarnings("unused")
	private String lobby;

	public ConnectionSocketClient() throws RemoteException {
		System.out.println("Start Socket Client");
		connect();
	}

	private void connect() {
		try {
			socket = new Socket(ip, port);
			System.out.println("Creato nuovo socket");
			// Creo i canali di comunicazione
			outputSocket = new ObjectOutputStream(socket.getOutputStream());
			outputSocket.flush();
			inputSocket = new ObjectInputStream(socket.getInputStream());
			System.out.println("Create a new connection");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String login(String account, String pw) throws ClassNotFoundException, IOException {
		System.out.println("Login");
		outputSocket.writeObject("login");
		outputSocket.flush();

		outputSocket.writeObject(account);
		outputSocket.flush();
		name = account;

		outputSocket.writeObject(pw);
		outputSocket.flush();

		System.out.println("Inviati i dati di login");
		return inputSocket.readObject().toString();
	}

	public void richiestaRegistrazione() throws IOException {
		outputSocket.writeObject("register");
		outputSocket.flush();
	}

	public String register(String account, String pw, String pw2, String email)
			throws IOException, ClassNotFoundException {
		outputSocket.writeObject(account);
		outputSocket.flush();
		outputSocket.writeObject(pw);
		outputSocket.flush();
		outputSocket.writeObject(pw2);
		outputSocket.flush();
		outputSocket.writeObject(email);
		outputSocket.flush();
		return inputSocket.readObject().toString();
	}

	public boolean createANewLobby(String lobby, String color) throws IOException {
		outputSocket.writeObject("create new lobby");
		outputSocket.flush();
		outputSocket.writeObject(lobby);
		outputSocket.flush();
		outputSocket.writeObject(color);
		outputSocket.flush();
		try {
			setPositionGame((int) inputSocket.readObject());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public ArrayList<Partita> lobbiesView() throws IOException, ClassNotFoundException {
		String mom;
		ArrayList<Partita> partita = new ArrayList<Partita>();
		outputSocket.writeObject("get lobbies");
		outputSocket.flush();
		while (true) {
			mom = (String) inputSocket.readObject();
			if (mom.equals("stop"))
				break;
			else
				partita.add(new Partita(mom));
		}
		return partita;
	}

	public int enterInALobby(String lobby, String color) throws IOException, ClassNotFoundException {
		this.lobby = lobby;
		outputSocket.writeObject("enter in a lobby");
		outputSocket.flush();
		outputSocket.writeObject(lobby);
		outputSocket.flush();
		outputSocket.writeObject(color);
		outputSocket.flush();
		setPositionGame((int) inputSocket.readObject());
		return positionGame;
	}

	public TesseraScomunica[] getCardsScomunica() throws ClassNotFoundException, IOException {
		outputSocket.writeObject("getTessereScomunica");
		outputSocket.flush();
		TesseraScomunica[] t = new TesseraScomunica[3];
		for (int i = 0; i < 3; i++) {
			t[i] = (TesseraScomunica) inputSocket.readObject();
			System.out.println(t[i].getNome());
		}
		return t;
	}

	public void startGame() throws IOException, ClassNotFoundException {
		System.out.println("Start");
		outputSocket.writeObject("Start");
		outputSocket.flush();
	}

	public Dado[] lanciaDadi() throws ClassNotFoundException, IOException {
		outputSocket.writeObject("dices");
		outputSocket.flush();
		return (Dado[]) inputSocket.readObject();
	}

	public String[] getColors(String lobby) throws IOException, ClassNotFoundException {
		outputSocket.writeObject("getColors");
		outputSocket.flush();
		outputSocket.writeObject(lobby);
		outputSocket.flush();
		return (String[]) inputSocket.readObject();

	}

	public int getNumberOfGamers() {
		return numberOfGamers;
	}

	public void setNumberOfGamers(int numberOfGamers) {
		this.numberOfGamers = numberOfGamers;
	}

	public void selectColorGamer(String color) throws IOException {
		outputSocket.writeObject(color);
		outputSocket.flush();
	}

	public String controlloPosizionamento(String color, double x, double y, int agg)
			throws IOException, ClassNotFoundException {
		outputSocket.writeObject("controllo posizionamento");
		outputSocket.flush();
		outputSocket.writeObject(color);
		outputSocket.flush();
		outputSocket.writeObject(x);
		outputSocket.flush();
		outputSocket.writeObject(y);
		outputSocket.flush();
		outputSocket.writeObject(positionGame);
		outputSocket.flush();
		outputSocket.writeObject(name);
		outputSocket.flush();
		outputSocket.writeObject(agg);
		outputSocket.flush();
		return inputSocket.readObject().toString();
	}

	public void setGuiGame(ControllerGame guiGame) {
		this.guiGame = guiGame;
	}

	/*
	 * public void waitServerAction() throws ClassNotFoundException, IOException
	 * { switch (inputSocket.readObject().toString()) { case "": break; case "":
	 * break; case "": break; case "": break; case "": break; } }
	 */

	public void waitTurno() throws ClassNotFoundException, IOException {
		double x;
		double y;
		String colorPlayer;
		String color;
		while (true) {
			switch (inputSocket.readObject().toString()) {
			case "disco":
				x = (double) inputSocket.readObject();
				y = (double) inputSocket.readObject();
				color = inputSocket.readObject().toString();
				guiGame.notifySpostamentoPuntiFede(x, y, color);
				break;
			case "familiareAvv":
				x = (double) inputSocket.readObject();
				y = (double) inputSocket.readObject();
				colorPlayer = inputSocket.readObject().toString();
				color = inputSocket.readObject().toString();
				guiGame.moveFamAvv(colorPlayer, color, x, y);
				break;
			case "discoFede":
				x = (double) inputSocket.readObject();
				y = (double) inputSocket.readObject();
				color = inputSocket.readObject().toString();
				guiGame.notifySpostamentoPuntiFede(x, y, color);
				break;
			case "startTurno":
				guiGame.enableGame((int) inputSocket.readObject());
				break;
			case "carteAvv":
				guiGame.notifyAddCardAvv(inputSocket.readObject().toString(), inputSocket.readObject().toString(),
						(int) inputSocket.readObject());
				break;
			case "militari":
				x = (double) inputSocket.readObject();
				y = (double) inputSocket.readObject();
				color = (String) inputSocket.readObject();
				break;
			}
		}
	}

	public void setCardGiocatore(CartaSviluppo carta, int i) throws IOException {
		outputSocket.writeObject("addCard");
		outputSocket.flush();
		outputSocket.writeObject(carta);
		outputSocket.flush();
		outputSocket.writeObject(i);
		outputSocket.flush();
	}

	public void notifySpostamento(String color, double x, double y) throws IOException {
		outputSocket.writeObject("notifySpostamento");
		outputSocket.flush();
		outputSocket.writeObject(color);
		outputSocket.flush();
		outputSocket.writeObject(x);
		outputSocket.flush();
		outputSocket.writeObject(y);
		outputSocket.flush();
	}

	public String getNamePosition(double x, double y) throws IOException, ClassNotFoundException {
		outputSocket.writeObject("getNamePosition");
		outputSocket.flush();
		outputSocket.writeObject(x);
		outputSocket.flush();
		outputSocket.writeObject(y);
		outputSocket.flush();
		return inputSocket.readObject().toString();
	}

	public void exitToTheGame(String lobby, String color) throws IOException {
		outputSocket.writeObject("exitToTheGame");
		outputSocket.flush();
		outputSocket.writeObject(lobby);
		outputSocket.flush();
		outputSocket.writeObject(color);
		outputSocket.flush();
	}

	public CartaSviluppo[] getCardsGame() throws ClassNotFoundException, IOException {
		CartaSviluppo[] c = new CartaSviluppo[16];
		outputSocket.writeObject("getCardsGame");
		outputSocket.flush();
		for(int i =0;i<16;i++)
			c[i] = (CartaSviluppo) inputSocket.readObject();
		return c;
	}

	public Portafoglio getRisorse() throws ClassNotFoundException, IOException {
		outputSocket.writeObject("getPortafglio");
		outputSocket.flush();
		Portafoglio p = (Portafoglio) inputSocket.readObject();
		System.out.println(p.getDimRisorse("monete"));
		return p;
	}

	public int getPositionGame() {
		return positionGame;
	}

	public void waitStartGame(StartClientGui start) throws ClassNotFoundException, IOException {
		System.out.println("Attesa ok");
		if (inputSocket.readObject().toString().equals("start")) {
			System.out.println("ok");
			start.changeStage(5);
		}
	}

	public void setPositionGame(int positionGame) {
		this.positionGame = positionGame;
	}

	public void setStart(StartClientGui start) {
		this.start = start;
	}

	public StartClientGui getStart() {
		return start;
	}

	public void deleteView() throws IOException {
		outputSocket.writeObject("deleteView");
		outputSocket.flush();
		outputSocket.writeObject(positionGame);
		outputSocket.flush();
	}

	public void removeAccount() throws IOException {
		outputSocket.writeObject("exitAccount");
		outputSocket.flush();
		outputSocket.writeObject(name);
		outputSocket.flush();
	}

	public Giocatore[] getGiocatori() throws IOException, ClassNotFoundException {
		outputSocket.writeObject("giocatori");
		outputSocket.flush();
		Giocatore[] g = new Giocatore[4];
		for (int j = 0; j < 4; j++) {
			g[j] = new Giocatore(inputSocket.readObject().toString(), inputSocket.readObject().toString(),
					(Portafoglio) inputSocket.readObject());
			System.out.println(g[j].getName());
		}
		return g;
	}

	@Override
	public void spendereRisorse(String risorsa, int qta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sostegnoChiesa(boolean flag) {
		// TODO Auto-generated method stub

	}

	public void addScomunica(int nScomuniche, Tooltip tooltip) {
	}
}
