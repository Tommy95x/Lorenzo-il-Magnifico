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
		int positionGame = impl.createNewLobby("prova", "prova", "blue");
		//assertEquals("prova", impl.getLobby().get(0).);
		impl.deleteView(positionGame);
	}
	
	/**
	 * Test che verifica che a seguito di una creazione di una partita l'ArrayList<Partita> delle partite non è piu' a null
	 * 
	 * @throws RemoteException
	 * @throws SQLException
	 */
	@Test
	public void testGetPartita() throws RemoteException, SQLException{
		StartServer server = new StartServer();
		ImplementServerInterface impl = new ImplementServerInterface(server);
		int positionGame = impl.createNewLobby("prova", "prova", "blue");
		assertNotNull(impl.getLobby());
		impl.deleteView(positionGame);
	}

	/**
	 * Test per verificare se e' possibile entrare in una partita in modo corretto, verificando se la posizione combacia con la partita creata in precedenza
	 * 
	 *@throws RemoteException
	 * @throws SQLException
	 */
	@Test
	public void testSelectALobbyKnowThePosition() throws RemoteException, SQLException{
		StartServer server = new StartServer();
		ImplementServerInterface impl = new ImplementServerInterface(server);
		int positionGame = impl.createNewLobby("prova", "prova", "blue");
		assertEquals(positionGame,impl.selectLobby("prova", "prova2", "green"));
		impl.deleteView(positionGame);
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
		int positionGame = impl.createNewLobby("prova", "prova", "blue");
		impl.selectLobby("prova", "prova2", "green");
		impl.selectLobby("prova", "prova3", "white");
		impl.selectLobby("prova", "prova4", "orange");
		assertEquals(-1, impl.selectLobby("prova", "prova5", "black"));
		impl.deleteView(positionGame);
	}
	
	/**
	 * Test per la verifica se al lancio dei dadi da parte di un giocatore l'array dei dadi non � nullo
	 * 
	 * @throws RemoteException
	 * @throws SQLException
	 */
	@Test
	public void showDiceValues() throws RemoteException, SQLException{
		StartServer server = new StartServer();
		ImplementServerInterface impl = new ImplementServerInterface(server);
		int positionGame = impl.createNewLobby("prova", "prova", "blue");
		impl.getGiocatori(positionGame)[0].setDadi(server.getDBConnection().getConnection("Test"));
		assertNotNull(impl.showDiceValues(positionGame, "prova"));
		impl.deleteView(positionGame);
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
		int positionGame = impl.createNewLobby("prova", "prova", "green");
		assertEquals("blue",impl.getColors(positionGame)[0]);
		assertNull(impl.getColors(positionGame)[3]);
		impl.deleteView(positionGame);
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
		int positionGame = impl.createNewLobby("prova", "prova", "blue");
		assertNotNull(impl.getCards(positionGame));
		impl.deleteView(positionGame);
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
		int positionGame = impl.createNewLobby("prova", "prova", "blue");
		assertNotNull(impl.getCardsScomunica(positionGame));
		impl.deleteView(positionGame);
	}
	
	/**
	 * Test per la verifica dell'effettiva presenza di un giocatore che � entrato in precendenza nella partita
	 * 
	 * @throws RemoteException
	 * @throws SQLException
	 */
	@Test
	public void testPresenzaGiocatore() throws RemoteException, SQLException{
		StartServer server = new StartServer();
		ImplementServerInterface impl = new ImplementServerInterface(server);
		int positionGame = impl.createNewLobby("prova", "prova", "blue");
		assertEquals("prova", impl.getGiocatori(positionGame)[0].getName());
		impl.deleteView(positionGame);
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
		int positionGame = impl.createNewLobby("prova", "prova", "blue");
		impl.selectLobby("prova", "prova2", "green");
		assertEquals(2, impl.getNumberOfPlayer(positionGame));
		impl.deleteView(positionGame);
	}
}
