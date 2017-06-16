package prova;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

import client.gui.StartClientGui;
import javafx.event.Event;
import javafx.fxml.FXML;
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
import javafx.stage.Stage;
import server.element.CartaEdifici;
import server.element.CartaImprese;
import server.element.CartaPersonaggi;
import server.element.CartaSviluppo;
import server.element.CartaTerritori;
import server.element.Dado;
import server.element.Giocatore;
import server.element.Portafoglio;
import server.element.TesseraScomunica;

public class Controller {

	private StartClientGui start;
	private CartaTerritori[] arrayCarteTerritori = new CartaTerritori[4];
	private CartaImprese[] arrayCarteImpresa = new CartaImprese[4];
	private CartaPersonaggi[] arrayCartePersonaggi = new CartaPersonaggi[4];
	private CartaEdifici[] arrayCarteEdifici = new CartaEdifici[4];
	private int numberOfGamers;
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
	
	//Componenti tabellone avversari
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

	
	private ImageView destinazione1;
	
	private HBox destinazione2;
	
	public void setGUI(StartClientGui startClientGui) throws ClassNotFoundException, IOException {
		this.setStart(startClientGui);
		numberOfGamers = start.getClient().getPlayers();
		//start.getClient().setGuiGame(this);
		/*mercatoPosMoneteMilitari.setOnDragDropped(event->{
			if(mercatoPosMoneteMilitari != null){
				familiareNeutro.getDestinazione(mercatoPosMoneteMilitari);
				familiareNero.getDestinazione(mercatoPosMoneteMilitari);
				familiareArancio.getDestinazione(mercatoPosMoneteMilitari);
				familiareBianco.getDestinazione(mercatoPosMoneteMilitari);
			}
		});
		mercatoPosServitori.setOnDragDropped(event->{
			if(mercatoPosServitori != null){
				familiareNeutro.getDestinazione(mercatoPosServitori);
				familiareNero.getDestinazione(mercatoPosServitori);
				familiareArancio.getDestinazione(mercatoPosServitori);
				familiareBianco.getDestinazione(mercatoPosServitori);
			}
		});
		mercatoPosMonete.setOnDragDropped(event->{
			if(mercatoPosMonete != null){
				familiareNeutro.getDestinazione(mercatoPosMonete);
				familiareNero.getDestinazione(mercatoPosMonete);
				familiareArancio.getDestinazione(mercatoPosMonete);
				familiareBianco.getDestinazione(mercatoPosMonete);
			}
		});
		mercatoPosMunicipio.setOnDragDropped(event->{
			if(mercatoPosMunicipio != null){
				familiareNeutro.getDestinazione(mercatoPosMunicipio);
				familiareNero.getDestinazione(mercatoPosMunicipio);
				familiareArancio.getDestinazione(mercatoPosMunicipio);
				familiareBianco.getDestinazione(mercatoPosMunicipio);
			}
		});
		pianoPrimoPalazzoMilitare.setOnDragDropped(event->{
			if(pianoPrimoPalazzoMilitare != null){
				familiareNeutro.getDestinazione(pianoPrimoPalazzoMilitare);
				familiareNero.getDestinazione(pianoPrimoPalazzoMilitare);
				familiareArancio.getDestinazione(pianoPrimoPalazzoMilitare);
				familiareBianco.getDestinazione(pianoPrimoPalazzoMilitare);
			}
		});
		pianoSecondoPalazzoMilitare.setOnDragDropped(event->{
			if(pianoSecondoPalazzoMilitare != null){
				familiareNeutro.getDestinazione(pianoSecondoPalazzoMilitare);
				familiareNero.getDestinazione(pianoSecondoPalazzoMilitare);
				familiareArancio.getDestinazione(pianoSecondoPalazzoMilitare);
				familiareBianco.getDestinazione(pianoSecondoPalazzoMilitare);
			}
		});
		pianoTerzoPalazzoMilitare.setOnDragDropped(event->{
			if(mercatoPosMoneteMilitari != null){
				familiareNeutro.getDestinazione(pianoTerzoPalazzoMilitare);
				familiareNero.getDestinazione(pianoTerzoPalazzoMilitare);
				familiareArancio.getDestinazione(pianoTerzoPalazzoMilitare);
				familiareBianco.getDestinazione(pianoTerzoPalazzoMilitare);
			}
		});
		pianoQuartoPalazzoMilitare.setOnDragDropped(event->{
			if(pianoQuartoPalazzoMilitare != null){
				familiareNeutro.getDestinazione(pianoQuartoPalazzoMilitare);
				familiareNero.getDestinazione(pianoQuartoPalazzoMilitare);
				familiareArancio.getDestinazione(pianoQuartoPalazzoMilitare);
				familiareBianco.getDestinazione(pianoQuartoPalazzoMilitare);
			}
		});
		pianoPrimoPalazzoPersonaggi.setOnDragDropped(event->{
			if(pianoPrimoPalazzoPersonaggi != null){
				familiareNeutro.getDestinazione(pianoPrimoPalazzoPersonaggi);
				familiareNero.getDestinazione(pianoPrimoPalazzoPersonaggi);
				familiareArancio.getDestinazione(pianoPrimoPalazzoPersonaggi);
				familiareBianco.getDestinazione(pianoPrimoPalazzoPersonaggi);
			}
		});
		pianoSecondoPalazzoPersonaggi.setOnDragDropped(event->{
			if(pianoSecondoPalazzoPersonaggi != null){
				familiareNeutro.getDestinazione(pianoSecondoPalazzoPersonaggi);
				familiareNero.getDestinazione(pianoSecondoPalazzoPersonaggi);
				familiareArancio.getDestinazione(pianoSecondoPalazzoPersonaggi);
				familiareBianco.getDestinazione(pianoSecondoPalazzoPersonaggi);
			}
		});
		pianoTerzoPalazzoPersonaggi.setOnDragDropped(event->{
			if(pianoTerzoPalazzoPersonaggi != null){
				familiareNeutro.getDestinazione(pianoTerzoPalazzoPersonaggi);
				familiareNero.getDestinazione(pianoTerzoPalazzoPersonaggi);
				familiareArancio.getDestinazione(pianoTerzoPalazzoPersonaggi);
				familiareBianco.getDestinazione(pianoTerzoPalazzoPersonaggi);
			}
		});
		pianoQuartoPalazzoPersonaggi.setOnDragDropped(event->{
			if(pianoQuartoPalazzoPersonaggi != null){
				familiareNeutro.getDestinazione(pianoQuartoPalazzoPersonaggi);
				familiareNero.getDestinazione(pianoQuartoPalazzoPersonaggi);
				familiareArancio.getDestinazione(pianoQuartoPalazzoPersonaggi);
				familiareBianco.getDestinazione(pianoQuartoPalazzoPersonaggi);
			}
		});
		pianoPrimoPalazzoEdifici.setOnDragDropped(event->{
			if(pianoPrimoPalazzoEdifici != null){
				familiareNeutro.getDestinazione(pianoPrimoPalazzoEdifici);
				familiareNero.getDestinazione(pianoPrimoPalazzoEdifici);
				familiareArancio.getDestinazione(pianoPrimoPalazzoEdifici);
				familiareBianco.getDestinazione(pianoPrimoPalazzoEdifici);
			}
		});
		pianoSecondoPalazzoEdifici.setOnDragDropped(event->{
			if(pianoSecondoPalazzoEdifici != null){
				familiareNeutro.getDestinazione(pianoSecondoPalazzoEdifici);
				familiareNero.getDestinazione(pianoSecondoPalazzoEdifici);
				familiareArancio.getDestinazione(pianoSecondoPalazzoEdifici);
				familiareBianco.getDestinazione(pianoSecondoPalazzoEdifici);
			}
		});
		pianoTerzoPalazzoEdifici.setOnDragDropped(event->{
			if(pianoTerzoPalazzoEdifici != null){
				familiareNeutro.getDestinazione(pianoTerzoPalazzoEdifici);
				familiareNero.getDestinazione(pianoTerzoPalazzoEdifici);
				familiareArancio.getDestinazione(pianoTerzoPalazzoEdifici);
				familiareBianco.getDestinazione(pianoTerzoPalazzoEdifici);
			}
		});
		pianoQuartoPalazzoEdifici.setOnDragDropped(event->{
			if(pianoQuartoPalazzoEdifici != null){
				familiareNeutro.getDestinazione(pianoQuartoPalazzoEdifici);
				familiareNero.getDestinazione(pianoQuartoPalazzoEdifici);
				familiareArancio.getDestinazione(pianoQuartoPalazzoEdifici);
				familiareBianco.getDestinazione(pianoQuartoPalazzoEdifici);
			}
		});
		pianoPrimoPalazzoTerritori.setOnDragDropped(event->{
			if(pianoPrimoPalazzoTerritori != null){
				familiareNeutro.getDestinazione(pianoPrimoPalazzoTerritori);
				familiareNero.getDestinazione(pianoPrimoPalazzoTerritori);
				familiareArancio.getDestinazione(pianoPrimoPalazzoTerritori);
				familiareBianco.getDestinazione(pianoPrimoPalazzoTerritori);
			}
		});
		pianoSecondoPalazzoTerritori.setOnDragDropped(event->{
			if(pianoSecondoPalazzoTerritori != null){
				familiareNeutro.getDestinazione(pianoSecondoPalazzoTerritori);
				familiareNero.getDestinazione(pianoSecondoPalazzoTerritori);
				familiareArancio.getDestinazione(pianoSecondoPalazzoTerritori);
				familiareBianco.getDestinazione(pianoSecondoPalazzoTerritori);
			}
		});
		pianoTerzoPalazzoTerritori.setOnDragDropped(event->{
			if(pianoTerzoPalazzoTerritori != null){
				familiareNeutro.getDestinazione(pianoTerzoPalazzoTerritori);
				familiareNero.getDestinazione(pianoTerzoPalazzoTerritori);
				familiareArancio.getDestinazione(pianoTerzoPalazzoTerritori);
				familiareBianco.getDestinazione(pianoTerzoPalazzoTerritori);
			}
		});
		pianoQuartoPalazzoTerritori.setOnDragDropped(event->{
			if(pianoQuartoPalazzoTerritori != null){
				familiareNeutro.getDestinazione(pianoQuartoPalazzoTerritori);
				familiareNero.getDestinazione(pianoQuartoPalazzoTerritori);
				familiareArancio.getDestinazione(pianoQuartoPalazzoTerritori);
				familiareBianco.getDestinazione(pianoQuartoPalazzoTerritori);
			}
		});
		
		municipio.setOnDragDropped(event->{
			if(municipio != null){
				familiareNeutro.getDestinazione(municipio);
				familiareNero.getDestinazione(municipio);
				familiareArancio.getDestinazione(municipio);
				familiareBianco.getDestinazione(municipio);
			}
		});
		azioniTerritoridapiuGiocatori.setOnDragDropped(event->{
			if(municipio != null){
				familiareNeutro.getDestinazione(municipio);
				familiareNero.getDestinazione(municipio);
				familiareArancio.getDestinazione(municipio);
				familiareBianco.getDestinazione(municipio);
			}
		});
		azioniEdificidapiuGiocatori.setOnDragDropped(event->{
			if(azioniEdificidapiuGiocatori != null){
				familiareNeutro.getDestinazione(azioniEdificidapiuGiocatori);
				familiareNero.getDestinazione(azioniEdificidapiuGiocatori);
				familiareArancio.getDestinazione(azioniEdificidapiuGiocatori);
				familiareBianco.getDestinazione(azioniEdificidapiuGiocatori);
			}
		});
		azioniTerritoridaunGiocatore.setOnDragDropped(event->{
			if(azioniTerritoridaunGiocatore != null){
				familiareNeutro.getDestinazione(azioniTerritoridaunGiocatore);
				familiareNero.getDestinazione(azioniTerritoridaunGiocatore);
				familiareArancio.getDestinazione(azioniTerritoridaunGiocatore);
				familiareBianco.getDestinazione(azioniTerritoridaunGiocatore);
			}
		});
		azioniEdificidaunGiocatore.setOnDragDropped(event->{
			if(azioniEdificidaunGiocatore != null){
				familiareNeutro.getDestinazione(azioniEdificidaunGiocatore);
				familiareNero.getDestinazione(azioniEdificidaunGiocatore);
				familiareArancio.getDestinazione(azioniEdificidaunGiocatore);
				familiareBianco.getDestinazione(azioniEdificidaunGiocatore);
			}
		});
		*/
	}


