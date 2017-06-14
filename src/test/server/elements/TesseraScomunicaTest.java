package test.server.elements;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import server.database.ConnectionDatabase;
import server.element.TesseraScomunica;

public class TesseraScomunicaTest {

	/**
	 * Test che verifica della corretta istanzia del tooltip dedicato alla carta
	 * scomunica
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testTooltipNotNull() throws SQLException {
		TesseraScomunica tessera = new TesseraScomunica();
		ConnectionDatabase db = new ConnectionDatabase(1, 1);
		tessera.setTesseraPrimoPeriodo(db.getConnection("Test"));
		assertNotNull(tessera.getTooltip());
	}

	/**
	 * Test di verifica della corretta istanzia dell'Image dedicata alla
	 * CartaScomunica di un singolo periodo di una singola partita
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testImageNotNull() throws SQLException {
		TesseraScomunica tessera = new TesseraScomunica();
		ConnectionDatabase db = new ConnectionDatabase(1, 1);
		tessera.setTesseraPrimoPeriodo(db.getConnection("Test"));
		assertNotNull(tessera.getImage());
	}

}
