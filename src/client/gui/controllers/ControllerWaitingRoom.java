package client.gui.controllers;

import client.gui.StartClientGui;

public class ControllerWaitingRoom {

	private StartClientGui start;
	
	@FXML
	public ImageView background;
	@FXML
	public Label text;
	
	public void getStartClientGui(StartClientGui startClientGui) {
		this.setStart(startClientGui);
		//Rivedo perche' forse non funziona, forse c'e' da cambiare posizione o creare una funzione di lancio apposita
		while(true){
			fade();
			//Dormo per 5 secondo circa, chiedo al prof se pero' in caso non riceve le notifiche
		}
	}

	public StartClientGui getStart() {
		return start;
	}

	private void setStart(StartClientGui start) {
		this.start = start;
	}
	
	public void fade(){
		
		FileReader file = new FileReader("");//creo il file da leggere
		BufferReader fileReader = new BufferReader(file);
		int random = Math.random();//devo moltiplicare per il numero massimo di immagini che inserisco
		for(int= 0;i<random-1;i++){
			fileReader.readLine();
		}
		String mom = fileReader.readLine();
		im.setImage(new Image(getClass().getResourceAsStream(mom)));
		FadeTransition fadeTransition = new FadeTransition(Duration.millis(2500), backgrond);
		fadeTransition.setFromValue(0.0);
		fadeTransition.setToValue(1.0);
		fadeTransition.play();
		
		file = new FileReader("");//creo il file da leggere
		fileReader = new BufferReader(file);
		random = Math.random();//devo moltiplicare per il numero massimo di immagini che inserisco
		for(int= 0;i<random-1;i++){
			fileReader.readLine();
		}
		mom = fileReader.readLine();
		random = Math.random();//Devo moltiplicare per il numero massimo di frasi che inserisco
		label.setText("");
		FadeTransition fadeTransition2 = new FadeTransition(Duration.millis(2500), text);
		fadeTransition2.setFromValue(0.0);
		fadeTransition2.setToValue(1.0);
		fadeTransition2.play();
	}
}
