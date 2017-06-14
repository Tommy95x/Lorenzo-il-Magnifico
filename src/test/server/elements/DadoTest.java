package test.server.elements;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import server.database.ConnectionDatabase;
import server.element.Dado;

public class DadoTest {

	/**
	 * Test di verifica della corretta estrazione casuale del dado di un valore
	 * compreso tra 1 e 6
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testVerificaGenerazioneCasualeDado() throws SQLException {
		Dado diceBlack = new Dado("black");
		Dado diceWhite = new Dado("white");
		ConnectionDatabase db = new ConnectionDatabase(2, 1);
		diceBlack.setValue(db.getConnection("Test"));
		diceWhite.setValue(db.getConnection("Test"));
		System.out.println(diceBlack.getValore() + " " + diceWhite.getValore());
		assertNotEquals(diceBlack.getValore(), diceWhite.getValore());
	}

	/**
	 * Test per la verica della corretta istanzia dell'immagine contenuta nel DB
	 * al momento della chiamata della funzione setValue
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testCheckImageNotNull() throws SQLException {
		Dado dice = new Dado("black");
		ConnectionDatabase db = new ConnectionDatabase(1, 1);
		dice.setValue(db.getConnection("Test"));
		assertNotNull(dice.getImage());
	}

}
