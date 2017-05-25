package server.element;

import javafx.scene.image.Image;
import server.StartServer;

public class Flag {

	private String color;
	private Image flagImage;
	
	public Flag(String color, StartServer commonServer, String account){
		this.setColor(color);
		setFlagImage(commonServer, account);
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Image getFlagImage() {
		return flagImage;
	}

	private void setFlagImage(StartServer commonServer, String account) {
		//Creare il metodo che dal DB prende l'immagine
	}
	
}
