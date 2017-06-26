package client;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

import client.gui.StartClientGui;
import client.gui.controllers.ControllerGame;
import server.element.CartaSviluppo;
import server.element.Dado;
import server.element.Giocatore;
import server.element.Partita;
import server.element.Portafoglio;
import server.element.TesseraScomunica;

/**
 * Classe padre dei due client di conenssione (ConnectionSocketCLient e
 * ConnectionRmiCLient) server principalmente per mentenere generico il client
 * contenuto dalle classi grafiche per la comunicazione client/server
 * 
 * @author Tommy
 *
 */

public class ConnectionClient implements ClientInterface {

	public ConnectionClient() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Metodo di login chiamato dalla grafica dall'utente per effettuare il
	 * login di sistema
	 * 
	 * @param String
	 *            account nome dell'utente che si collega al sistema
	 * @param String
	 *            pw password dedicata dell'utente
	 * @return String risposta del server a seconda dei possibili riscontri con
	 *         i dati ricevuti
	 * @throws RemoteException
	 */
	public String login(String account, String pw) throws ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Metodo di restrazione di una nuovo utente che si collega al sistema
	 * 
	 * @param String
	 *            account nuovo nome dell'utente che si vuole restistrare al
	 *            sistema
	 * @param String
	 *            pw1 prima password inserita dall'utente
	 * @param String
	 *            pw2 secondo password richiesta per il confronto del corretto
	 *            inserimento con la prima pw
	 * @param String
	 *            email emai richiesta per la registrazione a sistema
	 * @return String stringa di riscontro a secondo dei differenti casi dal
	 *         server che comunichera' la riuscita o meno della nuova
	 *         registrazione
	 * @throws RemoteException
	 */
	public String register(String account, String pw, String pw2, String email)
			throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Metodo di creazione di una nuova lobby ossia di una nuova partita che
	 * sarà pronta per essere giocata
	 * 
	 * @param String
	 *            lobby ossia il nome che l'utente dara' alla propria partita
	 * @param String
	 *            color ossia il colore scelto dall'utente
	 * @return boolean restituisce true se la crazione della lobby avviene con
	 *         successo false altrimenti
	 * @throws RemoteException
	 * 
	 */
	@Override
	public boolean createANewLobby(String lobby, String color) throws IOException, SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Metodi di richiesta delle partite attive all'interno del commonServer
	 * contente tutte le partite.
	 * 
	 * @return ArrayList<Partita> arrayList di partite pronte per essere giocate
	 *         e scelte dall'utente che ha richiesto le lobbies disponibili
	 * @throws RemoteException
	 */
	@Override
	public ArrayList<Partita> lobbiesView() throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Metodo di selezione di una lobby da parte di un giocatore
	 * 
	 * @param String
	 *            lobby nome della lobby scelta dal giocatore
	 * @param String
	 *            color colore scelto dal giocatore
	 * @return int numero della lobby all'interno dell'ArrayList di partite
	 *         contenute nel commonServer
	 * @throws RemoteException
	 */
	@Override
	public int enterInALobby(String lobby, String color) throws IOException, ClassNotFoundException {
		return 0;
		// TODO Auto-generated method stub

	}

	@Override
	public void selectColorGamer(String color) throws IOException {
		// TODO Auto-generated method stub

	}

