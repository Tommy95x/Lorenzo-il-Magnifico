package server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDatabase{

	private PoolDatabase pool; 
	private String dbUrl="jdbc:h2:~/test";
	private String username="sa";
	private String pwDB="";
	private String driverString="org.h2.Driver";
	
	
	public ConnectionDatabase(int numCon, int inc ){
		pool = new PoolDatabase(numCon, inc);
		try {
			newConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void newConnection() throws ClassNotFoundException, SQLException {
		startConnection();
		Class.forName(driverString);
		for(int i=0;i<pool.getNumConnection();i++){
			pool.setConnection(i,DriverManager.getConnection(dbUrl, username, pwDB));
		}
	}
	
	private void extendConnections() throws SQLException{
		//Copia e salva i vecchi vettori
		Connection saveCon[] = pool.getConnections();
		boolean saveBusy[] = pool.getBusies();
		String saveName[] = pool.getNames();
		int saveNumCon=pool.getNumConnection();
		
		pool.addInc();
		startConnection();
		//Copiare vecchie connessioni nel nuovo array di connessioni
		for(int i=0;i<pool.getNumConnection();i++){
			pool.setConnection(i, saveCon[i]);
			pool.setBusy(i,saveBusy[i]);
			pool.setWho(i,saveName[i]);
		}
		for(int i=saveNumCon;i<pool.getNumConnection();i++){
			pool.setConnection(i,DriverManager.getConnection(dbUrl, username, pwDB));
		}
		
	}
	
	private void startConnection(){
		pool.addNewConnection(new Connection[pool.getNumConnection()]);
		pool.addNewBusy(new boolean[pool.getNumConnection()]);
		pool.addWho(new String[pool.getNumConnection()]);
	}
	
	public Connection getConnection(String name) throws SQLException{
		//Indice di connessioni libere
		int indFree = pool.getIndFree();
		
		if(indFree<0){
			extendConnections();
			indFree = pool.getIndFree();
			if(indFree<0)
				return null;
		}
		pool.setBusy(indFree, true);
		pool.setWho(indFree, name);
		return pool.getConnection(indFree);
	}
	
	public void releaseConnection(Connection c){
		pool.realeaseCon(c);
	}
}
