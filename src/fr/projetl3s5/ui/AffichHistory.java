package fr.projetl3s5.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AffichHistory implements ActionListener {

	
	Ticket ticket;
	Interface i;
	
	public AffichHistory(Interface i, Ticket ticket) {
		this.ticket=ticket;
		this.i=i;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		i.setMsgHistory(ticket);	
	}

}
