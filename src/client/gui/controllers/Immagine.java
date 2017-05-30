package client.gui.controllers;

import java.rmi.RemoteException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

public class Immagine extends ImageView{

	private ImageView destinazione;
	private ControllerGame game;
	private String color;
	private VBox box;
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
        	this.setImage(new Image(getClass().getResourceAsStream("")));
        	if(game.controlloPosizionamento(getColor(), this.getX(), this.getY()))
        		if(flag){
        			destinazione.setImage(this.getImage());
        			this.setDisable(true);
        			game.setCard(game.getNamePosition(this.getX(),this.getY()));
        			try {
						game.notifySpostamento(this.getColor(),this.getX(), this.getY());
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		}
        		else{
        			box.getChildren().add(new ImageView(this.getImage()));
        			this.setDisable(true);
        			try {
						game.notifySpostamento(this.getColor(),this.getX(), this.getY());
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		}
        });
	}
	
	public void getDestinazione(ImageView destinazione){
		this.destinazione=destinazione;
		flag = true;
	}

	public void getDestinazione(VBox box){
		this.box = box;
		flag = false;
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
	
}
