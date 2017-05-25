package client;

import java.rmi.Remote;

public interface RMIClientInterface extends Remote{
	public void notifyStartGame();
}
