package client.gui.controllers;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

import client.gui.StartClientGui;
import javafx.animation.PathTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;
import server.element.CartaEdifici;
import server.element.CartaImprese;
import server.element.CartaPersonaggi;
import server.element.CartaSviluppo;
import server.element.CartaTerritori;
import server.element.Dado;
import server.element.Giocatore;
import server.element.Portafoglio;
import server.element.TesseraScomunica;

public class ControllerGame {

	private StartClientGui start;
	private CartaTerritori[] arrayCarteTerritori = new CartaTerritori[4];
	private CartaImprese[] arrayCarteImpresa = new CartaImprese[4];
	private CartaPersonaggi[] arrayCartePersonaggi = new CartaPersonaggi[4];
	private CartaEdifici[] arrayCarteEdifici = new CartaEdifici[4];
	private int numberOfGamers;
	private ImageView destinazione1;
	private HBox destinazione2;
	private boolean flag;

	// Componenti tabellone
	@FXML
	public VBox carteTerritori;
	@FXML
	public VBox carteEdifici;
	@FXML
	public VBox cartePersonaggi;
	@FXML
	public VBox carteImprese;
	@FXML
	public VBox posizioni;
	@FXML
	public HBox municipio;
	@FXML
	public HBox azioniTerritoridapiuGiocatori;
	@FXML
	public HBox azioniEdificidapiuGiocatori;
	@FXML
	public ImageView azioniTerritoridaunGiocatore;
	@FXML
	public ImageView azioniEdificidaunGiocatore;
	@FXML
	public ImageView dadoNero;
	@FXML
	public ImageView dadoBianco;
	@FXML
	public ImageView dadoArancio;
	@FXML
	public ImageView mercatoPosMoneteMilitari;
	@FXML
	public ImageView mercatoPosServitori;
	@FXML
	public ImageView mercatoPosMonete;
	@FXML
	public ImageView mercatoPosMunicipio;
	@FXML
	public ImageView pianoPrimoPalazzoMilitare;
	@FXML
	public ImageView pianoSecondoPalazzoMilitare;
	@FXML
	public ImageView pianoTerzoPalazzoMilitare;
	@FXML
	public ImageView pianoQuartoPalazzoMilitare;
	@FXML
	public ImageView pianoPrimoPalazzoPersonaggi;
	@FXML
	public ImageView pianoSecondoPalazzoPersonaggi;
	@FXML
	public ImageView pianoTerzoPalazzoPersonaggi;
	@FXML
	public ImageView pianoQuartoPalazzoPersonaggi;
	@FXML
	public ImageView pianoPrimoPalazzoEdifici;
	@FXML
	public ImageView pianoSecondoPalazzoEdifici;
	@FXML
	public ImageView pianoTerzoPalazzoEdifici;
	@FXML
	public ImageView pianoQuartoPalazzoEdifici;
	@FXML
	public ImageView pianoPrimoPalazzoTerritori;
	@FXML
	public ImageView pianoSecondoPalazzoTerritori;
	@FXML
	public ImageView pianoTerzoPalazzoTerritori;
	@FXML
	public ImageView pianoQuartoPalazzoTerritori;
	@FXML
	public ImageView cartaScomunica1;
	@FXML
	public ImageView cartaScomunica2;
	@FXML
	public ImageView cartaScomunica3;

	// Componenti tabellone avversari
	@FXML
	public ImageView puntiVittoriaBlu;
	@FXML
	public ImageView puntiVittoriaBianco;
	@FXML
	public ImageView puntiVittoriaArancio;
	@FXML
	public ImageView puntiVittoriaVerde;
	@FXML
	public ImageView puntiFedeBlu;
	@FXML
	public ImageView puntiFedeArancio;
	@FXML
	public ImageView puntiFedeBianco;
	@FXML
	public ImageView puntiFedeVerde;
	@FXML
	public ImageView puntiMilitariArancio;
	@FXML
	public ImageView puntiMilitariVerde;
	@FXML
	public ImageView puntiMilitariBlu;
	@FXML
	public ImageView puntiMilitariBianco;
	@FXML
	public ImageView familiareBlue1;
	@FXML
	public ImageView familiareBlue2;
	@FXML
	public ImageView familiareBlue3;
	@FXML
	public ImageView familiareBlue4;
	@FXML
	public ImageView familiareOrange1;
	@FXML
	public ImageView familiareOrange2;
	@FXML
	public ImageView familiareOrange3;
	@FXML
	public ImageView familiareOrange4;
	@FXML
	public ImageView familiareGreen1;
	@FXML
	public ImageView familiareGreen2;
	@FXML
	public ImageView familiareGreen3;
	@FXML
	public ImageView familiareGreen4;
	@FXML
	public ImageView familiareWhite1;
	@FXML
	public ImageView familiareWhite2;
	@FXML
	public ImageView familiareWhite3;
	@FXML
	public ImageView familiareWhite4;
	@FXML
	public Label name1;
	@FXML
	public Label name2;
	@FXML
	public Label name3;
	@FXML
	public ImageView flag1;
	@FXML
	public ImageView flag2;
	@FXML
	public ImageView flag3;

	// Componenti plancia
	@FXML
	public HBox carteImpresaGiocatore;
	@FXML
	public HBox carteTerritoriGiocatore;
	@FXML
	public HBox cartePersonaggiGiocatore;
	@FXML
	public HBox carteEdificiGiocatore;
	@FXML
	public Label monete;
	@FXML
	public Label servitori;
	@FXML
	public Label pietra;
	@FXML
	public Label lengo;
	@FXML
	public ImageView bandiera;
	@FXML
	public Button lanciaDadi;
	@FXML
	public ImageView familiareNeutro;
	@FXML
	public ImageView familiareNero;
	@FXML
	public ImageView familiareArancio;
	@FXML
	public ImageView familiareBianco;
	@FXML
	public ImageView cuboScomunica1;
	@FXML
	public ImageView cuboScomunica2;
	@FXML
	public ImageView cuboScomunica3;

	public void setGUI(StartClientGui startClientGui) throws ClassNotFoundException, IOException {
		this.setStart(startClientGui);
		numberOfGamers = start.getClient().getPlayers();
		start.getClient().setGuiGame(this);
	}

	public void setRisorse(Portafoglio risorse) {
		setLegno(risorse.getDimRisorse("legno"));
		setPietra(risorse.getDimRisorse("pietra"));
		setServitori(risorse.getDimRisorse("servitori"));
		setMonete(risorse.getDimRisorse("monete"));
	}

	public void setCardsScomunica(TesseraScomunica[] cardsScomunica) {
		cartaScomunica1.setImage(new Image(getClass().getResourceAsStream(cardsScomunica[0].getImage())));
		cartaScomunica2.setImage(new Image(getClass().getResourceAsStream(cardsScomunica[1].getImage())));
		cartaScomunica3.setImage(new Image(getClass().getResourceAsStream(cardsScomunica[2].getImage())));

	}

	public void setColorCubiScomunica(String color) {
		switch (color) {
		case "blue":
			cuboScomunica1.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaBlu.png")));
			cuboScomunica2.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaBlu.png")));
			cuboScomunica3.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaBlu.png")));
			break;
		case "green":
			cuboScomunica1.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaVerde.png")));
			cuboScomunica1.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaVerde.png")));
			cuboScomunica1.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaVerde.png")));
			break;
		case "white":
			cuboScomunica1.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaBianco.png")));
			cuboScomunica1.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaBianco.png")));
			cuboScomunica1.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaBianco.png")));
			break;
		case "orange":
			cuboScomunica1.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaArancio.png")));
			cuboScomunica1.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaArancio.png")));
			cuboScomunica1.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaArancio.png")));
			break;
		}
	}

