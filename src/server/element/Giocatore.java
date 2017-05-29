package server.element;

import java.sql.Connection;

import client.ConnectionRmiClient;
import server.RMIClientInterface;
import server.ThreadSocketServer;

public class Giocatore{

	private String name;
	private String color;
	private Disco[] discoGiocatore;
	private Portafoglio risorse;
	private FamiliareNeutro[] familiari;
	private CuboScomunica[] cubiScomunica;
	private Dado[] dadi;
	private Partita partita;
	private int positionGame;
	private ThreadSocketServer server = null;
	private ConnectionRmiClient client = null;
	private Flag flag;
	
	public Giocatore(String color, Partita partita, String name, int positionGame){
		this.name=name;
		this.positionGame=positionGame;
		int i;
		this.setColor(color);
		discoGiocatore[0]=new Disco(color);
		discoGiocatore[1]=new DiscoFede(color);
		discoGiocatore[2]=new DiscoMilitare(color);
		discoGiocatore[3]=new DiscoVittoria(color);
		risorse=new Portafoglio();
		familiari = new FamiliareNeutro[4];
		cubiScomunica = new CuboScomunica[3];
		dadi = new Dado[3];
		for(i=0;i<4;i++){
			switch(i){
				case 0:
					familiari[i]=new FamiliareNeutro();
					break;
				case 1:
					familiari[i]=new Familiare("black");
					break;
				case 2:
					familiari[i]=new Familiare("orange");
					break;
				case 3:
					familiari[i]=new Familiare("white");
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

	public Dado[] setDadi(Connection connection){
		for(Dado d:dadi){
			d.setValue(connection);
		}
		return dadi;
	}
	
	public void getSocket(ThreadSocketServer server){
		if(client == null && server == null )
			this.server=server;
	}
	
	public void getClient(ConnectionRmiClient client){
		if(client == null && server == null )	
			this.client = client;
	}

	public void notifyStartGame() {
		if(client == null)
			server.notifyStartGame();
		else
			client.notifyStartGame();
	}

	public Flag getFlag() {
		return flag;
	}

	public void setFlag(Flag flag) {
		this.flag = flag;
	}

	public void play() {
		if(client == null)
			server.playGamer();
		else
			client.playGamer();
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
		if(dadoMom.getValore() >= )
			return "OK";
		else if(da)
			return "Pay";
	}
}
