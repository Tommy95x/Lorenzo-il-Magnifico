package server.element;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import server.database.ConnectionDatabase;

/**
 * @author Tommy
 *
 *         Classe che rappresentera' la partita. Puo' contenere al massimo 4
 *         giocatori, tutte le carte giocate (sempre rappresentate da degli
 *         array) e il turno che far� terminare la partita una volta che
 *         raggiungera' il valore pari a 6.
 */
public class Partita implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int DIM = 4;
	private final int NUMCARTE = 4;
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
	private boolean setCards = true;
	private boolean ok = false;
	private int rimbalzo = 0;
	private int totaleMosse = 0;

	/**
	 * Questo metodo inizializza la partita creando nuove tabelle contenti le
	 * carte del gioco. Queste tabelle potranno essere utilizzate per essere
	 * modicate durante la partita. Alla fine della partita verranno eliminate.
	 * 
	 * @author Mattia & Tommy
	 * @param lobby
	 * @param namePlayer
	 * @param positionGame
	 * @param connection
	 * @throws SQLException
	 */
	public Partita(String lobby, String namePlayer, int positionGame, Connection connection) throws SQLException {
		this.setLobby(lobby);
		colors[0] = "blue";
		colors[1] = "orange";
		colors[2] = "white";
		colors[3] = "green";
		String queryterritorio = "CREATE TABLE " + lobby.toUpperCase()
				+ "CARTETERRITORIOPARTITARANDOM AS SELECT * FROM CARTATERRITORIO ORDER BY RAND()";
		String queryimpresa = "CREATE TABLE " + lobby.toUpperCase()
				+ "CARTEIMPRESAPARTITARANDOM AS SELECT * FROM CARTAIMPRESA ORDER BY RAND()";
		String querypersonaggio = "CREATE TABLE " + lobby.toUpperCase()
				+ "CARTEPERSONAGGIOPARTITARANDOM AS SELECT * FROM CARTAPERSONAGGIO ORDER BY RAND()";
		String queryedificio = "CREATE TABLE " + lobby.toUpperCase()
				+ "CARTEEDIFICIOPARTITARANDOM AS SELECT * FROM CARTAEDIFICIO ORDER BY RAND()";
		connection.createStatement().execute(queryterritorio);
		connection.createStatement().execute(queryimpresa);
		connection.createStatement().execute(querypersonaggio);
		connection.createStatement().execute(queryedificio);
		String queryterritorio1 = "CREATE TABLE " + lobby.toUpperCase() + "CARTETERRITORIOPARTITA AS SELECT * FROM "
				+ lobby.toUpperCase() + "CARTETERRITORIOPARTITARANDOM ORDER BY PERIODO";
		String queryimpresa1 = "CREATE TABLE " + lobby.toUpperCase() + "CARTEIMPRESAPARTITA AS SELECT * FROM "
				+ lobby.toUpperCase() + "CARTEIMPRESAPARTITARANDOM ORDER BY PERIODO";
		String querypersonaggio1 = "CREATE TABLE " + lobby.toUpperCase() + "CARTEPERSONAGGIOPARTITA AS SELECT * FROM "
				+ lobby.toUpperCase() + "CARTEPERSONAGGIOPARTITARANDOM ORDER BY PERIODO";
		String queryedificio1 = "CREATE TABLE " + lobby.toUpperCase() + "CARTEEDIFICIOPARTITA AS SELECT * FROM "
				+ lobby.toUpperCase() + "CARTEEDIFICIOPARTITARANDOM ORDER BY PERIODO";
		connection.createStatement().execute(queryterritorio1);
		connection.createStatement().execute(queryimpresa1);
		connection.createStatement().execute(querypersonaggio1);
		connection.createStatement().execute(queryedificio1);
		String queryterritorio2 = "DROP TABLE " + lobby.toUpperCase() + "CARTETERRITORIOPARTITARANDOM";
		String queryimpresa2 = "DROP TABLE " + lobby.toUpperCase() + "CARTEIMPRESAPARTITARANDOM";
		String querypersonaggio2 = "DROP TABLE " + lobby.toUpperCase() + "CARTEPERSONAGGIOPARTITARANDOM";
		String queryedificio2 = "DROP TABLE " + lobby.toUpperCase() + "CARTEEDIFICIOPARTITARANDOM";
		connection.createStatement().executeUpdate(queryterritorio2);
		connection.createStatement().executeUpdate(queryimpresa2);
		connection.createStatement().executeUpdate(querypersonaggio2);
		connection.createStatement().executeUpdate(queryedificio2);
		connection.close();
		for (int i = 0; i < DIM; i++) {
			start[i] = false;
		}
	}

	public Partita(String name) {
		this.name = name;
	}

	private void startPartita() throws RemoteException, SQLException {
		turno = 1;
		/*
		 * System.out.println("shuffle giocatori "); beShuffled();
		 * System.out.println("Sistemato ordine gioco");
		 */
		this.NumberOfPlayers = 1;
		for (int i = 0; i < 4; i++) {
			if (giocatori[i] != null) {
				switch (NumberOfPlayers) {
				case 1:
					NumberOfPlayers++;
					break;
				case 2:
					NumberOfPlayers++;
					giocatori[i].setRisorse("monete");
					break;
				case 3:
					NumberOfPlayers++;
					giocatori[i].setRisorse("monete");
					giocatori[i].setRisorse("monete");
					break;
				case 4:
					NumberOfPlayers++;
					giocatori[i].setRisorse("monete");
					giocatori[i].setRisorse("monete");
					giocatori[i].setRisorse("monete");
					break;
				}
			}
		}
		this.NumberOfPlayers = 0;
		for (int i = 0; i < 4; i++) {
			if (giocatori[i] != null) {
				System.out.println("notifico giocatori per l'inizio partita");
				System.out.println(giocatori[i]);
				try {
					System.out.println("Notifico il giocatore " + giocatori[i].getName());
					giocatori[i].notifyStartGame();
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else
				System.out.println("Giocatori a null");
		}
		changeGamer();
	}

	// Mettere a public per verificare con il metodo di test
	// public boolean checkBoolean(int dim){
	private boolean checkBoolean(int dim) {
		for (int i = 0; i < dim; i++) {
			if (!start[i])
				return false;
		}
		return true;
	}

	/**
	 * This method take the array of the gamers and shuffle it to decide the
	 * start order of the first turn of the game.
	 * 
	 * @author Mattia
	 */
	@SuppressWarnings("unused")
	private void beShuffled() {
		Giocatore[] mom = new Giocatore[DIM];
		Giocatore[] ordine = new Giocatore[DIM];
		boolean flag = false;
		for (int i = 0; i < DIM; i++) {
			mom[i] = giocatori[i];
		}
		int con = DIM;
		for (int i = 0; i < DIM; i++) {
			int casuale = (int) (Math.random() * con);
			if (mom[casuale] != null) {
				ordine[i] = mom[casuale];
				mom[casuale] = null;
				con--;
			} else {
				for (int j = 0; j < DIM; j++) {
					if (mom[i] != null)
						flag = false;
					else
						flag = true;
				}
				if (!flag)
					i--;
				else
					break;
			}
		}
		for (int i = 0; i < DIM; i++)
			giocatori[i] = ordine[i];
	}

	public String getLobbyName() {
		return name;
	}

	public void addGiocatore(Giocatore giocatore) {
		giocatori[NumberOfPlayers] = giocatore;
		System.out.println(giocatore.equals(giocatori[NumberOfPlayers]));
		NumberOfPlayers++;
	}

	public Giocatore getGiocatoreByName(String name) {
		for (Giocatore g : giocatori) {
			if (g != null && g.getName().equals(name))
				return g;
		}
		return null;
	}

	public int numberOfPlayer() {
		int n = 0;
		for (Giocatore g : giocatori)
			if (g != null)
				n++;
		return n;
	}

	public void setLobby(String lobby) {
		this.name = lobby;
	}

	public boolean addTurno() {
		NumberOfPlayers = 0;
		totaleMosse = 0;
		rimbalzo = 0;
		setCards = true;
		notifyResetTabellone();
		if (turno < 6) {
			if (turno == 2 || turno == 4)
				sostegnoChiesa();
			turno++;
			try {
				changeGamer();
			} catch (RemoteException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		} else {
			sostegnoChiesa();
			endGame();
			return false;
		}
	}

	private void notifyResetTabellone() {
		for (int i = 0; i < 4; i++) {
			if (giocatori[i] != null) {
				giocatori[i].notifyResetTabellone();
			}
		}
	}

	private void endGame() {
		int[] puntiG = new int[4];
		int max = 0;
		for (int i = 0; i < 4; i++) {
			if (giocatori[i] != null)
				puntiG[i] = giocatori[i].notifyEndGame();
		}
		for (int i = 0; i < 4; i++) {
			if (puntiG[i] > max)
				max = puntiG[i];
		}
		for (int i = 0; i < 4; i++) {
			if (giocatori[i] != null) {
				if (giocatori[i].getPuntiFinali() == max)
					giocatori[i].notifyVittoria();
				else
					giocatori[i].nofySconfitta(max);
			}
		}
	}

	private void sostegnoChiesa() {
		for (int i = 0; i < 4; i++) {
			if (giocatori[i] != null) {
				try {
					switch (turno) {
					case 2:
						if (giocatori[i].getRisorse().getPunti("fede") > 2)
							giocatori[i].notifyAskSostegnoChiesa();
						else
							giocatori[i].addScomunica();
						break;
					case 4:
						if (giocatori[i].getRisorse().getPunti("fede") > 3)
							giocatori[i].notifyAskSostegnoChiesa();
						else
							giocatori[i].addScomunica();
						break;
					case 6:
						if (giocatori[i].getRisorse().getPunti("fede") > 4)
							giocatori[i].notifyAskSostegnoChiesa();
						else
							giocatori[i].addScomunica();
						break;
					}

				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public String getCreator() {
		// Metodo sbagliato soprattutto dopo lo shuffle
		return giocatori[0].getName();
	}

	public void start(String account) throws RemoteException, SQLException {
		// Riguardare metodo che e' errato in quanto la partita parte solo e
		// soltanto per 4 giocatori.
		System.out.println("Setto giocatore pronto");
		for (int i = 0; i < DIM; i++) {
			if (giocatori[i].getName().equals(account)) {
				start[i] = true;
				break;
			}
		}
		System.out.println("Verifico quanti giocatori pronti");
		int dim = 0;
		for (Giocatore g : giocatori)
			if (g != null)
				dim++;
			else
				break;
		System.out.println("Se minori di due giocatori pronti ritorno");
		if (dim < 2)
			return;
		else if (checkBoolean(dim))
			startPartita();
		else
			return;
	}

	public String[] getColors() {
		return colors;
	}

	public CartaSviluppo[] getCards() {
		System.out.println("Allinterno del metodo");
		CartaSviluppo[] mom = new CartaSviluppo[16];
		for (int i = 0; i < NUMCARTE; i++)
			mom[i] = carteTerritori[i];
		for (int i = 0; i < NUMCARTE; i++)
			mom[i + 4] = cartePersonaggio[i];
		System.out.println("CarteEdificio");
		for (int i = 0; i < NUMCARTE; i++)
			mom[i + 8] = carteEdifici[i];
		for (int i = 0; i < NUMCARTE; i++)
			mom[i + 12] = carteImprese[i];
		return mom;
	}

	/**
	 * This method is used to set all the cards that will be used during the
	 * game.
	 * 
	 * @author Mattia
	 * @param connection
	 * @throws SQLException
	 */
	public void setCards(Connection connection) throws SQLException {

		if (setCards) {
			String query;
			String queryelimina;
			int i;
			System.out.println("All'interno della partita setto le carte");
			for (i = 0; i < NUMCARTE; i++) {
				System.out.println("CarteTerritori " + i + "");
				query = "SELECT * FROM " + name.toUpperCase() + "CARTETERRITORIOPARTITA LIMIT 1";
				carteTerritori[i] = new CartaTerritori();
				carteTerritori[i].setCarta(connection, query);
				queryelimina = "DELETE TOP 1 FROM " + name.toUpperCase() + "CARTETERRITORIOPARTITA";
				connection.createStatement().executeUpdate(queryelimina);
			}
			for (i = 0; i < NUMCARTE; i++) {
				System.out.println("CartePersonaggio " + i + "");
				query = "SELECT * FROM " + name.toUpperCase() + "CARTEPERSONAGGIOPARTITA LIMIT 1";
				System.out.println("Carta Personaggio " + i + " selezionata");
				cartePersonaggio[i] = new CartaPersonaggi();
				cartePersonaggio[i].setCarta(connection, query);
				queryelimina = "DELETE TOP 1 FROM " + name.toUpperCase() + "CARTEPERSONAGGIOPARTITA";
				connection.createStatement().executeUpdate(queryelimina);
				System.out.println("Carta personaggio " + i + " Eliminata");
			}
			for (i = 0; i < NUMCARTE; i++) {
				System.out.println("CarteEdificio " + i + "");
				query = "SELECT * FROM " + name.toUpperCase() + "CARTEEDIFICIOPARTITA LIMIT 1";// Scrivere
																								// la
																								// query
																								// in
																								// modo
																								// che
																								// cerchi
																								// differenti
																								// carte
																								// in
																								// generale
				carteEdifici[i] = new CartaEdifici();
				carteEdifici[i].setCarta(connection, query);
				queryelimina = "DELETE TOP 1 FROM " + name.toUpperCase() + "CARTEEDIFICIOPARTITA";
				connection.createStatement().executeUpdate(queryelimina);
			}
			for (i = 0; i < NUMCARTE; i++) {
				System.out.println("CarteImpresa " + i + "");
				query = "SELECT * FROM " + name.toUpperCase() + "CARTEIMPRESAPARTITA LIMIT 1";// Scrivere
																								// la
																								// query
																								// in
																								// modo
																								// che
																								// cerchi
																								// differenti
																								// carte
																								// in
																								// generale
				carteImprese[i] = new CartaImprese();
				carteImprese[i].setCarta(connection, query);
				queryelimina = "DELETE TOP 1 FROM " + name.toUpperCase() + "CARTEIMPRESAPARTITA";
				connection.createStatement().executeUpdate(queryelimina);
			}
		}
		setCards = false;
		connection.close();
	}

	public void changeColors(String color) {
		for (int i = 0; i < DIM; i++) {
			if (colors[i].equals(color) || colors[i].equals("")) {
				colors[i] = "";
				break;
			}
		}

	}

	public void setCardsScomunica(ConnectionDatabase connectionDatabase, String account) throws SQLException {
		System.out.println("Carta1");
		for (int i = 0; i < 3; i++) {
			tessereScomunica[i] = new TesseraScomunica();
		}
		tessereScomunica[0].setTesseraPrimoPeriodo(connectionDatabase.getConnection(account));
		System.out.println("Carta2");
		tessereScomunica[1].setTessereSecondoPeriodo(connectionDatabase.getConnection(account));
		System.out.println("Carta3");
		tessereScomunica[2].setTesseraTerzoPeriodo(connectionDatabase.getConnection(account));
	}

	public void changeGamer() throws RemoteException, SQLException {
		System.out.println("Inizio i turni " + NumberOfPlayers);
		if (totaleMosse == 16) {
			System.out.println("Aggiungo un turno");
			addTurno();
		} else {
			try {
				if (giocatori[NumberOfPlayers] != null) {
					giocatori[NumberOfPlayers].notifyTurno(turno);
					NumberOfPlayers++;
					rimbalzo++;
				} else {
					NumberOfPlayers++;
					rimbalzo++;
					changeGamer();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(NumberOfPlayers);
		}
	}

	public void scambio() {
		if (totaleMosse <= 16) {
			if (rimbalzo >= 4) {
				rimbalzo = 0;
				totaleMosse += 4;
			} else {
				if (giocatori[rimbalzo] != null) {
					giocatori[rimbalzo].rimbalzo();
				}
				rimbalzo++;
			}
		}else{
			try {
				changeGamer();
			} catch (RemoteException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void notifySpostamento(String color, String colorAvv, double x, double y) {
		for (Giocatore g : giocatori) {
			if (g != null && !g.getColor().equals(colorAvv)) {
				try {
					g.notifySpostamento(color, colorAvv, x, y);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public String getNamePosition(double x, double y, ConnectionDatabase c, String name) {
		String nomeposizione = null;
		Connection connection;
		try {
			connection = c.getConnection(name);
			String query = "SELECT NOME FROM POSIZIONETABELLONE WHERE POSX=" + x + " AND POSY=" + y + "";
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);// Scrivere query che
													// fornisce
													// il nome della posizione
													// tipo
													// primo piano palazzo...
			rs.next();
			nomeposizione = rs.getString("NOME");
			System.out.println(nomeposizione);
			rs.close();
			stmt.close();
			c.releaseConnection(connection);
		} catch (SQLException e) {
			if (x == 265.0 && y == 265.0)
				return "ZONA MERCATO";
			if (x == 100.0 && y == 691.0)
				return "AZIONE RACCOLTO 4";
			if (x == 105.0 && y == 628.0)
				return "AZIONE PRODUZIONE 4";
		}
		return nomeposizione;
	}

	public void exitToGame(String name, String color) {
		int i;
		for (i = 0; i < giocatori.length; i++) {
			if (giocatori[i].getName().equals(name)) {
				giocatori[i] = null;
			}
			for (i = 0; i < colors.length; i++) {
				if (colors[i] == null) {
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

	public void notifyAddCardGiocatoreAvv(String name2, CartaSviluppo carta, int piano) throws RemoteException {
		System.out.println("Notifico presa carta " + name2);
		for (int i = 0; i < 4; i++) {
			if (giocatori[i] != null) {
				System.out.println(giocatori[i].getName());
				if (!giocatori[i].getName().equals(name)) {
					if (carta.getId().contains("ED")) {
						giocatori[i].notifyAddCardAvv("ED", name2, piano);
					} else if (carta.getId().contains("TER")) {
						giocatori[i].notifyAddCardAvv("TER", name2, piano);
					} else if (carta.getId().contains("PER")) {
						giocatori[i].notifyAddCardAvv("PER", name2, piano);
					} else
						giocatori[i].notifyAddCardAvv("IMP", name2, piano);
				}
			}
		}
	}

	public void deleteView(Connection connection) throws SQLException {
		String querydroppersonaggi = "DROP TABLE " + name.toUpperCase() + "CARTEPERSONAGGIOPARTITA";
		String querydropedifici = "DROP TABLE " + name.toUpperCase() + "CARTEEDIFICIOPARTITA";
		String querydropterritori = "DROP TABLE " + name.toUpperCase() + "CARTETERRITORIOPARTITA";
		String querydropimprese = "DROP TABLE " + name.toUpperCase() + "CARTEIMPRESAPARTITA";
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

	public void notifySpostamentoPunti(String tipo, int qta, ConnectionDatabase connectionDatabase, String color2) {
		Connection c = null;
		try {
			c = connectionDatabase.getConnection(color2);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("\n\nSposto il punti " + tipo + " del giocatore\n\n");
		switch (tipo) {
		case "militari":
			for (Giocatore g : giocatori) {
				if (g != null) {
					double x = 0;
					double y = 0;
					String query;
					if (qta < 10)
						query = "SELECT POSX, POSY FROM POSIZIONETABELLONE WHERE ID='PM0" + qta + "'";
					else
						query = "SELECT POSX, POSY FROM POSIZIONETABELLONE WHERE ID='PM" + qta + "'";
					try {
						Statement stmt = c.createStatement();
						ResultSet rs = stmt.executeQuery(query);
						while (rs.next()) {
							x = rs.getDouble("POSX");
							y = rs.getDouble("POSY");
						}
						rs.close();
						stmt.close();
						g.notifySpostamentopuntiMilitari(x, y, g.getColor());
					} catch (RemoteException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			break;
		case "vittoria":
			for (Giocatore g : giocatori) {
				if (g != null) {
					double x = 0;
					double y = 0;
					String query;
					if (qta < 10)
						query = "SELECT POSX, POSY FROM POSIZIONETABELLONE WHERE ID='PV0" + qta + "'";
					else
						query = "SELECT POSX, POSY FROM POSIZIONETABELLONE WHERE ID='PV" + qta + "'";
					try {
						Statement stmt = c.createStatement();
						ResultSet rs = stmt.executeQuery(query);
						while (rs.next()) {
							x = rs.getDouble("POSX");
							y = rs.getDouble("POSY");
						}
						rs.close();
						stmt.close();
						System.out.println(x + "   " + y);
						g.notifySpostamentopuntiVittoria(x, y, g.getColor());
					} catch (RemoteException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			break;
		case "fede":
			for (int i = 0; i < 4; i++) {
				if (giocatori[i] != null) {
					double x = 0;
					double y = 0;
					String query;
					if (qta < 10)
						query = "SELECT POSX, POSY FROM POSIZIONETABELLONE WHERE ID='PF0" + qta + "'";
					else
						query = "SELECT POSX, POSY FROM POSIZIONETABELLONE WHERE ID='PF" + qta + "'";
					try {
						Statement stmt = c.createStatement();
						ResultSet rs = stmt.executeQuery(query);
						while (rs.next()) {
							x = rs.getDouble("POSX");
							y = rs.getDouble("POSY");
						}
						rs.close();
						stmt.close();
						giocatori[i].notifySpostamentopuntiFede(x, y, giocatori[i].getColor());
					} catch (RemoteException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			break;
		}
		connectionDatabase.releaseConnection(c);
	}

	public void notifyAddRisorse(String name, String tipo, int qta) {
		for (int i = 0; i < 4; i++) {
			if (giocatori[i] != null)
				giocatori[i].notifyAddRisorse(name, tipo, qta);
		}
	}

	public int getTurno() {
		return turno;
	}

	public void notfyAvvAddScomunica(String name, int numSco) {
		for (int i = 0; i < 4; i++) {
			if (giocatori[i] != null)
				giocatori[i].notfyAvvAddScomunica(name, numSco);
		}
	}

	public boolean getOk() {
		// TODO Auto-generated method stub
		return ok;
	}

	public void setOk() {
		ok = true;

	}

	public void setResetNumberOfGamer() {
		NumberOfPlayers = 0;

	}
}
