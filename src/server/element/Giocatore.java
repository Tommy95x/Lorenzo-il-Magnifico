package server.element;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import client.ConnectionRmiClient;
import server.ThreadSocketServer;
import shared.RMIClientInterface;

public class Giocatore implements Serializable {

	private String name;
	private String color;
	private Disco[] discoGiocatore;
	private Portafoglio risorse;
	private FamiliareNeutro[] familiari;
	private CuboScomunica[] cubiScomunica;
	//Mettere public per il test
	//public Dado[] dadi = new Dado[3];
	private Dado[] dadi;
	private ArrayList<CartaSviluppo> carte;
	private Partita partita;
	private int positionGame;
	private ThreadSocketServer server = null;
	private RMIClientInterface  client;
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
		risorse.addRis(ris,1);
	}

	public String getName() {
		return name;
	}

	public Dado[] setDadi(Connection connection) throws SQLException {
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
			System.out.println("utente socket"+server.toString());
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

	public String controlloPosizionamento(String color, double x, double y, Connection connection, int agg) throws SQLException {
		Dado dadoMom = null;
		for(Dado d : dadi){
			if(d.getColor().equals(color))
				dadoMom=d;
				break;
		}
		String query="SELECT VALOREAZIONE FROM POSIZIONETABELLONE WHERE "+x+"=POSX AND "+y+"=POSY";
		Statement stmt = connection.createStatement();
		ResultSet res = stmt.executeQuery(query);
		res.next();
		int valoreazione = res.getInt("VALOREAZIONE");
		res.close();
		stmt.close();
		connection.close();
		if(risorse.getDimRisorse("servitori") < agg){
			return "NotEnough";
			}else{
				if(dadoMom.getValore()+agg >= valoreazione){
					risorse.addRis("servitori", -agg);
					return "OK";
				}else{
					return "Pay";
				}
			}
	}

	public void getScomunica(TesseraScomunica scomunica) throws RemoteException {
		cubiScomunica[nScomuniche] = new CuboScomunica(color, nScomuniche, scomunica);
		if (client == null)
			server.addScomunica(nScomuniche, scomunica.getTooltip());
		else
			client.addScomunica(nScomuniche, scomunica.getTooltip());
	}

	public void notifyTurno() throws SQLException, IOException {
		if (client == null)
			server.notifyTurno();
		else
			client.notifyTurno();

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

	public void addCard(CartaSviluppo carta, int tipo) {
		carte.add(carta);
		activateCardEffettiImmediati(carta, tipo);
	}

	public void notifyAddCard(CartaSviluppo carta) throws RemoteException {
		if (client == null) {
			server.notifyAddCard(carta, this.getName(), this.getRisorse());
		} else {
			client.notifyAddCard(carta, this.getName(), this.getRisorse());
		}
	}

	private void activateCardEffettiImmediati(CartaSviluppo carta, int tipo) {
		switch(tipo){
		case 0:
			carta = (CartaTerritori) carta;
			for(Effetto e :  carta.getEffetti()){
				if(e.isImmediato() && e!=null){
					switch(e.getRisorsa()){
						case "punti"
					}
				}
			}
			break;
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		}
	}

	public ArrayList<CartaSviluppo> getCardsGamer() {
		return carte;
	}
}