	/**
	 * Metodo che serve all'utente per notificare al sistema di essere pronto ad
	 * iniziare la partita
	 * 
	 * @throws RemoteException
	 */
	@Override
	public void startGame() throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public void posizionareFamiliare(String color, double x, double y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void spendereRisorse(String risorsa, int qta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sostegnoChiesa(boolean flag) {
		// TODO Auto-generated method stub

	}

	@Override
	public void richiestaRegistrazione() throws IOException {
		// TODO Auto-generated method stub

	}

	public void waitStartGame(StartClientGui start) throws ClassNotFoundException, IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void takeCards(String name) {
		// TODO Auto-generated method stub

	}

	/**
	 * Metodo che richiede i dadi dedicati al giocatore e che vengono generati
	 * in modo casuale all'interno della classe giocatore
	 * 
	 * @param positionGame
	 * @return
	 * @throws SQLException
	 * @throws RemoteException
	 */
	public Dado[] lanciaDadi() throws SQLException, RemoteException, ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Metodo che fornisce i colori disponibili a seconda della lobby
	 * selezionata dall'utente
	 * 
	 * @param String
	 *            nome della lobby selezionata dall'utente
	 * @return String[] array di colori disponibili
	 * @throws RemoteException
	 */
	public String[] getColors(String lobby) throws RemoteException, IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Metodo utilizzato per il corretto posizionamento da parte dell'utente di
	 * una mossa di gioco.
	 * 
	 * @param String
	 *            color colore del familiare mosso dal giocatore
	 * @param double
	 *            x posizione x di posizionamento del familiare
	 * @param double
	 *            y posizione y del posizionamento del familiare
	 * @param int
	 *            agg possibile aggiunta di servitori da parte del giocatore per
	 *            aumentare il proprio valore d'azione
	 * @return String restituzione a seocnda dei differenti casi del riscontro
	 *         da parte del server
	 * @throws RemoteException
	 */
	public String controlloPosizionamento(String color, double x, double y, int integer)
			throws RemoteException, IOException, ClassNotFoundException, SQLException {
		return color;
	}

	public void setGuiGame(ControllerGame guiGame) {
	}

	/**
	 * Metodo per la notifica di una propria pedian agli avversari
	 * 
	 * @throws RemoteException
	 */
	public void notifySpostamento(String color, double x, double y) throws RemoteException, IOException {
		// TODO Auto-generated method stub

	}

	public String getNamePosition(double x, double y)
			throws RemoteException, SQLException, IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Metodo utilizzato per il corretto exit dal gioco
	 * 
	 * @throws RemoteException
	 */
	public void exitToTheGame(String lobby, String color) throws IOException {
		// TODO Auto-generated method stub

	}

	public void waitTurno() throws ClassNotFoundException, IOException {
		// TODO Auto-generated method stub

	}

	public ArrayList<CartaSviluppo> getCardsGamer() throws ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Metodo che viene invocato ad ogni aggiunta di una carta da parte del
	 * giocatore successivamente ad una propria mossa di giocao
	 * 
	 * @throws RemoteException
	 */
	public void setCardGiocatore(CartaSviluppo carta, int tipo, int piano) throws IOException {
		// TODO Auto-generated method stub

	}

	/*
	 * Metodo che restituisce le carte di gioco selezionate in modo casuale
	 * durante la creazioen della partita
	 * 
	 * @throws RemoteException
	 */
	public CartaSviluppo[] getCardsGame() throws ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public Giocatore[] getGiocatori() throws RemoteException, IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Metodo di richiesta da parte dell'utente in fase d'istanzia graficamente
	 * delle TessereScomunica selelzionate dal server in modo casuale in fase di
	 * crezione della partita
	 * 
	 * @return TessereScomunica[] array di tre tessere selezionate dal server e
	 *         presenti nella partita corrispondente contenuta nel commonServer
	 * @throws RemoteException
	 */
	public TesseraScomunica[] getCardsScomunica() throws RemoteException, ClassNotFoundException, IOException {
		return null;
	}

	/**
	 * Metodo utirlizzato per reperire le risorse del giocatore
	 * 
	 * @throws RemoteException
	 */
	public Portafoglio getRisorse() throws RemoteException, ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Metodo invocato dall'utente al momento dall'uscita del sistema per
	 * l'eliminazione della partita dal DB (questo metodo viene eseguito solo
	 * dall'utente che ha creato la partita)
	 * 
	 * @throws RemoteException
	 */
	public void deleteView() throws RemoteException, IOException {
		// TODO Auto-generated method stub

	}

	public void removeAccount() throws RemoteException, IOException {
		// TODO Auto-generated method stub

	}

	public int getPlayers() throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getColor() {
		return "blue";
	}

	/**
	 * Metodo utilizzato per la notifica dello spostamento dei punti da parte di
	 * un giocatore che ha appena eseguito una mossa
	 * 
	 * @param String
	 *            tipo e' la tipologia di punteggio da notrificare agli
	 *            avversari
	 * @throws RemoteException
	 */
	public void notifySpostamentoPunti(String string) throws RemoteException {
		// TODO Auto-generated method stub

	}

	/**
	 * Azione di produzione chiamato in caso di acquisizione di carte
	 * particolare o in caso di posizionamente di un proprio familiare su una
	 * posizione specifica del tabellone
	 * 
	 * @param int
	 *            qta forza della produzione
	 * @throws RemoteException
	 */
	public void produzione(int qta) throws RemoteException {
		// TODO Auto-generated method stub

	}

	/**
	 * Azione di raccolro chiamato in caso di acquisizione di carte particolare
	 * o in caso di posizionamente di un proprio familiare su una posizione
	 * specifica del tabellone
	 * 
	 * @param int
	 *            qta forza del racconto
	 * @throws RemoteException
	 */
	public void raccolto(int i) throws RemoteException {
		// TODO Auto-generated method stub

	}

	/**
	 * Metodo che aggiorna le risorse del giocatore all'interno del proprio
	 * portafoglio risorse
	 * 
	 * @param String
	 *            tipo di risorsa da aggiornare
	 * @param int
	 *            qta quantita' di risorsa da aggiungere alle risore gia'
	 *            presenti nel tabellone
	 * @throws RemoteException
	 */
	public void addRisorse(String string, int i) throws RemoteException, SQLException {
		// TODO Auto-generated method stub

	}

	/**
	 * Metodo di aggiunta e aggiornamento dei punti conenuti all'interno del
	 * portafoglio di un giocatore
	 * 
	 * @param Stirng
	 *            tipo tipo di risorsa da aggiornare
	 * @param int
	 *            qta quantita' da aggiugere alle risorse presenti nel
	 *            portafoglio
	 * @throws RemoteException
	 */
	public void addPunti(String tipo, int qta) throws RemoteException, SQLException {
		// TODO Auto-generated method stub

	}

	/**
	 * Notifica di aggiunta di una pergamente
	 * 
	 * @param int
	 *            qta quantita' di pergamene da attivare al giocatore
	 * @throws RemoteException
	 */
	public void addPergamene(int i) throws RemoteException {
		// TODO Auto-generated method stub

	}

	public void sendClient(StartClientGui start) {
	}

	public void resendClient() {
		// TODO Auto-generated method stub

	}

	public void setName(String text) {
		// TODO Auto-generated method stub
	}

	public boolean getPosPalLibero(String string, int i) {
		// TODO Auto-generated method stub
		return false;
	}

	public int getDado(String string) throws RemoteException {
		return 0;
	}

	/**
	 * Metodo chiamato a fine del turno per il cambio delle carte di gioco
	 * 
	 * @throws RemoteException
	 */
	public void changeGamer() throws RemoteException, SQLException {
		// TODO Auto-generated method stub

	}

	/**
	 * Metodo di notifica del cambiamento di risorse da parte dell'avversario
	 * 
	 * @param String
	 *            tipo ossia il tipo di risorsa aggiunta
	 * @param int
	 *            qta quantita' acquisita dall'avversario
	 */
	public void notifyDecisionChiesa(boolean b) throws RemoteException {
		// TODO Auto-generated method stub

	}

	public void addScomunica() throws RemoteException {
		// TODO Auto-generated method stub

	}

	public void changeCards() {
		// TODO Auto-generated method stub

	}

	public void notifyRisorse(String string, int i) {
		// TODO Auto-generated method stub

	}

	public void waitQualcosa() throws ClassNotFoundException, IOException {
		// TODO Auto-generated method stub

	}

	public int getNumberOfGamer() throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Metodo di conferma di ricezione di una scomunica da parte della chiesa
	 * 
	 * @param int
	 *            i
	 * @return int periodo di scomunica ricevuto
	 * @throws RemoteException
	 */
	public int scomunicato(int i) throws RemoteException {

		return 0;
	}

	public void sendGraficaReady() {
		// TODO Auto-generated method stub

	}

	/**
	 * Metodo di scambio all'interno di un turno della possibilita' di
	 * posizionare i propri familiare
	 * 
	 * @throws RemoteException
	 */
	public void scambio() throws RemoteException {
		// TODO Auto-generated method stub

	}

	public void sistemaPosizioni() {
		// TODO Auto-generated method stub

	}
}
