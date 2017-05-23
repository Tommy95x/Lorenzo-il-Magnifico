package client.gui.controllers;

import client.gui.StartClientGui;

public class ControllerWaitingRoom {

	private StartClientGui start;
	
	public void getStartClientGui(StartClientGui startClientGui) {
		this.setStart(startClientGui);
		
	}

	public StartClientGui getStart() {
		return start;
	}

	private void setStart(StartClientGui start) {
		this.start = start;
	}

}
