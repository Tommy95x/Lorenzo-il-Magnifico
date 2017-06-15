package server.element;

import java.io.Serializable;

import javafx.scene.image.Image;
import server.StartServer;

/**
 * @author Tommy
 *Class that contain in to every Gamer.
 */
public class Flag implements Serializable{

	private String color;
	private Image flagImage = new Image(getClass().getResourceAsStream("Vescovo.png"));
	/**
	 * Constructor Flag Class where set color and image parameter
	 * 
	 * @param color from Gamer's color
	 * @param commonServer for DB connection request
	 * @param account Gamer's name
	 */
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
