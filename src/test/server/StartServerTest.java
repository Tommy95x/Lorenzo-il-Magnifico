package test.server;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import server.StartServer;

public class StartServerTest {

	/**
	 * Test che verifica la non possibilita' di creare due lobby con il medesimo nome
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testCheckNotEqualsGame() throws SQLException{
		StartServer server = new StartServer();
		server.addGame("prova", "prova");
		assertEquals("Sorry, but the name of the game is already use, change name", server.addGame("prova", "prova2"));
		server.deletLobby(0);
	}

}
