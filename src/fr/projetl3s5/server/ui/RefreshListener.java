package fr.projetl3s5.server.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RefreshListener implements ActionListener{
	
	private ServerInterface serverInterface;
	
	public RefreshListener(ServerInterface serverInterface) {
		this.serverInterface = serverInterface;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		serverInterface.refreshUsers();
	}

}
