package server.element;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.control.Tooltip;

public class CartaTerritori extends CartaSviluppo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Le carte territorio non hanno alcun costo
	private int costoAzione;
	private String name = new String();
	private String nomeffetto = new String();
	private int qtaeffetto;
	private String ID;
	private ArrayList<Effetto> effetti = new ArrayList<Effetto>();
	private String image = new String();
	private String tooltip = new String();

	public CartaTerritori() {

	}

	public void setCarta(Connection connection, String query) {
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				name = rs.getString("NOME");
				ID = rs.getString("ID");
				nomeffetto = rs.getString("EFFETTOIMMEDIATO1").toLowerCase();
				qtaeffetto = rs.getInt("QTAEFFETTOIMMEDIATO1");
				effetti.add(new Effetto(nomeffetto, qtaeffetto, true, rs.getInt("TIPO"), rs.getInt("TIPOPERMANENTE")));
				nomeffetto = rs.getString("EFFETTOIMMEDIATO2").toLowerCase();
				qtaeffetto = rs.getInt("QTAEFFETTOIMMEDIATO2");
				effetti.add(new Effetto(nomeffetto, qtaeffetto, true, rs.getInt("TIPO"), rs.getInt("TIPOPERMANENTE")));
				nomeffetto = rs.getString("EFFETTOPERMANENTE1").toLowerCase();
				qtaeffetto = rs.getInt("QTAEFFETTOPERMANENTE1");
				effetti.add(new Effetto(nomeffetto, qtaeffetto, false, rs.getInt("TIPO"), rs.getInt("TIPOPERMANENTE")));
				nomeffetto = rs.getString("EFFETTOPERMANENTE2").toLowerCase();
				qtaeffetto = rs.getInt("QTAEFFETTOPERMANENTE2");
				effetti.add(new Effetto(nomeffetto, qtaeffetto, false, rs.getInt("TIPO"), rs.getInt("TIPOPERMANENTE")));
				nomeffetto = rs.getString("EFFETTOPERMANENTE3").toLowerCase();
				qtaeffetto = rs.getInt("QTAEFFETTOPERMANENTE3");
				effetti.add(new Effetto(nomeffetto, qtaeffetto, false, rs.getInt("TIPO"), rs.getInt("TIPOPERMANENTE")));
				costoAzione = rs.getInt("COSTOAZIONE");
				setImage(rs.getString("IMMAGINE"));
				setTooltip(rs.getString("DESCRIZIONE"));
				System.out.println(name);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setImage(String url) {
		image = url;
	}

	public String getNameCard() {
		return name;
	}

	public int getCostoAzione() {
		return costoAzione;
	}

	public String getImage() {
		return image;
	}

	public Tooltip getTooltip() {
		Tooltip tooltip = new Tooltip("");
		tooltip.setText(this.tooltip);
		return tooltip;
	}

	public void setNameCard(String name) {
		this.name = name;
	}

	public void setCostoAzione(int costoAzione) {
		this.costoAzione = costoAzione;
	}

	public void setTooltip(String string) {
		this.tooltip = string;
	}

	public String getTooltipString() {
		return tooltip;
	}

	public void setId(String ID) {
		this.ID = ID;
	}

	public String getId() {
		return ID;
	}

	public ArrayList<Effetto> getEffetti() {
		return effetti;
	}
}