	public void setColorsParents(String color) throws RemoteException, ClassNotFoundException, IOException {
		puntiVittoriaBlu.setImage(new Image(getClass().getResourceAsStream("Disco1.png")));
		puntiVittoriaBianco.setImage(new Image(getClass().getResourceAsStream("Disco12.png")));
		puntiVittoriaVerde.setImage(new Image(getClass().getResourceAsStream("Disco4.png")));
		puntiVittoriaArancio.setImage(new Image(getClass().getResourceAsStream("Disco3.png")));
		puntiFedeBlu.setImage(new Image(getClass().getResourceAsStream("Disco1.png")));
		puntiFedeArancio.setImage(new Image(getClass().getResourceAsStream("Disco3.png")));
		puntiFedeBianco.setImage(new Image(getClass().getResourceAsStream("Disco12.png")));
		puntiFedeVerde.setImage(new Image(getClass().getResourceAsStream("Disco4.png")));
		puntiMilitariBlu.setImage(new Image(getClass().getResourceAsStream("Disco1.png")));
		puntiMilitariVerde.setImage(new Image(getClass().getResourceAsStream("Disco4.png")));
		puntiMilitariBianco.setImage(new Image(getClass().getResourceAsStream("Disco12.png")));
		puntiMilitariArancio.setImage(new Image(getClass().getResourceAsStream("Disco3.png")));
		Giocatore[] giocatori = null;
		switch (color) {
		case "blue":
			familiareNeutro.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluNeutro.png")));
			familiareNero.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluNero.png")));
			familiareArancio.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluArancio.png")));
			familiareBianco.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluBianco.png")));
			giocatori = new Giocatore[4];
			giocatori = start.getClient().getGiocatori();
			for (Giocatore g : giocatori) {
				if (g != null && g.getName() != start.getClient().getName()) {
					switch (g.getColor()) {
					case "orange":
						familiareOrange1
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareArancioNeutro.png")));
						familiareOrange2.setImage(
								new Image(this.getClass().getResourceAsStream("FamiliareArancioArancio.png")));
						familiareOrange3
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareArancioBianco.png")));
						familiareOrange4
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareArancioNero.png")));
						flag1.setImage(new Image(this.getClass().getResourceAsStream("BandierinaArancio.png")));
						name1.setText(g.getName());
						
						break;
					case "green":
						familiareGreen1
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareVerdeNeutro.png")));
						familiareGreen2
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareVerdeArancio.png")));
						familiareGreen3
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareVerdeBianco.png")));
						familiareGreen4
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareVerdeNero.png")));
						flag2.setImage(new Image(this.getClass().getResourceAsStream("BandierinaVerde.png")));
						name2.setText(g.getName());
						break;
					case "white":
						familiareWhite1
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBiancoNeutro.png")));
						familiareWhite2
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBiancoArancio.png")));
						familiareWhite3
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBiancoBianco.png")));
						familiareWhite4
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBiancoNero.png")));
						flag3.setImage(new Image(this.getClass().getResourceAsStream("BandierinaBianca.png")));
						name3.setText(g.getName());
						break;
					}
				}
			}
			familiareOrange1.setOpacity(1);
			familiareOrange2.setOpacity(1);
			familiareOrange3.setOpacity(1);
			familiareOrange4.setOpacity(1);
			familiareGreen1.setOpacity(1);
			familiareGreen2.setOpacity(1);
			familiareGreen3.setOpacity(1);
			familiareGreen4.setOpacity(1);
			familiareWhite1.setOpacity(1);
			familiareWhite2.setOpacity(1);
			familiareWhite3.setOpacity(1);
			familiareWhite4.setOpacity(1);
			familiareOrange1.setDisable(false);
			familiareOrange2.setDisable(false);
			familiareOrange3.setDisable(false);
			familiareOrange4.setDisable(false);
			familiareGreen1.setDisable(false);
			familiareGreen2.setDisable(false);
			familiareGreen3.setDisable(false);
			familiareGreen4.setDisable(false);
			familiareWhite1.setDisable(false);
			familiareWhite2.setDisable(false);
			familiareWhite3.setDisable(false);
			familiareWhite4.setDisable(false);
			break;
		case "green":
			familiareNeutro.setImage(new Image(this.getClass().getResourceAsStream("FamiliareVerdeNeutro.png")));
			familiareNero.setImage(new Image(this.getClass().getResourceAsStream("FamiliareVerdeNero.png")));
			familiareArancio.setImage(new Image(this.getClass().getResourceAsStream("FamiliareVerdeArancio.png")));
			familiareBianco.setImage(new Image(this.getClass().getResourceAsStream("FamiliareVerdeBianco.png")));
			giocatori = new Giocatore[4];
			giocatori = start.getClient().getGiocatori();
			for (Giocatore g : giocatori) {
				if (g != null && g.getName() != start.getClient().getName()) {
					switch (g.getColor()) {
					case "orange":
						familiareOrange1
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareArancioNeutro.png")));
						familiareOrange2.setImage(
								new Image(this.getClass().getResourceAsStream("FamiliareArancioArancio.png")));
						familiareOrange3
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareArancioBianco.png")));
						familiareOrange4
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareArancioNero.png")));
						flag1.setImage(new Image(this.getClass().getResourceAsStream("BandierinaArancio.png")));
						name1.setText(g.getName());
						break;
					case "green":
						familiareBlue1
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluNeutro.png")));
						familiareBlue2
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluArancio.png")));
						familiareBlue3
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluBianco.png")));
						familiareBlue4.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluNero.png")));
						flag2.setImage(new Image(this.getClass().getResourceAsStream("BandierinaBlue.png")));
						name2.setText(g.getName());
						break;
					case "white":
						familiareWhite1
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBiancoNeutro.png")));
						familiareWhite2
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBiancoArancio.png")));
						familiareWhite3
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBiancoBianco.png")));
						familiareWhite4
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBiancoNero.png")));
						flag3.setImage(new Image(this.getClass().getResourceAsStream("BandierinaBianca.png")));
						name3.setText(g.getName());
						break;
					}
				}
			}
			familiareOrange1.setOpacity(1);
			familiareOrange2.setOpacity(1);
			familiareOrange3.setOpacity(1);
			familiareOrange4.setOpacity(1);
			familiareBlue1.setOpacity(1);
			familiareBlue2.setOpacity(1);
			familiareBlue3.setOpacity(1);
			familiareBlue4.setOpacity(1);
			familiareWhite1.setOpacity(1);
			familiareWhite2.setOpacity(1);
			familiareWhite3.setOpacity(1);
			familiareWhite4.setOpacity(1);
			familiareOrange1.setDisable(false);
			familiareOrange2.setDisable(false);
			familiareOrange3.setDisable(false);
			familiareOrange4.setDisable(false);
			familiareBlue1.setDisable(false);
			familiareBlue2.setDisable(false);
			familiareBlue3.setDisable(false);
			familiareBlue4.setDisable(false);
			familiareWhite1.setDisable(false);
			familiareWhite2.setDisable(false);
			familiareWhite3.setDisable(false);
			familiareWhite4.setDisable(false);
			break;
		case "white":
			familiareNeutro.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBiancoNeutro.png")));
			familiareNero.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBiancoNero.png")));
			familiareArancio.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBiancoArancio.png")));
			familiareBianco.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBiancoBianco.png")));
			for (Giocatore g : giocatori) {
				if (g != null && g.getName() != start.getClient().getName()) {
					switch (g.getColor()) {
					case "orange":
						familiareOrange1
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareArancioNeutro.png")));
						familiareOrange2.setImage(
								new Image(this.getClass().getResourceAsStream("FamiliareArancioArancio.png")));
						familiareOrange3
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareArancioBianco.png")));
						familiareOrange4
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareArancioNero.png")));
						flag1.setImage(new Image(this.getClass().getResourceAsStream("BandierinaArancio.png")));
						name1.setText(g.getName());
						break;
					case "green":
						familiareGreen1
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareVerdeNeutro.png")));
						familiareGreen2
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareVerdeArancio.png")));
						familiareGreen3
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareVerdeBianco.png")));
						familiareGreen4
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareVerdeNero.png")));
						flag2.setImage(new Image(this.getClass().getResourceAsStream("BandierinaVerde.png")));
						name2.setText(g.getName());
						break;
					case "white":
						familiareWhite1
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluNeutro.png")));
						familiareWhite2
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluArancio.png")));
						familiareWhite3
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluBianco.png")));
						familiareWhite4
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluNero.png")));
						flag3.setImage(new Image(this.getClass().getResourceAsStream("BandierinaBlue.png")));
						name3.setText(g.getName());
						break;
					}
				}
			}
			familiareOrange1.setOpacity(1);
			familiareOrange2.setOpacity(1);
			familiareOrange3.setOpacity(1);
			familiareOrange4.setOpacity(1);
			familiareGreen1.setOpacity(1);
			familiareGreen2.setOpacity(1);
			familiareGreen3.setOpacity(1);
			familiareGreen4.setOpacity(1);
			familiareBlue1.setOpacity(1);
			familiareBlue1.setOpacity(1);
			familiareBlue1.setOpacity(1);
			familiareBlue1.setOpacity(1);
			familiareOrange1.setDisable(false);
			familiareOrange2.setDisable(false);
			familiareOrange3.setDisable(false);
			familiareOrange4.setDisable(false);
			familiareGreen1.setDisable(false);
			familiareGreen2.setDisable(false);
			familiareGreen3.setDisable(false);
			familiareGreen4.setDisable(false);
			familiareBlue1.setDisable(false);
			familiareBlue1.setDisable(false);
			familiareBlue1.setDisable(false);
			familiareBlue1.setDisable(false);
			break;
		case "orange":
			familiareNeutro.setImage(new Image(this.getClass().getResourceAsStream("FamiliareArancioNeutro.png")));
			familiareNero.setImage(new Image(this.getClass().getResourceAsStream("FamiliareArancioNero.png")));
			familiareArancio.setImage(new Image(this.getClass().getResourceAsStream("FamiliareArancioArancio.png")));
			familiareBianco.setImage(new Image(this.getClass().getResourceAsStream("FamiliareArancioBianco.png")));
			familiareBlue1.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluNeutro.png")));
			for (Giocatore g : giocatori) {
				if (g != null && g.getName() != start.getClient().getName()) {
					switch (g.getColor()) {
					case "orange":
						familiareOrange1
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluNeutro.png")));
						familiareOrange2
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluArancio.png")));
						familiareOrange3
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluBianco.png")));
						familiareOrange4
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareVerdeArancio.png")));
						flag1.setImage(new Image(this.getClass().getResourceAsStream("BandierinaBlue.png")));
						name1.setText(g.getName());
						break;
					case "green":
						familiareGreen1
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareVerdeNeutro.png")));
						familiareGreen2
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareVerdeArancio.png")));
						familiareGreen3
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareVerdeBianco.png")));
						familiareGreen4
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareVerdeNero.png")));
						flag2.setImage(new Image(this.getClass().getResourceAsStream("BandierinaVerde.png")));
						name2.setText(g.getName());
						break;
					case "white":
						familiareWhite1
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBiancoNeutro.png")));
						familiareWhite2
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBiancoNeutro.png")));
						familiareWhite3
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBiancoBianco.png")));
						familiareWhite4
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBiancoNero.png")));
						flag3.setImage(new Image(this.getClass().getResourceAsStream("BandierinaBianca.png")));
						name3.setText(g.getName());
						break;
					}
				}
			}
			familiareBlue1.setOpacity(1);
			familiareBlue2.setOpacity(1);
			familiareBlue3.setOpacity(1);
			familiareBlue4.setOpacity(1);
			familiareGreen1.setOpacity(1);
			familiareGreen2.setOpacity(1);
			familiareGreen3.setOpacity(1);
			familiareGreen4.setOpacity(1);
			familiareWhite1.setOpacity(1);
			familiareWhite2.setOpacity(1);
			familiareWhite3.setOpacity(1);
			familiareWhite4.setOpacity(1);
			familiareBlue1.setDisable(false);
			familiareBlue2.setDisable(false);
			familiareBlue3.setDisable(false);
			familiareBlue4.setDisable(false);
			familiareGreen1.setDisable(false);
			familiareGreen2.setDisable(false);
			familiareGreen3.setDisable(false);
			familiareGreen4.setDisable(false);
			familiareWhite1.setDisable(false);
			familiareWhite2.setDisable(false);
			familiareWhite3.setDisable(false);
			familiareWhite4.setDisable(false);
			break;
		}
	}

