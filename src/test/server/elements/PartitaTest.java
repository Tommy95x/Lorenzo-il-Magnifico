package test.server.elements;

import static org.junit.Assert.*;

import java.rmi.RemoteException;
import java.sql.SQLException;

import org.junit.Test;

import server.database.ConnectionDatabase;
import server.element.Giocatore;
import server.element.Partita;

public class PartitaTest {
	/**
	 * Test per la verifica del metodo che controlla il non inizio della partita
	 * ,se non tutti i giocatori sono pronti per giocare,
	 * 
	 * @throws SQLException
	 * @throws RemoteException
	 */
	@Test
	public void testCheckGiocatoriNonProntiPerStartGame() throws SQLException, RemoteException {
		ConnectionDatabase db = new ConnectionDatabase(2, 2);
		Partita partita = new Partita("prova", "prova", 0, db.getConnection("Test"));
		partita.addGiocatore(new Giocatore("blue", partita, "prova", 0));
		partita.addGiocatore(new Giocatore("white", partita, "prova2", 0));
		// Discommenta per avviare il test e commenta in partita
		// assertFalse(partita.checkBoolean(1));
		partita.start("prova");
		// assertTrue(partita.checkBoolean(1));
		partita.deleteView(db.getConnection("Test"));
	}

	/**
	 * Test per il check dell'ugualianza di una nuova partita creata da un
	 * utente
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testNameLobby() throws SQLException {
		ConnectionDatabase db = new ConnectionDatabase(2, 2);
		Partita partita = new Partita("prova", "prova", 0, db.getConnection("Test"));
		assertEquals("prova", partita.getLobbyName());
	}

	/**
	 * Test per la verifica di un numero massimo di turni pari a 6
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testVerificaMax6Turni() throws SQLException {
		ConnectionDatabase db = new ConnectionDatabase(2, 2);
		Partita partita = new Partita("prova", "prova", 0, db.getConnection("Test"));
		for (int i = 0; i < 6; i++)
			partita.addTurno();
		assertFalse(partita.addTurno());
	}

	/**
	 * Test del corretto settaggio a "" dopo che un giocatore ha scelto in
	 * precedenza il medesimo colore
	 * 
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	@Test
	public void testCheckAvailableColors() throws SQLException {
		ConnectionDatabase db = new ConnectionDatabase(2, 2);
		Partita partita = new Partita("prova", "prova", 0, db.getConnection("Test"));
		Giocatore player = new Giocatore("blue", partita, "prova", 0);
		assertEquals("", partita.getColors()[0]);
	}

	/**
	 * Test di verifica se un giocatore appena aggiunto ad una partita non si
	 * trovaa null mentre è ancora a null un elemento dell'array di giocatori in
	 * cui non si è ancora aggiunto un giocatore
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testCardsGamerNotNull() throws SQLException {
		ConnectionDatabase db = new ConnectionDatabase(2, 2);
		Partita partita = new Partita("prova", "prova", 0, db.getConnection("Test"));
		Giocatore player = new Giocatore("blue", partita, "prova", 0);
		partita.addGiocatore(player);
		assertNotNull(partita.getGiocatori()[0]);
		assertNull(partita.getGiocatori()[3]);
	}
}
