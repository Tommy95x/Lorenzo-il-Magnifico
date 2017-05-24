package client.gui.controllers;

import client.gui.StartClientGui;

public class ControllerMenu{

  private StartClientGui client;

  public void getStartClient(StartClientGui client){
    this.setClient(client);
  }

public StartClientGui getClient() {
	return client;
}

public void setClient(StartClientGui client) {
	this.client = client;
}

}
