package client.gui.controllers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class Immagine extends ImageView{

	private ImageView destinazione;
	private ControllerGame game;
	
	public Immagine (Image im, ControllerGame game){
		this.setGame(game);
		setOnDragDetected(event -> {
        	System.out.println("setOnDragDetected");
            if (getImage() == null) {
                return;
            }	

        Dragboard drag = startDragAndDrop(TransferMode.MOVE);
        ClipboardContent content = new ClipboardContent();
        content.putImage(getImage());
        drag.setDragView(getImage());
        drag.setContent(content);
        event.consume();
		});
		
		setOnDragOver(event ->{
			System.out.println("setOnDragOver");       
        	if (!event.isDropCompleted() &&
                   event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }

            event.consume();
		});
		
        setOnDragEntered(event -> {
        	System.out.println("setOnDragEntered");
            if (!event.isDropCompleted() &&
                    event.getDragboard().hasString()) {
                setOpacity(0.3);
            }
        });
		
        setOnDragExited(event -> {
        	System.out.println("setOnDragExited");
            if (!event.isDropCompleted() &&
                    event.getDragboard().hasString()) {
                setOpacity(1);
            }
        });
        
        setOnDragDropped(event->{
        	System.out.println("setOnDragDropped");
            if (getImage() == null) {
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
        
        
        
        setOnDragDone(event ->{
        	destinazione.setImage(this.getImage());
        	this.setImage(new Image(getClass().getResourceAsStream("")));
        	game.controlloPosizionamento();
        });
	}
	
	public void getDestinazione(ImageView destinazione){
		this.destinazione=destinazione;
	}

	public ControllerGame getGame() {
		return game;
	}

	public void setGame(ControllerGame game) {
		this.game = game;
	}
	
}
