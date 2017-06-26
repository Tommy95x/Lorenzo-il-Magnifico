package test.client;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import org.junit.Test;

import client.ConnectionRmiClient;
import client.ConnectionRmiInterlocutorClient;
import client.ConnectionSocketClient;
import server.StartServer;

public class ConnectionRmiClientTest {

	@Test
	public void testCreateAInterlocutor() throws RemoteException {
		ConnectionRmiInterlocutorClient in = new ConnectionRmiInterlocutorClient();
		assertNotNull(in);
	}
	
	/*@Test
	public void testConnectionSocket(){
		StartServer s = new StartServer();
		ConnectionSocketClient c = null;
		try {
			c = new ConnectionSocketClient();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(c);
		assertNotNull(c.getSocket());
	}*/

	/*@Test
	public void testConnectionRmi(){
		StartServer s = new StartServer();
		ConnectionRmiClient c;
		try {
			c = new ConnectionRmiClient();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(c);
		assertTrue(c.testConnetcion());
	}*/
	
}
