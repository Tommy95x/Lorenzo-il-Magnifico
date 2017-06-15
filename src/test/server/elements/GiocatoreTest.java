package test.server.elements;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import server.database.ConnectionDatabase;
import server.element.Giocatore;
import server.element.Partita;

public class GiocatoreTest {

	@Test
	public void testControlloPosizionamento() throws SQLException {
		ConnectionDatabase db = new ConnectionDatabase(2,2);
		Partita partita = new Partita("prova", "prova", 0, db.getConnection("Test"));
		Giocatore player = new Giocatore("blue",partita, "prova", 0);
		partita.addGiocatore(player);
		
	}
	
	@Test
	public void testCardsGamerNotNull(){
		
	}

}
