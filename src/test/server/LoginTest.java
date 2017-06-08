package test.server;

import static org.junit.Assert.*;

import org.junit.Test;

import server.StartServer;

public class LoginTest {

	/**
	 * Test che verifica l'avvenuto successo del login e nello stesso tempo verifica la non possibilità del medesimo utente
	 * di riacedere al sistema
	 */
	@Test
	public void testCorrectLogin() {
		StartServer server = new StartServer();
		String account = "prova";
		String pw = "prova";
		assertEquals("Welcome to the game",server.addClient(account, pw));
		assertEquals("Player already login",server.addClient(account, pw));
	}
	
	/**
	 * Test che verifica la non presenza dell'utente all'interno del DB e che gli notifica di dover registrarsi al sistema
	 */
	@Test
	public void testIncorrectandRegisterLogin(){
		StartServer server = new StartServer();
		String account = "filippo";
		String pw = "ciccio";
		assertNotEquals("Welcome to the game", server.addClient(account, pw));
		assertEquals("For player must register a new account",server.addClient(account, pw));
	}
}