	public void setTabavv(Giocatore[] giocatori) {
		for(int i = 0;i<4;i++){
			if(!giocatori[i].getName().equals(start.getClient().getName())){
				if(name1.getText().equals("")){
					name1.setText(giocatori[i].getName());
				}else if(name2.getText().equals("")){
					name2.setText(giocatori[i].getName());
				}else if(name3.getText().equals("")){
					name3.setText(giocatori[i].getName());
				}
			}
		}
		
	}


	public void setRisorse(Portafoglio risorse) {
		setLegno(risorse.getDimRisorse("legno"));
		setPietra(risorse.getDimRisorse("pietra"));
		setServitori(risorse.getDimRisorse("servitori"));
		setMonete(risorse.getDimRisorse("monete"));
	}


	
	public void setCardsScomunica(TesseraScomunica[] cardsScomunica) {
		cartaScomunica1.setImage(new Image(getClass().getResourceAsStream("Tessera scomunica 7 periodo 1.png")));
		cartaScomunica2.setImage(new Image(getClass().getResourceAsStream("Tessera scomunica 7 periodo 2.png")));
		cartaScomunica3.setImage(new Image(getClass().getResourceAsStream("Tessera scomunica 7 periodo 3.png")));	
	}


	public void setColorCubiScomunica(String color) {
		switch(color){
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


	public void setColorsParents(String color) {
		switch(color){
			case "blue":
				familiareNeutro.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluNeutro.png")));
				familiareNero.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluNero.png")));
				familiareArancio.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluArancio.png")));
				familiareBianco.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluBianco.png")));
				familiareOrange1.setImage(new Image(this.getClass().getResourceAsStream("FamiliareArancioNeutro.png")));
				familiareOrange2.setImage(new Image(this.getClass().getResourceAsStream("FamiliareArancioArancio.png")));
				familiareOrange3.setImage(new Image(this.getClass().getResourceAsStream("FamiliareArancioBianco.png")));
				familiareOrange4.setImage(new Image(this.getClass().getResourceAsStream("FamiliareArancioNero.png")));
				familiareGreen1.setImage(new Image(this.getClass().getResourceAsStream("FamiliareVerdeNeutro.png")));
				familiareGreen2.setImage(new Image(this.getClass().getResourceAsStream("FamiliareVerdeArancio.png")));
				familiareGreen3.setImage(new Image(this.getClass().getResourceAsStream("FamiliareVerdeBianco.png")));
				familiareGreen4.setImage(new Image(this.getClass().getResourceAsStream("FamiliareVerdeNero.png")));
				familiareWhite1.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBiancoNeutro.png")));
				familiareWhite2.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBiancoArancio.png")));
				familiareWhite3.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBiancoBianco.png")));
				familiareWhite4.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBiancoNero.png")));
				flag1.setImage(new Image(this.getClass().getResourceAsStream("BandierinaVerde.png")));
				flag2.setImage(new Image(this.getClass().getResourceAsStream("BandierinaBianca.png")));
				flag3.setImage(new Image(this.getClass().getResourceAsStream("BandierinaArancio.png")));
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
				familiareOrange1.setImage(new Image(this.getClass().getResourceAsStream("FamiliareArancioNeutro.png")));
				familiareOrange2.setImage(new Image(this.getClass().getResourceAsStream("FamiliareArancioArancio.png")));
				familiareOrange3.setImage(new Image(this.getClass().getResourceAsStream("FamiliareArancioBianco.png")));
				familiareOrange4.setImage(new Image(this.getClass().getResourceAsStream("FamiliareArancioNero.png")));
				familiareBlue1.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluNeutro.png")));
				familiareBlue2.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluArancio.png")));
				familiareBlue3.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluBianco.png")));
				familiareBlue4.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluNero.png")));
				familiareWhite1.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBiancoNeutro.png")));
				familiareWhite2.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBiancoArancio.png")));
				familiareWhite3.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBiancoBianco.png")));
				familiareWhite4.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBiancoNero.png")));
				flag1.setImage(new Image(this.getClass().getResourceAsStream("BandierinaBlu.png")));
				flag2.setImage(new Image(this.getClass().getResourceAsStream("BandierinaBianca.png")));
				flag3.setImage(new Image(this.getClass().getResourceAsStream("BandierinaArancio.png")));
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
				familiareOrange1.setImage(new Image(this.getClass().getResourceAsStream("FamiliareArancioNeutro.png")));
				familiareOrange2.setImage(new Image(this.getClass().getResourceAsStream("FamiliareArancioArancio.png")));
				familiareOrange3.setImage(new Image(this.getClass().getResourceAsStream("FamiliareArancioBianco.png")));
				familiareOrange4.setImage(new Image(this.getClass().getResourceAsStream("FamiliareArancioNero.png")));
				familiareBlue1.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluNeutro.png")));
				familiareBlue2.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluArancio.png")));
				familiareBlue3.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluBianco.png")));
				familiareBlue4.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluNero.png")));
				familiareGreen1.setImage(new Image(this.getClass().getResourceAsStream("FamiliareVerdeNeutro.png")));
				familiareGreen2.setImage(new Image(this.getClass().getResourceAsStream("FamiliareVerdeArancio.png")));
				familiareGreen3.setImage(new Image(this.getClass().getResourceAsStream("FamiliareVerdeBianco.png")));
				familiareGreen4.setImage(new Image(this.getClass().getResourceAsStream("FamiliareVerdeNero.png")));
				flag1.setImage(new Image(this.getClass().getResourceAsStream("BandierinaVerde.png")));
				flag2.setImage(new Image(this.getClass().getResourceAsStream("BandierinaBlu.png")));
				flag3.setImage(new Image(this.getClass().getResourceAsStream("BandierinaArancio.png")));
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
				familiareBlue2.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluArancio.png")));
				familiareBlue3.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluBianco.png")));
				familiareBlue4.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBluNero.png")));
				familiareGreen1.setImage(new Image(this.getClass().getResourceAsStream("FamiliareVerdeNeutro.png")));
				familiareGreen2.setImage(new Image(this.getClass().getResourceAsStream("FamiliareVerdeArancio.png")));
				familiareGreen3.setImage(new Image(this.getClass().getResourceAsStream("FamiliareVerdeBianco.png")));
				familiareGreen4.setImage(new Image(this.getClass().getResourceAsStream("FamiliareVerdeNero.png")));
				familiareWhite1.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBiancoNeutro.png")));
				familiareWhite2.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBiancoArancio.png")));
				familiareWhite3.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBiancoBianco.png")));
				familiareWhite4.setImage(new Image(this.getClass().getResourceAsStream("FamiliareBiancoNero.png")));
				flag1.setImage(new Image(this.getClass().getResourceAsStream("")));
				flag2.setImage(new Image(this.getClass().getResourceAsStream("BandierinaBianca.png")));
				flag3.setImage(new Image(this.getClass().getResourceAsStream("")));
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
	
	public void setLegno(int legno){
		this.lengo.setText(Integer.toString(legno));
	}
	
	/*
	 * Controllo per verificare se si ha un numero di punti del dado
	 */
	public boolean controlloPosizionamento(String color, double x, double y, int integer) {
		String mom = null;
		boolean risposta = false;
		try {
			mom = start.getClient().controlloPosizionamento(color, x, y, integer);
		} catch (ClassNotFoundException | IOException e) {
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
			bOk.setOnAction(event -> {
				controlloPosizionamento(color,x,y,Integer.getInteger(text.getText()));
				popup.close();
			});
			bCancel.setOnAction(event -> {
				switch (color) {
				case "neutro":
					familiareNeutro.setImage(new Image(getClass().getResourceAsStream("")));
					break;
				case "black":
					familiareNero.setImage(new Image(getClass().getResourceAsStream("")));
					break;
				case "orange":
					familiareArancio.setImage(new Image(getClass().getResourceAsStream("")));
					break;
				case "white":
					familiareBianco.setImage(new Image(getClass().getResourceAsStream("")));
					break;
				}
				popup.close();
			});
		} else if (mom == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(start.getStage());
			alert.setTitle("Lost DB connection");
			alert.setContentText("Ci dispiace ma il nostro servizio a smesso di funzionare");
			alert.showAndWait();
			return false;
		}else if(mom.equals("OK"))
			return true;
		else if(mom.equals("NotEnough")){
			return false;
		}
		return false;
	}

	public void movePuntiFede(String color, double x, double y) {
		double momX;
		double momY;
		switch (color) {
		case "blue":
			momX = puntiFedeBlu.getX();
			momY = puntiFedeBlu.getY();
			while (momX == x && momY == y) {
				if (momX == x) {
					momY++;
					puntiFedeBlu.setY(momY);
				} else if (momY == y) {
					momX++;
					puntiFedeBlu.setY(momX);
				} else {
					momY++;
					puntiFedeBlu.setY(momY);
					momX++;
					puntiFedeBlu.setY(momX);
				}
			}
			break;
		case "white":
			momX = puntiFedeBianco.getX();
			momY = puntiFedeBianco.getY();
			while (momX == x && momY == y) {
				if (momX == x) {
					momY++;
					puntiFedeBianco.setY(momY);
				} else if (momY == y) {
					momX++;
					puntiFedeBianco.setY(momX);
				} else {
					momY++;
					puntiFedeBianco.setY(momY);
					momX++;
					puntiFedeBianco.setY(momX);
				}
			}
			break;
		case "orange":
			momX = puntiFedeArancio.getX();
			momY = puntiFedeArancio.getY();
			while (momX == x && momY == y) {
				if (momX == x) {
					momY++;
					puntiFedeArancio.setY(momY);
				} else if (momY == y) {
					momX++;
					puntiFedeArancio.setY(momX);
				} else {
					momY++;
					puntiFedeArancio.setY(momY);
					momX++;
					puntiFedeArancio.setY(momX);
				}
			}
			break;
		case "green":
			momX = puntiFedeVerde.getX();
			momY = puntiFedeVerde.getY();
			while (momX == x && momY == y) {
				if (momX == x) {
					momY++;
					puntiFedeVerde.setY(momY);
				} else if (momY == y) {
					momX++;
					puntiFedeVerde.setY(momX);
				} else {
					momY++;
					puntiFedeVerde.setY(momY);
					momX++;
					puntiFedeVerde.setY(momX);
				}
			}
			break;
		}
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

	public void movePunti(String color, double x, double y) {
		double momX;
		double momY;
		switch (color) {
		case "blue":
			momX = puntiVittoriaBlu.getX();
			momY = puntiVittoriaBlu.getY();
			while (momX == x && momY == y) {
				if (momX == x) {
					momY++;
					puntiVittoriaBlu.setY(momY);
				} else if (momY == y) {
					momX++;
					puntiVittoriaBlu.setY(momX);
				} else {
					momY++;
					puntiVittoriaBlu.setY(momY);
					momX++;
					puntiVittoriaBlu.setY(momX);
				}
			}
			break;
		case "white":
			momX = puntiVittoriaBianco.getX();
			momY = puntiVittoriaBianco.getY();
			while (momX == x && momY == y) {
				if (momX == x) {
					momY++;
					puntiVittoriaBianco.setY(momY);
				} else if (momY == y) {
					momX++;
					puntiVittoriaBianco.setY(momX);
				} else {
					momY++;
					puntiVittoriaBianco.setY(momY);
					momX++;
					puntiVittoriaBianco.setY(momX);
				}
			}
			break;
		case "orange":
			momX = puntiVittoriaArancio.getX();
			momY = puntiVittoriaArancio.getY();
			while (momX == x && momY == y) {
				if (momX == x) {
					momY++;
					puntiVittoriaArancio.setY(momY);
				} else if (momY == y) {
					momX++;
					puntiVittoriaArancio.setY(momX);
				} else {
					momY++;
					puntiVittoriaArancio.setY(momY);
					momX++;
					puntiVittoriaArancio.setY(momX);
				}
			}
			break;
		case "green":
			momX = puntiVittoriaVerde.getX();
			momY = puntiVittoriaVerde.getY();
			while (momX == x && momY == y) {
				if (momX == x) {
					momY++;
					puntiVittoriaVerde.setY(momY);
				} else if (momY == y) {
					momX++;
					puntiVittoriaVerde.setY(momX);
				} else {
					momY++;
					puntiVittoriaVerde.setY(momY);
					momX++;
					puntiVittoriaVerde.setY(momX);
				}
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

	public void setCards(CartaSviluppo[] carte) {
		for (int i = 0; i < 4; i++) {
			ImageView mom = new ImageView();
			mom.setImage(new Image(getClass().getResourceAsStream("Borgo.png")));
			Tooltip.install(mom,new Tooltip("Borgo.png" ));
			carteTerritori.getChildren().add(mom);
		}
		for (int i = 0; i < 4; i++) {
			ImageView mom = new ImageView();
			mom.setImage(new Image(getClass().getResourceAsStream("Artigiano.png")));
			Tooltip.install(mom, new Tooltip("Artigiano.png"));
			cartePersonaggi.getChildren().add(mom);
		}
		for (int i = 0; i < 4; i++) {
			ImageView mom = new ImageView();
			mom.setImage(new Image(getClass().getResourceAsStream("Caserma.png")));
			Tooltip.install(mom,new Tooltip("Caserma.png") );
			carteEdifici.getChildren().add(mom);
		}
		for (int i = 0; i < 4; i++) {
			
			ImageView mom = new ImageView();
			mom.setImage(new Image(getClass().getResourceAsStream("Ingaggiare Reclute.png")));
			Tooltip.install(mom,new Tooltip("Ingaggiare Reclute.png"));
			carteImprese.getChildren().add(mom);
		}
	}

	public void notifySpostamento(String color, double x, double y) throws IOException {
		start.getClient().notifySpostamento(color,x,y);
	}

	public String getNamePosition(double x, double y) throws IOException {
		try {
			return start.getClient().getNamePosition(x,y);
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
		switch(namePosition){
		case "PIANO 1 CARTE TERRITORI":
			carteTerritoriGiocatore.getChildren().add(carteTerritori.getChildren().get(0));
			Tooltip.install(carteTerritoriGiocatore.getChildren().get(0), arrayCarteTerritori[0].getTooltip());
			carteTerritori.getChildren().set(0, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCarteTerritori[0]);
			break;
		case "PIANO 2 CARTE TERRITORI":
			carteTerritoriGiocatore.getChildren().add(carteTerritori.getChildren().get(1));
			Tooltip.install(carteTerritoriGiocatore.getChildren().get(1), arrayCarteTerritori[1].getTooltip());
			carteTerritori.getChildren().set(1, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCarteTerritori[1]);
			break;
		case "PIANO 3 CARTE TERRITORI":
			carteTerritoriGiocatore.getChildren().add(carteTerritori.getChildren().get(2));
			Tooltip.install(carteTerritoriGiocatore.getChildren().get(2), arrayCarteTerritori[2].getTooltip());
			carteTerritori.getChildren().set(2, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCarteTerritori[2]);
			break;
		case "PIANO 4 CARTE TERRITORI":
			carteTerritoriGiocatore.getChildren().add(carteTerritori.getChildren().get(3));
			Tooltip.install(carteTerritoriGiocatore.getChildren().get(3), arrayCarteTerritori[3].getTooltip());
			carteTerritori.getChildren().set(3, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCarteTerritori[3]);
			break;
		case "PIANO 1 CARTE EDIFICI":
			carteEdificiGiocatore.getChildren().add(carteEdifici.getChildren().get(0));
			Tooltip.install(carteEdificiGiocatore.getChildren().get(0), arrayCarteEdifici[0].getTooltip());
			carteEdifici.getChildren().set(0, new ImageView(new Image(getClass().getResourceAsStream(""))));

			start.getClient().setCardGiocatore(arrayCarteEdifici[0]);
			break;
		case "PIANO 2 CARTE EDIFICI":
			carteEdificiGiocatore.getChildren().add(carteEdifici.getChildren().get(1));
			Tooltip.install(carteEdificiGiocatore.getChildren().get(1), arrayCarteEdifici[1].getTooltip());
			carteEdifici.getChildren().set(1, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCarteEdifici[1]);
			break;
		case "PIANO 3 CARTE EDIFICI":
			carteEdificiGiocatore.getChildren().add(carteEdifici.getChildren().get(2));
			Tooltip.install(carteEdificiGiocatore.getChildren().get(2), arrayCarteEdifici[2].getTooltip());
			carteEdifici.getChildren().set(2, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCarteEdifici[2]);
			break;
		case "PIANO 4 CARTE EDIFICI":
			carteEdificiGiocatore.getChildren().add(carteEdifici.getChildren().get(3));
			Tooltip.install(carteEdificiGiocatore.getChildren().get(3), arrayCarteEdifici[3].getTooltip());
			carteEdifici.getChildren().set(3, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCarteEdifici[3]);
			break;
		case "PIANO 1 CARTE IMPRESE":
			carteImpresaGiocatore.getChildren().add(carteImprese.getChildren().get(0));
			Tooltip.install(carteImpresaGiocatore.getChildren().get(0), arrayCarteImpresa[0].getTooltip());
			carteImprese.getChildren().set(0, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCarteImpresa[0]);
			break;
		case "PIANO 2 CARTE IMPRESE":
			carteImpresaGiocatore.getChildren().add(carteImprese.getChildren().get(1));
			Tooltip.install(carteImpresaGiocatore.getChildren().get(1), arrayCarteImpresa[1].getTooltip());
			carteImprese.getChildren().set(1, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCarteImpresa[1]);
			break;
		case "PIANO 3 CARTE IMPRESE":
			carteImpresaGiocatore.getChildren().add(carteImprese.getChildren().get(2));
			Tooltip.install(carteImpresaGiocatore.getChildren().get(2), arrayCarteImpresa[2].getTooltip());
			carteImprese.getChildren().set(2, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCarteImpresa[2]);
			break;
		case "PIANO 4 CARTE IMPRESE":
			carteImpresaGiocatore.getChildren().add(carteImprese.getChildren().get(3));
			Tooltip.install(carteImpresaGiocatore.getChildren().get(3), arrayCarteImpresa[3].getTooltip());
			carteImprese.getChildren().set(3, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCarteImpresa[3]);
			break;
		case "PIANO 1 CARTE PERSONAGGI":
			cartePersonaggiGiocatore.getChildren().add(cartePersonaggi.getChildren().get(0));
			Tooltip.install(cartePersonaggiGiocatore.getChildren().get(0), arrayCartePersonaggi[0].getTooltip());
			cartePersonaggi.getChildren().set(0, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCartePersonaggi[0]);
			break;
		case "PIANO 2 CARTE PERSONAGG":
			cartePersonaggiGiocatore.getChildren().add(cartePersonaggi.getChildren().get(1));
			Tooltip.install(cartePersonaggiGiocatore.getChildren().get(1), arrayCartePersonaggi[1].getTooltip());
			cartePersonaggi.getChildren().set(1, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCartePersonaggi[1]);
			break;
		case "PIANO 3 CARTE PERSONAGGI":
			cartePersonaggiGiocatore.getChildren().add(cartePersonaggi.getChildren().get(2));
			Tooltip.install(cartePersonaggiGiocatore.getChildren().get(2), arrayCartePersonaggi[2].getTooltip());
			cartePersonaggi.getChildren().set(2, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCartePersonaggi[2]);
			break;
		case "PIANO 4 CARTE PERSONAGGI":
			cartePersonaggiGiocatore.getChildren().add(cartePersonaggi.getChildren().get(3));
			Tooltip.install(cartePersonaggiGiocatore.getChildren().get(3), arrayCartePersonaggi[3].getTooltip());
			cartePersonaggi.getChildren().set(3, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCartePersonaggi[3]);
			break;
		case "":
			
		}
	}
	
	public void enableGame() {
		familiareNeutro.setDisable(false);
		familiareNero.setDisable(false);
		familiareArancio.setDisable(false);
		familiareBianco.setDisable(false);
		lanciaDadi.setDisable(false);
	}
	
	
	public void resetTabellon() throws ClassNotFoundException, IOException{
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
	
	public void setPosizioni() throws IOException, ClassNotFoundException {
		int i=0;
		ImageView mom = new ImageView(new Image(getClass().getResourceAsStream("Disco1.png")));
		mom.setFitWidth(35);
		mom.setFitHeight(38);
		posizioni.getChildren().add(mom);
		 mom = new ImageView(new Image(getClass().getResourceAsStream("Disco12.png")));
		 mom.setFitWidth(35);
			mom.setFitHeight(38);
		 posizioni.getChildren().add(mom);
		 mom = new ImageView(new Image(getClass().getResourceAsStream("Disco3.png")));
		 mom.setFitWidth(35);
			mom.setFitHeight(38);
		 posizioni.getChildren().add(mom);
		 mom = new ImageView(new Image(getClass().getResourceAsStream("Disco4.png")));
		 mom.setFitWidth(35);
			mom.setFitHeight(38);
		posizioni.getChildren().add(mom);
		
	}


	@FXML
	public void lanciaDadi() throws RemoteException, SQLException {
		
		dadoNero.setImage(new Image(getClass().getResourceAsStream("dado1.png")));
		dadoBianco.setImage(new Image(getClass().getResourceAsStream("dado14.png")));
		dadoArancio.setImage(new Image(getClass().getResourceAsStream("dado7.png")));
	}


	public void notifyAddCardAvv(CartaSviluppo carta, String nameAvv, Portafoglio portafoglio) {
		if(nameAvv.equals(name1.getText())){
			for(int i=0; i<4;i++){
				if(arrayCarteTerritori[i].equals(carta)){
					carteTerritori.getChildren().get(i).setClip(null);
					//aggiungere alla lista delle carte dell'avversari
					//aggiornare le label delle risorse del giocatore
				}
			}
		}else if(nameAvv.equals(name2.getText())){
			
		}else if(nameAvv.equals(name3.getText())){
			
		}
	}
	
	public void parentsProperties(){
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
		
		familiareNeutro.setOnDragOver(event ->{
			System.out.println("setOnDragOver");       
	    	if (!event.isDropCompleted() &&
	               event.getDragboard().hasString()) {
	            event.acceptTransferModes(TransferMode.MOVE);
	        }

	        event.consume();
		});
		
		familiareArancio.setOnDragOver(event ->{
			System.out.println("setOnDragOver");       
	    	if (!event.isDropCompleted() &&
	               event.getDragboard().hasString()) {
	            event.acceptTransferModes(TransferMode.MOVE);
	        }

	        event.consume();
		});
		
		familiareBianco.setOnDragOver(event ->{
			System.out.println("setOnDragOver");       
	    	if (!event.isDropCompleted() &&
	               event.getDragboard().hasString()) {
	            event.acceptTransferModes(TransferMode.MOVE);
	        }

	        event.consume();
		});
		
		familiareNero.setOnDragOver(event ->{
			System.out.println("setOnDragOver");       
	    	if (!event.isDropCompleted() &&
	               event.getDragboard().hasString()) {
	            event.acceptTransferModes(TransferMode.MOVE);
	        }
	        event.consume();
		});
	
	
		familiareBianco.setOnDragEntered(event -> {
    	System.out.println("setOnDragEntered");
        if (!event.isDropCompleted() &&
                event.getDragboard().hasString()) {
        	familiareBianco.setOpacity(0.3);
        }
		});
	
		familiareArancio.setOnDragEntered(event -> {
	    	System.out.println("setOnDragEntered");
	        if (!event.isDropCompleted() &&
	                event.getDragboard().hasString()) {
	        	familiareArancio.setOpacity(0.3);
	        }
	    });
		familiareNero.setOnDragEntered(event -> {
	    	System.out.println("setOnDragEntered");
	        if (!event.isDropCompleted() &&
	                event.getDragboard().hasString()) {
	        	familiareNero.setOpacity(0.3);
	        }
	    });
		familiareNeutro.setOnDragEntered(event -> {
	    	System.out.println("setOnDragEntered");
	        if (!event.isDropCompleted() &&
	                event.getDragboard().hasString()) {
	        	familiareNeutro.setOpacity(0.3);
	        }
	    });

		familiareNeutro.setOnDragExited(event -> {
    	System.out.println("setOnDragExited");
        if (!event.isDropCompleted() &&
                event.getDragboard().hasString()) {
        	familiareNeutro.setOpacity(1);
        }
    });
		
		familiareNero.setOnDragExited(event -> {
	    	System.out.println("setOnDragExited");
	        if (!event.isDropCompleted() &&
	                event.getDragboard().hasString()) {
	        	familiareNero.setOpacity(1);
	        }
	    });
		
		familiareArancio.setOnDragExited(event -> {
	    	System.out.println("setOnDragExited");
	        if (!event.isDropCompleted() &&
	                event.getDragboard().hasString()) {
	        	familiareArancio.setOpacity(1);
	        }
	    });
		
		familiareBianco.setOnDragExited(event -> {
	    	System.out.println("setOnDragExited");
	        if (!event.isDropCompleted() &&
	                event.getDragboard().hasString()) {
	        	familiareBianco.setOpacity(1);
	        }
	    });
    
		familiareBianco.setOnDragDropped(event->{
    	System.out.println("setOnDragDropped");
        if (familiareBianco.getImage() == null) {
            return;
        }
        Dragboard drag = event.getDragboard();
        boolean success = false;
        
        if(drag.hasImage()){
        	success = true;
        }
        
        event.setDropCompleted(success);
        event.consume();
        
    });
    
		familiareNero.setOnDragDropped(event->{
	    	System.out.println("setOnDragDropped");
	        if (familiareNero.getImage() == null) {
	            return;
	        }
	        Dragboard drag = event.getDragboard();
	        boolean success = false;
	        
	        if(drag.hasImage()){
	        	success = true;
	        }
	        
	        event.setDropCompleted(success);
	        event.consume();
	        
	    });
		
		familiareArancio.setOnDragDropped(event->{
	    	System.out.println("setOnDragDropped");
	        if (familiareArancio.getImage() == null) {
	            return;
	        }
	        Dragboard drag = event.getDragboard();
	        boolean success = false;
	        
	        if(drag.hasImage()){
	        	success = true;
	        }
	        
	        event.setDropCompleted(success);
	        event.consume();
	        
	    });
		
		familiareNeutro.setOnDragDropped(event->{
	    	System.out.println("setOnDragDropped");
	        if (familiareNeutro.getImage() == null) {
	            return;
	        }
	        Dragboard drag = event.getDragboard();
	        boolean success = false;
	        
	        if(drag.hasImage()){
	        	success = true;
	        }
	        
	        event.setDropCompleted(success);
	        event.consume();
	        
	    });
    
		azioniTerritoridaunGiocatore.setOnDragEntered(e->{
			setDestinazione1(azioniTerritoridaunGiocatore);
		});
    
		azioniTerritoridapiuGiocatori.setOnDragEntered(e ->{
			setDestinazione2(azioniTerritoridapiuGiocatori);
		});
		
		familiareNeutro.setOnDragDone(event -> {
		//if (controlloPosizionamento(start.getColor(), familiareNero.getX(), familiareNero.getY(), 0))
				if (flag) {
					if (flag) {
						destinazione1.setImage(new Image(getClass().getResourceAsStream("FamiliareBluNeutro.png")));
						familiareNeutro.setDisable(true);
					} else {
						ImageView mom = new ImageView(
								new Image(getClass().getResourceAsStream("FamiliareBluNeutro.png")));
						mom.setFitWidth(35);
						mom.setFitHeight(38);
						destinazione2.getChildren().add(mom);
						familiareNeutro.setDisable(true);
					}
					familiareNeutro.setOpacity(0);
				}
		});
		
		familiareNero.setOnDragDone(event -> {
			//if (controlloPosizionamento(start.getColor(), familiareNero.getX(), familiareNero.getY(), 0))
					if (flag) {
						destinazione1.setImage(new Image(getClass().getResourceAsStream("FamiliareBluNeutro.png")));
						familiareNero.setDisable(true);
					} else {
						ImageView mom = new ImageView(
								new Image(getClass().getResourceAsStream("FamiliareBluNeutro.png")));
						mom.setFitWidth(35);
						mom.setFitHeight(38);
						destinazione2.getChildren().add(mom);
						familiareNero.setDisable(true);
					}
					familiareNero.setOpacity(0);
		});
		
		familiareArancio.setOnDragDone(event -> {
			//if (controlloPosizionamento(start.getColor(), familiareArancio.getX(), familiareArancio.getY(), 0))
					if (flag) {
						destinazione1.setImage(new Image(getClass().getResourceAsStream("FamiliareBluNeutro.png")));
						familiareArancio.setDisable(true);
					} else {
						ImageView mom = new ImageView(
								new Image(getClass().getResourceAsStream("FamiliareBluNeutro.png")));
						mom.setFitWidth(35);
						mom.setFitHeight(38);
						destinazione2.getChildren().add(mom);
						familiareArancio.setDisable(true);
					}
					familiareNeutro.setOpacity(0);
		});
		
		familiareBianco.setOnDragDone(event -> {
			//if (controlloPosizionamento(start.getColor(), familiareBianco.getX(), familiareBianco.getY(), 0))
					if (flag) {
						destinazione1.setImage(new Image(getClass().getResourceAsStream("FamiliareBluNeutro.png")));
						familiareBianco.setDisable(true);
					} else {
						ImageView mom = new ImageView(
								new Image(getClass().getResourceAsStream("FamiliareBluNeutro.png")));
						mom.setFitWidth(35);
						mom.setFitHeight(38);
						destinazione2.getChildren().add(mom);
						familiareBianco.setDisable(true);
					}
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
}
