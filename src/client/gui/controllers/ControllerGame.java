package client.gui.controllers;

import java.rmi.RemoteException;
import java.sql.SQLException;

import client.gui.StartClientGui;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import server.element.Dado;

public class ControllerGame {

	private StartClientGui start;
	private ImageView[] arrayCarteTerritori;
	private ImageView[] arrayCarteImpresa; 
	private ImageView[] arrayCartePersonaggi;
	private ImageView[] arrayCarteEdifici;
	
	//Componenti tabellone
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
	public ImageView pianoQuestoPalazzoMilitare;
	@FXML
	public ImageView pianoPrimoPalazzoPersonaggi;
	@FXML
	public ImageView pianoSecondoPalazzoPersonaggi;
	@FXML
	public ImageView pianoTerzoPalazzoPersonaggi;
	@FXML
	public ImageView pianoQuestoPalazzoPersonaggi;
	@FXML
	public ImageView pianoPrimoPalazzoEdifici;
	@FXML
	public ImageView pianoSecondoPalazzoEdifici;
	@FXML
	public ImageView pianoTerzoPalazzoEdifici;
	@FXML
	public ImageView pianoQuestoPalazzoEdifici;
	@FXML
	public ImageView pianoPrimoPalazzoTerritori;
	@FXML
	public ImageView pianoSecondoPalazzoTerritori;
	@FXML
	public ImageView pianoTerzoPalazzoTerritori;
	@FXML
	public ImageView pianoQuestoPalazzoTerritori;
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
	
	//Componenti plancia
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
	
	
	
	public void getStartClient(StartClientGui startClientGui) {
		
		this.setStart(startClientGui);
	}

	public StartClientGui getStart() {
		return start;
	}

	private void setStart(StartClientGui start) {
		this.start = start;
	}

	private void setBandiera(Image bandiera){
		this.bandiera.setImage(bandiera);
	}
	
	private void setPietra(int pietra){
		this.pietra.setText(Integer.toString(pietra));
	}
	
	private void setMonete(int monete){
		this.monete.setText(Integer.toString(monete));
	}
	
	private void setServitori(int servitori){
		this.servitori.setText(Integer.toString(servitori));
	}
	
	private void addCompaniesPlayer(Image image){
		
	}

	public void controlloPosizionamento() {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	public void lanciaDadi() throws RemoteException, SQLException{
		Dado[] dadi = new Dado[3];
		dadi = start.getClient().lanciaDadi(0,null);
		dadoNero.setImage(dadi[0].getImage());
		dadoBianco.setImage(dadi[0].getImage());
		dadoArancio.setImage(dadi[0].getImage());
	}
	
	@FXML
	public void enteredDrag(){
		//Chiedere al prof come catturare l'immagine in cui viene posizionata
		familiareNeutro.getDestinazione(null);
		familiareNero.getDestinazione(null);
		familiareArancio.getDestinazione(null);
		familiareBianco.getDestinazione(null);
		
	}

	
	
}
