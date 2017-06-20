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
import client.gui.controllers.ControllerGame;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import server.ThreadSocketServer;
import shared.RMIClientInterface;

public class Giocatore implements Serializable {

	private String name;
	private String color;
	private Disco[] discoGiocatore;
	private Portafoglio risorse;
	private FamiliareNeutro[] familiari;
	private CuboScomunica[] cubiScomunica;
	// Mettere public per il test e istanziare
	private Dado[] dadi;
	private ArrayList<CartaSviluppo> carte = new ArrayList<CartaSviluppo>();
	private Partita partita;
	private int positionGame;
	private ThreadSocketServer server = null;
	private RMIClientInterface client;
	private Flag flag;
	private int nScomuniche = 0;
	private boolean palazzoTerritori[] = new boolean[4];
	private boolean palazzoPersonaggi[] = new boolean[4];
	private boolean palazzoImprese[] = new boolean[4];
	private boolean palazzoEdifici[] = new boolean[4];

	public Giocatore(String color, Partita partita, String name, int positionGame) {
		this.name = name;
		this.positionGame = positionGame;
		this.partita = partita;
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
			palazzoImprese[i] = true;
			palazzoEdifici[i] = true;
			palazzoTerritori[i] = true;
			palazzoPersonaggi[i] = true;
		}
	}

	public Giocatore(String name, String color, Portafoglio portafoglio) {
		this.color = color;
		this.name = name;
		this.risorse = portafoglio;
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
		dadi[0] = new Dado("black");
		dadi[1] = new Dado("white");
		dadi[2] = new Dado("orange");
		for (Dado d : dadi) {
			d.setValue(connection);
		}
		connection.close();
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
		int dado = 0;
		System.out.println(color);
		for (int i = 0; i < 3; i++) {
			if (dadi[i].getColor().equals(color)) {
				dado = i;
			}
		}
		String query = "SELECT VALOREAZIONE, NOME FROM POSIZIONETABELLONE WHERE " + x + "=POSX AND " + y + "=POSY";
		Statement stmt = connection.createStatement();
		ResultSet res = stmt.executeQuery(query);
		int valoreazione = 0;
		String nome = "";
		while (res.next()) {
			nome = res.getString("NOME");
			valoreazione = res.getInt("VALOREAZIONE");
		}
		res.close();
		stmt.close();
		connection.close();
		if (risorse.getDimRisorse("servitori") < agg) {
			return "NotEnough";
		} else {
			if (dadi[dado].getValore() + agg >= valoreazione || (color.equals("neutro") && agg > 0)) {
				risorse.addRis("servitori", -agg);
				switch (nome) {
				case "PIANO 1 FAMILIARE TERRITORI":
					palazzoTerritori[3] = false;
					break;
				case "PIANO 2 FAMILIARE TERRITORI":
					palazzoTerritori[2] = false;
					break;
				case "PIANO 3 FAMILIARE TERRITORI":
					palazzoTerritori[1] = false;
					break;
				case "PIANO 4 FAMILIARE TERRITORI":
					palazzoTerritori[0] = false;
					break;
				case "PIANO 1 FAMILIARE EDIFICI":
					palazzoEdifici[3] = false;
					break;
				case "PIANO 2 FAMILIARE EDIFICI":
					palazzoEdifici[2] = false;
					break;
				case "PIANO 3 FAMILIARE EDIFICI":
					palazzoEdifici[1] = false;
					break;
				case "PIANO 4 FAMILIARE EDIFICI":
					palazzoEdifici[0] = false;
					break;
				case "PIANO 1 FAMILIARE IMPRESE":
					palazzoImprese[3] = false;
					break;
				case "PIANO 2 FAMILIARE IMPRESE":
					palazzoImprese[2] = false;
					break;
				case "PIANO 3 FAMILIARE IMPRESE":
					palazzoImprese[1] = false;
					break;
				case "PIANO 4 FAMILIARE IMPRESE":
					palazzoImprese[0] = false;
					break;
				case "PIANO 1 FAMILIARE PERSONAGGI":
					palazzoPersonaggi[3] = false;
					break;
				case "PIANO 2 FAMILIARE PERSONAGGI":
					palazzoPersonaggi[2] = false;
					break;
				case "PIANO 3 FAMILIARE PERSONAGGI":
					palazzoPersonaggi[1] = false;
					break;
				case "PIANO 4 FAMILIARE PERSONAGGI":
					palazzoPersonaggi[0] = false;
					break;
				}
				return "OK";
			} else if (dadi[dado].getValore() + agg < valoreazione || (color.equals("neutro") && agg < 0)) {
				return "Pay";
			} else
				return "Cancel";
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
		try {
			activateCardEffettiImmediati(carta, tipo, c);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void notifyAddCardAvv(CartaSviluppo carta, String n) throws RemoteException {
		if (client == null) {
			try {
				server.notifyAddCardAvv(carta, n);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("Notifico il singolo giocatore per l'aggiunta della carta");
			client.notifyAddCardAvv(carta, n);
		}
	}

	private void activateCardEffettiImmediati(CartaSviluppo carta, int tipo, Connection c) throws SQLException {
		switch (tipo) {
		case 0:
			carta = (CartaTerritori) carta;
			for (Effetto e : carta.getEffetti()) {
				if (e.isImmediato() && e.getQta() != 0) {
					switch (e.getRisorsa()) {
					case "militari":
						risorse.addPunti("militari", e.getQta());
						System.out.println(partita.toString() + " " + e.getQta());
						partita.notifySpostamentoPunti("militari", risorse.getPunti("militari"), c);
						break;
					case "vittoria":
						risorse.addPunti("vittoria", e.getQta());
						System.out.println(partita.toString() + " " + e.getQta());
						partita.notifySpostamentoPunti("vittoria", risorse.getPunti("vittoria"), c);
						break;
					case "fede":
						risorse.addPunti("fede", e.getQta());
						System.out.println(partita.toString() + " " + e.getQta());
						partita.notifySpostamentoPunti("fede", risorse.getPunti("fede"), c);
						break;
					case "pietra":
						risorse.addRis("pietra", e.getQta());
						System.out.println(partita.toString() + " " + e.getQta());
						partita.notifyAddRisorse(name, "pietra", e.getQta());
						break;
					case "monete":
						risorse.addRis("monete", e.getQta());
						System.out.println(partita.toString());
						System.out.println(e.getQta());
						partita.notifyAddRisorse(name, "monete", e.getQta());
						break;
					case "servitori":
						risorse.addRis("servitori", e.getQta());
						System.out.println(partita.toString() + " " + e.getQta());
						partita.notifyAddRisorse(name, "servitori", e.getQta());
						break;
					case "legno":
						risorse.addRis("legno", e.getQta());
						System.out.println(partita.toString() + " " + e.getQta());
						partita.notifyAddRisorse(name, "legno", e.getQta());
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
						partita.notifySpostamentoPunti("militari", risorse.getPunti("militari"), c);
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
						partita.notifySpostamentoPunti("fede", risorse.getPunti("fede"), c);
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
						raccolto(e.getQta(), c);
						break;
					case "produzione":
						produzione(e.getQta(), c);
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
						partita.notifySpostamentoPunti("vittoria", risorse.getPunti("vittoria"), c);
						break;
					case "fede":
						risorse.addPunti("fede", e.getQta());
						partita.notifySpostamentoPunti("fede", risorse.getPunti("fede"), c);
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
						partita.notifySpostamentoPunti("militari", risorse.getPunti("militari"), c);
						break;
					case "fede":
						risorse.addPunti("fede", e.getQta());
						partita.notifySpostamentoPunti("fede", risorse.getPunti("fede"), c);
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
						raccolto(e.getQta(), c);
						break;
					case "produzione":
						produzione(e.getQta(), c);
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
		c.close();
	}

	public void raccolto(int qta, Connection conn) {
		setRisorse("legno");
		setRisorse("pietra");
		setRisorse("servitori");
		for (CartaSviluppo c : carte) {
			if (c.getId().contains("TER") && c.getCostoAzione() <= qta) {
				activateCardEffettiPermanenti(c, 0, conn);
			}
		}
	}

	public void produzione(int qta, Connection conn) {
		for (CartaSviluppo c : carte) {
			if (c.getId().contains("ED") && c.getCostoAzione() <= qta) {
				activateCardEffettiPermanenti(c, 2, conn);
			}
		}
	}

	public void activateCardEffettiPermanenti(CartaSviluppo c, int i, Connection conn) {
		switch (i) {
		case 0:
			c = (CartaTerritori) c;
			for (Effetto e : c.getEffetti()) {
				if (!e.isImmediato() && e.getQta() != 0) {
					switch (e.getRisorsa()) {
					case "legno":
						risorse.addRis("legno", e.getQta());
						break;
					case "pietra":
						risorse.addRis("pietra", e.getQta());
					case "pergamena":
						try {
							notifyPergamena(e.getQta());
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						break;
					case "monete":
						risorse.addRis("monete", e.getQta());
						break;
					case "servitori":
						risorse.addRis("servitori", e.getQta());
						break;
					case "militari":
						risorse.addPunti("militari", e.getQta());
						partita.notifySpostamentoPunti("militari", risorse.getPunti("militari"), conn);
						break;
					case "fede":
						risorse.addPunti("fede", e.getQta());
						partita.notifySpostamentoPunti("fede", risorse.getPunti("fede"), conn);
						break;
					case "vittoria":
						risorse.addPunti("vittoria", e.getQta());
						partita.notifySpostamentoPunti("vittoria", risorse.getPunti("vittoria"), conn);
						break;
					}
				}
			}
			break;
		case 2:
			c = (CartaEdifici) c;
			for (Effetto e : c.getEffetti()) {
				if (!e.isImmediato() && e.getQta() != 0) {
					int sum = 0;
					switch (e.getRisorsa()) {
					case "vittoria":
						switch (c.getPerOgniCarta()) {
						case "IMPRESA":
							for (CartaSviluppo c1 : carte) {
								if (c1.getId().contains("impr")) {
									sum++;
								}
							}
							risorse.addPunti("vittoria", sum);
							partita.notifySpostamentoPunti("vittoria", risorse.getPunti("vittoria"), conn);
							sum = 0;
							break;
						case "PERSONAGGI":
							for (CartaSviluppo c1 : carte) {
								if (c1.getId().contains("PER")) {
									sum++;
								}
							}
							risorse.addPunti("vittoria", sum);
							partita.notifySpostamentoPunti("vittoria", risorse.getPunti("vittoria"), conn);
							sum = 0;
							break;
						}
					case "monete":
						switch (c.getPerOgniCarta()) {
						case "EDIFICIO":
							for (CartaSviluppo c1 : carte) {
								if (c1.getId().contains("ED")) {
									sum++;
								}
							}
							risorse.addRis("monete", sum);
							partita.notifyAddRisorse(name, "monete", sum);
							;
							sum = 0;
							break;
						case "PERSONAGGI":
							for (CartaSviluppo c1 : carte) {
								if (c1.getId().contains("PER")) {
									sum++;
								}
							}
							risorse.addRis("monete", sum);
							partita.notifyAddRisorse(name, "monete", sum);
							sum = 0;
							break;
						}
					}
				}
			}
			Risorsa[] r = c.getSpendiRisorsa();
			Risorsa[] p = c.getPrendiRisorsa();
			for(int j=0; j<3; j++){
				if(r[j].getQta()!=0){
					switch (r[j].getRisorsa()){
					case "monete":
						if(r[j].getQta()<=risorse.getDimRisorse("monete")){
							risorse.addRis("monete", -r[j].getQta());
							prendiRisorseeffettipermanenti(p[j].getRisorsa(), p[j].getQta(), conn);
							if (p[j+1].getQta()!=0 && j<2){
								prendiRisorseeffettipermanenti(p[j].getRisorsa(), p[j].getQta(), conn);
							}
						}
					case "pietra":
						if(r[j].getQta()<=risorse.getDimRisorse("pietra") && j!=2){
							risorse.addRis("pietra", -r[j].getQta());
							prendiRisorseeffettipermanenti(p[j].getRisorsa(), p[j].getQta(), conn);
							if (p[j+1].getQta()!=0 && j<2){
								prendiRisorseeffettipermanenti(p[j].getRisorsa(), p[j].getQta(), conn);
							}
						}
					case "legno":
						if(r[j].getQta()<=risorse.getDimRisorse("legno") && j!=1){
							risorse.addRis("legno", -r[j].getQta());
							prendiRisorseeffettipermanenti(p[j].getRisorsa(), p[j].getQta(), conn);
							if (p[j+1].getQta()!=0 && j<2){
								prendiRisorseeffettipermanenti(p[j].getRisorsa(), p[j].getQta(), conn);
							}
						}
					case "fede":
						if(r[j].getQta()<=risorse.getDimRisorse("fede")){
							risorse.addRis("fede", -r[j].getQta());
							prendiRisorseeffettipermanenti(p[j].getRisorsa(), p[j].getQta(), conn);
							if (p[j+1].getQta()!=0 && j<2){
								prendiRisorseeffettipermanenti(p[j].getRisorsa(), p[j].getQta(), conn);
							}
						}
					case "servitori":
						if(r[j].getQta()<=risorse.getDimRisorse("servitori")){
							if(r[j+1].getRisorsa()=="niente"){
							risorse.addRis("servitori", -r[j].getQta());
							prendiRisorseeffettipermanenti(p[j].getRisorsa(), p[j].getQta(), conn);
							if (p[j+1].getQta()!=0 && j<2){
								prendiRisorseeffettipermanenti(p[j].getRisorsa(), p[j].getQta(), conn);
							}
							}else{
								risorse.addRis("servitori", -r[j].getQta());
								risorse.addRis("legno", -r[j+1].getQta());
								risorse.addRis("pietra", -r[j+2].getQta());
								prendiRisorseeffettipermanenti(p[j].getRisorsa(), p[j].getQta(), conn);
								if (p[j+1].getQta()!=0 && j<2){
									prendiRisorseeffettipermanenti(p[j].getRisorsa(), p[j].getQta(), conn);
								}
							}
						}
			}
			if(c.getSpendiRisorsa().equals("niente") && !c.getPrendiRisorsa().equals("niente")){
				for(int k=0; k<2; k++){
					switch(r[k].getRisorsa()){
					case "militari":
						risorse.addPunti("militari", r[k].getQta());
						partita.notifySpostamentoPunti("militari",risorse.getPunti("militari"), conn);
						break;
					case "vittoria":
						risorse.addPunti("vittoria", r[k].getQta());
						partita.notifySpostamentoPunti("vittoria", risorse.getPunti("vittoria"), conn);
						break;
					case "monete":
						risorse.addRis("monete", r[k].getQta());
						break;
					case "pergamena":
						try {
							notifyPergamena(r[k].getQta());
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						break;
					}
				}
			}
		}
	}
	}
	}

	private void prendiRisorseeffettipermanenti(String risorsa, int qta, Connection c) {
		switch (risorsa) {
		case "pietra":
			risorse.addRis("pietra", qta);
			break;
		case "legno":
			risorse.addRis("legno", qta);
			break;
		case "servitori":
			risorse.addRis("servitori", qta);
			break;
		case "fede":
			risorse.addRis("fede", qta);
			partita.notifySpostamentoPunti("vittoria", risorse.getPunti("fede"), c);
			break;
		case "vittoria":
			risorse.addRis("vittoria", qta);
			partita.notifySpostamentoPunti("vittoria", risorse.getPunti("vittoria"), c);
			break;
		}

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
		// Creo metodo notify risorse

	}

	public void addPunti(String tipo, int qta, Connection c) {
		risorse.addPunti(tipo, qta);
		partita.notifySpostamentoPunti(tipo, qta, c);
	}

	public void setGuiGame(ControllerGame guiGame) {

	}

	public boolean getPosPalLibero(String pos, int i) {
		switch (pos) {
		case "edifici":
			return palazzoEdifici[i];
		case "territori":
			return palazzoTerritori[i];
		case "personaggi":
			return palazzoPersonaggi[i];
		case "imprese":
			return palazzoImprese[i];
		}
		return false;

	}

	public void notifyAddRisorse(String name, String tipo, int qta) {
		if (client == null) {
			server.notifyAddRisorse(name, tipo, qta);
		} else {
			server.notifyAddRisorse(name, tipo, qta);
		}

	}
}
