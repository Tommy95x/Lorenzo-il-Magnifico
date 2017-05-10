package server;

public class RMIServer extends Thread{

	private int port;
	private String ip;
	private StartServer commonServer;
	
	public RMIServer(int port, StartServer commonServer){
		this.port=port;
		this.commonServer=commonServer;
	}
	
}
