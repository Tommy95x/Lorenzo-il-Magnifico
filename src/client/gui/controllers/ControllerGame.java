package client.gui.controllers;

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
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
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
import server.element.Legno;
import server.element.Portafoglio;
import server.element.TesseraScomunica;

public class ControllerGame {

	private StartClientGui start;
	private CartaTerritori[] arrayCarteTerritori;
	private CartaImprese[] arrayCarteImpresa;
	private CartaPersonaggi[] arrayCartePersonaggi;
	private CartaEdifici[] arrayCarteEdifici;

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
	public Immagine familiareNeutro;
	@FXML
	public Immagine familiareNero;
	@FXML
	public Immagine familiareArancio;
	@FXML
	public Immagine familiareBianco;
	@FXML
	public Immagine cuboScomunica1;
	@FXML
	public Immagine cuboScomunica2;
	@FXML
	public Immagine cuboScomunica3;

	public void getStartClient(StartClientGui startClientGui) throws ClassNotFoundException, IOException {
		this.setStart(startClientGui);
		setColorsParents(start.getColor());
		setColorCubiScomunica(start.getColor());
		setCards(start.getClient().getCardsGame());
		setCardsScomunica(start.getClient().getCardsScomunica());
		setRisorse(start.getClient().getRisorse());
		start.getClient().setGuiGame(this);
		start.getClient().waitTurno();
	}


	private void setRisorse(Portafoglio risorse) {
		setLegno(risorse.getDimRisorse("legno"));
		setPietra(risorse.getDimRisorse("pietra"));
		setServitori(risorse.getDimRisorse("servitori"));
		setMonete(risorse.getDimRisorse("monete"));
	}


	private void setCardsScomunica(TesseraScomunica[] cardsScomunica) {
		cartaScomunica1.setImage(cardsScomunica[0].getImage());
		cartaScomunica2.setImage(cardsScomunica[1].getImage());
		cartaScomunica3.setImage(cardsScomunica[2].getImage());
		
	}


