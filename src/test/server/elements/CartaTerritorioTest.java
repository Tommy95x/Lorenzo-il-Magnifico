package test.server.elements;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import javafx.scene.control.Tooltip;
import server.database.ConnectionDatabase;
import server.element.CartaTerritori;

public class CartaTerritorioTest {

	/**
	 * Test di verifica del corretto rilascio del tooltip di una carta
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testCorrectTooltipReturn() throws SQLException {
		ConnectionDatabase db = new ConnectionDatabase(2, 2);
		CartaTerritori c = new CartaTerritori();
		c.setCarta(db.getConnection("Test"), "SELECT * FROM CARTATERRITORIO WHERE NOME= 'Foresta'");
		Tooltip t = new Tooltip(c.getTooltipString());
		assertEquals(t,c.getTooltip());
	}

	/**
	 * Testi di verifica della corretta instanzia degli effetti di una carta
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testArrayListEffettiNotNull() throws SQLException {
		ConnectionDatabase db = new ConnectionDatabase(2, 2);
		CartaTerritori c = new CartaTerritori();
		c.setCarta(db.getConnection("Test"), "SELECT * FROM CARTATERRITORIO CARTETERRITORIOPARTITA LIMIT 1");
		assertNotNull(c.getEffetti());
	}
	
}
