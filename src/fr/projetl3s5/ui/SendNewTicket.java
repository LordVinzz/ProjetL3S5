package fr.projetl3s5.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SendNewTicket implements ActionListener {

	String msgSaisi;
	String sujet;
	String groupe;

	JTextArea msg;
	JTextField nomS;
	JComboBox<String> listG;
	Interface interfacz;

	public SendNewTicket(JTextArea msg, JTextField nomS, JComboBox<String> listG, Interface interfacz) {
		this.msg = msg;
		this.nomS = nomS;
		this.listG = listG;
		this.interfacz = interfacz;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		msgSaisi = msg.getText();
		sujet = nomS.getText();

		if (textAndTopicFilled()) {
			JOptionPane.showMessageDialog(null, "Ticket crée avec succès !", "Nouveau Ticket", JOptionPane.INFORMATION_MESSAGE);
			// i.getUser().addToTicketList(new Ticket(sujet, comboBox.getSelectedItem);
		} else {
			String errMessage = "Erreur dans la création du ticket! Vérifiez les conditions suivantes ;\n\n"
					+ "- Vous avez inséré un sujet, un message et un groupe destinataire\n"
					+ "- Vous n'avez pas déjà crée un ticket pour le même groupe avec un sujet similaire";
			JOptionPane.showMessageDialog(null, errMessage, "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}

	public boolean textAndTopicFilled() {
		String s1 = msgSaisi.trim();
		String s2 = sujet.trim();
		return !s1.isEmpty() && !s2.isEmpty();
	}
}
