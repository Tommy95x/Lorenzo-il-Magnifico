package client.gui;

import java.io.IOException;

import client.ConnectionClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartClientGui extends Application{

	private FXMLLoader loader ;
	private Parent root;
	private Stage primaryStage;
	private ConnectionClient client;
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	@SuppressWarnings("static-access")
	public void start(Stage primaryStage) throws IOException {
		
		loader = new FXMLLoader();
		this.primaryStage = primaryStage;
		
		loader.setLocation(getClass().getResource("ConncectionGui.fxml"));
		root = loader.load();
		
		primaryStage.setResizable(false);
		primaryStage.setTitle("Lorenzo il Magnifico Connection");
		//primaryStage.getIcons().add(e);
		ControllerConnection connection = loader.getController();
		connection.getStartClientGui(this);
		
		primaryStage.setScene(new Scene(root, 600, 400));
		primaryStage.show();
		noGraphics();
	}
	
	
	private void noGraphics() {
		/*System.out.println("Premi 0 su linea di comando per giocare in console");
		Scanner inKey = new Scanner(System.in);
		if(inKey.nextInt()==0){
			decision();
		}*/
		
	}


	@SuppressWarnings("static-access")
	public void changeStage(int numberOfStage){
		
		switch(numberOfStage){
			case 1:
				try {
					loader.setLocation(this.getClass().getResource(""));
					root = loader.load();
					primaryStage.setTitle("Lorenzo il Magnifico Login");
					ControllerLogin login = loader.getController();
					login.getStartClientGui(this);
					primaryStage.setScene(new Scene(root,600,400));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 2:
				try {
					loader.setLocation(this.getClass().getResource(""));
					root = loader.load();
					primaryStage.setTitle("Lorenzo il Magnifico Register");
					ControllerRegister register = loader.getController();
					register.getStartClientGui(this);
					primaryStage.setScene(new Scene(root,600,400));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 3:
				try {
					loader.setLocation(this.getClass().getResource(""));
					root = loader.load();
					primaryStage.setResizable(true);
					primaryStage.setTitle("Lorenzo il Magnifico");
					ControllerWaitingRoom waitingRoom = loader.getController();
					waitingRoom.getStartClientGui(this);
					primaryStage.setScene(new Scene(root));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 4:
				try {
					loader.setLocation(this.getClass().getResource(""));
					root = loader.load();
					ControllerGame game = loader.getController();
					game.getStartClient(this);
					primaryStage.setScene(new Scene(root));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
		}
	}
	
	public void exit(){
		primaryStage.close();
		System.exit(0);
	}

	public void getClient(ConnectionClient client) {
		this.client=client;
	}

	public ConnectionClient getClient() {
		return client;
	}

	public void setClient(ConnectionClient client) {
		this.client = client;
	}
	
	//Metodo test per l'avvio della connessione
		/*private static void decision(){
			Scanner in=new Scanner(System.in);
			System.out.println("Do you want to create a RMI connction or a Socket connection?");
			String decision=in.nextLine();
			if(decision.equals("RMI")||decision.equals("rmi")||decision.equals("Rmi"))
				client = new ConnectionRmiClient();
			else 
				client = new ConnectionSocketClient();
		}*/
	
}
