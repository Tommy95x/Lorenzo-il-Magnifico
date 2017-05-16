package server.database;

import java.sql.Connection;
import java.util.ArrayList;

public class PoolDatabase {

	//Array di connessioni al database
	private Connection con[];
		
	//Array disponibilità connessioni
	private	boolean busy[];
		
	//Registro chi tiene occupata la connessione
	private	String name[];
		
	//Numero connessioni attive
	private	int numconn;
		
	//incremento dimensione pool per nuove richieste
	private	int inc;
	
	public PoolDatabase(int numCon, int inc) {
		numconn=numCon;
		this.inc=inc;
	}

	public Connection[] getConnections(){
		return con;
	}

	public int getNumConnection() {
		return numconn;
	}

	public void addNewConnection(Connection[] connections) {
		con=connections;
	}

	public void addNewBusy(boolean[] bs) {
		busy = bs;
	}

	public void addWho(String[] strings) {
		name=strings;
		
	}

	public void setConnection(int i, Connection connection) {
		con[i]=connection;
		busy[i]=false;
		name[i]="";
	}

	public boolean[] getBusies() {
		return busy;
	}

	public String[] getNames() {
		
		return name;
	}

	public void addInc() {
		numconn=numconn+inc;
	}

	public void setBusy(int i, boolean b) {
		busy[i]=b;
	}

	public void setWho(int i, String string) {
		name[i]=string;
	}

	public int getIndFree() {
		for(int i=0; i<numconn; i++){
			if(!busy[i])
				return i;
		}
		return -1;
	}

	public Connection getConnection(int indFree) {
		return con[indFree];
	}

	public void realeaseCon(Connection c) {
		for(int i=0;i<numconn;i++){
			if(c==con[i]){
				busy[i]=false;
				name[i]="";
			}
		}
	}
}
