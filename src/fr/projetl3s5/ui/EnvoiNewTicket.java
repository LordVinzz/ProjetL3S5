package fr.projetl3s5.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class EnvoiNewTicket implements ActionListener {

	String msgSaisi;
	String sujet;
	String groupe;
	
	JTextArea msg;
	JTextField nomS;
	JComboBox<String> listG;
	User user;
	
	
	public EnvoiNewTicket(JTextArea msg, JTextField nomS, JComboBox<String> listG, User user) {
		this.msg = msg;
		this.nomS = nomS;
		this.listG=listG;
		this.user = user;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		msgSaisi = msg.getText();
		sujet = nomS.getText();
		
		if(possible()) {
			JOptionPane.showMessageDialog(null, "Ticket crée avec succès !", "Nouveau Ticket", JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			String msg = "Erreur dans la création du ticket! Vérifiez les conditions suivantes ;\n\n"
					+ "- Vous avez inséré un sujet, un message et un groupe destinataire\n"
					+ "- Vous n'avez pas déjà crée un ticket pour le même groupe avec un sujet similaire";
			JOptionPane.showMessageDialog(null, msg, "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public boolean possible() {
		String s1=msgSaisi.trim();
		String s2 = sujet.trim();
		return !s1.isEmpty() && !s2.isEmpty();
	}
}
