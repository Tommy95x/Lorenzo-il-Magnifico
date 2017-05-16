package server.database;

import java.sql.Connection;

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
	
}
