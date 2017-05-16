package server;
import java.sql.*;

public class DatabaseConnectionPool {
	//Array di connessioni al database
	Connection con[];
	
	//Array disponibilità connessioni
	boolean busy[];
	
	//Registro chi tiene occupata la connessione
	String name[];
	
	//Numero connessioni attive
	int numconn;
	
	//incremento dimensione pool per nuove richieste
	int inc;

}
