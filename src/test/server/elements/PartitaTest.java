package test.server.elements;

import static org.junit.Assert.*;

import java.rmi.RemoteException;
import java.sql.SQLException;

import org.junit.Test;

import server.database.ConnectionDatabase;
import server.element.Giocatore;
import server.element.Partita;

public class PartitaTest {

	@Test
	public void testCheckGiocatoriNonProntiPerStartGame() throws SQLException, RemoteException {
		ConnectionDatabase db = new ConnectionDatabase(2,2);
		Partita partita = new Partita("prova", "prova", 0, db.getConnection("Test"));
		partita.addGiocatore(new Giocatore("blue",partita, "prova", 0));
		partita.addGiocatore(new Giocatore("white",partita, "prova2",0 ));
		//Discommenta per avviare il test e commenta in partita
		//assertFalse(partita.checkBoolean(1));
		partita.start("prova");
		//assertTrue(partita.checkBoolean(1));
		partita.deleteView(db.getConnection("Test"));
	}
	
	@Test
	public void testCheckDicesNotNull() throws SQLException{
		ConnectionDatabase db = new ConnectionDatabase(2,2);
		Partita partita = new Partita("prova", "prova", 0, db.getConnection("Test"));
		assertEquals("prova", partita.getLobbyName());
	}

	@Test
	public void testVerificaMax6Turni() throws SQLException{
		ConnectionDatabase db = new ConnectionDatabase(2,2);
		Partita partita = new Partita("prova", "prova", 0, db.getConnection("Test"));
		for(int i=0;i<6;i++)
			partita.addTurno();
		assertFalse(partita.addTurno());
	}
	
	@Test
	public void testCheckAvailableColors() throws SQLException{
		ConnectionDatabase db = new ConnectionDatabase(2,2);
		Partita partita = new Partita("prova", "prova", 0, db.getConnection("Test"));
		Giocatore player = new Giocatore("blue",partita, "prova", 0);
		assertEquals("", partita.getColors()[0]);
	}
	
}
