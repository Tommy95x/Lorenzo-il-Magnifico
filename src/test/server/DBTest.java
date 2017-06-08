package test.server;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import server.database.ConnectionDatabase;
import server.database.PoolDatabase;

public class DBTest {

	/**
	 * Test che verifica la possibilit� ad un utente di richiedere pi� connessioni in parallelo
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testParallerlConnection() throws SQLException {
		ConnectionDatabase db = new ConnectionDatabase(2, 1);
		Connection connection = db.getConnection("prova");
		assertEquals(connection.getClientInfo(), db.getConnection("prova").getClientInfo());
	}
	
	/**
	 * Test che verifica la corretta chiusura di una connessione del DB
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testClosedConnection() throws SQLException{
		ConnectionDatabase db = new ConnectionDatabase(2, 1);
		Connection connection = db.getConnection("prova");
		connection.close();
		assertTrue(connection.isClosed());
	}

}
