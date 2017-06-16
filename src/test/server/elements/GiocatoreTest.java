package test.server.elements;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import server.database.ConnectionDatabase;
import server.element.Giocatore;
import server.element.Partita;

public class GiocatoreTest {

	/**
	 * Test di verifica se posizionando un familiare in plancia il posizionamento e':
	 * 1-Possibile se il valore del dado è uguale al valore della posizione selezionata
	 * 2-Non è possibile posizionare un familiare all'interno di una posizione non abilitata all'interno del tabellone
	 * 3-Possibile posizionare un familiare in una posizione con un valore maggiore del valore del dado pagando 4 servitori
	 * 4-Richiesta di pagamento dei servitori in caso il valore del dado non sia abbastanza
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testControlloPosizionamento() throws SQLException {
		ConnectionDatabase db = new ConnectionDatabase(2,2);
		Partita partita = new Partita("prova", "prova", 0, db.getConnection("Test"));
		Giocatore player = new Giocatore("blue",partita, "prova", 0);
		partita.addGiocatore(player);
		player.setDadi(db.getConnection("prova"));
		assertEquals("OK", player.controlloPosizionamento("black", 75.0, 628.0, db.getConnection("prova"), 0));
		assertNotEquals("OK", player.controlloPosizionamento("black", 120.7, 75.0, db.getConnection("prova"), 0));
		assertEquals("OK", player.controlloPosizionamento("black", 427.0, 262.0, db.getConnection("prova"), 4));
		assertEquals("Pay", player.controlloPosizionamento("black", 427.0, 262.0, db.getConnection("prova"), 0));
		partita.deleteView(db.getConnection("prova"));
	}
	
	/**
	 * Test di verifica della corretta istanzia ad inizio partita dell'ArrayList<CarteSviluppo> proprietarie di un giocatore
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testcheckNotNullCardsGamer() throws SQLException{
		ConnectionDatabase db = new ConnectionDatabase(2,2);
		Partita partita = new Partita("prova", "prova", 0, db.getConnection("Test"));
		Giocatore player = new Giocatore("blue",partita, "prova", 0);
		partita.addGiocatore(player);
	}

}
