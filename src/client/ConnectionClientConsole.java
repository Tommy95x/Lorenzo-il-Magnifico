package client;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.sun.glass.events.KeyEvent;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import server.element.CartaSviluppo;
import server.element.Dado;
import server.element.Giocatore;
import server.element.Portafoglio;
import server.element.Posizioni;
import server.element.TesseraScomunica;
import shared.RMIClientInterface;
import shared.ServerInterface;

@SuppressWarnings("restriction")
public class ConnectionClientConsole extends UnicastRemoteObject implements RMIClientInterface {

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
	private int mosseDisponibili = 4;
	private String lobby;
	private Dado[] dadi = new Dado[3];

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
			String mom = serverMethods.login(account, pw);
			System.out.println(mom);
			if (mom.equals("Welcome to the game")) {
				this.account = account;
				startMenu();
			} else
				login();
		} catch (RemoteException | SQLException e) {
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
		String[] colors = null;
		System.out.println(
				"Premi 1 se vuoi creare una nuova lobby di gioco o 2 se vuoi entrare in una lobby gia' esistente");
		int menu = 0;
		try {
			menu = input.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("La decisione devi inserirla premendo 1 o 2, son dei numeri");
		}
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
				serverMethods.setClientInterface(positionGame, account, this);
				this.color = color;
			} catch (RemoteException e) {
				System.out.println("Spiacenti c'e' gia una parita con questo nome cambia nome");
				startMenu();
			}
			break;
		case 2:
			ArrayList<String> lobbies = null;
			try {
				lobbies = serverMethods.getLobby();
				if (lobbies == null) {
					System.out.println("Spiacenti non ci sono partite disponibili");
				} else {
					for (String p : lobbies)
						System.out.println(p);
				}
				System.out.println("Scrivi il nome della lobby in cui vuoi entrare o scrivi back per tornare indietro");
				this.lobby = input.nextLine();
				if (lobby.equals("back"))
					startMenu();
				System.out.println("Questi sono i colori disponibili");
				colors = serverMethods.getColors(lobby);
				for (int i = 0; i < colors.length; i++) {
					System.out.println(colors[i]);
				}
				System.out.println("Inserisci il colore che vorrai avere");
				this.color = input.nextLine();
				this.positionGame = serverMethods.selectLobby(lobby, account, color);
				serverMethods.setClientInterface(positionGame, account, this);
			} catch (RemoteException e) {
				System.out.println("Devi inserire il nome della lobby se no non puoi giocare!");
				startMenu();
			}
			break;
		}
		waitGamer();
	}

	private void waitGamer() {
		System.out.println("Scrivi ready se sei pronto a entrare nel gioco o attendi");
		while (true)
			if (input.nextLine().equals("ready")) {
				System.out.println("In attesa degli altri giocatori...");
				try {
					serverMethods.startPartita(account, positionGame);
				} catch (RemoteException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				waitNotifiche();
			} else
				System.out.println("Ricorda che se vuoi iniziare devi scrivere ready");
	}

	private void waitNotifiche() {
		while (true) {

		}
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
		waitNotifiche();
	}

	private void lanciaDadi() {
		try {
			dadi = serverMethods.showDiceValues(positionGame, account);
			play();
		} catch (RemoteException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void play() throws RemoteException, SQLException {
		while (true) {
			System.out.println("Il turno di gioco e': " + turnodiGioco);
			System.out.println(
					"Fai le tue mosse nel seguente ordine:\n1)Lancia i dadi(verranno lanciati all'inizio del tuo turno mostrando i loro valori)\n2)Scrivi il colore del familiare che vuoi spostare (ricorda i familiari sono di colore Arancio, Nero, Bianco, Neutro)\n3)Scrivi la posizione numerica nel tabellone in cui vuoi inserire il familiare (scrivendo back de-selezioni il familiare selezionato)\n4)Acquisisci carte e vedi i relativi effetti\n5)Verifichi il tuo punteggio, scrivendo punteggio o personal score\n");
			System.out.println(
					"Ricordati che puoi sempre vedere le posizioni del tabellone scrivendo ''posizioni'' e con ''carte'' scopri che carte sono disponibili nei vari piani delle torri");
			for (int i = 0; i < 3; i++) {
				System.out.println("Il dado " + dadi[i].getColor() + " vale " + dadi[i].getValore());
			}
			String action = input.nextLine();
			try {
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
							incrPosizionamento();
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
							incrPosizionamento();
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
							incrPosizionamento();
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
							incrPosizionamento();
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
							incrPosizionamento();
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
							incrPosizionamento();
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
							incrPosizionamento();
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
							serverMethods.getCardsGamer(positionGame, account);
							incrPosizionamento();
						} else {
							System.out.println(mom);
							mosseDisponibili++;
						}
						serverMethods.notifySpostamento("neutro", Double.valueOf(action), y, account, positionGame);
					}
					break;
				case "posizioni":
					System.out.println("Stampo le posizioni taebellone: ");
					ArrayList<Posizioni> pos = serverMethods.getPositions(account);
					for (Posizioni p : pos) {
						System.out.println("Posizione: " + p.getName() + " con x = " + p.getX() + " e y = " + p.getY());
					}
					break;
				case "risorse":
					Portafoglio p = serverMethods.getRisorse(positionGame, account);
					System.out.println("Le monete che possiedi nel tuo forziere sono: " + p.getDimRisorse("monete"));
					System.out
							.println("I servitori che possiedi al tuo cospetto sono: " + p.getDimRisorse("servitori"));
					System.out.println("Le pietre che hai nella tua cava sono: " + p.getDimRisorse("pietre"));
					System.out.println("Il legno che hai nella tua legnaia e': " + p.getDimRisorse("legno"));
					break;
				case "punteggio":
					Portafoglio po = serverMethods.getRisorse(positionGame, account);
					System.out.println("I punti vittoria che hai sono: " + po.getPunti("vittoria"));
					System.out.println("I punti militari che hai sono. " + po.getPunti("militari"));
					System.out.println("I putni fede che hai sono: " + po.getPunti("fede"));
					break;
				case "carte":
					carte = serverMethods.getCards(positionGame);
					System.out.println("Palazzo dei territori:");
					for (int i = 0; i < 4; i++) {
						System.out.println("Primo piano: " + carte[i].getNameCard() + " la sua descrizione e': "
								+ carte[i].getTooltipString());
					}
					System.out.println("\nPalazzo dei personaggi:");
					for (int i = 4; i < 8; i++) {
						System.out.println("Primo piano: " + carte[i].getNameCard() + " la sua descrizione e': "
								+ carte[i].getTooltipString());
					}
					System.out.println("\nPalazzo degli edifici:");
					for (int i = 8; i < 12; i++) {
						System.out.println("Primo piano: " + carte[i].getNameCard() + " la sua descrizione e': "
								+ carte[i].getTooltipString());
					}
					System.out.println("\nPalazzo delle imprese:");
					for (int i = 12; i < 16; i++) {
						System.out.println("Primo piano: " + carte[i].getNameCard() + " la sua descrizione e': "
								+ carte[i].getTooltipString());
					}
					break;
				}
			} catch (InputMismatchException | NullPointerException e) {
				e.printStackTrace();
				System.out.println(
						"Devi inserire uno di questi campi: \n1)Colore del giocatore che vuoi giocare\n2)Per conoscere le posizioni posizioni tabellone\n3)Per conoscere il valore dei tuoi punti\n4)punteggio per coonoscere il tuo punteggio");
			}
			System.out.println();
		}
	}

	private void incrPosizionamento() {
		mosseDisponibili++;
		try {
			if (mosseDisponibili == 1) {
				serverMethods.changeGamer(positionGame);
				System.out.println(
						"Il tuo turno e' finito aspetta le notifiche degli altri giocatori e ossevali bene!!!");
			} else {
				serverMethods.scambio(positionGame);
				System.out.println(
						"Il tuo turno e' finito aspetta le notifiche degli altri giocatori e ossevali bene!!!");
			}
		} catch (RemoteException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		System.out.println("Il tabellone è stato resettato ora le nuove carte presenti sono: ");
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
		lanciaDadi();
	}

	@Override
	public void notifyAddCardAvv(String name, String tipo, int piano) throws RemoteException {
		switch (tipo) {
		case "TER":
			System.out.println("Il giocatore " + name + " ha acquisito la carta Territorio del piano " + (piano + 1)
					+ "del palazzo");
			break;
		case "PER":
			System.out.println("Il giocatore " + name + " ha acquisito la carta Personaggi del piano " + (piano + 1)
					+ "del palazzo");
			break;
		case "ED":
			System.out.println(
					"Il giocatore " + name + " ha acquisito la carta Edifici del piano " + (piano + 1) + "del palazzo");
			break;
		case "IMP":
			System.out.println(
					"Il giocatore " + name + " ha acquisito la carta Imprese del piano " + (piano + 1) + "del palazzo");
			break;
		}
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
		System.out.println(
				"Hai preso un effetto Tutte Carte puoi decidere di prendere una carta a scelta quale vuoi prendere??");

	}

	@Override
	public void notifyUnTipoCarta(int tipo, int qta, int scontoAzioneImmediata1) throws RemoteException {
		System.out.println(
				"Grazie ad un effetto di una carta di un tipo che hai appena preso puoi prendere una carta a tua scelta quale vuoi?");

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
		if (input.nextInt() == 0) {

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

	@Override
	public void rimbalzo() throws RemoteException {
		System.out.println("Ehi l'altro giocatore ha fatto una mossa posiziona un altro familiare");

	}
	
	public void setCardGiocatore(String namePosition, int piano, int tipo) throws IOException {
		ImageView mom;
		switch (namePosition) {
		case "PIANO 1 FAMILIARE TERRITORI":
			mom = new ImageView(new Image(getClass().getResourceAsStream(arrayCarteTerritori[3].getImage())));
			mom.setFitHeight(130);
			mom.setFitWidth(90);
			Tooltip.install(mom, arrayCarteTerritori[3].getTooltip());
			carteTerritoriGiocatore.getChildren().add(mom);
			sistemaCarte(0, 3);
			start.getClient().setCardGiocatore(arrayCarteTerritori[3], 0, 3);
			break;
		case "PIANO 2 FAMILIARE TERRITORI":
			mom = new ImageView(new Image(getClass().getResourceAsStream(arrayCarteTerritori[2].getImage())));
			mom.setFitHeight(130);
			mom.setFitWidth(90);
			Tooltip.install(mom, arrayCarteTerritori[2].getTooltip());
			carteTerritoriGiocatore.getChildren().add(mom);
			sistemaCarte(0, 2);
			start.getClient().setCardGiocatore(arrayCarteTerritori[2], 0, 2);
			break;
		case "PIANO 3 FAMILIARE TERRITORI":
			mom = new ImageView(new Image(getClass().getResourceAsStream(arrayCarteTerritori[1].getImage())));
			mom.setFitHeight(130);
			mom.setFitWidth(90);
			Tooltip.install(mom, arrayCarteTerritori[1].getTooltip());
			carteTerritoriGiocatore.getChildren().add(mom);
			sistemaCarte(0, 1);
			try {
				start.getClient().addRisorse("legno", 1);
				start.getClient().notifyRisorse("legno", start.getClient().getRisorse().getDimRisorse("legno"));
			} catch (SQLException | ClassNotFoundException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			start.getClient().setCardGiocatore(arrayCarteTerritori[1], 0, 1);
			break;
		case "PIANO 4 FAMILIARE TERRITORI":
			mom = new ImageView(new Image(getClass().getResourceAsStream(arrayCarteTerritori[0].getImage())));
			mom.setFitHeight(130);
			mom.setFitWidth(90);
			Tooltip.install(mom, arrayCarteTerritori[0].getTooltip());
			carteTerritoriGiocatore.getChildren().add(mom);
			sistemaCarte(0, 0);
			try {
				start.getClient().addRisorse("legno", 2);
				start.getClient().notifyRisorse("legno", start.getClient().getRisorse().getDimRisorse("legno"));
			} catch (SQLException | ClassNotFoundException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			start.getClient().setCardGiocatore(arrayCarteTerritori[0], 0, 0);
			break;
		case "PIANO 1 FAMILIARE EDIFICI":
			mom = new ImageView(new Image(getClass().getResourceAsStream(arrayCarteEdifici[3].getImage())));
			mom.setFitHeight(130);
			mom.setFitWidth(90);
			Tooltip.install(mom, arrayCarteEdifici[3].getTooltip());
			carteEdificiGiocatore.getChildren().add(mom);
			sistemaCarte(2, 3);
			start.getClient().setCardGiocatore(arrayCarteEdifici[3], 2, 3);
			break;
		case "PIANO 2 FAMILIARE EDIFICI":
			mom = new ImageView(new Image(getClass().getResourceAsStream(arrayCarteEdifici[2].getImage())));
			mom.setFitHeight(130);
			mom.setFitWidth(90);
			Tooltip.install(mom, arrayCarteEdifici[2].getTooltip());
			carteEdificiGiocatore.getChildren().add(mom);
			sistemaCarte(2, 2);
			start.getClient().setCardGiocatore(arrayCarteEdifici[2], 2, 2);
			break;
		case "PIANO 3 FAMILIARE EDIFICI":
			mom = new ImageView(new Image(getClass().getResourceAsStream(arrayCarteEdifici[1].getImage())));
			mom.setFitHeight(130);
			mom.setFitWidth(90);
			Tooltip.install(mom, arrayCarteEdifici[1].getTooltip());
			carteEdificiGiocatore.getChildren().add(mom);
			try {
				start.getClient().addPunti("militari", 1);
				start.getClient().notifySpostamentoPunti("militari");
			} catch (SQLException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			}
			sistemaCarte(2, 1);
			start.getClient().setCardGiocatore(arrayCarteEdifici[1], 2, 1);
			break;
		case "PIANO 4 FAMILIARE EDIFICI":
			mom = new ImageView(new Image(getClass().getResourceAsStream(arrayCarteEdifici[0].getImage())));
			mom.setFitHeight(130);
			mom.setFitWidth(90);
			Tooltip.install(mom, arrayCarteEdifici[0].getTooltip());
			carteEdificiGiocatore.getChildren().add(mom);
			try {
				start.getClient().addPunti("militari", 2);
				start.getClient().notifySpostamentoPunti("militari");
			} catch (SQLException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			}
			sistemaCarte(2, 0);
			start.getClient().setCardGiocatore(arrayCarteEdifici[0], 2, 0);
			break;
		case "PIANO 1 FAMILIARE IMPRESE":
			mom = new ImageView(new Image(getClass().getResourceAsStream(arrayCarteImpresa[3].getImage())));
			mom.setFitHeight(130);
			mom.setFitWidth(90);
			Tooltip.install(mom, arrayCarteImpresa[3].getTooltip());
			carteImpresaGiocatore.getChildren().add(mom);
			sistemaCarte(3, 3);
			start.getClient().setCardGiocatore(arrayCarteImpresa[3], 3, 3);
			break;
		case "PIANO 2 FAMILIARE IMPRESE":
			mom = new ImageView(new Image(getClass().getResourceAsStream(arrayCarteImpresa[2].getImage())));
			mom.setFitHeight(130);
			mom.setFitWidth(90);
			Tooltip.install(mom, arrayCarteImpresa[2].getTooltip());
			carteImpresaGiocatore.getChildren().add(mom);
			sistemaCarte(3, 2);
			start.getClient().setCardGiocatore(arrayCarteImpresa[2], 3, 2);
			break;
		case "PIANO 3 FAMILIARE IMPRESE":
			mom = new ImageView(new Image(getClass().getResourceAsStream(arrayCarteImpresa[1].getImage())));
			mom.setFitHeight(130);
			mom.setFitWidth(90);
			Tooltip.install(mom, arrayCarteImpresa[1].getTooltip());
			carteImpresaGiocatore.getChildren().add(mom);
			try {
				start.getClient().addRisorse("monete", 1);
				start.getClient().notifyRisorse("monete", start.getClient().getRisorse().getDimRisorse("monete"));
			} catch (SQLException | ClassNotFoundException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			}
			sistemaCarte(3, 1);
			start.getClient().setCardGiocatore(arrayCarteImpresa[1], 3, 1);
			break;
		case "PIANO 4 FAMILIARE IMPRESE":
			mom = new ImageView(new Image(getClass().getResourceAsStream(arrayCarteImpresa[0].getImage())));
			mom.setFitHeight(130);
			mom.setFitWidth(90);
			Tooltip.install(mom, arrayCarteImpresa[0].getTooltip());
			carteImpresaGiocatore.getChildren().add(mom);
			try {
				start.getClient().addRisorse("monete", 2);
				start.getClient().notifyRisorse("monete", start.getClient().getRisorse().getDimRisorse("monete"));
			} catch (SQLException | ClassNotFoundException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			}
			sistemaCarte(3, 0);
			start.getClient().setCardGiocatore(arrayCarteImpresa[0], 3, 0);
			break;
		case "PIANO 1 FAMILIARE PERSONAGGI":
			mom = new ImageView(new Image(getClass().getResourceAsStream(arrayCartePersonaggi[3].getImage())));
			mom.setFitHeight(130);
			mom.setFitWidth(90);
			Tooltip.install(mom, arrayCartePersonaggi[3].getTooltip());
			cartePersonaggiGiocatore.getChildren().add(mom);
			sistemaCarte(1, 3);
			start.getClient().setCardGiocatore(arrayCartePersonaggi[3], 1, 3);
			break;
		case "PIANO 2 FAMILIARE PERSONAGGI":
			mom = new ImageView(new Image(getClass().getResourceAsStream(arrayCartePersonaggi[2].getImage())));
			mom.setFitHeight(130);
			mom.setFitWidth(90);
			Tooltip.install(mom, arrayCartePersonaggi[2].getTooltip());
			cartePersonaggiGiocatore.getChildren().add(mom);
			sistemaCarte(1, 2);
			start.getClient().setCardGiocatore(arrayCartePersonaggi[2], 1, 2);
			break;
		case "PIANO 3 FAMILIARE PERSONAGGI":
			mom = new ImageView(new Image(getClass().getResourceAsStream(arrayCartePersonaggi[1].getImage())));
			mom.setFitHeight(130);
			mom.setFitWidth(90);
			Tooltip.install(mom, arrayCartePersonaggi[1].getTooltip());
			cartePersonaggiGiocatore.getChildren().add(mom);
			sistemaCarte(1, 1);
			try {
				start.getClient().addRisorse("pietra", 1);
				start.getClient().notifyRisorse("pietra", 1);
			} catch (SQLException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			start.getClient().setCardGiocatore(arrayCartePersonaggi[1], 1, 1);
			break;
		case "PIANO 4 FAMILIARE PERSONAGGI":
			mom = new ImageView(new Image(getClass().getResourceAsStream(arrayCartePersonaggi[0].getImage())));
			mom.setFitHeight(130);
			mom.setFitWidth(90);
			Tooltip.install(mom, arrayCartePersonaggi[0].getTooltip());
			cartePersonaggiGiocatore.getChildren().add(mom);
			sistemaCarte(1, 0);
			try {
				start.getClient().addRisorse("pietra", 2);
				start.getClient().notifyRisorse("pietra", start.getClient().getRisorse().getDimRisorse("pietra"));
			} catch (SQLException | ClassNotFoundException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			start.getClient().setCardGiocatore(arrayCartePersonaggi[0], 1, 0);
			break;
		case "AZIONE PRODUZIONE 4":
			switch (tipo) {
			case 0:
				start.getClient().produzione(0);
				break;
			case 1:
				start.getClient().produzione(start.getClient().getDado("black") - 3);
				break;
			case 2:
				produzione(start.getClient().getDado("orange") - 3);
				break;
			case 3:
				produzione(dadi[3],- 3);
				break;
			}
			break;
		case "AZIONE PRODUZIONE 1":
			produzione(0);
			break;
		case "AZIONE RACCOLTO 4":
			raccolto(-3);
			break;
		case "AZIONE RACCOLTO 1":
			raccolto(0);
			break;
		case "PRENDI 5 MONETE":
			try {
				addRisorse("monete", 5);
				notifyRisorse("monete", start.getClient().getRisorse().getDimRisorse("monete"));
			} catch (SQLException | ClassNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			break;
		case "PRENDI 5 SERVITORI":
			try {
				addRisorse("servitori", 5);
				notifyRisorse("servitori", start.getClient().getRisorse().getDimRisorse("servitori"));
			} catch (SQLException | ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case "PRENDI 2 MONETE E 3 MILITARI":
			try {
				addRisorse("monete", 2);
				addPunti("militari", 5);
				notifyRisorse("monete", start.getClient().getRisorse().getDimRisorse("monete"));
				notifySpostamentoPunti("militari");
			} catch (SQLException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case "PRENDI 2 PERGAMENE":
			addPergamene(2);
			break;
		case "ZONA MERCATO":
			addPergamene(1);
			try {
				addRisorse("monete", 1);
				notifyRisorse("monete", start.getClient().getRisorse().getDimRisorse("monete"));
			} catch (SQLException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "tutteCarte":
			switch (tipo) {
			case 0:
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;

			}
			break;
		}
	}

}
