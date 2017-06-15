package server.element;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.scene.image.Image;
import server.StartServer;

/**
 * @author Tommy
 *Class that contain in to every Gamer.
 */
public class Flag implements Serializable{

	private String color;
	private String flagImage;
	/**
	 * Constructor Flag Class where set color and image parameter
	 * 
	 * @param color from Gamer's color
	 * @param commonServer for DB connection request
	 * @param account Gamer's name
	 * @throws SQLException 
	 */
	public Flag(String color, StartServer commonServer, String account) throws SQLException{
		this.setColor(color);
		setFlagImage(commonServer, account);
	}
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getFlagImage() {
		return flagImage;
	}
	
	public void setFlagImage(String flagImage){
		this.flagImage=flagImage;
	}

	private void setFlagImage(StartServer commonServer, String account) throws SQLException {
		Connection connection = commonServer.getDBConnection().getConnection(account);
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT IMMAGINE FROM BANDIERA WHERE COLORE = "+color+"");
		rs.next();
		flagImage=rs.getString("IMMAGINE");
		rs.close();
		stmt.close();
		connection.close();
	}
	
}