	private void setStart(StartClientGui start) {
		this.start = start;
	}

	public StartClientGui getStart() {
		return start;
	}

	public void setBandiera(Image bandiera) {
		this.bandiera.setImage(bandiera);
	}

	public void setPietra(int pietra) {
		this.pietra.setText(Integer.toString(pietra));
	}

	public void setMonete(int monete) {
		this.monete.setText(Integer.toString(monete));
	}

	public void setServitori(int servitori) {
		this.servitori.setText(Integer.toString(servitori));
	}

	public void setLegno(int legno) {
		this.lengo.setText(Integer.toString(legno));
	}

	/*
	 * Controllo per verificare se si ha un numero di punti del dado
	 */
	public boolean controlloPosizionamento(String color, double x, double y, int addRisorse) {
		try {
			if (controlCard(x, y)) {
				String mom = null;
				try {
					mom = start.getClient().controlloPosizionamento(color, x, y, addRisorse);
				} catch (ClassNotFoundException | IOException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (mom.equals("Pay")) {
					Stage popup = new Stage();
					popup.setTitle("Pay or not Pay");
					VBox box = new VBox();
					HBox buttonBox = new HBox();
					Label title = new Label(
							"Non potresti posizionare qui il tuo familiare, a meno che non paghi qualche servitore\nVuoi pagare?\nQuanto?");
					TextField text = new TextField();
					Button bOk = new Button("OK");
					Button bCancel = new Button("Cancel");
					buttonBox.getChildren().addAll(bOk, bCancel);
					box.getChildren().addAll(title, text, buttonBox);
					bOk.setOnAction(event -> {
						controlloPosizionamento(color, x, y, Integer.getInteger(text.getText()));
						popup.close();
					});
					bCancel.setOnAction(event -> {
						/*
						 * switch (color) { case "neutro":
						 * familiareNeutro.setImage(new
						 * Image(getClass().getResourceAsStream(""))); break;
						 * case "black": familiareNero.setImage(new
						 * Image(getClass().getResourceAsStream(""))); break;
						 * case "orange": familiareArancio.setImage(new
						 * Image(getClass().getResourceAsStream(""))); break;
						 * case "white": familiareBianco.setImage(new
						 * Image(getClass().getResourceAsStream(""))); break;
						 */
						popup.close();
					});
					Scene scene = new Scene(box, 600, 400);
					popup.centerOnScreen();
					popup.setScene(scene);
					popup.show();
				} else if (mom == null) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.initOwner(start.getStage());
					alert.setTitle("Lost DB connection");
					alert.setContentText("Ci dispiace ma il nostro servizio a smesso di funzionare");
					alert.showAndWait();
					return false;
				} else if (mom.equals("OK"))
					return true;
				else if (mom.equals("NotEnough")) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.initOwner(start.getStage());
					alert.setTitle("Mossa negata");
					alert.setContentText("Non fare il furbo!! Non hai abbastanza servitori!");
					alert.showAndWait();
					return false;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public void moveFamAvv(String colorAvv, String colorFamm, double x, double y) {
		switch (colorAvv) {
		case "blue":
			switch (colorFamm) {
			case "white":
				familiareBlue1.setX(x);
				familiareBlue1.setY(y);
				break;
			case "black":
				familiareBlue2.setX(x);
				familiareBlue2.setY(y);
				break;
			case "orange":
				familiareBlue3.setX(x);
				familiareBlue3.setY(y);
				break;
			case "neutro":
				familiareBlue4.setX(x);
				familiareBlue4.setY(y);
				break;
			}
			break;
		case "white":
			switch (colorFamm) {
			case "white":
				familiareWhite1.setX(x);
				familiareWhite1.setY(y);
				break;
			case "black":
				familiareWhite2.setX(x);
				familiareWhite2.setY(y);
				break;
			case "orange":
				familiareWhite3.setX(x);
				familiareWhite3.setY(y);
				break;
			case "neutro":
				familiareWhite4.setX(x);
				familiareWhite4.setY(y);
				break;
			}
			break;
		case "orange":
			switch (colorFamm) {
			case "white":
				familiareOrange1.setX(x);
				familiareOrange1.setY(y);
				break;
			case "black":
				familiareOrange2.setX(x);
				familiareOrange2.setY(y);
				break;
			case "orange":
				familiareOrange3.setX(x);
				familiareOrange3.setY(y);
				break;
			case "neutro":
				familiareOrange4.setX(x);
				familiareOrange4.setY(y);
				break;
			}
			break;
		case "green":
			switch (colorFamm) {
			case "white":
				familiareGreen1.setX(x);
				familiareGreen1.setY(y);
				break;
			case "black":
				familiareGreen2.setX(x);
				familiareGreen2.setY(y);
				break;
			case "orange":
				familiareGreen3.setX(x);
				familiareGreen3.setY(y);
				break;
			case "neutro":
				familiareGreen4.setX(x);
				familiareGreen4.setY(y);
				break;
			}
			break;
		}
	}

	public void addScomunica(int nScomuniche, Tooltip tooltip) {
		switch (nScomuniche) {
		case 0:
			cuboScomunica1.setVisible(true);
			Tooltip.install(cuboScomunica1, tooltip);
			break;
		case 1:
			cuboScomunica2.setVisible(true);
			Tooltip.install(cuboScomunica2, tooltip);
			break;
		case 2:
			cuboScomunica2.setVisible(true);
			Tooltip.install(cuboScomunica2, tooltip);
			break;
		}
	}

	public boolean controlCard(double x, double y) throws IOException {
		Portafoglio p = null;
		try {
			p = start.getClient().getRisorse();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch (getNamePosition(x, y)) {
		case "PIANO 1 CARTE EDIFICI":
			if (arrayCarteEdifici[0].getCostoLegno() < p.getDimRisorse("legno")
					&& arrayCarteEdifici[0].getCostoMoneta() < p.getDimRisorse("monete")
					&& arrayCarteEdifici[0].getCostoPietra() < p.getDimRisorse("pietra")
					&& arrayCarteEdifici[0].getCostoServitori() < p.getDimRisorse("servitori"))
				return false;
			return true;
		case "PIANO 2 CARTE EDIFICI":
			if (arrayCarteEdifici[1].getCostoLegno() < p.getDimRisorse("legno")
					&& arrayCarteEdifici[1].getCostoMoneta() < p.getDimRisorse("monete")
					&& arrayCarteEdifici[1].getCostoPietra() < p.getDimRisorse("pietra")
					&& arrayCarteEdifici[1].getCostoServitori() < p.getDimRisorse("servitori"))
				return false;
			return true;
		case "PIANO 3 CARTE EDIFICI":
			if (arrayCarteEdifici[2].getCostoLegno() < p.getDimRisorse("legno")
					&& arrayCarteEdifici[2].getCostoMoneta() < p.getDimRisorse("monete")
					&& arrayCarteEdifici[2].getCostoPietra() < p.getDimRisorse("pietra")
					&& arrayCarteEdifici[2].getCostoServitori() < p.getDimRisorse("servitori"))
				return false;
			return true;
		case "PIANO 4 CARTE EDIFICI":
			if (arrayCarteEdifici[3].getCostoLegno() < p.getDimRisorse("legno")
					&& arrayCarteEdifici[3].getCostoMoneta() < p.getDimRisorse("monete")
					&& arrayCarteEdifici[3].getCostoPietra() < p.getDimRisorse("pietra")
					&& arrayCarteEdifici[3].getCostoServitori() < p.getDimRisorse("servitori"))
				return false;
			return true;
		case "PIANO 1 CARTE IMPRESE":
			if ((arrayCarteImpresa[0].getCostoLegno() < p.getDimRisorse("legno")
					&& arrayCarteImpresa[0].getCostoMoneta() < p.getDimRisorse("monete")
					&& arrayCarteImpresa[0].getCostoPietra() < p.getDimRisorse("pietra")
					&& arrayCarteImpresa[0].getCostoServitori() < p.getDimRisorse("servitori"))
					|| (arrayCarteImpresa[0].getPuntiMilitariRichiesti() < p.getPunti("militari")))
				return false;
			return true;
		case "PIANO 2 CARTE IMPRESE":
			if ((arrayCarteImpresa[1].getCostoLegno() < p.getDimRisorse("legno")
					&& arrayCarteImpresa[1].getCostoMoneta() < p.getDimRisorse("monete")
					&& arrayCarteImpresa[1].getCostoPietra() < p.getDimRisorse("pietra")
					&& arrayCarteImpresa[1].getCostoServitori() < p.getDimRisorse("servitori"))
					|| (arrayCarteImpresa[1].getPuntiMilitariRichiesti() < p.getPunti("militari")))
				return false;
			return true;
		case "PIANO 3 CARTE IMPRESE":
			if ((arrayCarteImpresa[2].getCostoLegno() < p.getDimRisorse("legno")
					&& arrayCarteImpresa[2].getCostoMoneta() < p.getDimRisorse("monete")
					&& arrayCarteImpresa[2].getCostoPietra() < p.getDimRisorse("pietra")
					&& arrayCarteImpresa[2].getCostoServitori() < p.getDimRisorse("servitori"))
					|| (arrayCarteImpresa[2].getPuntiMilitariRichiesti() < p.getPunti("militari")))
				return false;
			return true;
		case "PIANO 4 CARTE IMPRESE":
			if ((arrayCarteImpresa[3].getCostoLegno() < p.getDimRisorse("legno")
					&& arrayCarteImpresa[3].getCostoMoneta() < p.getDimRisorse("monete")
					&& arrayCarteImpresa[3].getCostoPietra() < p.getDimRisorse("pietra")
					&& arrayCarteImpresa[3].getCostoServitori() < p.getDimRisorse("servitori"))
					|| (arrayCarteImpresa[3].getPuntiMilitariRichiesti() < p.getPunti("militari")))
				return false;
			return true;
		case "PIANO 1 CARTE PERSONAGGI":
			if (arrayCartePersonaggi[0].getCostoMoneta() < p.getDimRisorse("monete"))
				return false;
			return true;
		case "PIANO 2 CARTE PERSONAGGI":
			if (arrayCartePersonaggi[1].getCostoMoneta() < p.getDimRisorse("monete"))
				return false;
			return true;
		case "PIANO 3 CARTE PERSONAGGI":
			if (arrayCartePersonaggi[2].getCostoMoneta() < p.getDimRisorse("monete"))
				return false;
			return true;
		case "PIANO 4 CARTE PERSONAGGI":
			if (arrayCartePersonaggi[3].getCostoMoneta() < p.getDimRisorse("monete"))
				return false;
			return true;
		}
		return true;
	}

	public void setCards(CartaSviluppo[] carte) {
		for (int i = 0; i < 4; i++) {
			arrayCarteTerritori[i] = new CartaTerritori();
			arrayCarteTerritori[i] = (CartaTerritori) carte[i];
			ImageView mom = new ImageView();
			System.out.println(carte[i].getImage());
			mom.setImage(new Image(getClass().getResourceAsStream(carte[i].getImage())));
			Tooltip.install(mom, carte[i].getTooltip());
			carteTerritori.getChildren().add(mom);
		}
		for (int i = 0; i < 4; i++) {
			arrayCartePersonaggi[i] = new CartaPersonaggi();
			arrayCartePersonaggi[i] = (CartaPersonaggi) carte[i + 4];
			ImageView mom = new ImageView();
			mom.setImage(new Image(getClass().getResourceAsStream(carte[i + 4].getImage())));
			Tooltip.install(mom, carte[i + 4].getTooltip());
			cartePersonaggi.getChildren().add(mom);
		}
		for (int i = 0; i < 4; i++) {
			arrayCarteEdifici[i] = new CartaEdifici();
			arrayCarteEdifici[i] = (CartaEdifici) carte[i + 8];
			ImageView mom = new ImageView();
			mom.setImage(new Image(getClass().getResourceAsStream(carte[i + 8].getImage())));
			Tooltip.install(mom, carte[i + 8].getTooltip());
			carteEdifici.getChildren().add(mom);
		}
		for (int i = 0; i < 4; i++) {
			arrayCarteImpresa[i] = new CartaImprese();
			arrayCarteImpresa[i] = (CartaImprese) carte[i + 12];
			ImageView mom = new ImageView();
			mom.setImage(new Image(getClass().getResourceAsStream(carte[i + 12].getImage())));
			Tooltip.install(mom, carte[i + 12].getTooltip());
			carteImprese.getChildren().add(mom);
		}
	}

	public void notifySpostamento(String color, double x, double y) throws IOException {
		start.getClient().notifySpostamento(color, x, y);
	}

	public String getNamePosition(double x, double y) throws IOException {
		try {
			return start.getClient().getNamePosition(x, y);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public void setCardGiocatore(String namePosition) throws IOException {
		switch (namePosition) {
		case "PIANO 1 CARTE TERRITORI":
			carteTerritoriGiocatore.getChildren().add(carteTerritori.getChildren().get(0));
			Tooltip.install(carteTerritoriGiocatore.getChildren().get(0), arrayCarteTerritori[0].getTooltip());
			carteTerritori.getChildren().get(0).setOpacity(0);
			start.getClient().setCardGiocatore(arrayCarteTerritori[0], 0);
			break;
		case "PIANO 2 CARTE TERRITORI":
			carteTerritoriGiocatore.getChildren().add(carteTerritori.getChildren().get(1));
			Tooltip.install(carteTerritoriGiocatore.getChildren().get(1), arrayCarteTerritori[1].getTooltip());
			carteTerritori.getChildren().set(1, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCarteTerritori[1], 0);
			break;
		case "PIANO 3 CARTE TERRITORI":
			carteTerritoriGiocatore.getChildren().add(carteTerritori.getChildren().get(2));
			Tooltip.install(carteTerritoriGiocatore.getChildren().get(2), arrayCarteTerritori[2].getTooltip());
			carteTerritori.getChildren().set(2, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCarteTerritori[2], 0);
			break;
		case "PIANO 4 CARTE TERRITORI":
			carteTerritoriGiocatore.getChildren().add(carteTerritori.getChildren().get(3));
			Tooltip.install(carteTerritoriGiocatore.getChildren().get(3), arrayCarteTerritori[3].getTooltip());
			carteTerritori.getChildren().set(3, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCarteTerritori[3], 0);
			break;
		case "PIANO 1 CARTE EDIFICI":
			carteEdificiGiocatore.getChildren().add(carteEdifici.getChildren().get(0));
			Tooltip.install(carteEdificiGiocatore.getChildren().get(0), arrayCarteEdifici[0].getTooltip());
			carteEdifici.getChildren().set(0, new ImageView(new Image(getClass().getResourceAsStream(""))));

			start.getClient().setCardGiocatore(arrayCarteEdifici[0], 3);
			break;
		case "PIANO 2 CARTE EDIFICI":
			carteEdificiGiocatore.getChildren().add(carteEdifici.getChildren().get(1));
			Tooltip.install(carteEdificiGiocatore.getChildren().get(1), arrayCarteEdifici[1].getTooltip());
			carteEdifici.getChildren().set(1, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCarteEdifici[1], 3);
			break;
		case "PIANO 3 CARTE EDIFICI":
			carteEdificiGiocatore.getChildren().add(carteEdifici.getChildren().get(2));
			Tooltip.install(carteEdificiGiocatore.getChildren().get(2), arrayCarteEdifici[2].getTooltip());
			carteEdifici.getChildren().set(2, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCarteEdifici[2], 3);
			break;
		case "PIANO 4 CARTE EDIFICI":
			carteEdificiGiocatore.getChildren().add(carteEdifici.getChildren().get(3));
			Tooltip.install(carteEdificiGiocatore.getChildren().get(3), arrayCarteEdifici[3].getTooltip());
			carteEdifici.getChildren().set(3, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCarteEdifici[3], 3);
			break;
		case "PIANO 1 CARTE IMPRESE":
			carteImpresaGiocatore.getChildren().add(carteImprese.getChildren().get(0));
			Tooltip.install(carteImpresaGiocatore.getChildren().get(0), arrayCarteImpresa[0].getTooltip());
			carteImprese.getChildren().set(0, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCarteImpresa[0], 4);
			break;
		case "PIANO 2 CARTE IMPRESE":
			carteImpresaGiocatore.getChildren().add(carteImprese.getChildren().get(1));
			Tooltip.install(carteImpresaGiocatore.getChildren().get(1), arrayCarteImpresa[1].getTooltip());
			carteImprese.getChildren().set(1, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCarteImpresa[1], 4);
			break;
		case "PIANO 3 CARTE IMPRESE":
			carteImpresaGiocatore.getChildren().add(carteImprese.getChildren().get(2));
			Tooltip.install(carteImpresaGiocatore.getChildren().get(2), arrayCarteImpresa[2].getTooltip());
			carteImprese.getChildren().set(2, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCarteImpresa[2], 4);
			break;
		case "PIANO 4 CARTE IMPRESE":
			carteImpresaGiocatore.getChildren().add(carteImprese.getChildren().get(3));
			Tooltip.install(carteImpresaGiocatore.getChildren().get(3), arrayCarteImpresa[3].getTooltip());
			carteImprese.getChildren().set(3, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCarteImpresa[3], 4);
			break;
		case "PIANO 1 CARTE PERSONAGGI":
			cartePersonaggiGiocatore.getChildren().add(cartePersonaggi.getChildren().get(0));
			Tooltip.install(cartePersonaggiGiocatore.getChildren().get(0), arrayCartePersonaggi[0].getTooltip());
			cartePersonaggi.getChildren().set(0, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCartePersonaggi[0], 1);
			break;
		case "PIANO 2 CARTE PERSONAGGI":
			cartePersonaggiGiocatore.getChildren().add(cartePersonaggi.getChildren().get(1));
			Tooltip.install(cartePersonaggiGiocatore.getChildren().get(1), arrayCartePersonaggi[1].getTooltip());
			cartePersonaggi.getChildren().set(1, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCartePersonaggi[1], 1);
			break;
		case "PIANO 3 CARTE PERSONAGGI":
			cartePersonaggiGiocatore.getChildren().add(cartePersonaggi.getChildren().get(2));
			Tooltip.install(cartePersonaggiGiocatore.getChildren().get(2), arrayCartePersonaggi[2].getTooltip());
			cartePersonaggi.getChildren().set(2, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCartePersonaggi[2], 1);
			break;
		case "PIANO 4 CARTE PERSONAGGI":
			cartePersonaggiGiocatore.getChildren().add(cartePersonaggi.getChildren().get(3));
			Tooltip.install(cartePersonaggiGiocatore.getChildren().get(3), arrayCartePersonaggi[3].getTooltip());
			cartePersonaggi.getChildren().set(3, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCartePersonaggi[3], 1);
			break;
		case "AZIONE PRODUZIONE 4":
			start.getClient().produzione(-3);
			break;
		case "AZIONE PRODUZIONE 1":
			start.getClient().produzione(0);
			break;
		case "AZIONE RACCOLTO 4":
			start.getClient().raccolto(-3);
			break;
		case "AZIONE RACCOLTO 1":
			start.getClient().raccolto(0);
			break;
		case "PRENDI 5 MONETE":
			try {
				start.getClient().addRisorse("monete",5);
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			break;
		case "PRENDI 5 SERVITORI":
			try {
				start.getClient().addRisorse("servitori",5);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case "PRENDI 2 MONETE E 3 MILITARI":
			try {
				start.getClient().addRisorse("monete",2);
				start.getClient().addPunti("militari",5);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case "PRENDI 2 PERGAMENE":
			start.getClient().addPergamene(2);
			break;
		case "ZONA MERCATO":
			break;
		}
	}

	public void enableGame() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(start.getStage());
		alert.setTitle("Notifica Turno");
		alert.setContentText("Hei, e' iniziato il tuo turno lancia i dadi e fai le tue mosse");
		alert.showAndWait();
		lanciaDadi.setDisable(false);
	}

	public void resetTabellon() throws ClassNotFoundException, IOException {
		try {
			setCards(start.getClient().getCardsGame());
			azioniTerritoridapiuGiocatori = new HBox();
			azioniEdificidapiuGiocatori = new HBox();
			municipio = new HBox();
			setPosizioni();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		start.getClient().waitTurno();
	}

	private void setPosizioni() {
		// TODO Auto-generated method stub
		
	}

	@FXML
	public void lanciaDadi() throws RemoteException, SQLException {
		Dado[] dadi = new Dado[3];
		try {
			dadi = start.getClient().lanciaDadi();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dadoNero.setImage(new Image(getClass().getResourceAsStream(dadi[0].getImage())));
		dadoBianco.setImage(new Image(getClass().getResourceAsStream(dadi[1].getImage())));
		dadoArancio.setImage(new Image(getClass().getResourceAsStream(dadi[2].getImage())));
		familiareNeutro.setDisable(false);
		familiareNero.setDisable(false);
		familiareArancio.setDisable(false);
		familiareBianco.setDisable(false);
	}

	public void notifyAddCardAvv(CartaSviluppo carta, String nameAvv, Portafoglio portafoglio) {
		if (nameAvv.equals(name1.getText())) {
			for (int i = 0; i < 4; i++) {
				if (arrayCarteTerritori[i].equals(carta)) {
					carteTerritori.getChildren().get(i).setClip(null);
					// aggiungere alla lista delle carte dell'avversari
					// aggiornare le label delle risorse del giocatore
				}
			}
		} else if (nameAvv.equals(name2.getText())) {

		} else if (nameAvv.equals(name3.getText())) {

		}
	}

	public void parentsProperties() {
		familiareNeutro.setOnDragDetected(e -> {
			System.out.println("DragDetected");
			if (familiareNeutro.getImage() == null) {
				return;
			}
			Dragboard drag = familiareNeutro.startDragAndDrop(TransferMode.MOVE);
			ClipboardContent content = new ClipboardContent();
			content.putImage(familiareNeutro.getImage());
			drag.setDragView(familiareNeutro.getImage());
			drag.setContent(content);
			e.consume();
		});

		familiareNero.setOnDragDetected(e -> {
			System.out.println("DragDetected");
			if (familiareNero.getImage() == null) {
				return;
			}
			Dragboard drag = familiareNero.startDragAndDrop(TransferMode.MOVE);
			ClipboardContent content = new ClipboardContent();
			content.putImage(familiareNero.getImage());
			drag.setDragView(familiareNero.getImage());
			drag.setContent(content);
			e.consume();
		});

		familiareBianco.setOnDragDetected(e -> {
			System.out.println("DragDetected");
			if (familiareBianco.getImage() == null) {
				return;
			}
			Dragboard drag = familiareBianco.startDragAndDrop(TransferMode.MOVE);
			ClipboardContent content = new ClipboardContent();
			content.putImage(familiareBianco.getImage());
			drag.setDragView(familiareBianco.getImage());
			drag.setContent(content);
			e.consume();
		});

		familiareArancio.setOnDragDetected(e -> {
			System.out.println("DragDetected");
			if (familiareArancio.getImage() == null) {
				return;
			}
			Dragboard drag = familiareArancio.startDragAndDrop(TransferMode.MOVE);
			ClipboardContent content = new ClipboardContent();
			content.putImage(familiareArancio.getImage());
			drag.setDragView(familiareArancio.getImage());
			drag.setContent(content);
			e.consume();
		});

		familiareNeutro.setOnDragOver(event -> {
			System.out.println("setOnDragOver");
			if (!event.isDropCompleted() && event.getDragboard().hasString()) {
				event.acceptTransferModes(TransferMode.MOVE);
			}

			event.consume();
		});

		familiareArancio.setOnDragOver(event -> {
			System.out.println("setOnDragOver");
			if (!event.isDropCompleted() && event.getDragboard().hasString()) {
				event.acceptTransferModes(TransferMode.MOVE);
			}

			event.consume();
		});

		familiareBianco.setOnDragOver(event -> {
			System.out.println("setOnDragOver");
			if (!event.isDropCompleted() && event.getDragboard().hasString()) {
				event.acceptTransferModes(TransferMode.MOVE);
			}

			event.consume();
		});

		familiareNero.setOnDragOver(event -> {
			System.out.println("setOnDragOver");
			if (!event.isDropCompleted() && event.getDragboard().hasString()) {
				event.acceptTransferModes(TransferMode.MOVE);
			}
			event.consume();
		});

		familiareBianco.setOnDragEntered(event -> {
			System.out.println("setOnDragEntered");
			if (!event.isDropCompleted() && event.getDragboard().hasString()) {
				familiareBianco.setOpacity(0.3);
			}
		});

		familiareArancio.setOnDragEntered(event -> {
			System.out.println("setOnDragEntered");
			if (!event.isDropCompleted() && event.getDragboard().hasString()) {
				familiareArancio.setOpacity(0.3);
			}
		});
		familiareNero.setOnDragEntered(event -> {
			System.out.println("setOnDragEntered");
			if (!event.isDropCompleted() && event.getDragboard().hasString()) {
				familiareNero.setOpacity(0.3);
			}
		});
		familiareNeutro.setOnDragEntered(event -> {
			System.out.println("setOnDragEntered");
			if (!event.isDropCompleted() && event.getDragboard().hasString()) {
				familiareNeutro.setOpacity(0.3);
			}
		});

		familiareNeutro.setOnDragExited(event -> {
			System.out.println("setOnDragExited");
			if (!event.isDropCompleted() && event.getDragboard().hasString()) {
				familiareNeutro.setOpacity(1);
			}
		});

		familiareNero.setOnDragExited(event -> {
			System.out.println("setOnDragExited");
			if (!event.isDropCompleted() && event.getDragboard().hasString()) {
				familiareNero.setOpacity(1);
			}
		});

		familiareArancio.setOnDragExited(event -> {
			System.out.println("setOnDragExited");
			if (!event.isDropCompleted() && event.getDragboard().hasString()) {
				familiareArancio.setOpacity(1);
			}
		});

		familiareBianco.setOnDragExited(event -> {
			System.out.println("setOnDragExited");
			if (!event.isDropCompleted() && event.getDragboard().hasString()) {
				familiareBianco.setOpacity(1);
			}
		});

		familiareBianco.setOnDragDropped(event -> {
			System.out.println("setOnDragDropped");
			if (familiareBianco.getImage() == null) {
				return;
			}
			Dragboard drag = event.getDragboard();
			boolean success = false;

			if (drag.hasImage()) {
				success = true;
			}

			event.setDropCompleted(success);
			event.consume();

		});

		familiareNero.setOnDragDropped(event -> {
			System.out.println("setOnDragDropped");
			if (familiareNero.getImage() == null) {
				return;
			}
			Dragboard drag = event.getDragboard();
			boolean success = false;

			if (drag.hasImage()) {
				success = true;
			}

			event.setDropCompleted(success);
			event.consume();

		});

		familiareArancio.setOnDragDropped(event -> {
			System.out.println("setOnDragDropped");
			if (familiareArancio.getImage() == null) {
				return;
			}
			Dragboard drag = event.getDragboard();
			boolean success = false;

			if (drag.hasImage()) {
				success = true;
			}

			event.setDropCompleted(success);
			event.consume();

		});

		familiareNeutro.setOnDragDropped(event -> {
			System.out.println("setOnDragDropped");
			if (familiareNeutro.getImage() == null) {
				return;
			}
			Dragboard drag = event.getDragboard();
			boolean success = false;

			if (drag.hasImage()) {
				success = true;
			}

			event.setDropCompleted(success);
			event.consume();

		});

		familiareNeutro.setOnDragDone(event -> {
			if (flag) {
				if (controlloPosizionamento(start.getColor(), destinazione1.getLayoutX(), destinazione1.getLayoutY(),
						0)) {
					destinazione1.setImage(familiareNeutro.getImage());
					familiareNeutro.setDisable(true);
					familiareNeutro.setOpacity(0);
					try {
						setCardGiocatore(start.getClient().getNamePosition(destinazione1.getLayoutX(),
								destinazione1.getLayoutY()));
					} catch (IOException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			} else {
				if (controlloPosizionamento(start.getColor(), destinazione2.getLayoutX(), destinazione2.getLayoutX(),
						0)) {
					ImageView mom = new ImageView(familiareNeutro.getImage());
					mom.setFitWidth(35);
					mom.setFitHeight(38);
					destinazione2.getChildren().add(mom);
					familiareNeutro.setDisable(true);
					familiareNeutro.setOpacity(0);
					try {
						setCardGiocatore(start.getClient().getNamePosition(destinazione2.getLayoutX(),
								destinazione2.getLayoutY()));
					} catch (IOException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

		familiareNero.setOnDragDone(event -> {
			if (flag) {
				if (controlloPosizionamento(start.getColor(), destinazione1.getLayoutX(), destinazione1.getLayoutY(),
						0)) {
					destinazione1.setImage(familiareNero.getImage());
					familiareNero.setDisable(true);
					familiareNero.setOpacity(0);
					try {
						setCardGiocatore(start.getClient().getNamePosition(destinazione1.getLayoutX(),
								destinazione1.getLayoutY()));
					} catch (IOException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			} else {
				if (controlloPosizionamento(start.getColor(), destinazione2.getLayoutX(), destinazione2.getLayoutX(),
						0)) {
					ImageView mom = new ImageView(familiareNero.getImage());
					mom.setFitWidth(35);
					mom.setFitHeight(38);
					destinazione2.getChildren().add(mom);
					familiareNero.setDisable(true);
					familiareNero.setOpacity(0);
					try {
						setCardGiocatore(start.getClient().getNamePosition(destinazione2.getLayoutX(),
								destinazione2.getLayoutY()));
					} catch (IOException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

		familiareArancio.setOnDragDone(event -> {
			if (flag) {
				if (controlloPosizionamento(start.getColor(), destinazione1.getLayoutX(), destinazione1.getLayoutY(),
						0)) {
					destinazione1.setImage(familiareArancio.getImage());
					familiareArancio.setDisable(true);
					familiareArancio.setOpacity(0);
					try {
						setCardGiocatore(start.getClient().getNamePosition(destinazione1.getLayoutX(),
								destinazione1.getLayoutY()));
					} catch (IOException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			} else {
				if (controlloPosizionamento(start.getColor(), destinazione2.getLayoutX(), destinazione2.getLayoutX(),
						0)) {
					ImageView mom = new ImageView(familiareArancio.getImage());
					mom.setFitWidth(35);
					mom.setFitHeight(38);
					destinazione2.getChildren().add(mom);
					familiareArancio.setDisable(true);
					familiareArancio.setOpacity(0);
					try {
						setCardGiocatore(start.getClient().getNamePosition(destinazione2.getLayoutX(),
								destinazione2.getLayoutY()));
					} catch (IOException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

		familiareBianco.setOnDragDone(event -> {
			if (flag) {
				if (controlloPosizionamento(start.getColor(), destinazione1.getLayoutX(), destinazione1.getLayoutY(),
						0)) {
					destinazione1.setImage(familiareBianco.getImage());
					familiareBianco.setDisable(true);
					familiareBianco.setOpacity(0);
					try {
						setCardGiocatore(start.getClient().getNamePosition(destinazione1.getLayoutX(),
								destinazione1.getLayoutY()));
					} catch (IOException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			} else {
				if (controlloPosizionamento(start.getColor(), destinazione2.getLayoutX(), destinazione2.getLayoutX(),
						0)) {
					ImageView mom = new ImageView(familiareBianco.getImage());
					mom.setFitWidth(35);
					mom.setFitHeight(38);
					destinazione2.getChildren().add(mom);
					familiareBianco.setDisable(true);
					familiareBianco.setOpacity(0);
					try {
						setCardGiocatore(start.getClient().getNamePosition(destinazione2.getLayoutX(),
								destinazione2.getLayoutY()));
					} catch (IOException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

		azioniTerritoridaunGiocatore.setOnDragEntered(e -> {
			setDestinazione1(azioniTerritoridaunGiocatore);
		});

		azioniEdificidaunGiocatore.setOnDragEntered(e -> {
			setDestinazione1(azioniEdificidaunGiocatore);
		});

		mercatoPosMoneteMilitari.setOnDragEntered(e -> {
			setDestinazione1(mercatoPosMoneteMilitari);
		});

		mercatoPosServitori.setOnDragEntered(e -> {
			setDestinazione1(mercatoPosServitori);
		});

		mercatoPosMonete.setOnDragEntered(e -> {
			setDestinazione1(mercatoPosMonete);
		});

		mercatoPosMunicipio.setOnDragEntered(e -> {
			setDestinazione1(mercatoPosMunicipio);
		});

		pianoPrimoPalazzoMilitare.setOnDragEntered(e -> {
			setDestinazione1(pianoPrimoPalazzoMilitare);
		});

		pianoSecondoPalazzoMilitare.setOnDragEntered(e -> {
			setDestinazione1(pianoSecondoPalazzoMilitare);
		});

		pianoTerzoPalazzoMilitare.setOnDragEntered(e -> {
			setDestinazione1(pianoTerzoPalazzoMilitare);
		});

		pianoQuartoPalazzoMilitare.setOnDragEntered(e -> {
			setDestinazione1(pianoQuartoPalazzoMilitare);
		});

		pianoPrimoPalazzoPersonaggi.setOnDragEntered(e -> {
			setDestinazione1(pianoPrimoPalazzoPersonaggi);
		});

		pianoSecondoPalazzoPersonaggi.setOnDragEntered(e -> {
			setDestinazione1(pianoSecondoPalazzoPersonaggi);
		});

		pianoTerzoPalazzoPersonaggi.setOnDragEntered(e -> {
			setDestinazione1(pianoTerzoPalazzoPersonaggi);
		});

		pianoQuartoPalazzoPersonaggi.setOnDragEntered(e -> {
			setDestinazione1(pianoQuartoPalazzoPersonaggi);
		});

		pianoPrimoPalazzoEdifici.setOnDragEntered(e -> {
			setDestinazione1(pianoPrimoPalazzoEdifici);
		});

		pianoSecondoPalazzoEdifici.setOnDragEntered(e -> {
			setDestinazione1(pianoSecondoPalazzoEdifici);
		});

		pianoTerzoPalazzoEdifici.setOnDragEntered(e -> {
			setDestinazione1(pianoTerzoPalazzoEdifici);
		});

		pianoQuartoPalazzoEdifici.setOnDragEntered(e -> {
			setDestinazione1(pianoQuartoPalazzoEdifici);
		});

		pianoPrimoPalazzoTerritori.setOnDragEntered(e -> {
			setDestinazione1(pianoPrimoPalazzoTerritori);
		});

		pianoSecondoPalazzoTerritori.setOnDragEntered(e -> {
			setDestinazione1(pianoSecondoPalazzoTerritori);
		});

		pianoTerzoPalazzoTerritori.setOnDragEntered(e -> {
			setDestinazione1(pianoTerzoPalazzoTerritori);
		});

		pianoQuartoPalazzoTerritori.setOnDragEntered(e -> {
			setDestinazione1(pianoQuartoPalazzoTerritori);
		});

		azioniTerritoridapiuGiocatori.setOnDragEntered(e -> {
			setDestinazione2(azioniTerritoridapiuGiocatori);
		});

		municipio.setOnDragEntered(e -> {
			setDestinazione2(municipio);
		});

		azioniEdificidapiuGiocatori.setOnDragEntered(e -> {
			setDestinazione2(azioniEdificidapiuGiocatori);
		});

	}

	private void setDestinazione2(HBox azioniTerritoridaunGiocatore2) {
		flag = false;
		destinazione2 = azioniTerritoridaunGiocatore2;
	}

	private void setDestinazione1(ImageView azioniTerritoridaunGiocatore2) {
		flag = true;
		this.destinazione1 = azioniTerritoridaunGiocatore2;

	}

	public void setFlag(String string) {
		bandiera.setImage(new Image(getClass().getResourceAsStream("BandierinaBlu.png")));

	}

	public void notifySpostamentoPuntiMilitari(double x, double y, String color) {
		Path path = new Path();
		PathTransition pathTransition = new PathTransition();
		switch (color) {
		case "blue":
			path.getElements().add(new MoveTo(x,y));
			path.getElements().add(new CubicCurveTo(puntiMilitariBlu.getLayoutX(), puntiMilitariBlu.getLayoutY(), 200, 20, x, y+20.0));
			pathTransition.setDuration(Duration.millis(3000));
			pathTransition.setPath(path);
			pathTransition.setNode(puntiMilitariBlu);
			pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
			pathTransition.setCycleCount(1);
			pathTransition.play();
			System.out.println("Fine Spostamento pedina");
			break;
		case "white":
			path.getElements().add(new MoveTo(x,y));
			path.getElements().add(new CubicCurveTo(puntiMilitariBianco.getLayoutX(), puntiMilitariBianco.getLayoutY(), 200, 20, x, y+20.0));
			pathTransition.setDuration(Duration.millis(3000));
			pathTransition.setPath(path);
			pathTransition.setNode(puntiMilitariBianco);
			pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
			pathTransition.setCycleCount(1);
			pathTransition.play();
			System.out.println("Fine Spostamento pedina");
			break;
		case "green":
			path.getElements().add(new MoveTo(x,y));
			path.getElements().add(new CubicCurveTo(puntiMilitariVerde.getLayoutX(), puntiMilitariVerde.getLayoutY(), 200, 20, x, y+20.0));
			pathTransition.setDuration(Duration.millis(3000));
			pathTransition.setPath(path);
			pathTransition.setNode(puntiMilitariVerde);
			pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
			pathTransition.setCycleCount(1);
			pathTransition.play();
			System.out.println("Fine Spostamento pedina");
			break;
		case "orange":
			path.getElements().add(new MoveTo(x,y));
			path.getElements().add(new CubicCurveTo(puntiMilitariArancio.getLayoutX(), puntiMilitariArancio.getLayoutY(), 200, 20, x, y+20.0));
			pathTransition.setDuration(Duration.millis(3000));
			pathTransition.setPath(path);
			pathTransition.setNode(puntiMilitariArancio);
			pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
			pathTransition.setCycleCount(1);
			pathTransition.play();
			break;
		}
	}

	public void notifySpostamentoPuntiFede(double x, double y, String color) {
		Path path = new Path();
		PathTransition pathTransition = new PathTransition();
		switch (color) {
		case "blue":
			path.getElements().add(new MoveTo(x,y));
			path.getElements().add(new CubicCurveTo(puntiFedeBlu.getLayoutX(), puntiFedeBlu.getLayoutY(), 200, 20, x, y+20.0));
			pathTransition.setDuration(Duration.millis(3000));
			pathTransition.setPath(path);
			pathTransition.setNode(puntiFedeBlu);
			pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
			pathTransition.setCycleCount(1);
			pathTransition.play();
			System.out.println("Fine Spostamento pedina");
			break;
		case "white":
			path.getElements().add(new MoveTo(x,y));
			path.getElements().add(new CubicCurveTo(puntiFedeBianco.getLayoutX(), puntiFedeBianco.getLayoutY(), 200, 20, x, y+20.0));
			pathTransition.setDuration(Duration.millis(3000));
			pathTransition.setPath(path);
			pathTransition.setNode(puntiFedeBianco);
			pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
			pathTransition.setCycleCount(1);
			pathTransition.play();
			System.out.println("Fine Spostamento pedina");
			break;
		case "green":
			path.getElements().add(new MoveTo(x,y));
			path.getElements().add(new CubicCurveTo(puntiFedeVerde.getLayoutX(), puntiFedeVerde.getLayoutY(), 200, 20, x, y+20.0));
			pathTransition.setDuration(Duration.millis(3000));
			pathTransition.setPath(path);
			pathTransition.setNode(puntiFedeVerde);
			pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
			pathTransition.setCycleCount(1);
			pathTransition.play();
			System.out.println("Fine Spostamento pedina");
			break;
		case "orange":
			path.getElements().add(new MoveTo(x,y));
			path.getElements().add(new CubicCurveTo(puntiFedeArancio.getLayoutX(), puntiFedeArancio.getLayoutY(), 200, 20, x, y+20.0));
			pathTransition.setDuration(Duration.millis(3000));
			pathTransition.setPath(path);
			pathTransition.setNode(puntiFedeArancio);
			pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
			pathTransition.setCycleCount(1);
			pathTransition.play();
			System.out.println("Fine Spostamento pedina");
			break;
		}
	}
	
	public void notifySpostamentoPuntiVittoria(double x, double y, String color) {
		double startX;
		double startY;
		Path path = new Path();
		PathTransition pathTransition = new PathTransition();
		switch (color) {
		case "blue":
			startX = puntiVittoriaBlu.getLayoutX();
			startY = puntiVittoriaBlu.getLayoutY();
			path.getElements().add(new MoveTo(x,y));
			path.getElements().add(new CubicCurveTo(startX, startY, 200, 20, x, y+20.0));
			pathTransition.setDuration(Duration.millis(3000));
			pathTransition.setPath(path);
			pathTransition.setNode(puntiVittoriaBlu);
			pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
			pathTransition.setCycleCount(1);
			pathTransition.play();
			System.out.println("Fine Spostamento pedina");
			break;
		case "white":
			startX = puntiVittoriaBianco.getLayoutX();
			startY = puntiVittoriaBianco.getLayoutY();
			path.getElements().add(new MoveTo(x,y));
			path.getElements().add(new CubicCurveTo(startX, startY, 200, 20, x, y+20.0));
			pathTransition.setDuration(Duration.millis(3000));
			pathTransition.setPath(path);
			pathTransition.setNode(puntiVittoriaBianco);
			pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
			pathTransition.setCycleCount(1);
			pathTransition.play();
			System.out.println("Fine Spostamento pedina");
			break;
		case "green":
			startX = puntiVittoriaVerde.getLayoutX();
			startY = puntiVittoriaVerde.getLayoutY();
			path.getElements().add(new MoveTo(x,y));
			path.getElements().add(new CubicCurveTo(startX, startY, 200, 20, x, y+20.0));
			pathTransition.setDuration(Duration.millis(3000));
			pathTransition.setPath(path);
			pathTransition.setNode(puntiVittoriaVerde);
			pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
			pathTransition.setCycleCount(1);
			pathTransition.play();
			System.out.println("Fine Spostamento pedina");
			break;
		case "orange":
			startX = puntiVittoriaArancio.getLayoutX();
			startY = puntiVittoriaArancio.getLayoutY();
			path.getElements().add(new MoveTo(x,y));
			path.getElements().add(new CubicCurveTo(startX, startY, 200, 20, x, y+20.0));
			pathTransition.setDuration(Duration.millis(3000));
			pathTransition.setPath(path);
			pathTransition.setNode(puntiVittoriaArancio);
			pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
			pathTransition.setCycleCount(1);
			pathTransition.play();
			System.out.println("Fine Spostamento pedina");
			break;
		}
	}

	public void notifyPergamena(int i) {
		for(int j=0; j<i;j++){
		Stage popup = new Stage();
		popup.setTitle("Pergamene");
		VBox box = new VBox();
		HBox buttonBox = new HBox();
		ImageView im = new ImageView(new Image(getClass().getResourceAsStream("Pergamena.png")));
		Button risorse = new Button("Click Me!");
		risorse.setOnAction(e -> {
			try {
				start.getClient().getRisorse().addRis("pietra", 1);
				start.getClient().getRisorse().addRis("legno", 1);
				pietra.setText(String.valueOf(start.getClient().getRisorse().getDimRisorse("pietra")));
				lengo.setText(String.valueOf(start.getClient().getRisorse().getDimRisorse("legno")));
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			popup.close();
			e.consume();
		});

		Button servitoriB = new Button("Click Me!");
		servitoriB.setOnAction(e -> {
			try {
				start.getClient().getRisorse().addRis("servitori", 2);
				servitori.setText(String.valueOf(start.getClient().getRisorse().getDimRisorse("servitori")));
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			popup.close();
			e.consume();
		});

		Button moneteB = new Button("Click Me!");
		moneteB.setOnAction(e -> {
			try {
				start.getClient().getRisorse().addRis("monete", 2);
				monete.setText(String.valueOf(start.getClient().getRisorse().getDimRisorse("monete")));
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			popup.close();
			e.consume();
		});

		Button militariB = new Button("Click Me!");
		moneteB.setOnAction(e -> {
			try {
				start.getClient().getRisorse().addPunti("militari", 2);
				start.getClient().notifySpostamentoPunti("militari");
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			popup.close();
			e.consume();
		});
		Button fedeB = new Button("Click Me!");
		fedeB.setOnAction(e -> {
			try {
				start.getClient().getRisorse().addPunti("fede", 1);
				start.getClient().notifySpostamentoPunti("fede");
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			popup.close();
			e.consume();
		});
		buttonBox.getChildren().addAll(risorse, moneteB, militariB, fedeB);
		box.getChildren().addAll(im, buttonBox);
		Scene scene = new Scene(box, 600, 400);
		popup.centerOnScreen();
		popup.setScene(scene);
		popup.show();
		}
	}

	public void notifyTutteCarte(int valore) {
		Stage popup = new Stage();
		popup.setTitle("TutteCarte");
		VBox vBox = new VBox();
		HBox box = new HBox();
		HBox labelBox = new HBox();
		Label[] l = { new Label(" "),
				new Label(
						"Hei, puoi prendere una carta in più, clicca sulla carta che vorresti prendere"),
				new Label(" ") };
		Immagine[] im = new Immagine[16];
		labelBox.getChildren().addAll(l[0], l[1], l[2]);
		for (int i = 0; i < 4; i++) {
			if (arrayCarteTerritori[i] != null && i < (valore / 2)) {
				im[i] = new Immagine(new Image(getClass().getResourceAsStream(arrayCarteTerritori[i].getImage())));
				im[i].setC(arrayCarteTerritori[i]);
				im[i].setTipo(0);
			}
		}
		for (int i = 0; i < 4; i++) {
			if (arrayCartePersonaggi[i] != null && i < (valore / 2)) {
				im[i + 4] = new Immagine(new Image(getClass().getResourceAsStream(arrayCartePersonaggi[i].getImage())));
				im[i + 4].setC(arrayCartePersonaggi[i]);
				im[i + 4].setTipo(1);
			}
		}
		for (int i = 0; i < 4; i++) {
			if (arrayCarteEdifici[i] != null && i < (valore / 2)) {
				im[i + 8] = new Immagine(new Image(getClass().getResourceAsStream(arrayCarteEdifici[i].getImage())));
				im[i + 8].setC(arrayCarteEdifici[i]);
				im[i + 8].setTipo(2);
			}
		}
		for (int i = 0; i < 4; i++) {
			if (arrayCarteImpresa[i] != null && i < (valore / 2)) {
				im[i + 12] = new Immagine(new Image(getClass().getResourceAsStream(arrayCarteImpresa[i].getImage())));
				im[i + 12].setC(arrayCarteImpresa[i]);
				im[i + 12].setTipo(3);
			}
		}
		for (Immagine i : im) {
			if (i != null) {
				box.getChildren().add(i);
				i.setOnMouseClicked(e -> {
					try {
						start.getClient().getRisorse().addRis("monete", -3);
						start.getClient().setCardGiocatore(i.getC(), i.getTipo());
					} catch (ClassNotFoundException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				});
			}
		}
		vBox.getChildren().addAll(labelBox, box);
		Scene scene = new Scene(vBox, 600, 400);
		popup.centerOnScreen();
		popup.setScene(scene);
		popup.show();
	}

	public void notifyUnTipoCarta(int tipo, int valore, int scontoAzioneImmediata1) {
		Stage popup = new Stage();
		popup.setTitle("Un tipo di carta");
		VBox vBox = new VBox();
		HBox box = new HBox();
		HBox labelBox = new HBox();
		Label[] l = { new Label(" "),
				new Label(
						"Hei, puoi prendere un tipo di carta in più, clicca sulla carta che vorresti prendere"),
				new Label(" ") };
		Immagine[] im = new Immagine[4];
		labelBox.getChildren().addAll(l[0], l[1], l[2]);
		switch(tipo){
			case 0:
				for(int i = 0; i< 4; i++){
					if (arrayCarteTerritori[i] != null && i < (valore / 2)) {
						im[i] = new Immagine(new Image(getClass().getResourceAsStream(arrayCarteTerritori[i].getImage())));
						im[i].setC(arrayCarteEdifici[i]);
						im[i].setTipo(0);
					}
				}
				break;
			case 1:
				for(int i = 0; i< 4; i++){
				if (arrayCartePersonaggi[i] != null && i < (valore / 2)) {
					im[i] = new Immagine(new Image(getClass().getResourceAsStream(arrayCartePersonaggi[i].getImage())));
					im[i].setC(arrayCartePersonaggi[i]);
					im[i].setTipo(1);
				}}
				break;
			case 2:
				for(int i = 0; i< 4; i++){
				if (arrayCarteEdifici[i] != null && i < (valore / 2)) {
					im[i] = new Immagine(new Image(getClass().getResourceAsStream(arrayCarteEdifici[i].getImage())));
					im[i].setC(arrayCarteEdifici[i]);
					im[i].setTipo(2);
				}}
				break;
			case 3:
				for(int i = 0; i< 4; i++){
				if (arrayCarteImpresa[i] != null && i < (valore / 2)) {
					im[i] = new Immagine(new Image(getClass().getResourceAsStream(arrayCarteImpresa[i].getImage())));
					im[i].setC(arrayCarteImpresa[i]);
					im[i].setTipo(3);
				}}
				break;
		}
		for (Immagine i : im) {
			if (i != null) {
				box.getChildren().add(i);
				i.setOnMouseClicked(e -> {
					try {
						start.getClient().getRisorse().addRis("monete", -3+scontoAzioneImmediata1);
						start.getClient().setCardGiocatore(i.getC(), i.getTipo());
					} catch (ClassNotFoundException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				});
			}
		}
		vBox.getChildren().addAll(labelBox, box);
		Scene scene = new Scene(vBox, 600, 400);
		popup.centerOnScreen();
		popup.setScene(scene);
		popup.show();
	}
}
