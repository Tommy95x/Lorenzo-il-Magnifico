package client.gui.controllers;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import client.gui.StartClientGui;
import javafx.animation.PathTransition;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
	private TesseraScomunica[] tessereScomunica = new TesseraScomunica[3];
	@SuppressWarnings("unused")
	private int numberOfGamers;
	private ImageView destinazione1;
	private HBox destinazione2;
	private boolean flag;
	private Dado[] dadi = new Dado[3];
	boolean pay = false;
	private int nFamPos = 0;
	private boolean isInTurno = false;
	private Image trasparente = new Image(getClass().getResourceAsStream("Trasparente.png"));
	private int valoreAgg;
	private boolean sostegno = false;

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
	@FXML
	public Label turno;

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
	@FXML
	public Label monete1;
	@FXML
	public Label monete2;
	@FXML
	public Label monete3;
	@FXML
	public Label pietre1;
	@FXML
	public Label pietre2;
	@FXML
	public Label pietre3;
	@FXML
	public Label legno1;
	@FXML
	public Label legno2;
	@FXML
	public Label legno3;
	@FXML
	public Label servitori1;
	@FXML
	public Label servitori2;
	@FXML
	public Label servitori3;
	@FXML
	public VBox listCards1;
	@FXML
	public VBox listCards2;
	@FXML
	public VBox listCards3;
	@FXML
	public ImageView cuboScomunica11;
	@FXML
	public ImageView cuboScomunica12;
	@FXML
	public ImageView cuboScomunica13;
	@FXML
	public ImageView cuboScomunica21;
	@FXML
	public ImageView cuboScomunica22;
	@FXML
	public ImageView cuboScomunica23;
	@FXML
	public ImageView cuboScomunica31;
	@FXML
	public ImageView cuboScomunica32;
	@FXML
	public ImageView cuboScomunica33;

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
		// numberOfGamers = start.getClient().getPlayers();
		start.getClient().setGuiGame(this);
	}

	public void setRisorse(Portafoglio risorse) {
		setLegno(risorse.getDimRisorse("legno"));
		setPietra(risorse.getDimRisorse("pietra"));
		setServitori(risorse.getDimRisorse("servitori"));
		setMonete(risorse.getDimRisorse("monete"));
	}

	public void setCardsScomunica(TesseraScomunica[] cardsScomunica) {
		tessereScomunica[0] = cardsScomunica[0];
		Tooltip.install(cartaScomunica1, cardsScomunica[0].getTooltip());
		cartaScomunica1.setImage(new Image(getClass().getResourceAsStream(cardsScomunica[0].getImage())));
		tessereScomunica[1] = cardsScomunica[1];
		Tooltip.install(cartaScomunica2, cardsScomunica[1].getTooltip());
		cartaScomunica2.setImage(new Image(getClass().getResourceAsStream(cardsScomunica[1].getImage())));
		tessereScomunica[2] = cardsScomunica[2];
		Tooltip.install(cartaScomunica3, cardsScomunica[2].getTooltip());
		cartaScomunica3.setImage(new Image(getClass().getResourceAsStream(cardsScomunica[2].getImage())));

	}

	public void setColorCubiScomunica(String color) {
		@SuppressWarnings("unused")
		Portafoglio p;
		switch (color) {
		case "blue":
			cuboScomunica1.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaBlu.png")));
			cuboScomunica2.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaBlu.png")));
			cuboScomunica3.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaBlu.png")));
			break;
		case "green":
			cuboScomunica1.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaVerde.png")));
			cuboScomunica2.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaVerde.png")));
			cuboScomunica3.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaVerde.png")));
			break;
		case "white":
			cuboScomunica1.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaBianco.png")));
			cuboScomunica2.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaBianco.png")));
			cuboScomunica3.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaBianco.png")));
			break;
		case "orange":
			cuboScomunica1.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaArancio.png")));
			cuboScomunica2.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaArancio.png")));
			cuboScomunica3.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaArancio.png")));
			break;
		}
	}

	public void setColorsParents(String color) throws RemoteException, ClassNotFoundException, IOException {
		Giocatore[] giocatori = new Giocatore[4];
		giocatori = start.getClient().getGiocatori();
		Portafoglio p = null;
		switch (color) {
		case "blue":
			puntiVittoriaBlu.setImage(new Image(getClass().getResourceAsStream("Disco1.png")));
			puntiFedeBlu.setImage(new Image(getClass().getResourceAsStream("Disco1.png")));
			puntiMilitariBlu.setImage(new Image(getClass().getResourceAsStream("Disco1.png")));

			familiareNeutro.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluNeutro.png")));
			familiareNero.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluNero.png")));
			familiareArancio.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluArancio.png")));
			familiareBianco.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluBianco.png")));
			bandiera.setImage(new Image(this.getClass().getResourceAsStream("BandierinaBlu.png")));
			for (Giocatore g : giocatori) {
				if (g != null && !g.getName().equals(start.getClient().getName()) && !g.getName().equals("niente")) {
					switch (g.getColor()) {
					case "orange":
						puntiVittoriaArancio.setImage(new Image(getClass().getResourceAsStream("Disco3.png")));
						puntiFedeArancio.setImage(new Image(getClass().getResourceAsStream("Disco3.png")));
						puntiMilitariArancio.setImage(new Image(getClass().getResourceAsStream("Disco3.png")));

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
						System.out.println("Nome giocatore avv 1" + name1.getText() + "\n");
						cuboScomunica11.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaArancio.png")));
						cuboScomunica12.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaArancio.png")));
						cuboScomunica13.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaArancio.png")));
						p = g.getRisorse();
						legno1.setText(String.valueOf(p.getDimRisorse("legno")));
						monete1.setText(String.valueOf(p.getDimRisorse("monete")));
						pietre1.setText(String.valueOf(p.getDimRisorse("pietra")));
						servitori1.setText(String.valueOf(p.getDimRisorse("servitori")));
						break;
					case "green":
						puntiVittoriaVerde.setImage(new Image(getClass().getResourceAsStream("Disco4.png")));
						puntiFedeVerde.setImage(new Image(getClass().getResourceAsStream("Disco4.png")));
						puntiMilitariVerde.setImage(new Image(getClass().getResourceAsStream("Disco4.png")));

						familiareGreen1
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareVerdeNeutro.png")));
						familiareGreen2
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareVerdeArancio.png")));
						familiareGreen3
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareVerdeBianco.png")));
						familiareGreen4
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareVerdeNero.png")));
						flag2.setImage(new Image(this.getClass().getResourceAsStream("BandierinaVerde.png")));
						System.out.println(g.getName());
						name2.setText(g.getName());
						System.out.println("Nome giocatore avv 2" + name2.getText() + "\n");
						cuboScomunica21.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaVerde.png")));
						cuboScomunica22.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaVerde.png")));
						cuboScomunica23.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaVerde.png")));
						p = g.getRisorse();
						legno2.setText(String.valueOf(p.getDimRisorse("legno")));
						monete2.setText(String.valueOf(p.getDimRisorse("monete")));
						pietre2.setText(String.valueOf(p.getDimRisorse("pietra")));
						servitori2.setText(String.valueOf(p.getDimRisorse("servitori")));
						break;
					case "white":
						puntiMilitariBianco.setImage(new Image(getClass().getResourceAsStream("Disco12.png")));
						puntiFedeBianco.setImage(new Image(getClass().getResourceAsStream("Disco12.png")));
						puntiVittoriaBianco.setImage(new Image(getClass().getResourceAsStream("Disco12.png")));

						familiareWhite1
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBiancoNeutro.png")));
						familiareWhite2
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBiancoArancio.png")));
						familiareWhite3
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBiancoBianco.png")));
						familiareWhite4
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBiancoNero.png")));
						flag3.setImage(new Image(this.getClass().getResourceAsStream("BandierinaBianca.png")));
						System.out.println(g.getName());
						name3.setText(g.getName());
						System.out.println("Nome giocatore avv 3" + name3.getText() + "\n");
						cuboScomunica31.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaBianco.png")));
						cuboScomunica32.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaBianco.png")));
						cuboScomunica33.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaBianco.png")));
						p = g.getRisorse();
						legno3.setText(String.valueOf(p.getDimRisorse("legno")));
						monete3.setText(String.valueOf(p.getDimRisorse("monete")));
						pietre3.setText(String.valueOf(p.getDimRisorse("pietra")));
						servitori3.setText(String.valueOf(p.getDimRisorse("servitori")));
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
			puntiVittoriaVerde.setImage(new Image(getClass().getResourceAsStream("Disco4.png")));
			puntiFedeVerde.setImage(new Image(getClass().getResourceAsStream("Disco4.png")));
			puntiMilitariVerde.setImage(new Image(getClass().getResourceAsStream("Disco4.png")));
			familiareNeutro.setImage(new Image(this.getClass().getResourceAsStream("FamiliareVerdeNeutro.png")));
			familiareNero.setImage(new Image(this.getClass().getResourceAsStream("FamiliareVerdeNero.png")));
			familiareArancio.setImage(new Image(this.getClass().getResourceAsStream("FamiliareVerdeArancio.png")));
			familiareBianco.setImage(new Image(this.getClass().getResourceAsStream("FamiliareVerdeBianco.png")));
			bandiera.setImage(new Image(this.getClass().getResourceAsStream("BandierinaVerde.png")));
			for (Giocatore g : giocatori) {
				if (g != null)
					System.out.println(g.getName() + "\n\n\n\n" + start.getClient().getName() + " "
							+ !g.getName().equals(start.getClient().getName()));
				if (g != null && !g.getName().equals(start.getClient().getName()) && !g.getName().equals("niente")) {
					switch (g.getColor()) {
					case "orange":
						puntiVittoriaArancio.setImage(new Image(getClass().getResourceAsStream("Disco3.png")));
						puntiFedeArancio.setImage(new Image(getClass().getResourceAsStream("Disco3.png")));
						puntiMilitariArancio.setImage(new Image(getClass().getResourceAsStream("Disco3.png")));
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
						System.out.println("Nome giocatore avv 1" + name1.getText() + "\n");
						cuboScomunica11.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaArancio.png")));
						cuboScomunica12.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaArancio.png")));
						cuboScomunica13.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaArancio.png")));
						p = g.getRisorse();
						legno1.setText(String.valueOf(p.getDimRisorse("legno")));
						monete1.setText(String.valueOf(p.getDimRisorse("monete")));
						pietre1.setText(String.valueOf(p.getDimRisorse("pietra")));
						servitori1.setText(String.valueOf(p.getDimRisorse("servitori")));
						break;
					case "blue":
						puntiVittoriaBlu.setImage(new Image(getClass().getResourceAsStream("Disco1.png")));
						puntiFedeBlu.setImage(new Image(getClass().getResourceAsStream("Disco1.png")));
						puntiMilitariBlu.setImage(new Image(getClass().getResourceAsStream("Disco1.png")));
						familiareGreen1
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluNeutro.png")));
						familiareGreen2
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluArancio.png")));
						familiareGreen3
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluBianco.png")));
						familiareGreen4
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluNero.png")));
						flag2.setImage(new Image(this.getClass().getResourceAsStream("BandierinaBlu.png")));
						name2.setText(g.getName());
						System.out.println("Nome giocatore avv 2" + name2.getText() + "\n");
						cuboScomunica21.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaBlu.png")));
						cuboScomunica22.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaBlu.png")));
						cuboScomunica23.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaBlu.png")));
						p = g.getRisorse();
						legno2.setText(String.valueOf(p.getDimRisorse("legno")));
						monete2.setText(String.valueOf(p.getDimRisorse("monete")));
						pietre2.setText(String.valueOf(p.getDimRisorse("pietra")));
						servitori2.setText(String.valueOf(p.getDimRisorse("servitori")));
						break;
					case "white":
						puntiMilitariBianco.setImage(new Image(getClass().getResourceAsStream("Disco12.png")));
						puntiFedeBianco.setImage(new Image(getClass().getResourceAsStream("Disco12.png")));
						puntiVittoriaBianco.setImage(new Image(getClass().getResourceAsStream("Disco12.png")));
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
						System.out.println("Nome giocatore avv 3" + name3.getText() + "\n");
						cuboScomunica31.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaBianco.png")));
						cuboScomunica32.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaBianco.png")));
						cuboScomunica33.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaBianco.png")));
						p = g.getRisorse();
						legno3.setText(String.valueOf(p.getDimRisorse("legno")));
						monete3.setText(String.valueOf(p.getDimRisorse("monete")));
						pietre3.setText(String.valueOf(p.getDimRisorse("pietra")));
						servitori3.setText(String.valueOf(p.getDimRisorse("servitori")));
						break;
					}
				}
			}
			familiareGreen1.setOpacity(1);
			familiareGreen2.setOpacity(1);
			familiareGreen3.setOpacity(1);
			familiareGreen4.setOpacity(1);
			familiareOrange1.setOpacity(1);
			familiareOrange2.setOpacity(1);
			familiareOrange3.setOpacity(1);
			familiareOrange4.setOpacity(1);
			familiareWhite1.setOpacity(1);
			familiareWhite2.setOpacity(1);
			familiareWhite3.setOpacity(1);
			familiareWhite4.setOpacity(1);
			familiareOrange1.setDisable(false);
			familiareOrange2.setDisable(false);
			familiareOrange3.setDisable(false);
			familiareOrange4.setDisable(false);
			familiareWhite1.setDisable(false);
			familiareWhite2.setDisable(false);
			familiareWhite3.setDisable(false);
			familiareWhite4.setDisable(false);
			familiareGreen1.setDisable(false);
			familiareGreen2.setDisable(false);
			familiareGreen3.setDisable(false);
			familiareGreen4.setDisable(false);
			break;
		case "white":
			puntiMilitariBianco.setImage(new Image(getClass().getResourceAsStream("Disco12.png")));
			puntiFedeBianco.setImage(new Image(getClass().getResourceAsStream("Disco12.png")));
			puntiVittoriaBianco.setImage(new Image(getClass().getResourceAsStream("Disco12.png")));
			familiareNeutro.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBiancoNeutro.png")));
			familiareNero.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBiancoNero.png")));
			familiareArancio.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBiancoArancio.png")));
			familiareBianco.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBiancoBianco.png")));
			bandiera.setImage(new Image(this.getClass().getResourceAsStream("BandierinaBianca.png")));
			for (Giocatore g : giocatori) {
				if (g != null)
					System.out.println(g.getName() + "\n\n\n\n" + start.getClient().getName()
							+ !g.getName().equals(start.getClient().getName()));
				if (g != null && !g.getName().equals(start.getClient().getName()) && !g.getName().equals("niente")) {
					switch (g.getColor()) {
					case "orange":
						puntiVittoriaArancio.setImage(new Image(getClass().getResourceAsStream("Disco3.png")));
						puntiFedeArancio.setImage(new Image(getClass().getResourceAsStream("Disco3.png")));
						puntiMilitariArancio.setImage(new Image(getClass().getResourceAsStream("Disco3.png")));
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
						System.out.println("Nome giocatore avv 1" + name1.getText() + "\n");
						System.out.println(name1.getText());
						cuboScomunica11.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaArancio.png")));
						cuboScomunica12.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaArancio.png")));
						cuboScomunica13.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaArancio.png")));
						p = g.getRisorse();
						legno1.setText(String.valueOf(p.getDimRisorse("legno")));
						monete1.setText(String.valueOf(p.getDimRisorse("monete")));
						pietre1.setText(String.valueOf(p.getDimRisorse("pietra")));
						servitori1.setText(String.valueOf(p.getDimRisorse("servitori")));
						break;
					case "green":
						puntiVittoriaVerde.setImage(new Image(getClass().getResourceAsStream("Disco4.png")));
						puntiFedeVerde.setImage(new Image(getClass().getResourceAsStream("Disco4.png")));
						puntiMilitariVerde.setImage(new Image(getClass().getResourceAsStream("Disco4.png")));
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
						System.out.println("Nome giocatore avv 2" + name2.getText() + "\n");
						cuboScomunica21.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaVerde.png")));
						cuboScomunica22.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaVerde.png")));
						cuboScomunica23.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaVerde.png")));
						p = g.getRisorse();
						legno2.setText(String.valueOf(p.getDimRisorse("legno")));
						monete2.setText(String.valueOf(p.getDimRisorse("monete")));
						pietre2.setText(String.valueOf(p.getDimRisorse("pietra")));
						servitori2.setText(String.valueOf(p.getDimRisorse("servitori")));
						break;
					case "blue":
						puntiVittoriaBlu.setImage(new Image(getClass().getResourceAsStream("Disco1.png")));
						puntiFedeBlu.setImage(new Image(getClass().getResourceAsStream("Disco1.png")));
						puntiMilitariBlu.setImage(new Image(getClass().getResourceAsStream("Disco1.png")));
						familiareWhite1
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluNeutro.png")));
						familiareWhite2
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluArancio.png")));
						familiareWhite3
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluBianco.png")));
						familiareWhite4
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluNero.png")));
						flag3.setImage(new Image(this.getClass().getResourceAsStream("BandierinaBlu.png")));
						name3.setText(g.getName());
						System.out.println("Nome giocatore avv 3" + name3.getText() + "\n");
						p = g.getRisorse();
						legno3.setText(String.valueOf(p.getDimRisorse("legno")));
						monete3.setText(String.valueOf(p.getDimRisorse("monete")));
						pietre3.setText(String.valueOf(p.getDimRisorse("pietra")));
						servitori3.setText(String.valueOf(p.getDimRisorse("servitori")));
						break;
					}
				}
			}
			familiareGreen1.setOpacity(1);
			familiareGreen2.setOpacity(1);
			familiareGreen3.setOpacity(1);
			familiareGreen4.setOpacity(1);
			familiareOrange1.setOpacity(1);
			familiareOrange2.setOpacity(1);
			familiareOrange3.setOpacity(1);
			familiareOrange4.setOpacity(1);
			familiareWhite1.setOpacity(1);
			familiareWhite2.setOpacity(1);
			familiareWhite3.setOpacity(1);
			familiareWhite4.setOpacity(1);
			familiareOrange1.setDisable(false);
			familiareOrange2.setDisable(false);
			familiareOrange3.setDisable(false);
			familiareOrange4.setDisable(false);
			familiareWhite1.setDisable(false);
			familiareWhite2.setDisable(false);
			familiareWhite3.setDisable(false);
			familiareWhite4.setDisable(false);
			familiareGreen1.setDisable(false);
			familiareGreen2.setDisable(false);
			familiareGreen3.setDisable(false);
			familiareGreen4.setDisable(false);
			break;
		case "orange":
			puntiVittoriaArancio.setImage(new Image(getClass().getResourceAsStream("Disco3.png")));
			puntiFedeArancio.setImage(new Image(getClass().getResourceAsStream("Disco3.png")));
			puntiMilitariArancio.setImage(new Image(getClass().getResourceAsStream("Disco3.png")));
			familiareNeutro.setImage(new Image(this.getClass().getResourceAsStream("FamiliareArancioNeutro.png")));
			familiareNero.setImage(new Image(this.getClass().getResourceAsStream("FamiliareArancioNero.png")));
			familiareArancio.setImage(new Image(this.getClass().getResourceAsStream("FamiliareArancioArancio.png")));
			familiareBianco.setImage(new Image(this.getClass().getResourceAsStream("FamiliareArancioBianco.png")));
			bandiera.setImage(new Image(this.getClass().getResourceAsStream("BandierinaArancio.png")));
			for (Giocatore g : giocatori) {
				if (g != null)
					System.out.println(g.getName() + "\n\n\n\n" + start.getClient().getName()
							+ !g.getName().equals(start.getClient().getName()));
				if (g != null && !g.getName().equals(start.getClient().getName()) && !g.getName().equals("niente")) {
					switch (g.getColor()) {
					case "blue":
						puntiVittoriaBlu.setImage(new Image(getClass().getResourceAsStream("Disco1.png")));
						puntiFedeBlu.setImage(new Image(getClass().getResourceAsStream("Disco1.png")));
						puntiMilitariBlu.setImage(new Image(getClass().getResourceAsStream("Disco1.png")));
						familiareOrange1
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluNeutro.png")));
						familiareOrange2
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluArancio.png")));
						familiareOrange3
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluBianco.png")));
						familiareOrange4
								.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluNero.png")));
						flag1.setImage(new Image(this.getClass().getResourceAsStream("BandierinaBlu.png")));
						name1.setText(g.getName());
						System.out.println("Nome giocatore avv 1" + name1.getText() + "\n");
						cuboScomunica11.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaBlu.png")));
						cuboScomunica12.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaBlu.png")));
						cuboScomunica13.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaBlu.png")));
						p = g.getRisorse();
						legno1.setText(String.valueOf(p.getDimRisorse("legno")));
						monete1.setText(String.valueOf(p.getDimRisorse("monete")));
						pietre1.setText(String.valueOf(p.getDimRisorse("pietra")));
						servitori1.setText(String.valueOf(p.getDimRisorse("servitori")));
						break;
					case "green":
						puntiVittoriaVerde.setImage(new Image(getClass().getResourceAsStream("Disco4.png")));
						puntiFedeVerde.setImage(new Image(getClass().getResourceAsStream("Disco4.png")));
						puntiMilitariVerde.setImage(new Image(getClass().getResourceAsStream("Disco4.png")));
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
						System.out.println("Nome giocatore avv 2" + name2.getText() + "\n");
						cuboScomunica21.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaVerde.png")));
						cuboScomunica22.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaVerde.png")));
						cuboScomunica23.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaVerde.png")));
						p = g.getRisorse();
						legno2.setText(String.valueOf(p.getDimRisorse("legno")));
						monete2.setText(String.valueOf(p.getDimRisorse("monete")));
						pietre2.setText(String.valueOf(p.getDimRisorse("pietra")));
						servitori2.setText(String.valueOf(p.getDimRisorse("servitori")));
						break;
					case "white":
						puntiMilitariBianco.setImage(new Image(getClass().getResourceAsStream("Disco12.png")));
						puntiFedeBianco.setImage(new Image(getClass().getResourceAsStream("Disco12.png")));
						puntiVittoriaBianco.setImage(new Image(getClass().getResourceAsStream("Disco12.png")));
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
						System.out.println("Nome giocatore avv 3" + name3.getText() + "\n");
						cuboScomunica31.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaBianco.png")));
						cuboScomunica32.setImage(new Image(getClass().getResourceAsStream("CuboScomunicaBianco.png")));
						p = g.getRisorse();
						legno3.setText(String.valueOf(p.getDimRisorse("legno")));
						monete3.setText(String.valueOf(p.getDimRisorse("monete")));
						pietre3.setText(String.valueOf(p.getDimRisorse("pietra")));
						servitori3.setText(String.valueOf(p.getDimRisorse("servitori")));
						break;
					}
				}
			}
			familiareGreen1.setOpacity(1);
			familiareGreen2.setOpacity(1);
			familiareGreen3.setOpacity(1);
			familiareGreen4.setOpacity(1);
			familiareOrange1.setOpacity(1);
			familiareOrange2.setOpacity(1);
			familiareOrange3.setOpacity(1);
			familiareOrange4.setOpacity(1);
			familiareWhite1.setOpacity(1);
			familiareWhite2.setOpacity(1);
			familiareWhite3.setOpacity(1);
			familiareWhite4.setOpacity(1);
			familiareOrange1.setDisable(false);
			familiareOrange2.setDisable(false);
			familiareOrange3.setDisable(false);
			familiareOrange4.setDisable(false);
			familiareWhite1.setDisable(false);
			familiareWhite2.setDisable(false);
			familiareWhite3.setDisable(false);
			familiareWhite4.setDisable(false);
			familiareGreen1.setDisable(false);
			familiareGreen2.setDisable(false);
			familiareGreen3.setDisable(false);
			familiareGreen4.setDisable(false);
			break;
		}
	}

	private void setStart(StartClientGui start) {
		this.start = start;
	}

	public StartClientGui getStart() {
		return start;
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
	@SuppressWarnings("unused")
	public boolean controlloPosizionamento(String color, double x, double y, int addRisorse, ImageView destinazione) {
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
					pay = false;
					List<String> choices = new ArrayList<>();
					for (int i = 0; i < 10; i++) {
						choices.add(String.valueOf(i));
					}
					ChoiceDialog<String> dialog = new ChoiceDialog<>("0", choices);
					dialog.setTitle("Pay or not Pay");
					dialog.setHeaderText(
							"Non potresti posizionare qui il tuo familiare, a meno che non paghi qualche servitore\nVuoi pagare?\nQuanto?");
					dialog.setContentText("Inserisci il numero di servitori:");

					// Traditional way to get the response value.
					Optional<String> result = dialog.showAndWait();
					result.ifPresent(val -> {
						valoreAgg = Integer.parseInt(val);
					});
					try {
						if (start.getClient().scomunicato(2) == 25)
							valoreAgg -= 2;
						if (controlloPosizionamento(color, x, y, valoreAgg, destinazione)) {
							try {
								start.getClient().getRisorse().addRis("servitori", -valoreAgg);
								start.getClient().notifyRisorse("servitori",
										start.getClient().getRisorse().getDimRisorse("servitori"));
							} catch (NumberFormatException | ClassNotFoundException | IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							setFlag();
						}
					} catch (NumberFormatException | RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return pay;
				} else if (mom == null) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.initOwner(start.getStage());
					alert.setTitle("Lost DB connection");
					alert.setContentText("Ci dispiace ma il nostro servizio a smesso di funzionare");
					alert.showAndWait();
					return false;
				} else if (mom.equals("OK")) {
					if (destinazione != null)
						destinazione.setDisable(true);
					return true;
				} else if (mom.equals("NotEnough")) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.initOwner(start.getStage());
					alert.setTitle("Mossa negata");
					alert.setContentText("Non fare il furbo!! Non hai abbastanza servitori!");
					alert.showAndWait();
					return false;
				} else if (mom.equals("Cancel")) {
					return false;
				}
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.initOwner(start.getStage());
				alert.setTitle("Mossa negata");
				alert.setContentText("Non hai abbastanza risorse per acquisire questa carta");
				alert.showAndWait();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	private void setFlag() {
		pay = true;

	}

	public void moveFamAvv(String colorAvv, String colorFamm, double x, double y) {
		setFlagFammAvv(x, y);
		ImageView mom = new ImageView();
		HBox momB = new HBox();
		if (flag)
			mom = getPosFamAvv(x, y);
		else
			momB = getPosFamAvv2(x, y);
		switch (colorAvv) {
		case "blue":
			switch (start.getColor()) {
			case "green":
				switch (colorFamm) {
				case "white":
					if (flag) {
						mom.setImage(familiareGreen3.getImage());
						mom.setDisable(true);
					} else {
						mom.setImage(familiareGreen3.getImage());
						mom.setFitWidth(35);
						mom.setFitHeight(38);
						momB.getChildren().add(mom);
					}
					familiareGreen3.setOpacity(0);
					break;
				case "black":
					if (flag) {
						mom.setImage(familiareGreen4.getImage());
						mom.setDisable(true);
					} else {
						mom.setImage(familiareGreen4.getImage());
						mom.setFitWidth(35);
						mom.setFitHeight(38);
						momB.getChildren().add(mom);
					}
					familiareGreen4.setOpacity(0);
					break;
				case "orange":
					if (flag) {
						mom.setImage(familiareGreen2.getImage());
						mom.setDisable(true);
					} else {
						mom.setImage(familiareGreen2.getImage());
						mom.setFitWidth(35);
						mom.setFitHeight(38);
						momB.getChildren().add(mom);
					}
					familiareGreen2.setOpacity(0);
					break;
				case "neutro":
					if (flag) {
						mom.setImage(familiareGreen1.getImage());
						mom.setDisable(true);
					} else {
						mom.setImage(familiareGreen1.getImage());
						mom.setFitWidth(35);
						mom.setFitHeight(38);
						momB.getChildren().add(mom);
					}
					familiareGreen1.setOpacity(0);
					break;
				}
				break;
			case "orange":
				switch (colorFamm) {
				case "white":
					if (flag) {
						mom.setImage(familiareOrange3.getImage());
						mom.setDisable(true);
					} else {
						mom.setImage(familiareOrange3.getImage());
						mom.setFitWidth(35);
						mom.setFitHeight(38);
						momB.getChildren().add(mom);
					}
					familiareOrange3.setOpacity(0);
					break;
				case "black":
					if (flag) {
						mom.setImage(familiareOrange4.getImage());
						mom.setDisable(true);
					} else {
						mom.setImage(familiareOrange4.getImage());
						mom.setFitWidth(35);
						mom.setFitHeight(38);
						momB.getChildren().add(mom);
					}
					familiareOrange4.setOpacity(0);
					break;
				case "orange":
					if (flag) {
						mom.setImage(familiareOrange2.getImage());
						mom.setDisable(true);
					} else {
						mom.setImage(familiareOrange2.getImage());
						mom.setFitWidth(35);
						mom.setFitHeight(38);
						momB.getChildren().add(mom);
					}
					familiareOrange2.setOpacity(0);
					break;
				case "neutro":
					if (flag) {
						mom.setImage(familiareOrange1.getImage());
						mom.setDisable(true);
					} else {
						mom.setImage(familiareOrange1.getImage());
						mom.setFitWidth(35);
						mom.setFitHeight(38);
						momB.getChildren().add(mom);
					}
					familiareOrange1.setOpacity(0);
					break;
				}
				break;
			case "white":
				switch (colorFamm) {
				case "white":
					if (flag) {
						mom.setImage(familiareWhite3.getImage());
						mom.setDisable(true);
					} else {
						mom.setImage(familiareWhite3.getImage());
						mom.setFitWidth(35);
						mom.setFitHeight(38);
						momB.getChildren().add(mom);
					}
					familiareWhite3.setOpacity(0);
					break;
				case "black":
					if (flag) {
						mom.setImage(familiareWhite4.getImage());
						mom.setDisable(true);
					} else {
						mom.setImage(familiareWhite4.getImage());
						mom.setFitWidth(35);
						mom.setFitHeight(38);
						momB.getChildren().add(mom);
					}
					familiareWhite4.setOpacity(0);
					break;
				case "orange":
					if (flag) {
						mom.setImage(familiareWhite2.getImage());
						mom.setDisable(true);
					} else {
						mom.setImage(familiareWhite2.getImage());
						mom.setFitWidth(35);
						mom.setFitHeight(38);
						momB.getChildren().add(mom);
					}
					familiareWhite2.setOpacity(0);
					break;
				case "neutro":
					if (flag) {
						mom.setImage(familiareWhite1.getImage());
						mom.setDisable(true);
					} else {
						mom.setImage(familiareWhite1.getImage());
						mom.setFitWidth(35);
						mom.setFitHeight(38);
						momB.getChildren().add(mom);
					}
					familiareWhite1.setOpacity(0);
					break;
				}
				break;
			}
			break;
		case "white":
			switch (colorFamm) {
			case "white":
				if (flag) {
					mom.setImage(familiareWhite3.getImage());
					mom.setDisable(true);
				} else {
					mom.setImage(familiareWhite3.getImage());
					mom.setFitWidth(35);
					mom.setFitHeight(38);
					momB.getChildren().add(mom);
				}
				familiareWhite3.setOpacity(0);
				break;
			case "black":
				if (flag) {
					mom.setImage(familiareWhite4.getImage());
					mom.setDisable(true);
				} else {
					mom.setImage(familiareWhite4.getImage());
					mom.setFitWidth(35);
					mom.setFitHeight(38);
					momB.getChildren().add(mom);
				}
				familiareWhite3.setOpacity(0);
				break;
			case "orange":
				if (flag) {
					mom.setImage(familiareWhite2.getImage());
					mom.setDisable(true);
				} else {
					mom.setImage(familiareWhite2.getImage());
					mom.setFitWidth(35);
					mom.setFitHeight(38);
					momB.getChildren().add(mom);
				}
				familiareWhite2.setOpacity(0);
				break;
			case "neutro":
				if (flag) {
					mom.setImage(familiareWhite1.getImage());
					mom.setDisable(true);
				} else {
					mom.setImage(familiareWhite1.getImage());
					mom.setFitWidth(35);
					mom.setFitHeight(38);
					momB.getChildren().add(mom);
				}
				familiareWhite1.setOpacity(0);
				break;
			}
			break;
		case "orange":
			switch (colorFamm) {
			case "white":
				if (flag) {
					mom.setImage(familiareOrange3.getImage());
					mom.setDisable(true);
				} else {
					mom.setImage(familiareOrange3.getImage());
					mom.setFitWidth(35);
					mom.setFitHeight(38);
					momB.getChildren().add(mom);
				}
				familiareOrange3.setOpacity(0);
				break;
			case "black":
				if (flag) {
					mom.setImage(familiareOrange4.getImage());
					mom.setDisable(true);
				} else {
					mom.setImage(familiareOrange4.getImage());
					mom.setFitWidth(35);
					mom.setFitHeight(38);
					momB.getChildren().add(mom);
				}
				familiareOrange4.setOpacity(0);
				break;
			case "orange":
				if (flag) {
					mom.setImage(familiareOrange2.getImage());
					mom.setDisable(true);
				} else {
					mom.setImage(familiareOrange2.getImage());
					mom.setFitWidth(35);
					mom.setFitHeight(38);
					momB.getChildren().add(mom);
				}
				familiareOrange2.setOpacity(0);
				break;
			case "neutro":
				if (flag) {
					mom.setImage(familiareOrange1.getImage());
					mom.setDisable(true);
				} else {
					mom.setImage(familiareOrange1.getImage());
					mom.setFitWidth(35);
					mom.setFitHeight(38);
					momB.getChildren().add(mom);
				}
				familiareOrange1.setOpacity(0);
				break;
			}
			break;
		case "green":
			switch (colorFamm) {
			case "white":
				if (flag) {
					mom.setImage(familiareGreen3.getImage());
					mom.setDisable(true);
				} else {
					mom.setImage(familiareGreen3.getImage());
					mom.setFitWidth(35);
					mom.setFitHeight(38);
					momB.getChildren().add(mom);
				}
				familiareGreen3.setOpacity(0);
				break;
			case "black":
				if (flag) {
					mom.setImage(familiareGreen4.getImage());
					mom.setDisable(true);
				} else {
					mom.setImage(familiareGreen4.getImage());
					mom.setFitWidth(35);
					mom.setFitHeight(38);
					momB.getChildren().add(mom);
				}
				familiareGreen4.setOpacity(0);
				break;
			case "orange":
				if (flag) {
					mom.setImage(familiareGreen2.getImage());
					mom.setDisable(true);
				} else {
					mom.setImage(familiareGreen2.getImage());
					mom.setFitWidth(35);
					mom.setFitHeight(38);
					momB.getChildren().add(mom);
				}
				familiareGreen2.setOpacity(0);
				break;
			case "neutro":
				if (flag) {
					mom.setImage(familiareGreen1.getImage());
					mom.setDisable(true);
				} else {
					mom.setImage(familiareGreen1.getImage());
					mom.setFitWidth(35);
					mom.setFitHeight(38);
					momB.getChildren().add(mom);
				}
				familiareGreen1.setOpacity(0);
				break;
			}
			break;
		}

	}

	private void setFlagFammAvv(double x, double y) {
		if (x == pianoPrimoPalazzoMilitare.getLayoutX() && y == pianoPrimoPalazzoMilitare.getLayoutY()) {
			flag = true;
		} else if (x == pianoSecondoPalazzoMilitare.getLayoutX() && y == pianoSecondoPalazzoMilitare.getLayoutY()) {
			flag = true;
		} else if (x == pianoTerzoPalazzoMilitare.getLayoutX() && y == pianoTerzoPalazzoMilitare.getLayoutY()) {
			flag = true;
		} else if (x == pianoQuartoPalazzoMilitare.getLayoutX() && y == pianoQuartoPalazzoMilitare.getLayoutY()) {
			flag = true;
		} else if (x == pianoPrimoPalazzoPersonaggi.getLayoutX() && y == pianoPrimoPalazzoPersonaggi.getLayoutY()) {
			flag = true;
		} else if (x == pianoSecondoPalazzoPersonaggi.getLayoutX() && y == pianoSecondoPalazzoPersonaggi.getLayoutY()) {
			flag = true;
		} else if (x == pianoTerzoPalazzoPersonaggi.getLayoutX() && y == pianoTerzoPalazzoPersonaggi.getLayoutY()) {
			flag = true;
		} else if (x == pianoQuartoPalazzoPersonaggi.getLayoutX() && y == pianoQuartoPalazzoPersonaggi.getLayoutY()) {
			flag = true;
		} else if (x == pianoQuartoPalazzoPersonaggi.getLayoutX() && y == pianoQuartoPalazzoPersonaggi.getLayoutX()) {
			flag = true;
		} else if (x == pianoPrimoPalazzoEdifici.getLayoutX() && y == pianoPrimoPalazzoEdifici.getLayoutY()) {
			flag = true;
		} else if (x == pianoSecondoPalazzoEdifici.getLayoutX() && y == pianoSecondoPalazzoEdifici.getLayoutY()) {
			flag = true;
		} else if (x == pianoTerzoPalazzoEdifici.getLayoutX() && y == pianoTerzoPalazzoEdifici.getLayoutY()) {
			flag = true;
		} else if (x == pianoQuartoPalazzoEdifici.getLayoutX() && y == pianoQuartoPalazzoEdifici.getLayoutY()) {
			flag = true;
		} else if (x == pianoPrimoPalazzoTerritori.getLayoutX() && y == pianoPrimoPalazzoTerritori.getLayoutY()) {
			flag = true;
		} else if (x == pianoSecondoPalazzoTerritori.getLayoutX() && y == pianoSecondoPalazzoTerritori.getLayoutY()) {
			flag = true;
		} else if (x == pianoTerzoPalazzoTerritori.getLayoutX() && y == pianoTerzoPalazzoTerritori.getLayoutY()) {
			flag = true;
		} else if (x == pianoQuartoPalazzoTerritori.getLayoutX() && y == pianoQuartoPalazzoTerritori.getLayoutY()) {
			flag = true;
		} else if (x == azioniEdificidapiuGiocatori.getLayoutX() && y == azioniEdificidapiuGiocatori.getLayoutY()) {
			flag = false;
		} else if (x == azioniTerritoridapiuGiocatori.getLayoutX() && y == azioniTerritoridapiuGiocatori.getLayoutY()) {
			flag = false;
		} else if (x == azioniTerritoridaunGiocatore.getLayoutX() && y == azioniTerritoridaunGiocatore.getLayoutY()) {
			flag = true;
		} else if (x == azioniEdificidaunGiocatore.getLayoutX() && y == azioniEdificidaunGiocatore.getLayoutY()) {
			flag = true;
		} else if (x == municipio.getLayoutX() && y == municipio.getLayoutY()) {
			flag = false;
		} else if (x == mercatoPosMoneteMilitari.getLayoutX() && y == mercatoPosMoneteMilitari.getLayoutY()) {
			flag = true;
		} else if (x == mercatoPosServitori.getLayoutX() && y == mercatoPosServitori.getLayoutY()) {
			flag = true;
		} else if (x == mercatoPosMonete.getLayoutX() && y == mercatoPosMonete.getLayoutY()) {
			flag = true;
		} else if (x == mercatoPosMunicipio.getLayoutX() && y == mercatoPosMunicipio.getLayoutY()) {
			flag = true;
		}

	}

	private HBox getPosFamAvv2(double x, double y) {
		if (x == azioniEdificidapiuGiocatori.getLayoutX() && y == azioniEdificidapiuGiocatori.getLayoutY()) {
			flag = false;
			return azioniEdificidapiuGiocatori;
		} else if (x == azioniTerritoridapiuGiocatori.getLayoutX() && y == azioniTerritoridapiuGiocatori.getLayoutY()) {
			flag = false;
			return azioniTerritoridapiuGiocatori;
		} else if (x == municipio.getLayoutX() && y == municipio.getLayoutY()) {
			flag = false;
			return municipio;
		}
		return null;
	}

	private ImageView getPosFamAvv(double x, double y) {
		if (x == pianoPrimoPalazzoMilitare.getLayoutX() && y == pianoPrimoPalazzoMilitare.getLayoutY()) {
			flag = true;
			return pianoPrimoPalazzoMilitare;
		} else if (x == pianoSecondoPalazzoMilitare.getLayoutX() && y == pianoSecondoPalazzoMilitare.getLayoutY()) {
			flag = true;
			return pianoSecondoPalazzoMilitare;
		} else if (x == pianoTerzoPalazzoMilitare.getLayoutX() && y == pianoTerzoPalazzoMilitare.getLayoutY()) {
			flag = true;
			return pianoTerzoPalazzoMilitare;
		} else if (x == pianoQuartoPalazzoMilitare.getLayoutX() && y == pianoQuartoPalazzoMilitare.getLayoutY()) {
			flag = true;
			return pianoQuartoPalazzoMilitare;
		} else if (x == pianoPrimoPalazzoPersonaggi.getLayoutX() && y == pianoPrimoPalazzoPersonaggi.getLayoutY()) {
			flag = true;
			return pianoPrimoPalazzoPersonaggi;
		} else if (x == pianoSecondoPalazzoPersonaggi.getLayoutX() && y == pianoSecondoPalazzoPersonaggi.getLayoutY()) {
			flag = true;
			return pianoSecondoPalazzoPersonaggi;
		} else if (x == pianoTerzoPalazzoPersonaggi.getLayoutX() && y == pianoTerzoPalazzoPersonaggi.getLayoutY()) {
			flag = true;
			return pianoTerzoPalazzoPersonaggi;
		} else if (x == pianoQuartoPalazzoPersonaggi.getLayoutX() && y == pianoQuartoPalazzoPersonaggi.getLayoutY()) {
			flag = true;
			return pianoQuartoPalazzoPersonaggi;
		} else if (x == pianoQuartoPalazzoPersonaggi.getLayoutX() && y == pianoQuartoPalazzoPersonaggi.getLayoutX()) {
			flag = true;
			return pianoQuartoPalazzoPersonaggi;
		} else if (x == pianoPrimoPalazzoEdifici.getLayoutX() && y == pianoPrimoPalazzoEdifici.getLayoutY()) {
			flag = true;
			return pianoPrimoPalazzoEdifici;
		} else if (x == pianoSecondoPalazzoEdifici.getLayoutX() && y == pianoSecondoPalazzoEdifici.getLayoutY()) {
			flag = true;
			return pianoSecondoPalazzoEdifici;
		} else if (x == pianoTerzoPalazzoEdifici.getLayoutX() && y == pianoTerzoPalazzoEdifici.getLayoutY()) {
			flag = true;
			return pianoTerzoPalazzoEdifici;
		} else if (x == pianoQuartoPalazzoEdifici.getLayoutX() && y == pianoQuartoPalazzoEdifici.getLayoutY()) {
			flag = true;
			return pianoQuartoPalazzoEdifici;
		} else if (x == pianoPrimoPalazzoTerritori.getLayoutX() && y == pianoPrimoPalazzoTerritori.getLayoutY()) {
			flag = true;
			return pianoPrimoPalazzoTerritori;
		} else if (x == pianoSecondoPalazzoTerritori.getLayoutX() && y == pianoSecondoPalazzoTerritori.getLayoutY()) {
			flag = true;
			return pianoSecondoPalazzoTerritori;
		} else if (x == pianoTerzoPalazzoTerritori.getLayoutX() && y == pianoTerzoPalazzoTerritori.getLayoutY()) {
			flag = true;
			return pianoTerzoPalazzoTerritori;
		} else if (x == pianoQuartoPalazzoTerritori.getLayoutX() && y == pianoQuartoPalazzoTerritori.getLayoutY()) {
			flag = true;
			return pianoQuartoPalazzoTerritori;
		} else if (x == azioniTerritoridaunGiocatore.getLayoutX() && y == azioniTerritoridaunGiocatore.getLayoutY()) {
			flag = true;
			return azioniTerritoridaunGiocatore;
		} else if (x == azioniEdificidaunGiocatore.getLayoutX() && y == azioniEdificidaunGiocatore.getLayoutY()) {
			flag = true;
			return azioniEdificidaunGiocatore;
		} else if (x == mercatoPosMoneteMilitari.getLayoutX() && y == mercatoPosMoneteMilitari.getLayoutY()) {
			flag = true;
			return mercatoPosMoneteMilitari;
		} else if (x == mercatoPosServitori.getLayoutX() && y == mercatoPosServitori.getLayoutY()) {
			flag = true;
			return mercatoPosServitori;
		} else if (x == mercatoPosMonete.getLayoutX() && y == mercatoPosMonete.getLayoutY()) {
			flag = true;
			return mercatoPosMonete;
		} else if (x == mercatoPosMunicipio.getLayoutX() && y == mercatoPosMunicipio.getLayoutY()) {
			flag = true;
			return mercatoPosMunicipio;
		}
		return null;
	}

	public void addScomunica(int nScomuniche, String name) {
		if (name.equals(start.getClient().getName())) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Avvenuta Scomunica");
			alert.setHeaderText("Mannaggia a te che non sostieni la chiesa");
			alert.setContentText("Ooops, hai preso una scomunica!");
			alert.showAndWait();
			switch (nScomuniche) {
			case 0:
				cuboScomunica1.setLayoutY(471.0);
				cuboScomunica1.setLayoutX(113.);
				Tooltip.install(cuboScomunica1, tessereScomunica[0].getTooltip());
				Tooltip.uninstall(cartaScomunica1, tessereScomunica[0].getTooltip());
				break;
			case 1:
				cuboScomunica1.setLayoutY(476.0);
				cuboScomunica1.setLayoutX(154.0);
				Tooltip.install(cuboScomunica2, tessereScomunica[1].getTooltip());
				Tooltip.uninstall(cartaScomunica2, tessereScomunica[1].getTooltip());
				break;
			case 2:
				cuboScomunica1.setLayoutY(471.0);
				cuboScomunica1.setLayoutX(196.0);
				Tooltip.install(cuboScomunica3, tessereScomunica[2].getTooltip());
				Tooltip.uninstall(cartaScomunica3, tessereScomunica[2].getTooltip());
				break;
			}
		} else if (name.equals(name1.getText())) {
			switch (nScomuniche) {
			case 0:
				cuboScomunica11.setOpacity(1);
				Tooltip.install(cuboScomunica11, tessereScomunica[0].getTooltip());
				break;
			case 1:
				cuboScomunica12.setOpacity(1);
				Tooltip.install(cuboScomunica12, tessereScomunica[1].getTooltip());
				break;
			case 2:
				cuboScomunica13.setOpacity(1);
				Tooltip.install(cuboScomunica13, tessereScomunica[2].getTooltip());
				break;
			}
		} else if (name.equals(name2.getText())) {
			switch (nScomuniche) {
			case 0:
				cuboScomunica21.setOpacity(1);
				Tooltip.install(cuboScomunica21, tessereScomunica[0].getTooltip());
				break;
			case 1:
				cuboScomunica22.setOpacity(1);
				Tooltip.install(cuboScomunica22, tessereScomunica[1].getTooltip());
				break;
			case 2:
				cuboScomunica23.setOpacity(1);
				Tooltip.install(cuboScomunica23, tessereScomunica[2].getTooltip());
				break;
			}
		} else {
			switch (nScomuniche) {
			case 0:
				cuboScomunica31.setOpacity(1);
				Tooltip.install(cuboScomunica31, tessereScomunica[0].getTooltip());
				break;
			case 1:
				cuboScomunica32.setOpacity(1);
				Tooltip.install(cuboScomunica32, tessereScomunica[1].getTooltip());
				break;
			case 2:
				cuboScomunica33.setOpacity(1);
				Tooltip.install(cuboScomunica33, tessereScomunica[2].getTooltip());
				break;
			}
		}
	}

	public boolean controlCard(double x, double y) throws IOException {
		Portafoglio p = new Portafoglio();
		try {
			p = start.getClient().getRisorse();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String pos = "ciao";
		pos = getNamePosition(x, y);
		System.out.println(pos);
		switch (pos) {
		case "PIANO 1 CARTE EDIFICI":
			if (arrayCarteEdifici[3].getCostoLegno() < p.getDimRisorse("legno")
					&& arrayCarteEdifici[3].getCostoMoneta() < p.getDimRisorse("monete")
					&& arrayCarteEdifici[3].getCostoPietra() < p.getDimRisorse("pietra")
					&& arrayCarteEdifici[3].getCostoServitori() < p.getDimRisorse("servitori"))
				return false;
			return true;
		case "PIANO 2 CARTE EDIFICI":
			if (arrayCarteEdifici[2].getCostoLegno() < p.getDimRisorse("legno")
					&& arrayCarteEdifici[2].getCostoMoneta() < p.getDimRisorse("monete")
					&& arrayCarteEdifici[2].getCostoPietra() < p.getDimRisorse("pietra")
					&& arrayCarteEdifici[2].getCostoServitori() < p.getDimRisorse("servitori"))
				return false;
			return true;
		case "PIANO 3 CARTE EDIFICI":
			if (arrayCarteEdifici[1].getCostoLegno() < p.getDimRisorse("legno")
					&& arrayCarteEdifici[1].getCostoMoneta() < p.getDimRisorse("monete")
					&& arrayCarteEdifici[1].getCostoPietra() < p.getDimRisorse("pietra")
					&& arrayCarteEdifici[1].getCostoServitori() < p.getDimRisorse("servitori"))
				return false;
			return true;
		case "PIANO 4 CARTE EDIFICI":
			if (arrayCarteEdifici[0].getCostoLegno() < p.getDimRisorse("legno")
					&& arrayCarteEdifici[0].getCostoMoneta() < p.getDimRisorse("monete")
					&& arrayCarteEdifici[0].getCostoPietra() < p.getDimRisorse("pietra")
					&& arrayCarteEdifici[0].getCostoServitori() < p.getDimRisorse("servitori"))
				return false;
			return true;
		case "PIANO 1 CARTE IMPRESE":
			if ((arrayCarteImpresa[3].getCostoLegno() < p.getDimRisorse("legno")
					&& arrayCarteImpresa[3].getCostoMoneta() < p.getDimRisorse("monete")
					&& arrayCarteImpresa[3].getCostoPietra() < p.getDimRisorse("pietra")
					&& arrayCarteImpresa[3].getCostoServitori() < p.getDimRisorse("servitori"))
					|| (arrayCarteImpresa[3].getPuntiMilitariRichiesti() < p.getPunti("militari")))
				return false;
			return true;
		case "PIANO 2 CARTE IMPRESE":
			if ((arrayCarteImpresa[2].getCostoLegno() < p.getDimRisorse("legno")
					&& arrayCarteImpresa[2].getCostoMoneta() < p.getDimRisorse("monete")
					&& arrayCarteImpresa[2].getCostoPietra() < p.getDimRisorse("pietra")
					&& arrayCarteImpresa[2].getCostoServitori() < p.getDimRisorse("servitori"))
					|| (arrayCarteImpresa[2].getPuntiMilitariRichiesti() < p.getPunti("militari")))
				return false;
			return true;
		case "PIANO 3 CARTE IMPRESE":
			if ((arrayCarteImpresa[1].getCostoLegno() < p.getDimRisorse("legno")
					&& arrayCarteImpresa[1].getCostoMoneta() < p.getDimRisorse("monete")
					&& arrayCarteImpresa[1].getCostoPietra() < p.getDimRisorse("pietra")
					&& arrayCarteImpresa[1].getCostoServitori() < p.getDimRisorse("servitori"))
					|| (arrayCarteImpresa[1].getPuntiMilitariRichiesti() < p.getPunti("militari")))
				return false;
			return true;
		case "PIANO 4 CARTE IMPRESE":
			if ((arrayCarteImpresa[0].getCostoLegno() < p.getDimRisorse("legno")
					&& arrayCarteImpresa[0].getCostoMoneta() < p.getDimRisorse("monete")
					&& arrayCarteImpresa[0].getCostoPietra() < p.getDimRisorse("pietra")
					&& arrayCarteImpresa[0].getCostoServitori() < p.getDimRisorse("servitori"))
					|| (arrayCarteImpresa[0].getPuntiMilitariRichiesti() < p.getPunti("militari")))
				return false;
			return true;
		case "PIANO 1 CARTE PERSONAGGI":
			if (arrayCartePersonaggi[3].getCostoMoneta() < p.getDimRisorse("monete"))
				return false;
			return true;
		case "PIANO 2 CARTE PERSONAGGI":
			if (arrayCartePersonaggi[2].getCostoMoneta() < p.getDimRisorse("monete"))
				return false;
			return true;
		case "PIANO 3 CARTE PERSONAGGI":
			if (arrayCartePersonaggi[1].getCostoMoneta() < p.getDimRisorse("monete"))
				return false;
			return true;
		case "PIANO 4 CARTE PERSONAGGI":
			if (arrayCartePersonaggi[0].getCostoMoneta() < p.getDimRisorse("monete"))
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
			mom.setFitHeight(97.5);
			mom.setFitWidth(63);
			mom.setImage(new Image(getClass().getResourceAsStream(carte[i].getImage())));
			Tooltip.install(mom, carte[i].getTooltip());
			carteTerritori.getChildren().add(mom);
		}
		for (int i = 0; i < 4; i++) {
			arrayCartePersonaggi[i] = new CartaPersonaggi();
			arrayCartePersonaggi[i] = (CartaPersonaggi) carte[i + 4];
			ImageView mom = new ImageView();
			mom.setFitHeight(97.5);
			mom.setFitWidth(63);
			mom.setImage(new Image(getClass().getResourceAsStream(carte[i + 4].getImage())));
			Tooltip.install(mom, carte[i + 4].getTooltip());
			cartePersonaggi.getChildren().add(mom);
		}
		for (int i = 0; i < 4; i++) {
			arrayCarteEdifici[i] = new CartaEdifici();
			arrayCarteEdifici[i] = (CartaEdifici) carte[i + 8];
			ImageView mom = new ImageView();
			mom.setFitHeight(97.5);
			mom.setFitWidth(63);
			mom.setImage(new Image(getClass().getResourceAsStream(carte[i + 8].getImage())));
			Tooltip.install(mom, carte[i + 8].getTooltip());
			carteEdifici.getChildren().add(mom);
		}
		for (int i = 0; i < 4; i++) {
			arrayCarteImpresa[i] = new CartaImprese();
			arrayCarteImpresa[i] = (CartaImprese) carte[i + 12];
			ImageView mom = new ImageView();
			mom.setFitHeight(97.5);
			mom.setFitWidth(63);
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
			String mom = start.getClient().getNamePosition(x, y);
			System.out.println(mom);
			return mom;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			if (x == 265.0 && y == 440.0)
				return "ZONA MERCATO";
			if (x == 100.0 && y == 691.0)
				return "AZIONE RACCOLTO 4";
			if (x == 105.0 && y == 628.0)
				return "AZIONE PRODUZIONE 4";
		}
		return "ciao";
	}

	public void setCardGiocatore(String namePosition, int piano, int tipo, String coloreFam) throws IOException {
		ImageView mom;
		switch (namePosition) {
		case "PIANO 1 FAMILIARE TERRITORI":
			mom = new ImageView(new Image(getClass().getResourceAsStream(arrayCarteTerritori[3].getImage())));
			mom.setFitHeight(130);
			mom.setFitWidth(90);
			Tooltip.install(mom, arrayCarteTerritori[3].getTooltip());
			carteTerritoriGiocatore.getChildren().add(mom);
			sistemaCarte(0, 3);
			start.getClient().setCardGiocatore(arrayCarteTerritori[3], 0, 3);
			break;
		case "PIANO 2 FAMILIARE TERRITORI":
			mom = new ImageView(new Image(getClass().getResourceAsStream(arrayCarteTerritori[2].getImage())));
			mom.setFitHeight(130);
			mom.setFitWidth(90);
			Tooltip.install(mom, arrayCarteTerritori[2].getTooltip());
			carteTerritoriGiocatore.getChildren().add(mom);
			sistemaCarte(0, 2);
			start.getClient().setCardGiocatore(arrayCarteTerritori[2], 0, 2);
			break;
		case "PIANO 3 FAMILIARE TERRITORI":
			mom = new ImageView(new Image(getClass().getResourceAsStream(arrayCarteTerritori[1].getImage())));
			mom.setFitHeight(130);
			mom.setFitWidth(90);
			Tooltip.install(mom, arrayCarteTerritori[1].getTooltip());
			carteTerritoriGiocatore.getChildren().add(mom);
			sistemaCarte(0, 1);
			try {
				start.getClient().addRisorse("legno", 1);
				start.getClient().notifyRisorse("legno", start.getClient().getRisorse().getDimRisorse("legno"));
			} catch (SQLException | ClassNotFoundException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			start.getClient().setCardGiocatore(arrayCarteTerritori[1], 0, 1);
			break;
		case "PIANO 4 FAMILIARE TERRITORI":
			mom = new ImageView(new Image(getClass().getResourceAsStream(arrayCarteTerritori[0].getImage())));
			mom.setFitHeight(130);
			mom.setFitWidth(90);
			Tooltip.install(mom, arrayCarteTerritori[0].getTooltip());
			carteTerritoriGiocatore.getChildren().add(mom);
			sistemaCarte(0, 0);
			try {
				start.getClient().addRisorse("legno", 2);
				start.getClient().notifyRisorse("legno", start.getClient().getRisorse().getDimRisorse("legno"));
			} catch (SQLException | ClassNotFoundException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			start.getClient().setCardGiocatore(arrayCarteTerritori[0], 0, 0);
			break;
		case "PIANO 1 FAMILIARE EDIFICI":
			mom = new ImageView(new Image(getClass().getResourceAsStream(arrayCarteEdifici[3].getImage())));
			mom.setFitHeight(130);
			mom.setFitWidth(90);
			Tooltip.install(mom, arrayCarteEdifici[3].getTooltip());
			carteEdificiGiocatore.getChildren().add(mom);
			sistemaCarte(2, 3);
			start.getClient().setCardGiocatore(arrayCarteEdifici[3], 2, 3);
			break;
		case "PIANO 2 FAMILIARE EDIFICI":
			mom = new ImageView(new Image(getClass().getResourceAsStream(arrayCarteEdifici[2].getImage())));
			mom.setFitHeight(130);
			mom.setFitWidth(90);
			Tooltip.install(mom, arrayCarteEdifici[2].getTooltip());
			carteEdificiGiocatore.getChildren().add(mom);
			sistemaCarte(2, 2);
			start.getClient().setCardGiocatore(arrayCarteEdifici[2], 2, 2);
			break;
		case "PIANO 3 FAMILIARE EDIFICI":
			mom = new ImageView(new Image(getClass().getResourceAsStream(arrayCarteEdifici[1].getImage())));
			mom.setFitHeight(130);
			mom.setFitWidth(90);
			Tooltip.install(mom, arrayCarteEdifici[1].getTooltip());
			carteEdificiGiocatore.getChildren().add(mom);
			try {
				start.getClient().addPunti("militari", 1);
				start.getClient().notifySpostamentoPunti("militari");
			} catch (SQLException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			}
			sistemaCarte(2, 1);
			start.getClient().setCardGiocatore(arrayCarteEdifici[1], 2, 1);
			break;
		case "PIANO 4 FAMILIARE EDIFICI":
			mom = new ImageView(new Image(getClass().getResourceAsStream(arrayCarteEdifici[0].getImage())));
			mom.setFitHeight(130);
			mom.setFitWidth(90);
			Tooltip.install(mom, arrayCarteEdifici[0].getTooltip());
			carteEdificiGiocatore.getChildren().add(mom);
			try {
				start.getClient().addPunti("militari", 2);
				start.getClient().notifySpostamentoPunti("militari");
			} catch (SQLException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			}
			sistemaCarte(2, 0);
			start.getClient().setCardGiocatore(arrayCarteEdifici[0], 2, 0);
			break;
		case "PIANO 1 FAMILIARE IMPRESE":
			mom = new ImageView(new Image(getClass().getResourceAsStream(arrayCarteImpresa[3].getImage())));
			mom.setFitHeight(130);
			mom.setFitWidth(90);
			Tooltip.install(mom, arrayCarteImpresa[3].getTooltip());
			carteImpresaGiocatore.getChildren().add(mom);
			sistemaCarte(3, 3);
			start.getClient().setCardGiocatore(arrayCarteImpresa[3], 3, 3);
			break;
		case "PIANO 2 FAMILIARE IMPRESE":
			mom = new ImageView(new Image(getClass().getResourceAsStream(arrayCarteImpresa[2].getImage())));
			mom.setFitHeight(130);
			mom.setFitWidth(90);
			Tooltip.install(mom, arrayCarteImpresa[2].getTooltip());
			carteImpresaGiocatore.getChildren().add(mom);
			sistemaCarte(3, 2);
			start.getClient().setCardGiocatore(arrayCarteImpresa[2], 3, 2);
			break;
		case "PIANO 3 FAMILIARE IMPRESE":
			mom = new ImageView(new Image(getClass().getResourceAsStream(arrayCarteImpresa[1].getImage())));
			mom.setFitHeight(130);
			mom.setFitWidth(90);
			Tooltip.install(mom, arrayCarteImpresa[1].getTooltip());
			carteImpresaGiocatore.getChildren().add(mom);
			try {
				start.getClient().addRisorse("monete", 1);
				start.getClient().notifyRisorse("monete", start.getClient().getRisorse().getDimRisorse("monete"));
			} catch (SQLException | ClassNotFoundException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			}
			sistemaCarte(3, 1);
			start.getClient().setCardGiocatore(arrayCarteImpresa[1], 3, 1);
			break;
		case "PIANO 4 FAMILIARE IMPRESE":
			mom = new ImageView(new Image(getClass().getResourceAsStream(arrayCarteImpresa[0].getImage())));
			mom.setFitHeight(130);
			mom.setFitWidth(90);
			Tooltip.install(mom, arrayCarteImpresa[0].getTooltip());
			carteImpresaGiocatore.getChildren().add(mom);
			try {
				start.getClient().addRisorse("monete", 2);
				start.getClient().notifyRisorse("monete", start.getClient().getRisorse().getDimRisorse("monete"));
			} catch (SQLException | ClassNotFoundException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			}
			sistemaCarte(3, 0);
			start.getClient().setCardGiocatore(arrayCarteImpresa[0], 3, 0);
			break;
		case "PIANO 1 FAMILIARE PERSONAGGI":
			mom = new ImageView(new Image(getClass().getResourceAsStream(arrayCartePersonaggi[3].getImage())));
			mom.setFitHeight(130);
			mom.setFitWidth(90);
			Tooltip.install(mom, arrayCartePersonaggi[3].getTooltip());
			cartePersonaggiGiocatore.getChildren().add(mom);
			sistemaCarte(1, 3);
			start.getClient().setCardGiocatore(arrayCartePersonaggi[3], 1, 3);
			break;
		case "PIANO 2 FAMILIARE PERSONAGGI":
			mom = new ImageView(new Image(getClass().getResourceAsStream(arrayCartePersonaggi[2].getImage())));
			mom.setFitHeight(130);
			mom.setFitWidth(90);
			Tooltip.install(mom, arrayCartePersonaggi[2].getTooltip());
			cartePersonaggiGiocatore.getChildren().add(mom);
			sistemaCarte(1, 2);
			start.getClient().setCardGiocatore(arrayCartePersonaggi[2], 1, 2);
			break;
		case "PIANO 3 FAMILIARE PERSONAGGI":
			mom = new ImageView(new Image(getClass().getResourceAsStream(arrayCartePersonaggi[1].getImage())));
			mom.setFitHeight(130);
			mom.setFitWidth(90);
			Tooltip.install(mom, arrayCartePersonaggi[1].getTooltip());
			cartePersonaggiGiocatore.getChildren().add(mom);
			sistemaCarte(1, 1);
			try {
				start.getClient().addRisorse("pietra", 1);
				start.getClient().notifyRisorse("pietra", start.getClient().getRisorse().getDimRisorse("pietra"));
			} catch (SQLException | ClassNotFoundException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			start.getClient().setCardGiocatore(arrayCartePersonaggi[1], 1, 1);
			break;
		case "PIANO 4 FAMILIARE PERSONAGGI":
			mom = new ImageView(new Image(getClass().getResourceAsStream(arrayCartePersonaggi[0].getImage())));
			mom.setFitHeight(130);
			mom.setFitWidth(90);
			Tooltip.install(mom, arrayCartePersonaggi[0].getTooltip());
			cartePersonaggiGiocatore.getChildren().add(mom);
			sistemaCarte(1, 0);
			try {
				start.getClient().addRisorse("pietra", 2);
				start.getClient().notifyRisorse("pietra", start.getClient().getRisorse().getDimRisorse("pietra"));
			} catch (SQLException | ClassNotFoundException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			start.getClient().setCardGiocatore(arrayCartePersonaggi[0], 1, 0);
			break;
		case "AZIONE PRODUZIONE 4":
			switch (coloreFam) {
			case "neutro":
				start.getClient().produzione(valoreAgg - 3);
				valoreAgg = 0;
				break;
			case "black":
				start.getClient().produzione(start.getClient().getDado("black") - 3);
				break;
			case "orange":
				start.getClient().produzione(start.getClient().getDado("orange") - 3);
				break;
			case "white":
				start.getClient().produzione(start.getClient().getDado("white") - 3);
				break;
			}
			break;
		case "AZIONE PRODUZIONE 1":
			switch (coloreFam) {
			case "neutro":
				start.getClient().produzione(valoreAgg);
				valoreAgg = 0;
				break;
			case "black":
				start.getClient().produzione(start.getClient().getDado("black"));
				break;
			case "orange":
				start.getClient().produzione(start.getClient().getDado("orange"));
				break;
			case "white":
				start.getClient().produzione(start.getClient().getDado("white"));
				break;
			}
			break;
		case "AZIONE RACCOLTO 4":
			switch (coloreFam) {
			case "neutro":
				start.getClient().raccolto(valoreAgg - 3);
				valoreAgg = 0;
				break;
			case "black":
				start.getClient().raccolto(start.getClient().getDado("black") - 3);
				break;
			case "orange":
				start.getClient().raccolto(start.getClient().getDado("orange") - 3);
				break;
			case "white":
				start.getClient().raccolto(start.getClient().getDado("white") - 3);
				break;
			}
			break;
		case "AZIONE RACCOLTO 1":
			switch (coloreFam) {
			case "neutro":
				start.getClient().raccolto(valoreAgg);
				valoreAgg = 0;
				break;
			case "black":
				start.getClient().raccolto(start.getClient().getDado("black"));
				break;
			case "orange":
				start.getClient().raccolto(start.getClient().getDado("orange"));
				break;
			case "white":
				start.getClient().raccolto(start.getClient().getDado("white"));
				break;
			}
			break;
		case "PRENDI 5 MONETE":
			try {
				start.getClient().addRisorse("monete", 5);
				start.getClient().notifyRisorse("monete", start.getClient().getRisorse().getDimRisorse("monete"));
			} catch (SQLException | ClassNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			break;
		case "PRENDI 5 SERVITORI":
			try {
				start.getClient().addRisorse("servitori", 5);
				start.getClient().notifyRisorse("servitori", start.getClient().getRisorse().getDimRisorse("servitori"));
			} catch (SQLException | ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case "PRENDI 2 MONETE E 3 MILITARI":
			try {
				start.getClient().addRisorse("monete", 2);
				start.getClient().addPunti("militari", 5);
				start.getClient().notifyRisorse("monete", start.getClient().getRisorse().getDimRisorse("monete"));
				start.getClient().notifySpostamentoPunti("militari");
			} catch (SQLException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case "PRENDI 2 PERGAMENE":
			start.getClient().addPergamene(2);
			break;
		case "ZONA MERCATO":
			start.getClient().addPergamene(1);
			try {
				start.getClient().addRisorse("monete", 1);
				start.getClient().notifyRisorse("monete", start.getClient().getRisorse().getDimRisorse("monete"));
			} catch (SQLException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "tutteCarte":
			switch (tipo) {
			case 0:
				mom = new ImageView(new Image(getClass().getResourceAsStream(arrayCarteTerritori[piano].getImage())));
				mom.setFitWidth(90);
				mom.setFitHeight(130);
				carteTerritoriGiocatore.getChildren().add(mom);
				break;
			case 1:
				mom = new ImageView(new Image(getClass().getResourceAsStream(arrayCartePersonaggi[piano].getImage())));
				mom.setFitWidth(90);
				mom.setFitHeight(130);
				cartePersonaggiGiocatore.getChildren().add(mom);
				break;
			case 2:
				mom = new ImageView(new Image(getClass().getResourceAsStream(arrayCarteEdifici[piano].getImage())));
				mom.setFitWidth(90);
				mom.setFitHeight(130);
				carteEdificiGiocatore.getChildren().add(mom);
				break;
			case 3:
				mom = new ImageView(new Image(getClass().getResourceAsStream(arrayCarteImpresa[piano].getImage())));
				mom.setFitWidth(90);
				mom.setFitHeight(130);
				carteImpresaGiocatore.getChildren().add(mom);
				break;

			}
			break;
		}
	}

	private void sistemaCarte(int tipo, int piano) {
		ImageView mom = new ImageView(trasparente);
		mom.setFitHeight(97.5);
		mom.setFitWidth(63);
		switch (tipo) {
		case 0:
			carteTerritori.getChildren().set(piano, mom);
			break;
		case 1:
			cartePersonaggi.getChildren().set(piano, mom);
			break;
		case 2:
			carteEdifici.getChildren().set(piano, mom);
			break;
		case 3:
			carteImprese.getChildren().set(piano, mom);
			break;
		}

	}

	public void enableGame(int turno) {
		isInTurno = true;
		nFamPos = 0;
		System.out.println("Abilito i bottone dei dadi");
		this.turno.setText(String.valueOf(turno));
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(start.getStage());
		alert.setTitle("Notifica Turno");
		alert.setContentText("Hei, e' iniziato il tuo turno lancia i dadi e dopo fai le tue mosse");
		alert.showAndWait();
		lanciaDadi.setDisable(false);
	}

	public void resetTabellon() throws ClassNotFoundException, IOException {
		start.getClient().changeCards();
		setPosizioni();
		familiareNeutro.setVisible(true);
		familiareNero.setVisible(true);
		familiareArancio.setVisible(true);
		familiareBianco.setVisible(true);
		setColorsParents(start.getClient().getColor());
		try {
			setCardsOtherTurn(start.getClient().getCardsGame());
			for (int i = 0; i < 4; i++) {
				azioniTerritoridapiuGiocatori.getChildren().remove(i);
				azioniEdificidapiuGiocatori.getChildren().remove(i);
				municipio.getChildren().remove(i);
			}
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("Durante il reset del tabellone qualcosa non era pieno");
			e.printStackTrace();
		}
		start.getClient().waitTurno();
	}

	private void setCardsOtherTurn(CartaSviluppo[] cardsGame) {
		for (int i = 0; i < 4; i++) {
			arrayCarteTerritori[i] = new CartaTerritori();
			arrayCarteTerritori[i] = (CartaTerritori) cardsGame[i];
			ImageView mom = new ImageView();
			System.out.println(cardsGame[i].getImage());
			mom.setFitHeight(97.5);
			mom.setFitWidth(63);
			mom.setImage(new Image(getClass().getResourceAsStream(cardsGame[i].getImage())));
			Tooltip.install(mom, cardsGame[i].getTooltip());
			carteTerritori.getChildren().set(i, mom);
		}
		for (int i = 0; i < 4; i++) {
			arrayCartePersonaggi[i] = new CartaPersonaggi();
			arrayCartePersonaggi[i] = (CartaPersonaggi) cardsGame[i + 4];
			ImageView mom = new ImageView();
			mom.setFitHeight(97.5);
			mom.setFitWidth(63);
			mom.setImage(new Image(getClass().getResourceAsStream(cardsGame[i + 4].getImage())));
			Tooltip.install(mom, cardsGame[i + 4].getTooltip());
			cartePersonaggi.getChildren().set(i, mom);
		}
		for (int i = 0; i < 4; i++) {
			arrayCarteEdifici[i] = new CartaEdifici();
			arrayCarteEdifici[i] = (CartaEdifici) cardsGame[i + 8];
			ImageView mom = new ImageView();
			mom.setFitHeight(97.5);
			mom.setFitWidth(63);
			mom.setImage(new Image(getClass().getResourceAsStream(cardsGame[i + 8].getImage())));
			Tooltip.install(mom, cardsGame[i + 8].getTooltip());
			carteEdifici.getChildren().set(i, mom);
		}
		for (int i = 0; i < 4; i++) {
			arrayCarteImpresa[i] = new CartaImprese();
			arrayCarteImpresa[i] = (CartaImprese) cardsGame[i + 12];
			ImageView mom = new ImageView();
			mom.setFitHeight(97.5);
			mom.setFitWidth(63);
			mom.setImage(new Image(getClass().getResourceAsStream(cardsGame[i + 12].getImage())));
			Tooltip.install(mom, cardsGame[i + 12].getTooltip());
			carteImprese.getChildren().set(i, mom);
		}
	}

	private void setPosizioni() {
		System.out.println("\nesetto le posizioni del tabellone\n");
		mercatoPosMoneteMilitari.setImage(null);
		mercatoPosServitori.setImage(null);
		mercatoPosMonete.setImage(null);
		mercatoPosMunicipio.setImage(null);
		pianoPrimoPalazzoMilitare.setImage(null);
		pianoSecondoPalazzoMilitare.setImage(null);
		pianoTerzoPalazzoMilitare.setImage(null);
		pianoQuartoPalazzoMilitare.setImage(null);
		pianoPrimoPalazzoPersonaggi.setImage(null);
		pianoSecondoPalazzoPersonaggi.setImage(null);
		pianoTerzoPalazzoPersonaggi.setImage(null);
		pianoQuartoPalazzoPersonaggi.setImage(null);
		pianoPrimoPalazzoEdifici.setImage(null);
		pianoSecondoPalazzoEdifici.setImage(null);
		pianoTerzoPalazzoEdifici.setImage(null);
		pianoQuartoPalazzoEdifici.setImage(null);
		pianoPrimoPalazzoTerritori.setImage(null);
		pianoSecondoPalazzoTerritori.setImage(null);
		pianoTerzoPalazzoTerritori.setImage(null);
		mercatoPosServitori.setImage(null);
		mercatoPosMoneteMilitari.setImage(null);
		mercatoPosMonete.setImage(null);
		mercatoPosMunicipio.setImage(null);
		azioniTerritoridaunGiocatore.setImage(null);
		azioniEdificidaunGiocatore.setImage(null);

		azioniTerritoridaunGiocatore.setDisable(false);
		azioniEdificidaunGiocatore.setDisable(false);
		mercatoPosServitori.setDisable(false);
		mercatoPosMoneteMilitari.setDisable(false);
		mercatoPosMonete.setDisable(false);
		mercatoPosMunicipio.setDisable(false);
		mercatoPosMoneteMilitari.setDisable(false);
		mercatoPosServitori.setDisable(false);
		mercatoPosMonete.setDisable(false);
		mercatoPosMunicipio.setDisable(false);
		pianoPrimoPalazzoMilitare.setDisable(false);
		pianoSecondoPalazzoMilitare.setDisable(false);
		pianoTerzoPalazzoMilitare.setDisable(false);
		pianoQuartoPalazzoMilitare.setDisable(false);
		pianoPrimoPalazzoPersonaggi.setDisable(false);
		pianoSecondoPalazzoPersonaggi.setDisable(false);
		pianoTerzoPalazzoPersonaggi.setDisable(false);
		pianoQuartoPalazzoPersonaggi.setDisable(false);
		pianoPrimoPalazzoEdifici.setDisable(false);
		pianoSecondoPalazzoEdifici.setDisable(false);
		pianoTerzoPalazzoEdifici.setDisable(false);
		pianoQuartoPalazzoEdifici.setDisable(false);
		pianoPrimoPalazzoTerritori.setDisable(false);
		pianoSecondoPalazzoTerritori.setDisable(false);
		pianoTerzoPalazzoTerritori.setDisable(false);
	}

	@FXML
	public void lanciaDadi() throws RemoteException, SQLException {
		lanciaDadi.setDisable(true);
		try {
			dadi = start.getClient().lanciaDadi();
		} catch (ClassNotFoundException | IOException e) {
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
		bandiera.setDisable(false);
	}

	public void notifyAddCardAvv(String nameAvv, String tipo, int piano) {
		Label mom;
		System.out.println("Notifica carta Avv, l'avversario e':" + nameAvv);
		if (nameAvv.equals(name1.getText())) {
			System.out.println(tipo + " " + name1.getText() + " " + nameAvv + " " + nameAvv.equals(name1.getText()));
			switch (tipo) {
			case "ED":
				sistemaCarte(2, piano);
				mom = new Label(arrayCarteEdifici[piano].getNameCard());
				mom.setTextFill(Color.WHITE);
				mom.setTooltip(arrayCarteEdifici[piano].getTooltip());
				listCards1.getChildren().add(mom);
				System.out.println("Aggiunta carta all'avversario");
				break;
			case "TER":
				sistemaCarte(0, piano);
				mom = new Label(arrayCarteTerritori[piano].getNameCard());
				mom.setTextFill(Color.WHITE);
				mom.setTooltip(arrayCarteTerritori[piano].getTooltip());
				listCards1.getChildren().add(mom);
				break;
			case "PER":
				sistemaCarte(1, piano);
				mom = new Label(arrayCartePersonaggi[piano].getNameCard());
				mom.setTextFill(Color.WHITE);
				mom.setTooltip(arrayCartePersonaggi[piano].getTooltip());
				listCards1.getChildren().add(mom);
				break;
			case "IMP":
				sistemaCarte(3, piano);
				mom = new Label(arrayCarteImpresa[piano].getNameCard());
				mom.setTextFill(Color.WHITE);
				mom.setTooltip(arrayCarteImpresa[piano].getTooltip());
				listCards1.getChildren().add(mom);
				break;
			}
		} else if (nameAvv.equals(name2.getText())) {
			System.out.println(tipo + " " + name2.getText() + " " + nameAvv);
			switch (tipo) {
			case "ED":
				sistemaCarte(2, piano);
				mom = new Label(arrayCarteEdifici[piano].getNameCard());
				mom.setTextFill(Color.WHITE);
				mom.setTooltip(arrayCarteEdifici[piano].getTooltip());
				listCards2.getChildren().add(mom);
				break;
			case "TER":
				sistemaCarte(0, piano);
				mom = new Label(arrayCarteTerritori[piano].getNameCard());
				mom.setTextFill(Color.WHITE);
				mom.setTooltip(arrayCarteTerritori[piano].getTooltip());
				listCards2.getChildren().add(mom);
				break;
			case "PER":
				sistemaCarte(1, piano);
				mom = new Label(arrayCartePersonaggi[piano].getNameCard());
				mom.setTextFill(Color.WHITE);
				mom.setTooltip(arrayCartePersonaggi[piano].getTooltip());
				listCards2.getChildren().add(mom);
				break;
			case "IMP":
				sistemaCarte(3, piano);
				mom = new Label(arrayCarteImpresa[piano].getNameCard());
				mom.setTextFill(Color.WHITE);
				mom.setTooltip(arrayCarteImpresa[piano].getTooltip());
				listCards2.getChildren().add(mom);
				break;
			}
		} else if (nameAvv.equals(name3.getText())) {
			System.out.println(tipo + " " + name3.getText() + " " + nameAvv);
			switch (tipo) {
			case "ED":
				sistemaCarte(2, piano);
				mom = new Label(arrayCarteEdifici[piano].getNameCard());
				mom.setTextFill(Color.WHITE);
				mom.setTooltip(arrayCarteEdifici[piano].getTooltip());
				listCards3.getChildren().add(mom);
				break;
			case "TER":
				sistemaCarte(0, piano);
				mom = new Label(arrayCarteTerritori[piano].getNameCard());
				mom.setTextFill(Color.WHITE);
				mom.setTooltip(arrayCarteTerritori[piano].getTooltip());
				listCards3.getChildren().add(mom);
				break;
			case "PER":
				sistemaCarte(1, piano);
				mom = new Label(arrayCartePersonaggi[piano].getNameCard());
				mom.setTextFill(Color.WHITE);
				mom.setTooltip(arrayCartePersonaggi[piano].getTooltip());
				listCards3.getChildren().add(mom);
				break;
			case "IMP":
				sistemaCarte(3, piano);
				mom = new Label(arrayCarteImpresa[piano].getNameCard());
				mom.setTextFill(Color.WHITE);
				mom.setTooltip(arrayCarteImpresa[piano].getTooltip());
				listCards3.getChildren().add(mom);

				break;
			}
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
			if (destinazione1 != null && flag) {
				if (controlloPosizionamento("neutro", destinazione1.getLayoutX(), destinazione1.getLayoutY(), 0,
						destinazione1)) {
					destinazione1.setImage(familiareNeutro.getImage());
					familiareNeutro.setDisable(true);
					familiareNeutro.setVisible(false);
					try {
						setCardGiocatore(start.getClient().getNamePosition(destinazione1.getLayoutX(),
								destinazione1.getLayoutY()), 0, 0, "neutro");
						start.getClient().notifySpostamento("neutro", destinazione1.getLayoutX(),
								destinazione1.getLayoutY());
					} catch (IOException | ClassNotFoundException | SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					posizionatoFamiliare();
					setDestinazione1(null);
				}
			} else {
				if (controlloPosizionamento("neutro", destinazione2.getLayoutX(), destinazione2.getLayoutX(), 0,
						null)) {
					ImageView mom = new ImageView(familiareNeutro.getImage());
					mom.setFitWidth(35);
					mom.setFitHeight(38);
					destinazione2.getChildren().add(mom);
					familiareNeutro.setDisable(true);
					familiareNeutro.setVisible(false);
					try {
						setCardGiocatore(start.getClient().getNamePosition(destinazione2.getLayoutX(),
								destinazione2.getLayoutY()), 0, 0, "neutro");
						start.getClient().notifySpostamento("neutro", destinazione1.getLayoutX(),
								destinazione1.getLayoutY());
					} catch (IOException | ClassNotFoundException | SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					posizionatoFamiliare();
					setDestinazione2(null);
				}
			}
		});

		familiareNero.setOnDragDone(event -> {
			if (flag && destinazione1 != null) {
				int i = 0;
				try {
					if (start.getClient().scomunicato(1) == 17) {
						i = -1;
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (controlloPosizionamento("black", destinazione1.getLayoutX(), destinazione1.getLayoutY(), i,
						destinazione1)) {
					System.out.print(
							"neroooooooooooooo " + destinazione1.getLayoutX() + "  " + destinazione1.getLayoutY());
					destinazione1.setImage(familiareNero.getImage());
					familiareNero.setDisable(true);
					familiareNero.setVisible(false);
					try {
						setCardGiocatore(start.getClient().getNamePosition(destinazione1.getLayoutX(),
								destinazione1.getLayoutY()), 1, 0, "black");
						start.getClient().notifySpostamento("black", destinazione1.getLayoutX(),
								destinazione1.getLayoutY());
					} catch (IOException | ClassNotFoundException | SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					posizionatoFamiliare();
					setDestinazione1(null);
				}
			} else {
				int i = 0;
				try {
					if (start.getClient().scomunicato(1) == 17) {
						i = -1;
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (controlloPosizionamento("black", destinazione2.getLayoutX(), destinazione2.getLayoutX(), i, null)) {
					ImageView mom = new ImageView(familiareNero.getImage());
					mom.setFitWidth(35);
					mom.setFitHeight(38);
					System.out.print("neroooooooooooooo altrimenti " + destinazione2.getLayoutX() + "  "
							+ destinazione2.getLayoutY());
					destinazione2.getChildren().add(mom);
					familiareNero.setDisable(true);
					familiareNero.setVisible(false);
					try {
						setCardGiocatore(start.getClient().getNamePosition(destinazione2.getLayoutX(),
								destinazione2.getLayoutY()), 1, 0, "black");
						start.getClient().notifySpostamento("black", destinazione2.getLayoutX(),
								destinazione2.getLayoutY());
					} catch (IOException | ClassNotFoundException | SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					posizionatoFamiliare();
					setDestinazione2(null);
				}
			}
		});

		familiareArancio.setOnDragDone(event -> {
			if (flag && destinazione1 != null) {
				int i = 0;
				try {
					if (start.getClient().scomunicato(1) == 17) {
						i = -1;
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (controlloPosizionamento("orange", destinazione1.getLayoutX(), destinazione1.getLayoutY(), i,
						destinazione1)) {
					destinazione1.setImage(familiareArancio.getImage());
					familiareArancio.setDisable(true);
					familiareArancio.setVisible(false);
					try {
						setCardGiocatore(start.getClient().getNamePosition(destinazione1.getLayoutX(),
								destinazione1.getLayoutY()), 2, 0, "black");
						start.getClient().notifySpostamento("orange", destinazione1.getLayoutX(),
								destinazione1.getLayoutY());
					} catch (IOException | ClassNotFoundException | SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					posizionatoFamiliare();
					setDestinazione1(null);
				}
			} else {
				int i = 0;
				try {
					if (start.getClient().scomunicato(1) == 17) {
						i = -1;
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (controlloPosizionamento("orange", destinazione2.getLayoutX(), destinazione2.getLayoutX(), i,
						null)) {
					ImageView mom = new ImageView(familiareArancio.getImage());
					mom.setFitWidth(35);
					mom.setFitHeight(38);
					destinazione2.getChildren().add(mom);
					familiareArancio.setDisable(true);
					familiareArancio.setVisible(false);
					try {
						setCardGiocatore(start.getClient().getNamePosition(destinazione2.getLayoutX(),
								destinazione2.getLayoutY()), 2, 0, "black");
						start.getClient().notifySpostamento("orange", destinazione2.getLayoutX(),
								destinazione2.getLayoutY());
					} catch (IOException | ClassNotFoundException | SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					posizionatoFamiliare();
					setDestinazione2(null);
				}
			}
		});

		familiareBianco.setOnDragDone(event -> {
			if (flag && destinazione1 != null) {
				int i = 0;
				try {
					if (start.getClient().scomunicato(1) == 17) {
						i = -1;
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (controlloPosizionamento("white", destinazione1.getLayoutX(), destinazione1.getLayoutY(), i,
						destinazione1)) {
					destinazione1.setImage(familiareBianco.getImage());
					familiareBianco.setDisable(true);
					familiareBianco.setVisible(false);
					try {
						setCardGiocatore(start.getClient().getNamePosition(destinazione1.getLayoutX(),
								destinazione1.getLayoutY()), 3, 0, "black");
						start.getClient().notifySpostamento("white", destinazione1.getLayoutX(),
								destinazione1.getLayoutY());
					} catch (IOException | ClassNotFoundException | SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					posizionatoFamiliare();
					setDestinazione1(null);
				}
			} else {
				int i = 0;
				try {
					if (start.getClient().scomunicato(1) == 17) {
						i = -1;
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (controlloPosizionamento("white", destinazione2.getLayoutX(), destinazione2.getLayoutX(), i, null)) {
					ImageView mom = new ImageView(familiareBianco.getImage());
					mom.setFitWidth(35);
					mom.setFitHeight(38);
					destinazione2.getChildren().add(mom);
					familiareBianco.setDisable(true);
					familiareBianco.setVisible(false);
					try {
						setCardGiocatore(start.getClient().getNamePosition(destinazione2.getLayoutX(),
								destinazione2.getLayoutY()), 3, 0, "black");
						start.getClient().notifySpostamento("white", destinazione2.getLayoutX(),
								destinazione2.getLayoutY());
					} catch (IOException | ClassNotFoundException | SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					if (destinazione2.equals(municipio))
						start.getClient().sistemaPosizioni();
					posizionatoFamiliare();
					setDestinazione2(null);
				}
			}
		});

		pianoPrimoPalazzoMilitare.setOnDragEntered(e -> {
			if (!pianoPrimoPalazzoMilitare.isDisabled() && ((start.getClient().getPosPalLibero("imprese", 1))
					|| (start.getClient().getPosPalLibero("imprese", 2))
					|| (start.getClient().getPosPalLibero("imprese", 3))))
				setDestinazione1(pianoPrimoPalazzoMilitare);
		});

		pianoSecondoPalazzoMilitare.setOnDragEntered(e -> {
			if (!pianoSecondoPalazzoMilitare.isDisabled() && (start.getClient().getPosPalLibero("imprese", 0)
					|| start.getClient().getPosPalLibero("imprese", 2)
					|| start.getClient().getPosPalLibero("imprese", 3)))
				setDestinazione1(pianoSecondoPalazzoMilitare);
		});

		pianoTerzoPalazzoMilitare.setOnDragEntered(e -> {
			if (!pianoTerzoPalazzoMilitare.isDisabled() && (start.getClient().getPosPalLibero("imprese", 0)
					|| start.getClient().getPosPalLibero("imprese", 1)
					|| start.getClient().getPosPalLibero("imprese", 3)))
				setDestinazione1(pianoTerzoPalazzoMilitare);
		});

		pianoQuartoPalazzoMilitare.setOnDragEntered(e -> {
			if (!pianoQuartoPalazzoMilitare.isDisabled() && (start.getClient().getPosPalLibero("imprese", 0)
					|| start.getClient().getPosPalLibero("imprese", 1)
					|| start.getClient().getPosPalLibero("imprese", 2)))
				setDestinazione1(pianoQuartoPalazzoMilitare);
		});

		pianoPrimoPalazzoPersonaggi.setOnDragEntered(e -> {
			if (!pianoPrimoPalazzoPersonaggi.isDisabled() && (start.getClient().getPosPalLibero("personaggi", 1)
					|| start.getClient().getPosPalLibero("personaggi", 2)
					|| start.getClient().getPosPalLibero("personaggi", 3)))
				setDestinazione1(pianoPrimoPalazzoPersonaggi);
		});

		pianoSecondoPalazzoPersonaggi.setOnDragEntered(e -> {
			if (!pianoSecondoPalazzoPersonaggi.isDisabled() && (start.getClient().getPosPalLibero("personaggi", 0)
					|| start.getClient().getPosPalLibero("personaggi", 2)
					|| start.getClient().getPosPalLibero("personaggi", 3)))
				setDestinazione1(pianoSecondoPalazzoPersonaggi);
		});

		pianoTerzoPalazzoPersonaggi.setOnDragEntered(e -> {
			if (!pianoTerzoPalazzoPersonaggi.isDisabled() && (start.getClient().getPosPalLibero("personaggi", 0)
					|| start.getClient().getPosPalLibero("personaggi", 1)
					|| start.getClient().getPosPalLibero("personaggi", 3)))
				setDestinazione1(pianoTerzoPalazzoPersonaggi);
		});

		pianoQuartoPalazzoPersonaggi.setOnDragEntered(e -> {
			if (!pianoQuartoPalazzoPersonaggi.isDisabled() && (start.getClient().getPosPalLibero("personaggi", 0)
					|| start.getClient().getPosPalLibero("personaggi", 1)
					|| start.getClient().getPosPalLibero("personaggi", 2)))
				setDestinazione1(pianoQuartoPalazzoPersonaggi);
		});

		pianoPrimoPalazzoEdifici.setOnDragEntered(e -> {
			if (!pianoPrimoPalazzoEdifici.isDisabled() && (start.getClient().getPosPalLibero("edifici", 1)
					|| start.getClient().getPosPalLibero("edifici", 2)
					|| start.getClient().getPosPalLibero("edifici", 3)))
				setDestinazione1(pianoPrimoPalazzoEdifici);
		});

		pianoSecondoPalazzoEdifici.setOnDragEntered(e -> {
			if (!pianoSecondoPalazzoEdifici.isDisabled() && (start.getClient().getPosPalLibero("edifici", 0)
					|| start.getClient().getPosPalLibero("edifici", 2)
					|| start.getClient().getPosPalLibero("edifici", 3)))
				setDestinazione1(pianoSecondoPalazzoEdifici);
		});

		pianoTerzoPalazzoEdifici.setOnDragEntered(e -> {
			if (!pianoTerzoPalazzoEdifici.isDisabled() && (start.getClient().getPosPalLibero("edifici", 0)
					|| start.getClient().getPosPalLibero("edifici", 1)
					|| start.getClient().getPosPalLibero("edifici", 3)))
				setDestinazione1(pianoTerzoPalazzoEdifici);
		});

		pianoQuartoPalazzoEdifici.setOnDragEntered(e -> {
			if (!pianoQuartoPalazzoEdifici.isDisabled() && (start.getClient().getPosPalLibero("edifici", 0)
					|| start.getClient().getPosPalLibero("edifici", 1)
					|| start.getClient().getPosPalLibero("edifici", 2)))
				setDestinazione1(pianoQuartoPalazzoEdifici);
		});

		pianoPrimoPalazzoTerritori.setOnDragEntered(e -> {
			if (!pianoPrimoPalazzoTerritori.isDisabled() && (start.getClient().getPosPalLibero("territori", 1)
					|| start.getClient().getPosPalLibero("territori", 2)
					|| start.getClient().getPosPalLibero("territori", 3)))
				setDestinazione1(pianoPrimoPalazzoTerritori);
		});

		pianoSecondoPalazzoTerritori.setOnDragEntered(e -> {
			if (!pianoSecondoPalazzoTerritori.isDisabled() && (start.getClient().getPosPalLibero("territori", 0)
					|| start.getClient().getPosPalLibero("territori", 2)
					|| start.getClient().getPosPalLibero("territori", 3)))
				setDestinazione1(pianoSecondoPalazzoTerritori);
		});

		pianoTerzoPalazzoTerritori.setOnDragEntered(e -> {
			if (!pianoTerzoPalazzoTerritori.isDisabled() && (start.getClient().getPosPalLibero("territori", 0)
					|| start.getClient().getPosPalLibero("territori", 1)
					|| start.getClient().getPosPalLibero("territori", 3)))
				setDestinazione1(pianoTerzoPalazzoTerritori);
		});

		pianoQuartoPalazzoTerritori.setOnDragEntered(e -> {
			if (!pianoQuartoPalazzoTerritori.isDisabled() && (start.getClient().getPosPalLibero("territori", 0)
					|| start.getClient().getPosPalLibero("territori", 1)
					|| start.getClient().getPosPalLibero("territori", 2)))
				setDestinazione1(pianoQuartoPalazzoTerritori);
		});

		azioniTerritoridapiuGiocatori.setOnDragEntered(e -> {
			if (!azioniTerritoridapiuGiocatori.isDisabled() && (start.getClient().getPosPalLibero("territori", 3)))
				setDestinazione2(azioniTerritoridapiuGiocatori);
		});

		azioniTerritoridaunGiocatore.setOnDragEntered(e -> {
			if (!azioniTerritoridaunGiocatore.isDisabled())
				setDestinazione1(azioniTerritoridaunGiocatore);
		});

		azioniEdificidaunGiocatore.setOnDragEntered(e -> {
			if (!azioniEdificidaunGiocatore.isDisabled())
				setDestinazione1(azioniEdificidaunGiocatore);
		});

		mercatoPosServitori.setOnDragEntered(e -> {
			try {
				if (!mercatoPosServitori.isDisabled() && start.getClient().scomunicato(2) != 26)
					setDestinazione1(mercatoPosServitori);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		mercatoPosMunicipio.setOnDragEntered(e -> {
			try {
				if (!mercatoPosMunicipio.isDisabled() && start.getClient().getNumberOfGamer() > 3
						&& start.getClient().scomunicato(2) != 26)
					setDestinazione1(mercatoPosMunicipio);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		mercatoPosMonete.setOnDragEntered(e -> {
			try {
				if (!mercatoPosMonete.isDisabled() && start.getClient().scomunicato(2) != 26)
					setDestinazione1(mercatoPosMonete);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		mercatoPosMoneteMilitari.setOnDragEntered(e -> {
			try {
				if ((!mercatoPosMoneteMilitari.isDisabled() && start.getClient().getNumberOfGamer() >= 3)
						&& start.getClient().scomunicato(2) != 26)
					setDestinazione1(mercatoPosMoneteMilitari);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		municipio.setOnDragEntered(e -> {
			setDestinazione2(municipio);
		});

		azioniEdificidapiuGiocatori.setOnDragEntered(e -> {
			try {
				if (start.getClient().getNumberOfGamer() > 2)
					setDestinazione2(azioniEdificidapiuGiocatori);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		azioniTerritoridapiuGiocatori.setOnDragEntered(e -> {
			try {
				if (start.getClient().getNumberOfGamer() > 2)
					setDestinazione2(azioniTerritoridapiuGiocatori);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

	}

	private void posizionatoFamiliare() {
		nFamPos++;
		if (nFamPos == 1) {
			try {
				start.getClient().changeGamer();
				isInTurno = false;
				if (familiareBianco.isVisible())
					familiareBianco.setDisable(true);
				if (familiareNero.isVisible())
					familiareNero.setDisable(true);
				if (familiareArancio.isVisible())
					familiareArancio.setDisable(true);
				if (familiareNeutro.isVisible())
					familiareNeutro.setDisable(true);
				bandiera.setDisable(true);
			} catch (RemoteException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				start.getClient().scambio();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (familiareBianco.isVisible())
				;
			familiareBianco.setDisable(true);
			if (familiareNero.isVisible())
				familiareNero.setDisable(true);
			if (familiareArancio.isVisible())
				familiareArancio.setDisable(true);
			if (familiareNeutro.isVisible())
				familiareNeutro.setDisable(true);
			bandiera.setDisable(true);
		}
	}

	public void scambio() {
		isInTurno = true;
		bandiera.setDisable(false);
		if (familiareBianco.isVisible())
			familiareBianco.setDisable(false);
		if (familiareNero.isVisible())
			familiareNero.setDisable(false);
		if (familiareArancio.isVisible())
			familiareArancio.setDisable(false);
		if (familiareNeutro.isVisible())
			;
		familiareNeutro.setDisable(false);
		if (familiareBianco.isVisible() && !familiareNero.isVisible() && familiareArancio.isVisible()
				&& familiareNeutro.isVisible())
			try {
				start.getClient().scambio();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	private void setDestinazione2(HBox azioniTerritoridaunGiocatore2) {
		flag = false;
		destinazione2 = azioniTerritoridaunGiocatore2;
	}

	private void setDestinazione1(ImageView azioniTerritoridaunGiocatore2) {
		flag = true;
		this.destinazione1 = azioniTerritoridaunGiocatore2;

	}

	public void notifySpostamentoPuntiMilitari(double x, double y, String color2) {
		System.out.println(color2.equals(start.getClient().getColor())
				|| (!color2.equals(start.getClient().getColor()) && !isInTurno()));
		if (color2.equals(start.getClient().getColor())
				|| (!color2.equals(start.getClient().getColor()) && !isInTurno())) {
			System.out.println("Notifico lo spostamento della pedina punti militari del giocatore con il colore "
					+ color2 + " " + x + " " + y);
			switch (color2) {
			case "blue":
				System.out.println("Si sposta la pedina blu a: " + x + " " + y);
				puntiMilitariBlu.setLayoutX(x);
				puntiMilitariBlu.setLayoutY(y);
				break;
			case "white":
				System.out.println("Si sposta la pedina bianca a: " + x + " " + y);
				puntiMilitariBianco.setLayoutX(x);
				puntiMilitariBianco.setLayoutY(y);
				System.out.println("Fine Spostamento pedina");
				break;
			case "green":
				System.out.println("Si sposta la pedina verde a: " + x + " " + y);
				puntiMilitariVerde.setLayoutX(x);
				puntiMilitariVerde.setLayoutY(y);
				System.out.println("Fine Spostamento pedina");
				break;
			case "orange":
				System.out.println("Si sposta la pedina orange a: " + x + " " + y);
				puntiMilitariArancio.setLayoutX(x);
				puntiMilitariArancio.setLayoutY(y);
				break;
			}
		}
	}

	private boolean isInTurno() {
		return isInTurno;
	}

	public void notifySpostamentoPuntiFede(double x, double y, String color2) {
		System.out.println(color2.equals(start.getClient().getColor())
				|| (!color2.equals(start.getClient().getColor()) && !isInTurno()));
		if (color2.equals(start.getClient().getColor())
				|| (!color2.equals(start.getClient().getColor()) && !isInTurno())) {
			System.out.println("Notifico lo spostamento della pedina punti fede del giocatore con il colore " + color2
					+ " " + x + " " + y);
			switch (color2) {
			case "blue":
				puntiFedeBlu.setLayoutX(x);
				puntiFedeBlu.setLayoutY(y);
				System.out.println("Fine Spostamento pedina");
				break;
			case "white":
				puntiFedeBianco.setLayoutX(x);
				puntiFedeBianco.setLayoutY(y);
				System.out.println("Fine Spostamento pedina");
				break;
			case "green":
				puntiFedeVerde.setLayoutX(x);
				puntiFedeVerde.setLayoutY(y);
				System.out.println("Fine Spostamento pedina");
				break;
			case "orange":
				puntiFedeArancio.setLayoutX(x);
				puntiFedeArancio.setLayoutY(y);
				System.out.println("Fine Spostamento pedina");
				break;
			}
		}
	}

	public void notifySpostamentoPuntiVittoria(double x, double y, String color2) {
		System.out.println(color2.equals(start.getClient().getColor())
				|| (!color2.equals(start.getClient().getColor()) && !isInTurno()));
		if (color2.equals(start.getClient().getColor())
				|| !isInTurno() && !color2.equals(start.getClient().getColor())) {
			System.out.println("Notifico lo spostamento della pedina punti vittoria del giocatore con il colore "
					+ color2 + " " + x + " " + y);
			switch (color2) {
			case "blue":
				puntiVittoriaBlu.setLayoutX(x);
				puntiVittoriaBlu.setLayoutY(y);
				System.out.println("Fine Spostamento pedina");
				break;
			case "white":
				puntiVittoriaBianco.setLayoutX(x);
				puntiVittoriaBianco.setLayoutY(y);
				System.out.println("Fine Spostamento pedina");
				break;
			case "green":
				puntiVittoriaVerde.setLayoutX(x);
				puntiVittoriaVerde.setLayoutY(y);
				System.out.println("Fine Spostamento pedina");
				break;
			case "orange":
				puntiVittoriaArancio.setLayoutX(x);
				puntiVittoriaArancio.setLayoutY(y);
				System.out.println("Fine Spostamento pedina");
				break;
			}
		}
	}

	public void notifyRisorse(Giocatore g) {
		Portafoglio p = g.getRisorse();
		if (g != null && g.getName().equals(start.getClient().getName())) {
			monete.setText(String.valueOf(p.getDimRisorse("monete")));
			servitori.setText(String.valueOf(p.getDimRisorse("servitori")));
			pietra.setText(String.valueOf(p.getDimRisorse("pietra")));
			lengo.setText(String.valueOf(p.getDimRisorse("legno")));
		} else {
			if (g != null && g.getName().equals(name1.getText())) {
				monete1.setText(String.valueOf(p.getDimRisorse("monete")));
				pietre1.setText(String.valueOf(p.getDimRisorse("pietra")));
				servitori1.setText(String.valueOf(p.getDimRisorse("servitori")));
				legno1.setText(String.valueOf(p.getDimRisorse("legno")));
			} else if (g != null && g.getName().equals(name2.getText())) {
				monete2.setText(String.valueOf(p.getDimRisorse("monete")));
				pietre2.setText(String.valueOf(p.getDimRisorse("pietra")));
				servitori2.setText(String.valueOf(p.getDimRisorse("servitori")));
				legno2.setText(String.valueOf(p.getDimRisorse("legno")));
			} else if (g != null) {
				monete3.setText(String.valueOf(p.getDimRisorse("monete")));
				pietre3.setText(String.valueOf(p.getDimRisorse("pietra")));
				servitori3.setText(String.valueOf(p.getDimRisorse("servitori")));
				legno3.setText(String.valueOf(p.getDimRisorse("legno")));
			}
		}
	}

	public void notifyPergamena(int i) {
		int decisione = 0;
		for (int j = 0; j < i; j++) {
			Stage popup = new Stage();
			popup.setTitle("Pergamene");
			VBox box = new VBox();
			HBox buttonBox = new HBox();
			ImageView im = new ImageView(new Image(getClass().getResourceAsStream("Pergamena.png")));
			Button risorse = new Button("Click Me!");
			risorse.setOnAction(e -> {
				try {
					start.getClient().addRisorse("pietra", 1);
					start.getClient().notifyRisorse("pietra", start.getClient().getRisorse().getDimRisorse("pietra"));
					start.getClient().notifyRisorse("legno", start.getClient().getRisorse().getDimRisorse("legno"));
					start.getClient().addRisorse("legno", 1);
				} catch (SQLException | ClassNotFoundException | IOException e1) {
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
					start.getClient().notifyRisorse("servitori", start.getClient().getRisorse().getDimRisorse("servitori"));
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
					start.getClient().notifyRisorse("monete", start.getClient().getRisorse().getDimRisorse("monete"));
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				popup.close();
				e.consume();
			});

			Button militariB = new Button("Click Me!");
			militariB.setOnAction(e -> {
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
			buttonBox.getChildren().addAll(risorse, servitoriB, moneteB, militariB, fedeB);
			box.getChildren().addAll(im, buttonBox);
			Scene scene = new Scene(box, 600, 400);
			popup.centerOnScreen();
			popup.setScene(scene);
			popup.show();
		}
	}

	private void setDecisione(int i) {
		valoreAgg = i;

	}

	public void notifyTutteCarte(int valore) {
		Stage popup = new Stage();
		popup.setTitle("TutteCarte");
		ScrollPane pane = new ScrollPane();
		VBox vBox = new VBox();
		HBox box = new HBox();
		HBox labelBox = new HBox();
		Label[] l = { new Label(" "),
				new Label("Hei, puoi prendere una carta in piu', clicca sulla carta che vorresti prendere"),
				new Label(" ") };
		Immagine[] im = new Immagine[16];
		labelBox.getChildren().addAll(l[0], l[1], l[2]);
		for (int i = 0; i < 4; i++) {
			if (arrayCarteTerritori[i] != null && i < (valore / 2)) {
				im[i] = new Immagine(new Image(getClass().getResourceAsStream(arrayCarteTerritori[i].getImage())));
				im[i].setC(arrayCarteTerritori[i]);
				im[i].setTipo(0);
				im[i].setPiano(i);
			}
		}
		for (int i = 0; i < 4; i++) {
			if (arrayCartePersonaggi[i] != null && i < (valore / 2)) {
				im[i + 4] = new Immagine(new Image(getClass().getResourceAsStream(arrayCartePersonaggi[i].getImage())));
				im[i + 4].setC(arrayCartePersonaggi[i]);
				im[i + 4].setTipo(1);
				im[i].setPiano(i);
			}
		}
		for (int i = 0; i < 4; i++) {
			if (arrayCarteEdifici[i] != null && i < (valore / 2)) {
				im[i + 8] = new Immagine(new Image(getClass().getResourceAsStream(arrayCarteEdifici[i].getImage())));
				im[i + 8].setC(arrayCarteEdifici[i]);
				im[i + 8].setTipo(2);
				im[i].setPiano(i);
			}
		}
		for (int i = 0; i < 4; i++) {
			if (arrayCarteImpresa[i] != null && i < (valore / 2)) {
				im[i + 12] = new Immagine(new Image(getClass().getResourceAsStream(arrayCarteImpresa[i].getImage())));
				im[i + 12].setC(arrayCarteImpresa[i]);
				im[i + 12].setTipo(3);
				im[i].setPiano(i);
			}
		}
		for (Immagine i : im) {
			if (i != null) {
				box.getChildren().add(i);
				i.setOnMouseClicked(e -> {
					try {
						start.getClient().getRisorse().addRis("monete", -3);
						start.getClient().notifyRisorse("monete",
								start.getClient().getRisorse().getDimRisorse("monete"));
						start.getClient().setCardGiocatore(i.getC(), i.getTipo(), i.getPiano());
						setCardGiocatore("tutteCarte", i.getPiano(), i.getTipo(), "niente");
						sistemaCarte(i.getTipo(), i.getPiano());
						popup.close();
					} catch (ClassNotFoundException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				});
			}
		}
		vBox.getChildren().addAll(labelBox, box);
		pane.setContent(vBox);
		Scene scene = new Scene(pane, 600, 400);
		popup.centerOnScreen();
		popup.setScene(scene);
		popup.show();
	}

	public void notifyUnTipoCarta(int tipo, int valore, int scontoAzioneImmediata1) {
		System.out.println("Notifica di scegliere un tipo di carta da prendere");
		Stage popup = new Stage();
		popup.setTitle("Un tipo di carta");
		VBox vBox = new VBox();
		HBox box = new HBox();
		HBox labelBox = new HBox();
		Label[] l = { new Label(" "),
				new Label("Hei, puoi prendere un tipo di carta in più, clicca sulla carta che vorresti prendere"),
				new Label(" ") };
		Immagine[] im = new Immagine[4];
		labelBox.getChildren().addAll(l[0], l[1], l[2]);
		switch (tipo) {
		case 0:
			for (int i = 0; i < 4; i++) {
				if (arrayCarteTerritori[i] != null && i < (valore / 2)) {
					im[i] = new Immagine(new Image(getClass().getResourceAsStream(arrayCarteTerritori[i].getImage())));
					im[i].setC(arrayCarteEdifici[i]);
					im[i].setTipo(0);
				}
			}
			break;
		case 1:
			for (int i = 0; i < 4; i++) {
				if (arrayCartePersonaggi[i] != null && i < (valore / 2)) {
					im[i] = new Immagine(new Image(getClass().getResourceAsStream(arrayCartePersonaggi[i].getImage())));
					im[i].setC(arrayCartePersonaggi[i]);
					im[i].setTipo(1);
				}
			}
			break;
		case 2:
			for (int i = 0; i < 4; i++) {
				if (arrayCarteEdifici[i] != null && i < (valore / 2)) {
					im[i] = new Immagine(new Image(getClass().getResourceAsStream(arrayCarteEdifici[i].getImage())));
					im[i].setC(arrayCarteEdifici[i]);
					im[i].setTipo(2);
				}
			}
			break;
		case 3:
			for (int i = 0; i < 4; i++) {
				if (arrayCarteImpresa[i] != null && i < (valore / 2)) {
					im[i] = new Immagine(new Image(getClass().getResourceAsStream(arrayCarteImpresa[i].getImage())));
					im[i].setC(arrayCarteImpresa[i]);
					im[i].setTipo(3);
				}
			}
			break;
		}
		for (Immagine i : im) {
			if (i != null) {
				box.getChildren().add(i);
				i.setOnMouseClicked(e -> {
					try {
						start.getClient().getRisorse().addRis("monete", -3 + scontoAzioneImmediata1);
						start.getClient().notifyRisorse("monete",
								start.getClient().getRisorse().getDimRisorse("monete"));
						start.getClient().setCardGiocatore(i.getC(), i.getTipo(), i.getPiano());
						setCardGiocatore("tutteCarte", i.getPiano(), i.getTipo(), "niente");
						sistemaCarte(i.getTipo(), i.getPiano());
						popup.close();
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

	public void notifyAddRisorse(String name, String tipo, int qta) {
		System.out.println("Notifico per l'aggiunta delle carte");
		if (name.equals(start.getClient().getName())) {
			switch (tipo) {
			case "servitori":
				System.out.println(qta);
				System.out.println(Integer.parseInt(servitori.getText()));
				servitori.setText(String.valueOf(qta));
				break;
			case "monete":
				System.out.println(qta);
				System.out.println(Integer.parseInt(monete.getText()));
				monete.setText(String.valueOf(qta));
				break;
			case "pietra":
				System.out.println(qta);
				System.out.println(Integer.parseInt(pietra.getText()));
				pietra.setText(String.valueOf(qta));
				break;
			case "legno":
				System.out.println(qta);
				System.out.println(Integer.parseInt(lengo.getText()));
				lengo.setText(String.valueOf(qta));
				break;
			}
		} else if (name.equals(name1.getText())) {
			switch (tipo) {
			case "servitori":
				servitori1.setText(String.valueOf(qta));
				break;
			case "monete":
				monete1.setText(String.valueOf(qta));
				break;
			case "pietra":
				pietre1.setText(String.valueOf(qta));
				break;
			case "legno":
				legno1.setText(String.valueOf(qta));
				break;
			}
		} else if (name.equals(name2.getText())) {
			switch (tipo) {
			case "servitori":
				servitori2.setText(String.valueOf(qta));
				break;
			case "monete":
				monete2.setText(String.valueOf(qta));
				break;
			case "pietra":
				pietre2.setText(String.valueOf(qta));
				break;
			case "legno":
				legno2.setText(String.valueOf(qta));
				break;
			}
		} else {
			switch (tipo) {
			case "servitori":
				servitori3.setText(String.valueOf(qta));
				break;
			case "monete":
				monete3.setText(String.valueOf(qta));
				break;
			case "pietra":
				pietre3.setText(String.valueOf(qta));
				break;
			case "legno":
				legno3.setText(String.valueOf(qta));
				break;
			}
		}
	}

	public void notifyAskSostegnoChiesa() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Sostieni la chiesa");
		alert.setHeaderText("Dedici se sostenere o meglio la chiesa, attento alle scomuniche!");
		alert.setContentText("Choose your option.");

		ButtonType buttonTypeOne = new ButtonType("Yes");
		ButtonType buttonTypeTwo = new ButtonType("No");

		alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOne) {
			sostegno = true;

		} else if (result.get() == buttonTypeTwo) {
			sostegno = false;
		}
		if (sostegno) {
			try {
				start.getClient().addPunti("fede", -start.getClient().getRisorse().getPunti("fede"));
				start.getClient().notifySpostamentoPunti("fede");
				start.getClient().notifyDecisionChiesa(true);
			} catch (ClassNotFoundException | SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				start.getClient().notifyDecisionChiesa(false);
				start.getClient().addScomunica();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void notifyVittoria() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Fine partita");
		alert.setHeaderText(null);
		alert.setContentText("Complimenti hai vinto!");
		alert.showAndWait();
		File f = new File("src/client/gui/Applausi.wav");
		AudioInputStream audioIn;
		try {
			audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start();
		} catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void nofySconfitta(int max) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Fine partita");
		alert.setHeaderText(null);
		alert.setContentText("Peccato hai perso!!\nHa vinto il giocatore con il punteggio " + String.valueOf(max));
		alert.showAndWait();
		File f = new File("src/client/gui/Sconfitta.wav");
		AudioInputStream audioIn;
		try {
			audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start();
		} catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	public void skipMossa() {
		posizionatoFamiliare();
	}

}
