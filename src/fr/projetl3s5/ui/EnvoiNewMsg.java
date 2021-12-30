package fr.projetl3s5.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;

import fr.projetl3s5.network.Client;

public class EnvoiNewMsg implements ActionListener {

	JTextArea content;
	Ticket ticket;
	User user;


	public EnvoiNewMsg(JTextArea content, Ticket ticket, User user) {
		this.content=content;
		this.ticket=ticket;
		this.user=user;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		
	}

}
