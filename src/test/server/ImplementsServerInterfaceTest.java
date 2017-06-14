package test.server;

import static org.junit.Assert.*;

import java.rmi.RemoteException;
import java.sql.SQLException;

import org.junit.Test;

import server.ImplementServerInterface;
import server.StartServer;

public class ImplementsServerInterfaceTest {

	/**
	 * Test per la verifica della corretta creazione di una partita
	 * 
	 * @throws RemoteException
	 * @throws SQLException
	 */	
	@Test
	public void testCreazioneNuovaPartita() throws RemoteException, SQLException {
		StartServer server = new StartServer();
		ImplementServerInterface impl = new ImplementServerInterface(server);
		assertEquals(0,impl.createNewLobby("prova", "prova", "blue", null));
	}
	
	/**
	 * Test che verifica che è possibile acquisire correttamente una partita
	 * 
	 * @throws RemoteException
	 * @throws SQLException
	 */
	@Test
	public void testGetPartita() throws RemoteException, SQLException{
		StartServer server = new StartServer();
		ImplementServerInterface impl = new ImplementServerInterface(server);
		impl.createNewLobby("prova", "prova", "blue", null);
		assertNotNull(impl.getLobby());
	}

	/**
	 * Test per verificare se è possibile entrare in una partita in modo corretto, verificando se la posizione combacia con la partita creata in precedenza
	 * 
	 *@throws RemoteException
	 * @throws SQLException
	 */
	@Test
	public void testSelectALobbyKnowThePosition() throws RemoteException, SQLException{
		StartServer server = new StartServer();
		ImplementServerInterface impl = new ImplementServerInterface(server);
		int positionGame = impl.createNewLobby("prova", "prova", "blue", null);
		assertEquals(positionGame,impl.selectLobby("prova", "prova2", "green", null));
	}
	
	/**
	 * Test per la verifica della presenza di un massimo di 4 giocatori in una partita
	 * 
	 * @throws RemoteException
	 * @throws SQLException
	 */
	@Test
	public void testMaxFourPlayers() throws RemoteException, SQLException{
		StartServer server = new StartServer();
		ImplementServerInterface impl = new ImplementServerInterface(server);
		int positionGame = impl.createNewLobby("prova", "prova", "blue", null);
		impl.selectLobby("prova", "prova2", "green", null);
		impl.selectLobby("prova", "prova3", "white", null);
		impl.selectLobby("prova", "prova4", "orange", null);
		assertEquals(-1, impl.selectLobby("prova", "prova5", "black", null));
	}
	
	/**
	 * Test per la verifica se al lancio dei dadi da parte di un giocatore l'array dei dadi non è nullo
	 * 
	 * @throws RemoteException
	 * @throws SQLException
	 */
	@Test
	public void showDiceValues() throws RemoteException, SQLException{
		StartServer server = new StartServer();
		ImplementServerInterface impl = new ImplementServerInterface(server);
		int positionGame = impl.createNewLobby("prova", "prova", "blue", null);
		impl.getGiocatori(positionGame)[0].setDadi(server.getDBConnection().getConnection("Test"));
		assertNotNull(impl.showDiceValues(positionGame, "prova"));
	}
	
	/**
	 * Test per la verifica della possibilita' o meno di scegliere un colore da quelli disponibili in game
	 * 
	 * @throws RemoteException
	 * @throws SQLException
	 */
	@Test
	public void testConfirmAvailableColors() throws RemoteException, SQLException{
		StartServer server = new StartServer();
		ImplementServerInterface impl = new ImplementServerInterface(server);
		int positionGame = impl.createNewLobby("prova", "prova", "green", null);
		assertEquals("blue",impl.getColors(positionGame)[0]);
		assertNull(impl.getColors(positionGame)[3]);
	}
	
	/**
	 *Test per la verifica che non sia a null l'arraylist delle carte svuluppo di una partita
	 * 
	 * @throws RemoteException
	 * @throws SQLException
	 */
	@Test
	public void testPresenzaCarteSviluppo() throws RemoteException, SQLException{
		StartServer server = new StartServer();
		ImplementServerInterface impl = new ImplementServerInterface(server);
		int positionGame = impl.createNewLobby("prova", "prova", "blue", null);
		assertNotNull(impl.getCards(positionGame));
	}
	
	/**
	 * Test per la verifica in partita della presenza delle tessere scomunica
	 * 
	 * @throws RemoteException
	 * @throws SQLException
	 */
	@Test
	public void testPresenzaTessereScomuniche() throws RemoteException, SQLException{
		StartServer server = new StartServer();
		ImplementServerInterface impl = new ImplementServerInterface(server);
		int positionGame = impl.createNewLobby("prova", "prova", "blue", null);
		assertNotNull(impl.getCardsScomunica(positionGame));
	}
	
	/**
	 * Test per la verifica dell'effettiva presenza di un giocatore che è entrato in precendenza nella partita
	 * 
	 * @throws RemoteException
	 * @throws SQLException
	 */
	@Test
	public void testPresenzaGiocatore() throws RemoteException, SQLException{
		StartServer server = new StartServer();
		ImplementServerInterface impl = new ImplementServerInterface(server);
		int positionGame = impl.createNewLobby("prova", "prova", "blue", null);
		assertEquals("prova", impl.getGiocatori(positionGame)[0].getName());
	}
	
	/**
	 * Test per verificare la corrispondenza esatta del numero di giocatorti all'interno di una partita
	 * 
	 * @throws RemoteException
	 * @throws SQLException
	 */
	@Test
	public void testCorrispondenzaNumeroGiocatori() throws RemoteException, SQLException{
		StartServer server = new StartServer();
		ImplementServerInterface impl = new ImplementServerInterface(server);
		int positionGame = impl.createNewLobby("prova", "prova", "blue", null);
		impl.selectLobby("prova", "prova2", "green", null);
		assertEquals(2, impl.getNumberOfPlayer(positionGame));
	}
}
