package server;

import java.rmi.Remote;

public interface RMIClientInterface extends Remote{
	public void notifyStartGame();
}
