package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.sun.glass.events.KeyEvent;

import server.element.CartaSviluppo;
import server.element.Dado;
import server.element.Giocatore;
import server.element.Partita;
import server.element.Portafoglio;
import server.element.Posizioni;
import server.element.TesseraScomunica;
import shared.RMIClientInterface;
import shared.ServerInterface;

@SuppressWarnings("restriction")
public class ConnectionClientConsole extends ConnectionRmiClient implements RMIClientInterface {

	private int port;
	private ServerInterface serverMethods;
	private int positionGame;
	private int numberOfGamers;
	private Scanner input;
	private String account;
	private String color;
	private CartaSviluppo[] carte = new CartaSviluppo[16];
	private TesseraScomunica[] scomuniche = new TesseraScomunica[3];
	private ArrayList<CartaSviluppo> cartePersonali = new ArrayList<CartaSviluppo>();
	private int turnodiGioco = 0;

	public ConnectionClientConsole() throws RemoteException {

		connect();
		login();

	}

	private void connect() {

		try {
			Registry registry = LocateRegistry.getRegistry(port);
			System.out.println("Get registry from Server");
			String[] e = registry.list();
			for (String mom : e)
				System.out.println(mom);
			String remoteInterface = "ServerInterface";
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

	// Metodo di test per il login
	private void login() {
		input = new Scanner(System.in);
		System.out.println("Inserisci il nome dell'account o scrivi Register per creare un nuovo account");
		String account = input.nextLine();
		if (account.equals("Register"))
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
		} catch (RemoteException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void startMenu() throws SQLException {
		String lobby;
		String[] colors = null;
		String color;
		System.out.println(
				"Premi 1 se vuoi creare una nuova lobby di gioco o 2 se vuoi entrare in una lobby gia' esistente");
		int menu = input.nextInt();
		input.nextLine();
		switch (menu) {
		case 1:
			System.out.println("Inserisci il nome della nuova lobby");
			lobby = input.nextLine();
			System.out.println("Questi sono i colori che puoi scegliere: blue, orange, white e green");
			System.out.println("Inserisci il colore che vorrai avere");
			color = input.nextLine();
			try {
				positionGame = serverMethods.createNewLobby(lobby, account, color);
				this.color = color;
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 2:
			ArrayList<String> lobbies = null;
			try {
				lobbies = serverMethods.getLobby();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (int i = 0; i < lobbies.size(); i++) {
				System.out.println(lobbies.get(i));
			}
			System.out.println("Scrivi il nome della lobby in cui vuoi entrare");
			lobby = input.nextLine();
			try {
				colors = serverMethods.getColors(lobby);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (int i = 0; i < colors.length; i++) {
				System.out.println(colors);
			}
			System.out.println("Inserisci il colore che vorrai avere");
			color = input.nextLine();
			try {
				positionGame = serverMethods.createNewLobby(lobby, account, color);
				serverMethods.setClientInterface(positionGame, account, this);
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
		while (true)
			if (keyPressed(new KeyEvent())) {
				break;
			}
		System.out.println("In attesa degli altri giocatori...");
	}

	private boolean keyPressed(KeyEvent e) {
		if (e.equals(KeyEvent.VK_ENTER)) {
			return true;
		}
		return false;
	}

	private void setPositionGame(int positionGame) {
		this.positionGame = positionGame;
	}

	public int getPositionGame() {
		return positionGame;
	}

	public String getName() {
		return account;
	}

	public void startGame(String name) {
		System.out.println("Il gioco e' iniziato");
		System.out.println("Inizia il turno " + name);
		System.out.println("Potrai fare le tue mosse quando arriverà il tuo turno");
	}

	public void play() throws RemoteException, SQLException {
		int mosseDisponibili = 4;
		System.out.println("Il turno di gioco e': " + turnodiGioco);
		System.out.println(
				"Fai le tue mosse nel seguente ordine:\n1)Lancia i dadi(verranno lanciati all'inizio del tuo turno mostrando i loro valori)\n2)Scrivi il colore del familiare che vuoi spostare (ricorda i familiari sono di colore Arancio, Nero, Bianco, Neutro)\n3)Scrivi la posizione numerica nel tabellone in cui vuoi inserire il familiare (scrivendo back de-selezioni il familiare selezionato)\n4)Acquisisci carte e vedi i relativi effetti\n5)Verifichi il tuo punteggio, scrivendo punteggio o personal score\n");
		Dado[] dadi = serverMethods.showDiceValues(positionGame, account);
		for (int i = 0; i < 3; i++) {
			System.out.println("Il dado " + dadi[i].getColor() + " vale " + dadi[i].getValore());
		}
		do {
			String action = input.nextLine();
			switch (action.toLowerCase()) {
			case "nero":
				System.out.println("Inserisci la posizione o scrivi back per tornare indietro");
				action = input.nextLine();
				if (action.equals("back"))
					break;
				else {
					double y = input.nextDouble();
					input.nextLine();
					String mom = serverMethods.controlloPosizionamento("black", positionGame, account,
							Double.parseDouble(action), y, 0);
					if (mom.equals("OK")) {
						System.out.println("Familiare posizionato");
					} else {
						System.out.println(mom);
						mosseDisponibili++;
					}
					serverMethods.notifySpostamento("black", Double.valueOf(action), y, account, positionGame);
					mosseDisponibili--;
				}
				break;
			case "black":
				System.out.println("Inserisci la posizione o scrivi back per tornare indietro");
				action = input.nextLine();
				if (action.equals("back"))
					break;
				else {
					double y = input.nextDouble();
					input.nextLine();
					String mom = serverMethods.controlloPosizionamento("black", positionGame, account,
							Double.valueOf(action), y, 0);
					if (mom.equals("OK")) {
						System.out.println("Familiare posizionato");
					} else {
						System.out.println(mom);
						mosseDisponibili++;
					}
					serverMethods.notifySpostamento("black", Double.valueOf(action), y, account, positionGame);
					mosseDisponibili--;
				}
				break;
			case "orange":
				System.out.println("Inserisci la posizione o scrivi back per tornare indietro");
				action = input.nextLine();
				if (action.equals("back"))
					break;
				else {
					double y = input.nextDouble();
					input.nextLine();
					String mom = serverMethods.controlloPosizionamento("orange", positionGame, account,
							Double.valueOf(action), y, 0);
					if (mom.equals("OK")) {
						System.out.println("Familiare posizionato");
					} else {
						System.out.println(mom);
						mosseDisponibili++;
					}
					serverMethods.notifySpostamento("orange", Double.valueOf(action), y, account, positionGame);
					mosseDisponibili--;
				}
				break;
			case "arancione":
				System.out.println("Inserisci la posizione o scrivi back per tornare indietro");
				action = input.nextLine();
				if (action.equals("back"))
					break;
				else {
					double y = input.nextDouble();
					input.nextLine();
					String mom = serverMethods.controlloPosizionamento("orange", positionGame, account,
							Double.valueOf(action), y, 0);
					if (mom.equals("OK")) {
						System.out.println("Familiare posizionato");
					} else {
						System.out.println(mom);
						mosseDisponibili++;
					}
					serverMethods.notifySpostamento("orange", Double.valueOf(action), y, account, positionGame);
					mosseDisponibili--;
				}
				break;
			case "white":
				System.out.println("Inserisci la posizione o scrivi back per tornare indietro");
				action = input.nextLine();
				if (action.equals("back"))
					break;
				else {
					double y = input.nextDouble();
					input.nextLine();
					String mom = serverMethods.controlloPosizionamento("white", positionGame, account,
							Double.valueOf(action), y, 0);
					if (mom.equals("OK")) {
						System.out.println("Familiare posizionato");
					} else {
						System.out.println(mom);
						mosseDisponibili++;
					}
					serverMethods.notifySpostamento("white", Double.valueOf(action), y, account, positionGame);
					mosseDisponibili--;
				}
				break;
			case "bianco":
				System.out.println("Inserisci la posizione x e poi o scrivi back per tornare indietro");
				action = input.nextLine();
				if (action.equals("back"))
					break;
				else {
					double y = input.nextDouble();
					input.nextLine();
					String mom = serverMethods.controlloPosizionamento("white", positionGame, account,
							Double.valueOf(action), y, 0);
					if (mom.equals("OK")) {
						System.out.println("Familiare posizionato");
					} else {
						System.out.println(mom);
						mosseDisponibili++;
					}
					serverMethods.notifySpostamento("white", Double.valueOf(action), y, account, positionGame);
					mosseDisponibili--;
				}
				break;
			case "neutro":
				System.out.println("Inserisci la posizione o scrivi back per tornare indietro");
				action = input.nextLine();
				if (action.equals("back"))
					break;
				else {
					double y = input.nextDouble();
					input.nextLine();
					String mom = serverMethods.controlloPosizionamento("neutro", positionGame, account,
							Double.valueOf(action), y, 0);
					if (mom.equals("OK")) {
						System.out.println("Familiare posizionato");
					} else {
						System.out.println(mom);
						mosseDisponibili++;
					}
					serverMethods.notifySpostamento("neutro", Double.valueOf(action), y, account, positionGame);
					mosseDisponibili--;
				}
				break;
			case "neutral":
				System.out.println("Inserisci la posizione o scrivi back per tornare indietro");
				action = input.nextLine();
				if (action.equals("back"))
					break;
				else {
					double y = input.nextDouble();
					input.nextLine();
					String mom = serverMethods.controlloPosizionamento("neutro", positionGame, account,
							Double.valueOf(action), y, 0);
					if (mom.equals("OK")) {
						System.out.println("Familiare posizionato");
					} else {
						System.out.println(mom);
						mosseDisponibili++;
					}
					serverMethods.notifySpostamento("neutro", Double.valueOf(action), y, account, positionGame);
					mosseDisponibili--;
				}
				break;
			case "mostra le posizioni":
				ArrayList<Posizioni> pos = serverMethods.getPositions();
				for (Posizioni p : pos) {
					System.out.println("Posizione: " + p.getName() + " con x = " + p.getX() + " e y = " + p.getY());
				}
				break;
			case "risorse":
				Portafoglio p = serverMethods.getRisorse(positionGame, account);
				System.out.println("Le monete che possiedi nel tuo forziere sono: " + p.getDimRisorse("monete"));
				System.out.println("I servitori che possiedi al tuo cospetto sono: " + p.getDimRisorse("servitori"));
				System.out.println("Le pietre che hai nella tua cava sono: " + p.getDimRisorse("pietre"));
				System.out.println("Il legno che hai nella tua legnaia e': " + p.getDimRisorse("legno"));
				// Guardare le hash maps per utilizzare direttamente quelle
				break;
			case "punteggio":
				Portafoglio po = serverMethods.getRisorse(positionGame, account);
				System.out.println("I punti vittoria che hai sono: " + po.getPunti("vittoria"));
				System.out.println("I punti militari che hai sono. " + po.getPunti("militari"));
				System.out.println("I putni fede che hai sono: " + po.getPunti("fede"));
				break;
			}
		} while (mosseDisponibili > 0);
		serverMethods.changeGamer(positionGame);
		System.out.println("Il tuo turno e' finito aspetta le notifiche degli altri giocatori e ossevali bene!!!");
	}

	public void moveFamiliareAvv(double x, double y, String colorPlayer, String colorFam) throws RemoteException {
		System.out.println("Il giocatore di colore " + colorPlayer + "ha mosso il suo familiare di colore " + colorFam
				+ " nella posizione " + x + " " + y);
	}

	@Override
	public void notifyStartGame() throws RemoteException {
		System.out.println("Ci sono abbastanza giocatori, che la partita abbai inizio!!");

	}

	@Override
	public void richestaSostegnoChiesa() throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void resetTabellone() throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void endGame(Giocatore[] giocatoriPartita) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void addScomunica(int nScomuniche, String name) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyTurno(int turno) throws RemoteException, SQLException {
		System.out.println("Ehi!! E' il tuo turno fai le tue mosse");
		this.turnodiGioco = turno;
		try {
			play();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void notifyAddCardAvv(String name, String tipo, int piano) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifySpostamentoPuntiMilitari(double x, double y, String string) throws RemoteException {
		System.out.println("Il giocatore di colore " + string
				+ "muove le sue forze armate aumentando il suo punteggio militare " + x + " " + y);

	}

	@Override
	public void notifySpostamentoPuntiVittoria(double x, double y, String color2) throws RemoteException {
		System.out.println("Il giocatore di colore " + color2
				+ "ha totalizzato dei punti il suo disco dei punti vittoria si muove nella posizione " + x + " " + y);
	}

	@Override
	public void notifySpostamentoPuntiFede(double x, double y, String color2) throws RemoteException {
		System.out.println("Il giocatore di colore " + color2
				+ "sostiene il credo e la chiesa il suo disco fede si muove nella posizione " + x + " " + y);
		carte = serverMethods.getCards(positionGame);
		System.out.println(
				"Il palazzo dei territori: \tIl palazzo dei personaggi: \tPalazzo degli edifici: \tPalazzo delle imprese:");
		int i = 0, j = 4, k = 8, l = 12;
		for (int p = 0; p < 4; p++) {
			System.out.println(carte[i].getNameCard() + " \t" + carte[j].getNameCard() + " \t" + carte[k].getNameCard()
					+ " \t" + carte[l].getNameCard());
			System.out.println(carte[i].getTooltipString() + " \t" + carte[j].getTooltipString() + " \t"
					+ carte[k].getTooltipString() + " \t" + carte[l].getTooltipString());
			System.out.println();
			i++;
			j++;
			k++;
			l++;
		}
	}

	@Override
	public void notifyPergamena(int i) throws RemoteException {
		System.out.println(
				"Hai preso una pergamena devi scambiarla!\nPremi 1 se la vuoi scambiare per una pietra e una moneta\nPremi 2 per scambiarla con due servitori\nPremi 3 se la vuoi scambiare per due monete\nPremi 4 per scambiarla per due punti militari\nPremi 5 per scambiarla con un punto fede");
		try {
			switch (input.nextInt()) {
			case 2:
				serverMethods.addRisorse(positionGame, account, "servitori", 2);
				serverMethods.notifyAddRisorse(positionGame, account, "servitori", 2);
				break;
			case 1:
				serverMethods.addRisorse(positionGame, account, "pietra", 1);
				serverMethods.notifyAddRisorse(positionGame, account, "pietra", 1);
				serverMethods.addRisorse(positionGame, account, "legno", 1);
				serverMethods.notifyAddRisorse(positionGame, account, "legno", 1);
				break;
			case 3:
				serverMethods.addRisorse(positionGame, account, "monete", 2);
				serverMethods.notifyAddRisorse(positionGame, account, "monete", 2);
				break;
			case 4:
				serverMethods.addPunti(positionGame, account, "militari", 2);
				serverMethods.notifySpostamentoPunti(positionGame, account, "militari", color);
				break;
			case 5:
				serverMethods.addPunti(positionGame, account, "fede", 1);
				serverMethods.notifySpostamentoPunti(positionGame, account, "fede", color);
				break;
			default:
				System.out.println("Attento devi aver sbagliato la tua decisione");
				break;

			}
			input.nextLine();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void notifyTutteCarte(int i) throws RemoteException {
		System.out.println("Hai preso un effetto Tutte Carte puoi decidere di prendere una carta a scelta quale vuoi prendere??");

	}

	@Override
	public void notifyUnTipoCarta(int tipo, int qta, int scontoAzioneImmediata1) throws RemoteException {
		System.out.println("Grazie ad un effetto di una carta di un tipo che hai appena preso puoi prendere una carta a tua scelta quale vuoi?");
		

	}

	@Override
	public void notifyAddRisorse(String name, String tipo, int qta) throws RemoteException {
		System.out.println(
				"Il giocatore " + name + " ha preso delle risorse " + tipo + " con una quantita' pari a :" + qta);
	}

	@Override
	public void notifyAskSostegnoChiesa() throws RemoteException {
		System.out.println(
				"E' il momento se decidere se sotenere o meno la chiesa cosa fai? \nPremi 1 se vuoi sostenerla, altrimenti 0 (Stai attento alle scomuniche la chiesa non perdona facilmente!!)");
		if(input.nextInt() == 0){
			
		}

	}

	@Override
	public void notifyVittoria() throws RemoteException {
		System.out.println("Complimenti hai vinto!!!!");
		endGame();
	}

	private void endGame() {
		System.out.println("Grazie per aver giocato al lorenzo il magnifico tornerai al menu' principale di gioco");
		try {
			startMenu();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void nofySconfitta(int max) throws RemoteException {
		System.out.println("Nooooo, hai perso\nLa vittoria sara' tua alla prossiam partita");

	}

}
