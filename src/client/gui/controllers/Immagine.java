package client.gui.controllers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import server.element.CartaSviluppo;

public class Immagine extends ImageView{

	private CartaSviluppo c;
	private int tipo;

	public Immagine(Image image) {
		super(image);
	}

	public CartaSviluppo getC() {
		return c;
	}

	public void setC(CartaSviluppo c) {
		this.c = c;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	
}
