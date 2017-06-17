package server.element;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Mattia
 *Tessera che verra' posizionata sul tabellone a inizio partita, possiede un periodo e un effetto che viene attivato
  su ogni giocatore scomunicato
 */
public class TesseraScomunica implements Serializable{

	private String ID;
	private String nome;
	private int periodo;
	private String image;
	private String tooltip;
	
	//Vengono settati i parametri della tessera scomunica
	public TesseraScomunica(){
	}

	public TesseraScomunica(String readObject, String readObject2, String readObject3) {
		this.nome=readObject;
		this.image=readObject2;
		this.tooltip=readObject3;
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public int getTurno() {
		return periodo;
	}

	public void setTurno(int turno) {
		this.periodo = turno;
	}


	/**
	 * Method that with a DB connection sets all parameters
	 * 
	 * @param connection
	 */
	public void setTessera(Connection connection) {
		
		
	}

	public Tooltip getTooltip() {
		Tooltip tooltip = new Tooltip("");
		tooltip.setText(this.tooltip);
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * Method that with a DB connection are set all parameters
	 * 
	 * @param connection
	 * @throws SQLException 
	 */
	public void setTesseraPrimoPeriodo(Connection connection) throws SQLException {
		System.out.println("Tessera 1");
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM TESSERASCOMUNICA WHERE PERIODO=1 ORDER BY RAND() LIMIT 1");
		while(rs.next()){
			System.out.println("ID");
			ID = rs.getString("ID");
			System.out.println("Nome");
			nome = rs.getString("NOME");
			System.out.println("Periodo");
			periodo=rs.getInt("PERIODO");
			System.out.println("Image");
			image = rs.getString("IMMAGINE");
			System.out.println("tool");
			setTooltip(rs.getString("DESCRIZIONE"));
		}
		rs.close();
		stmt.close();
		connection.close();
	}

	public void setTesseraTerzoPeriodo(Connection connection) throws SQLException {
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM TESSERASCOMUNICA WHERE PERIODO=3 ORDER BY RAND() LIMIT 1");
		while(rs.next()){
			ID = rs.getString("ID");
			nome = rs.getString("NOME");
			periodo=rs.getInt("PERIODO");
			setImage(rs.getString("IMMAGINE"));
			setTooltip(rs.getString("DESCRIZIONE"));
		}
		rs.close();
		stmt.close();
		connection.close();
	}

	public void setTessereSecondoPeriodo(Connection connection) throws SQLException {
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM TESSERASCOMUNICA WHERE PERIODO=2 ORDER BY RAND() LIMIT 1");
		while(rs.next()){
			ID = rs.getString("ID");
			nome = rs.getString("NOME");
			periodo=rs.getInt("PERIODO");
			setImage(rs.getString("IMMAGINE"));
			setTooltip(rs.getString("DESCRIZIONE"));
		}
		rs.close();
		stmt.close();
		connection.close();
	}
	
	public String getTooltipByString(){
		return tooltip;
	}

}
