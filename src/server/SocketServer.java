package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer extends Thread{

	private int port;
	@SuppressWarnings("unused")
	private String ip="127.0.0.1";
	private StartServer commonServer;
	
	public SocketServer(int i, StartServer server) {
		this.commonServer=server;
		this.port = i;
	}

	@SuppressWarnings("resource")
	private void startSocketServer(int port){
		ExecutorService ex=Executors.newCachedThreadPool();
		ServerSocket socket = null;
		try {
			socket=new ServerSocket(port);
			System.out.println("Server Socket start");
		} catch (IOException e) {
			System.out.println("Port already use");
			e.printStackTrace();
		}
		while(true){
			try {
				Socket executorSocket=socket.accept();
				ex.submit(new ThreadSocketServer(executorSocket,commonServer));
				System.out.println("Un nuovo utente ha richiesto una connessione");
			} catch (IOException e) {
				System.out.println("Error no space for new connection");
				e.printStackTrace();
			}
		}
	}
		
	public void run(){
		this.startSocketServer(port);
	}
}
