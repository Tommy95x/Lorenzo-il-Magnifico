package server.element;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import client.ConnectionRmiClient;
import client.RMIClientInterface;
import server.ThreadSocketServer;

public class Giocatore implements Serializable{

	private String name;
	private String color;
	private Disco[] discoGiocatore;
	private Portafoglio risorse;
	private FamiliareNeutro[] familiari;
	private CuboScomunica[] cubiScomunica;
	private Dado[] dadi;
	private ArrayList<CartaSviluppo> carte;
	private Partita partita;
	private int positionGame;
	private ThreadSocketServer server = null;
	private RMIClientInterface client = null;
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

	public void setRisorse(Risorse ris) {
		risorse.addRis(ris);
	}

	public String getName() {
		return name;
	}

	public Dado[] setDadi(Connection connection) {
		for (Dado d : dadi) {
			d.setValue(connection);
		}
		return dadi;
	}

	public void getSocket(ThreadSocketServer server) {
		if (client == null && server == null)
			this.server = server;
	}

	public void getClient(RMIClientInterface client2) {
		if (client2 == null && server == null)
			this.client = client2;
	}

	public void notifyStartGame() throws RemoteException {
		if (client == null){
			server.notifyStartGame();
		}else{
			client.notifyStartGame();
		}
	}

	public Flag getFlag() {
		return flag;
	}

	public void setFlag(Flag flag) {
		this.flag = flag;
	}

	public String controlloPosizionamento(String color, double x, double y, Connection connection) {
		Dado dadoMom;
		for(Dado d : dadi){
			if(d.getColor().equals(color))
				dadoMom=d;
				break;
		}
		//Scrivere la query che fornisce il valore nella tabella corrispondenza del valore tabellone
		String query;
		if(){
			
		}else{
			if(dadoMom.getValore() >= )
				return "OK";
			else if()
				return "Pay";
		}
	}
	
	public void getScomunica(TesseraScomunica scomunica) throws RemoteException{
		cubiScomunica[nScomuniche] = new CuboScomunica(color, nScomuniche, scomunica);
		if (client == null)
			server.addScomunica(nScomuniche, scomunica.getTooltip());
		else
			client.addScomunica(nScomuniche,scomunica.getTooltip());
	}

	public void notifyTurno() throws RemoteException, SQLException {
		if (client == null)
			server.notifyTurno();
		else
			client.notifyTurno();
		
	}

	public void notifySpostamento(String color, String colorPlayer, double x, double y) {
		if (client == null)
			server.moveFamiliareAvv(x,y,colorPlayer,color);
		else
			try {
				client.moveFamiliareAvv(x, y, colorPlayer, color);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public void getCard(CartaSviluppo carta) {
		carte.add(carta);
		//Molto probabilmente bisognerà notificare gli altri giocatori
	}
}
