package server.element;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import client.ConnectionRmiClient;
import client.ConnectionRmiInterlocutorClient;
import server.ThreadSocketServer;
import shared.RMIClientInterface;

public class Giocatore implements Serializable {

	private String name;
	private String color;
	private Disco[] discoGiocatore;
	private Portafoglio risorse;
	private FamiliareNeutro[] familiari;
	private CuboScomunica[] cubiScomunica;
	// Mettere public per il test
	// public Dado[] dadi = new Dado[3];
	private Dado[] dadi;
	private ArrayList<CartaSviluppo> carte = new ArrayList<CartaSviluppo>();
	private Partita partita;
	private int positionGame;
	private ThreadSocketServer server = null;
	private RMIClientInterface client;
	private Flag flag;
	private int nScomuniche = 0;

	public Giocatore(String color, Partita partita, String name, int positionGame) {
		this.name = name;
		this.positionGame = positionGame;
		this.setColor(color);
		risorse = new Portafoglio();
		familiari = new FamiliareNeutro[4];
		cubiScomunica = new CuboScomunica[3];
		discoGiocatore = new Disco[4];
		dadi = new Dado[3];
		for (int i = 0; i < 4; i++) {
			switch (i) {
			case 0:
				familiari[i] = new FamiliareNeutro();
				discoGiocatore[i] = new Disco(color);
				break;
			case 1:
				familiari[i] = new Familiare("black");
				discoGiocatore[i] = new DiscoFede(color);
				break;
			case 2:
				familiari[i] = new Familiare("orange");
				discoGiocatore[i] = new DiscoMilitare(color);
				break;
			case 3:
				familiari[i] = new Familiare("white");
				discoGiocatore[i] = new DiscoVittoria(color);
				break;
			}
		}
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Portafoglio getRisorse() {
		return risorse;
	}

	public void setRisorse(String ris) {
		risorse.addRis(ris, 1);
	}

	public String getName() {
		return name;
	}

	public Dado[] setDadi(Connection connection) throws SQLException {
		// Commentare quando non si testa
		/*
		 * dadi[0] = new Dado("black"); dadi[1] = new Dado("white"); dadi[2] =
		 * new Dado("orange");
		 */
		for (Dado d : dadi) {
			d.setValue(connection);
		}
		return dadi;
	}

	public void getSocket(ThreadSocketServer mom) {
		if (server == null)
			server = mom;
		System.out.println(server);
	}

	public void getClient(RMIClientInterface connectionRmiClient) {
		if (server == null)
			this.client = connectionRmiClient;
		System.out.println(client);
	}

	public void notifyStartGame() throws IOException, ClassNotFoundException {
		System.out.println("Enter in a notifica start");
		if (server != null) {
			System.out.println("utente socket" + server.toString());
			server.notifyStartGame();
			return;
		} else {
			System.out.println("utente rmi");
			client.notifyStartGame();
		}
	}

	public Flag getFlag() {
		return flag;
	}

	public void setFlag(Flag flag) {
		this.flag = flag;
	}

	public String controlloPosizionamento(String color, double x, double y, Connection connection, int agg)
			throws SQLException {
		Dado dadoMom = null;
		for (Dado d : dadi) {
			if (d.getColor().equals(color))
				dadoMom = d;
			break;
		}
		String query = "SELECT VALOREAZIONE FROM POSIZIONETABELLONE WHERE " + x + "=POSX AND " + y + "=POSY";
		Statement stmt = connection.createStatement();
		ResultSet res = stmt.executeQuery(query);
		res.next();
		int valoreazione = res.getInt("VALOREAZIONE");
		res.close();
		stmt.close();
		connection.close();
		if (risorse.getDimRisorse("servitori") < agg) {
			return "NotEnough";
		} else {
			if (dadoMom.getValore() + agg >= valoreazione) {
				risorse.addRis("servitori", -agg);
				return "OK";
			} else {
				return "Pay";
			}
		}
	}

	public void getScomunica(TesseraScomunica scomunica) throws RemoteException {
		cubiScomunica[nScomuniche] = new CuboScomunica(color, nScomuniche, scomunica);
		if (client == null)
			server.addScomunica(nScomuniche, scomunica.getTooltipByString());
		else
			client.addScomunica(nScomuniche, scomunica.getTooltipByString());
	}

	public void notifyTurno(int turno) throws SQLException, IOException {
		if (client == null)
			server.notifyTurno(turno);
		else
			client.notifyTurno(turno);

	}

	public void notifySpostamento(String color, String colorPlayer, double x, double y) throws IOException {
		if (client == null)
			server.moveFamiliareAvv(x, y, colorPlayer, color);
		else
			try {
				client.moveFamiliareAvv(x, y, colorPlayer, color);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

	public void addCard(CartaSviluppo carta, int tipo, Connection c) {
		carte.add(carta);
		activateCardEffettiImmediati(carta, tipo, c);
	}

	public void notifyAddCardAvv(CartaSviluppo carta) throws RemoteException {
		if (client == null) {
			try {
				server.notifyAddCardAvv(carta, this.getName());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			client.notifyAddCardAvv(carta, this.getName());
		}
	}

	private void activateCardEffettiImmediati(CartaSviluppo carta, int tipo, Connection c) {
		switch (tipo) {
		case 0:
			carta = (CartaTerritori) carta;
			for (Effetto e : carta.getEffetti()) {
				if (e.isImmediato() && e.getQta() != 0) {
					switch (e.getRisorsa()) {
					case "militari":
						risorse.addPunti("militari", e.getQta());
						partita.notifySpostamentoPunti("militari", e.getQta(), c);
						break;
					case "vittoria":
						risorse.addPunti("vittoria", e.getQta());
						partita.notifySpostamentoPunti("vittoria", e.getQta(), c);
						break;
					case "fede":
						risorse.addPunti("fede", e.getQta());
						partita.notifySpostamentoPunti("fede", e.getQta(), c);
						break;
					case "pietra":
						risorse.addRis("pietra", e.getQta());
						break;
					case "monete":
						risorse.addRis("monete", e.getQta());
						break;
					case "servitori":
						risorse.addRis("servitori", e.getQta());
						break;
					case "legno":
						risorse.addRis("legno", e.getQta());
						break;
					case "pergamena":
						try {
							notifyPergamena(e.getQta());
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						break;
					}
				}
			}
			break;
		case 1:
			int sum = 0;
			carta = (CartaPersonaggi) carta;
			risorse.addRis("monete", -carta.getCostoMoneta());
			for (Effetto e : carta.getEffetti()) {
				if (e.isImmediato() && e.getQta() != 0) {
					switch (e.getRisorsa()) {
					case "militari":
						risorse.addPunti("militari", e.getQta());
						partita.notifySpostamentoPunti("militari", e.getQta(), c);
						break;
					case "vittoria":
						switch (carta.getPerOgniCarta()) {
						case "EDIFICIO":
							for (CartaSviluppo c1 : carte) {
								if (c1.getId().contains("ED")) {
									sum++;
								}
							}
							risorse.addPunti("vittoria", 2 * sum);
							partita.notifySpostamentoPunti("vittoria", risorse.getPunti("vittoria"), c);
							sum = 0;
							break;
						case "PERSONAGGI":
							for (CartaSviluppo c1 : carte) {
								if (c1.getId().contains("PER")) {
									sum++;
								}
							}
							risorse.addPunti("vittoria", 2 * sum);
							partita.notifySpostamentoPunti("vittoria", risorse.getPunti("vittoria"), c);
							sum = 0;
							break;
						case "IMPRESA":
							for (CartaSviluppo c1 : carte) {
								if (c1.getId().contains("IMP")) {
									sum++;
								}
							}
							risorse.addPunti("vittoria", 2 * sum);
							partita.notifySpostamentoPunti("vittoria", risorse.getPunti("vittoria"), c);
							sum = 0;
							break;
						case "2MILITARI":
							risorse.addPunti("vittoria", (int) risorse.getPunti("militari") / 2);
							partita.notifySpostamentoPunti("vittoria", risorse.getPunti("vittoria"), c);
							sum = 0;
							break;
						case "TERRITORI":
							for (CartaSviluppo c1 : carte) {
								if (c1.getId().contains("TER")) {
									sum++;
								}
							}
							risorse.addPunti("vittoria", 2 * sum);
							partita.notifySpostamentoPunti("vittoria", risorse.getPunti("vittoria"), c);
							sum = 0;
							break;
						}
						break;
					case "fede":
						risorse.addPunti("fede", e.getQta());
						partita.notifySpostamentoPunti("fede", e.getQta(), c);
						break;
					case "pergamena":
						try {
							notifyPergamena(e.getQta());
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						break;
					case "raccolto":
						raccolto(e.getQta());
						break;
					case "produzione":
						produzione(e.getQta());
						break;
					case "tuttecarte":
						try {
							this.notifyTutteCarte(e.getQta());
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						break;
					case "unTipodicarta":
						try {
							notifyUnTipoCarta(e.getTipo(), e.getQta(), carta.scontoAzione);
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						break;
					}
				}
			}
			break;
		case 2:
			carta = (CartaEdifici) carta;
			for (Effetto e : carta.getEffetti()) {
				if (e.isImmediato() && e.getQta() != 0) {
					switch (e.getRisorsa()) {
					case "vittoria":
						risorse.addPunti("vittoria", e.getQta());
						partita.notifySpostamentoPunti("vittoria", e.getQta(), c);
						break;
					case "fede":
						risorse.addPunti("fede", e.getQta());
						partita.notifySpostamentoPunti("fede", e.getQta(), c);
						break;
					}
				}
			}
			break;
		case 3:
			carta = (CartaImprese) carta;
			for (Effetto e : carta.getEffetti()) {
				if (e.isImmediato() && e.getQta() != 0) {
					switch (e.getRisorsa()) {
					case "militari":
						risorse.addPunti("militari", e.getQta());
						partita.notifySpostamentoPunti("militari", e.getQta(), c);
						break;
					case "fede":
						risorse.addPunti("fede", e.getQta());
						partita.notifySpostamentoPunti("fede", e.getQta(), c);
						break;
					case "pietra":
						risorse.addRis("pietra", e.getQta());
						break;
					case "monete":
						risorse.addRis("monete", e.getQta());
						break;
					case "servitori":
						risorse.addRis("servitori", e.getQta());
						break;
					case "legno":
						risorse.addRis("legno", e.getQta());
						break;
					case "tuttecarte":
						try {
							this.notifyTutteCarte(e.getQta());
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						break;
					case "raccolto":
						raccolto(e.getQta());
						break;
					case "produzione":
						produzione(e.getQta());
						break;
					case "pergamena":
						try {
							notifyPergamena(e.getQta());
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						break;
					}
				}
			}
			break;
		}
	}

	public void raccolto(int qta) {
		for (CartaSviluppo c : carte) {
			if (c.getId().contains("TER") && c.getCostoAzione() <= qta) {
				activateCardEffettiPermanenti(c);
			}
		}
	}

	public void produzione(int qta) {
		for (CartaSviluppo c : carte) {
			if (c.getId().contains("ED") && c.getCostoAzione() <= qta) {
				activateCardEffettiPermanenti(c);
			}
		}
	}

	public void activateCardEffettiPermanenti(CartaSviluppo c) {

	}

	private void notifyUnTipoCarta(int tipo, int qta, int scontoAzioneImmediata1) throws RemoteException {

		if (client == null) {
			server.notifyUnTipoCarta(tipo, qta, scontoAzioneImmediata1);
		} else {
			client.notifyUnTipoCarta(tipo, qta, scontoAzioneImmediata1);
		}
	}

	private void notifyTutteCarte(int i) throws RemoteException {
		if (client == null) {
			server.notifyTutteCarte(i);
		} else {
			client.notifyTutteCarte(i);
		}
	}

	public void notifyPergamena(int i) throws RemoteException {
		if (client == null) {
			server.notifyPergamena(i);
		} else {
			client.notifyPergamena(i);
		}
	}

	public ArrayList<CartaSviluppo> getCardsGamer() {
		return carte;
	}

	public void notifySpostamentopuntiMilitari(double x, double y, String color) throws RemoteException {
		if (client == null) {
			try {
				server.notifySpostamentoPuntiMilitari(x, y, color);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			client.notifySpostamentoPuntiMilitari(x, y, color);
		}
	}

	public void notifySpostamentopuntiVittoria(double x, double y, String color2) throws RemoteException {
		if (client == null) {
			try {
				server.notifySpostamentoPuntiVittoria(x, y, color2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			client.notifySpostamentoPuntiVittoria(x, y, color2);
		}
	}

	public void notifySpostamentopuntiFede(double x, double y, String color2) throws RemoteException {
		if (client == null) {
			try {
				server.notifySpostamentoPuntiFede(x, y, color2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			client.notifySpostamentoPuntiFede(x, y, color2);
		}
	}

	public void addRis(String tipo, int qta, Connection c) {
		risorse.addRis(tipo, qta);
		//Creo metodo notify risorse
		
	}

	public void addPunti(String tipo, int qta, Connection c) {
		risorse.addPunti(tipo, qta);
		partita.notifySpostamentoPunti(tipo,qta, c);
	}
}
