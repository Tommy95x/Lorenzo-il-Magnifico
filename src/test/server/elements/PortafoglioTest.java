package test.server.elements;

import static org.junit.Assert.*;

import org.junit.Test;

import server.element.Portafoglio;

public class PortafoglioTest {

	/**
	 * Test di verifica di corretta istanzia di un nuovo portafoglio
	 */
	@Test
	public void testCreazionePortafoglio() {
		Portafoglio portafoglio = new Portafoglio();
		assertNotNull(portafoglio);
	}

	/**
	 * Test che verifica la corretta assegnazione ad inizio partita delle
	 * risorse dovute ad ogni giocatore indipendente dalla posizione di gioco
	 */
	@Test
	public void testVerificaAssegnazioneRisorseCreazione() {
		Portafoglio portafoglio = new Portafoglio();
		assertEquals(2, portafoglio.getDimRisorse("legno"));
		assertEquals(2, portafoglio.getDimRisorse("pietra"));
		assertEquals(3, portafoglio.getDimRisorse("servitori"));
		assertEquals(5, portafoglio.getDimRisorse("monete"));
	}

	/**
	 * Test di verifica dell'impossibilita' di avere risorse negative e che
	 * verifica il corretto funzionamento dell'aggiunta delle risorse
	 */
	@Test
	public void testRisorseNegative() {
		Portafoglio portafoglio = new Portafoglio();
		portafoglio.addRis("monete", -5);
		assertEquals(0, portafoglio.getDimRisorse("monete"));
		// Ricordati che per eliminare ed eseguire il test devi andare nella
		// classe Portafoglio e discommentare public boolean addMonete(int
		// incr){
		// assertFalse(portafoglio.addMonete(-1));
	}

	/**
	 * Altro controllo di verifica di corretto incremento di una risorda selezionata 
	 */
	@Test
	public void testConfirmAddRisorse() {
		Portafoglio portafoglio = new Portafoglio();
		portafoglio.addRis("monete", 1);
		assertEquals(6, portafoglio.getDimRisorse("monete"));
	}

}