	private void setColorCubiScomunica(String color) {
		cuboScomunica1.setColor(color);
		cuboScomunica2.setColor(color);
		cuboScomunica3.setColor(color);
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


	private void setColorsParents(String color) {
		familiareNeutro.setColor("neutro");
		familiareNero.setColor("black");
		familiareArancio.setColor("orange");
		familiareBianco.setColor("white");
		switch(color){
			case "blue":
				familiareNeutro.setImage(new Image(getClass().getResourceAsStream("")));
				familiareNero.setImage(new Image(getClass().getResourceAsStream("")));
				familiareArancio.setImage(new Image(getClass().getResourceAsStream("")));
				familiareBianco.setImage(new Image(getClass().getResourceAsStream("")));
				familiareOrange1.setImage(new Image(getClass().getResourceAsStream("")));
				familiareOrange2.setImage(new Image(getClass().getResourceAsStream("")));
				familiareOrange3.setImage(new Image(getClass().getResourceAsStream("")));
				familiareOrange4.setImage(new Image(getClass().getResourceAsStream("")));
				familiareGreen1.setImage(new Image(getClass().getResourceAsStream("")));
				familiareGreen2.setImage(new Image(getClass().getResourceAsStream("")));
				familiareGreen3.setImage(new Image(getClass().getResourceAsStream("")));
				familiareGreen4.setImage(new Image(getClass().getResourceAsStream("")));
				familiareGreen1.setImage(new Image(getClass().getResourceAsStream("")));
				familiareWhite2.setImage(new Image(getClass().getResourceAsStream("")));
				familiareWhite3.setImage(new Image(getClass().getResourceAsStream("")));
				familiareWhite4.setImage(new Image(getClass().getResourceAsStream("")));
				break;
			case "green":
				familiareNeutro.setImage(new Image(getClass().getResourceAsStream("")));
				familiareNero.setImage(new Image(getClass().getResourceAsStream("")));
				familiareArancio.setImage(new Image(getClass().getResourceAsStream("")));
				familiareBianco.setImage(new Image(getClass().getResourceAsStream("")));
				familiareOrange1.setImage(new Image(getClass().getResourceAsStream("")));
				familiareOrange2.setImage(new Image(getClass().getResourceAsStream("")));
				familiareOrange3.setImage(new Image(getClass().getResourceAsStream("")));
				familiareOrange4.setImage(new Image(getClass().getResourceAsStream("")));
				familiareBlue1.setImage(new Image(getClass().getResourceAsStream("")));
				familiareBlue2.setImage(new Image(getClass().getResourceAsStream("")));
				familiareBlue3.setImage(new Image(getClass().getResourceAsStream("")));
				familiareBlue4.setImage(new Image(getClass().getResourceAsStream("")));
				familiareWhite1.setImage(new Image(getClass().getResourceAsStream("")));
				familiareWhite2.setImage(new Image(getClass().getResourceAsStream("")));
				familiareWhite3.setImage(new Image(getClass().getResourceAsStream("")));
				familiareWhite4.setImage(new Image(getClass().getResourceAsStream("")));
				break;
			case "white":
				familiareNeutro.setImage(new Image(getClass().getResourceAsStream("")));
				familiareNero.setImage(new Image(getClass().getResourceAsStream("")));
				familiareArancio.setImage(new Image(getClass().getResourceAsStream("")));
				familiareBianco.setImage(new Image(getClass().getResourceAsStream("")));
				familiareOrange1.setImage(new Image(getClass().getResourceAsStream("")));
				familiareOrange2.setImage(new Image(getClass().getResourceAsStream("")));
				familiareOrange3.setImage(new Image(getClass().getResourceAsStream("")));
				familiareOrange4.setImage(new Image(getClass().getResourceAsStream("")));
				familiareBlue1.setImage(new Image(getClass().getResourceAsStream("")));
				familiareBlue2.setImage(new Image(getClass().getResourceAsStream("")));
				familiareBlue3.setImage(new Image(getClass().getResourceAsStream("")));
				familiareBlue4.setImage(new Image(getClass().getResourceAsStream("")));
				familiareGreen1.setImage(new Image(getClass().getResourceAsStream("")));
				familiareGreen2.setImage(new Image(getClass().getResourceAsStream("")));
				familiareGreen3.setImage(new Image(getClass().getResourceAsStream("")));
				familiareGreen4.setImage(new Image(getClass().getResourceAsStream("")));
				break;
			case "orange":
				familiareNeutro.setImage(new Image(getClass().getResourceAsStream("")));
				familiareNero.setImage(new Image(getClass().getResourceAsStream("")));
				familiareArancio.setImage(new Image(getClass().getResourceAsStream("")));
				familiareBianco.setImage(new Image(getClass().getResourceAsStream("")));
				familiareBlue1.setImage(new Image(getClass().getResourceAsStream("")));
				familiareBlue2.setImage(new Image(getClass().getResourceAsStream("")));
				familiareBlue3.setImage(new Image(getClass().getResourceAsStream("")));
				familiareBlue4.setImage(new Image(getClass().getResourceAsStream("")));
				familiareGreen1.setImage(new Image(getClass().getResourceAsStream("")));
				familiareGreen2.setImage(new Image(getClass().getResourceAsStream("")));
				familiareGreen3.setImage(new Image(getClass().getResourceAsStream("")));
				familiareGreen4.setImage(new Image(getClass().getResourceAsStream("")));
				familiareWhite1.setImage(new Image(getClass().getResourceAsStream("")));
				familiareWhite2.setImage(new Image(getClass().getResourceAsStream("")));
				familiareWhite3.setImage(new Image(getClass().getResourceAsStream("")));
				familiareWhite4.setImage(new Image(getClass().getResourceAsStream("")));
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
	public boolean controlloPosizionamento(String color, double x, double y, Integer integer) {
		String mom = null;
		boolean risposta = false;
		try {
			mom = start.getClient().controlloPosizionamento(color, x, y, integer);
		} catch (RemoteException e) {
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

	public void setCards(ArrayList<CartaSviluppo> carte) {
		for (int i = 0; i < 4; i++) {
			arrayCarteTerritori[i] = (CartaTerritori) carte.get(i);
			ImageView mom = new ImageView();
			mom.setImage(carte.get(i).getImage());
			Tooltip.install(mom,carte.get(i).getTooltip() );
			carteTerritori.getChildren().add(mom);
		}
		for (int i = 4; i < 8; i++) {
			arrayCarteEdifici[i] = (CartaEdifici) carte.get(i);
			ImageView mom = new ImageView();
			mom.setImage(carte.get(i).getImage());
			Tooltip.install(mom,carte.get(i).getTooltip() );
			carteEdifici.getChildren().add(mom);
		}
		for (int i = 8; i < 12; i++) {
			arrayCartePersonaggi[i] = (CartaPersonaggi) carte.get(i);
			ImageView mom = new ImageView();
			mom.setImage(carte.get(i).getImage());
			Tooltip.install(mom,carte.get(i).getTooltip() );
			cartePersonaggi.getChildren().add(mom);
		}
		for (int i = 12; i < 16; i++) {
			arrayCarteImpresa[i] = (CartaImprese) carte.get(i);
			ImageView mom = new ImageView();
			mom.setImage(carte.get(i).getImage());
			Tooltip.install(mom,carte.get(i).getTooltip() );
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
		case "primo piano territori":
			carteTerritoriGiocatore.getChildren().add(carteTerritori.getChildren().get(0));
			Tooltip.install(carteTerritoriGiocatore.getChildren().get(0), arrayCarteTerritori[0].getTooltip());
			carteTerritori.getChildren().set(0, new ImageView(new Image(getClass().getResourceAsStream(""))));
			
			start.getClient().setCardGiocatore(arrayCarteTerritori[0]);
			break;
		case "secondo piano territori":
			carteTerritoriGiocatore.getChildren().add(carteTerritori.getChildren().get(1));
			Tooltip.install(carteTerritoriGiocatore.getChildren().get(1), arrayCarteTerritori[1].getTooltip());
			carteTerritori.getChildren().set(1, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCarteTerritori[1]);
			break;
		case "terzo piano territori":
			carteTerritoriGiocatore.getChildren().add(carteTerritori.getChildren().get(2));
			Tooltip.install(carteTerritoriGiocatore.getChildren().get(2), arrayCarteTerritori[2].getTooltip());
			carteTerritori.getChildren().set(2, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCarteTerritori[2]);
			break;
		case "quarto piano territori":
			carteTerritoriGiocatore.getChildren().add(carteTerritori.getChildren().get(3));
			Tooltip.install(carteTerritoriGiocatore.getChildren().get(3), arrayCarteTerritori[3].getTooltip());
			carteTerritori.getChildren().set(3, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCarteTerritori[3]);
			break;
		case "primo piano edifici":
			carteEdificiGiocatore.getChildren().add(carteEdifici.getChildren().get(0));
			Tooltip.install(carteEdificiGiocatore.getChildren().get(0), arrayCarteEdifici[0].getTooltip());
			carteEdifici.getChildren().set(0, new ImageView(new Image(getClass().getResourceAsStream(""))));

			start.getClient().setCardGiocatore(arrayCarteEdifici[0]);
			break;
		case "secondo piano edifici":
			carteEdificiGiocatore.getChildren().add(carteEdifici.getChildren().get(1));
			Tooltip.install(carteEdificiGiocatore.getChildren().get(1), arrayCarteEdifici[1].getTooltip());
			carteEdifici.getChildren().set(1, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCarteEdifici[1]);
			break;
		case "terzo piano edifici":
			carteEdificiGiocatore.getChildren().add(carteEdifici.getChildren().get(2));
			Tooltip.install(carteEdificiGiocatore.getChildren().get(2), arrayCarteEdifici[2].getTooltip());
			carteEdifici.getChildren().set(2, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCarteEdifici[2]);
			break;
		case "quarto piano edifici":
			carteEdificiGiocatore.getChildren().add(carteEdifici.getChildren().get(3));
			Tooltip.install(carteEdificiGiocatore.getChildren().get(3), arrayCarteEdifici[3].getTooltip());
			carteEdifici.getChildren().set(3, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCarteEdifici[3]);
			break;
		case "primo piano imprese":
			carteImpresaGiocatore.getChildren().add(carteImprese.getChildren().get(0));
			Tooltip.install(carteImpresaGiocatore.getChildren().get(0), arrayCarteImpresa[0].getTooltip());
			carteImprese.getChildren().set(0, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCarteImpresa[0]);
			break;
		case "secondo piano imprese":
			carteImpresaGiocatore.getChildren().add(carteImprese.getChildren().get(1));
			Tooltip.install(carteImpresaGiocatore.getChildren().get(1), arrayCarteImpresa[1].getTooltip());
			carteImprese.getChildren().set(1, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCarteImpresa[1]);
			break;
		case "terzo piano imprese":
			carteImpresaGiocatore.getChildren().add(carteImprese.getChildren().get(2));
			Tooltip.install(carteImpresaGiocatore.getChildren().get(2), arrayCarteImpresa[2].getTooltip());
			carteImprese.getChildren().set(2, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCarteImpresa[2]);
			break;
		case "quarto piano imprese":
			carteImpresaGiocatore.getChildren().add(carteImprese.getChildren().get(3));
			Tooltip.install(carteImpresaGiocatore.getChildren().get(3), arrayCarteImpresa[3].getTooltip());
			carteImprese.getChildren().set(3, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCarteImpresa[3]);
			break;
		case "primo piano personaggi":
			cartePersonaggiGiocatore.getChildren().add(cartePersonaggi.getChildren().get(0));
			Tooltip.install(cartePersonaggiGiocatore.getChildren().get(0), arrayCartePersonaggi[0].getTooltip());
			cartePersonaggi.getChildren().set(0, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCartePersonaggi[0]);
			break;
		case "secondo piano personaggi":
			cartePersonaggiGiocatore.getChildren().add(cartePersonaggi.getChildren().get(1));
			Tooltip.install(cartePersonaggiGiocatore.getChildren().get(1), arrayCartePersonaggi[1].getTooltip());
			cartePersonaggi.getChildren().set(1, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCartePersonaggi[1]);
			break;
		case "terzo piano personaggi":
			cartePersonaggiGiocatore.getChildren().add(cartePersonaggi.getChildren().get(2));
			Tooltip.install(cartePersonaggiGiocatore.getChildren().get(2), arrayCartePersonaggi[2].getTooltip());
			cartePersonaggi.getChildren().set(2, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCartePersonaggi[2]);
			break;
		case "quarto piano personaggi":
			cartePersonaggiGiocatore.getChildren().add(cartePersonaggi.getChildren().get(3));
			Tooltip.install(cartePersonaggiGiocatore.getChildren().get(3), arrayCartePersonaggi[3].getTooltip());
			cartePersonaggi.getChildren().set(3, new ImageView(new Image(getClass().getResourceAsStream(""))));
			start.getClient().setCardGiocatore(arrayCartePersonaggi[3]);
			break;
		//Vanno aggiunti i casi del tabellone, ossia gli spazi singoli d'azione
		}
	}
	
	public void enableGame() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(start.getStage());
		alert.setTitle("Notifica Turno");
		alert.setContentText("Hei, e' iniziato il tuo turno fai le tue mosse");
		alert.showAndWait();
		familiareNeutro.setDisable(false);
		familiareNero.setDisable(false);
		familiareArancio.setDisable(false);
		familiareBianco.setDisable(false);
		lanciaDadi.setDisable(false);
	}

	
	public void resetTabellon() throws ClassNotFoundException, IOException{
		try {
			setCards(start.getClient().getCardsGame());
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		start.getClient().waitTurno();
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
		dadoNero.setImage(dadi[0].getImage());
		dadoBianco.setImage(dadi[0].getImage());
		dadoArancio.setImage(dadi[0].getImage());
	}

	@FXML
	public void enteredDropImage(DragEvent e) {
		// Chiedere al prof come catturare l'immagine in cui viene posizionata
		// if()
		// Devo controllare se è libero se no non posso piazzare
		familiareNeutro.getDestinazione((ImageView) e.getAcceptingObject());
		familiareNero.getDestinazione((ImageView) e.getAcceptingObject());
		familiareArancio.getDestinazione((ImageView) e.getAcceptingObject());
		familiareBianco.getDestinazione((ImageView) e.getAcceptingObject());
	}

	@FXML
	public void enterDropBox(DragEvent e){
		familiareNeutro.getDestinazione( (HBox) e.getAcceptingObject());
		familiareNero.getDestinazione((HBox) e.getAcceptingObject());
		familiareArancio.getDestinazione((HBox) e.getAcceptingObject());
		familiareBianco.getDestinazione((HBox) e.getAcceptingObject());
	}
	
}
