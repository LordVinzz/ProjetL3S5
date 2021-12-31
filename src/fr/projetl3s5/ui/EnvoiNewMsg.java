package fr.projetl3s5.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import fr.projetl3s5.network.Client;

public class EnvoiNewMsg implements ActionListener {

	JTextArea content;
	Ticket ticket;
	User user;
	Interface3 i;

	public EnvoiNewMsg(JTextArea content, Interface3 i, User user) {
		this.content = content;
		this.i=i;
		//this.ticket = ticket;
		this.user = user;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String msgSaisi = content.getText();

		if(possible(msgSaisi)) {
			//TODO
			i.addMessageToTicket(new Message(user, 2, msgSaisi,0, 20, MsgState.EN_ATTENTE));
		}
		
		else {
			String msg = "Alors t'es bien gentil mais le fait d'envoyer juste rien c'est un peu con";
			JOptionPane.showMessageDialog(null, msg, "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}

	public boolean possible(String msgSaisi) {
		String s1 = msgSaisi.trim();
		return !s1.isEmpty();
	}

}
