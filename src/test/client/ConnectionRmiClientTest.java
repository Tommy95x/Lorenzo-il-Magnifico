package test.client;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import org.junit.Test;

import client.ConnectionRmiInterlocutorClient;

public class ConnectionRmiClientTest {

	@Test
	public void testCreateAInterlocutor() throws RemoteException {
		ConnectionRmiInterlocutorClient in = new ConnectionRmiInterlocutorClient();
		assertNotNull(in);
	}

}
