package test.server;

import static org.junit.Assert.*;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;

import org.junit.Test;

import client.gui.controllers.ControllerRegister;
import server.ImplementServerInterface;
import server.StartServer;

public class RegisterTest {

	/**
	 * Test per la verifica della corretta registrazione di un nuovo utente
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testRegistConfirm() throws SQLException {
		StartServer server = new StartServer();
		assertEquals("You are now registered!",server.registerNewClient("prova3", "prova", "prova@prova.it"));
	}
	
	/**
	 * Test per la verifica dell'inserimento in modo corretto di due email uguali tra loro
	 * 
	 * @throws RemoteException
	 * @throws SQLException
	 */
	@Test
	public void testPw1differentToPw2() throws RemoteException, SQLException{
		ImplementServerInterface server = new ImplementServerInterface(new StartServer());
		String pw1 = "prova";
		String pw2 = "test";
		assertEquals("The passwords are not equal",server.register("prova", pw1, pw2, "prova@prova.it"));
	}
	
	/**
	 * Teest per la conferma di mancata registrazione in caso di presenza nel DB di un utente già presente
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testNotregistred() throws SQLException{
		StartServer server = new StartServer();
		String account = "prova";
		String pw = "prova";
		String email = "prova@prova.it";
		assertNotEquals("You are now registered!",server.registerNewClient(account, pw, email));
		assertEquals("You are registered yet! You have to login!",server.registerNewClient(account, pw, email));
	}
	
}
