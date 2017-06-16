package test.client.Gui;

import static org.junit.Assert.*;

import org.junit.Test;

import client.gui.StartClientGui;

public class StartClientGuiTest {

	/**
	 * Test di verifica della corretta istanza del colore di un giocatore;
	 */
	@Test
	public void testControllColor() {
		StartClientGui s = new StartClientGui();
		s.setColor("blue");
		assertEquals("blue", s.getColor());
	}

	/**
	 * Test di verifica del corretto settaggio a true della variabile booleana
	 * che conferma il corretto svolgimento di tutte le azione pre quit game
	 */
	/*
	 * @Test public void testCreateFlase(){ StartClientGui s = new
	 * StartClientGui(); assertFalse(s.create); s.setCreate(true);
	 * assertTrue(s.create); }
	 */

}
