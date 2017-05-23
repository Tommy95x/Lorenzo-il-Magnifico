package client.gui;

public class ControllerRegister {

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
