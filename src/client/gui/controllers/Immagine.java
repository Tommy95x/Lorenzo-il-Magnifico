package client.gui.controllers;

import java.io.IOException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;

public class Immagine extends ImageView{

	private ImageView destinazione;
	private ControllerGame game;
	private String color;
	private HBox box;
	private boolean flag;
	
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
        	if(game.controlloPosizionamento(getColor(), this.getX(), this.getY(),0))
        		if(flag){
        			destinazione.setImage(this.getImage());
        			this.setDisable(true);
        			try {
						game.setCardGiocatore(game.getNamePosition(this.getX(),this.getY()));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
        			try {
						game.notifySpostamento(this.getColor(),this.getX(), this.getY());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		}
        		else{
        			box.getChildren().add(new ImageView(this.getImage()));
        			this.setDisable(true);
        			try {
						game.notifySpostamento(this.getColor(),this.getX(), this.getY());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		}
        	this.setImage(new Image(getClass().getResourceAsStream("")));
        });
	}
	
	public void getDestinazione(ImageView destinazione){
		this.destinazione=destinazione;
		flag = true;
	}
	
	public ControllerGame getGame() {
		return game;
	}

	public void setGame(ControllerGame game) {
		this.game = game;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void getDestinazione(HBox box) {
		this.box = box;
		flag = false;
		
	}
	
}
