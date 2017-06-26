package test.client.Gui;

import static org.junit.Assert.*;

import org.junit.Test;

import client.gui.StartClientGui;
import server.StartServer;

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

}
