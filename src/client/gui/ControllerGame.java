package client.gui;

public class ControllerGame {

	private StartClientGui start;
	
	public void getStartClient(StartClientGui startClientGui) {
		
		this.setStart(startClientGui);
		
	}

	public StartClientGui getStart() {
		return start;
	}

	private void setStart(StartClientGui start) {
		this.start = start;
	}

}
