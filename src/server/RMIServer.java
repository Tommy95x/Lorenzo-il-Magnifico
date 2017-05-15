package server;

/*
*Classe utilizzata utilizzata per creare una connessione passiva RMI che permette agli utenti di connettersi e giocare con una connessione 
*differente dai Socket.
*/
public class RMIServer extends Thread{

	private int port;
	private String ip;
	private StartServer commonServer;
	
	public RMIServer(int port, StartServer commonServer){
		this.port=port;
		this.commonServer=commonServer;
	}
	
}
