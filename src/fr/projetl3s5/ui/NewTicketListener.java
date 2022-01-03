package fr.projetl3s5.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import fr.projetl3s5.network.Context;

public class NewTicketListener implements ActionListener, Context{

	JComboBox<String> listG;
	Interface interfacz;

	public NewTicketListener(JComboBox<String> listG, Interface interfacz) {
		this.listG = listG;
		this.interfacz = interfacz;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String message = interfacz.getMsgT().getText();
		String topic = interfacz.getNameT().getText();

		if (textAndTopicFilled(message, topic)) {
			
			interfacz.getUser().getClient().getOut().writeObject(new NewTopicObject());
			
			interfacz.clearTicketTopic();
			interfacz.clearMessageTopic();
			
		} else {
			String errMessage = "Erreur dans la création du ticket! Vérifiez les conditions suivantes ;\n\n"
					+ "- Vous avez inséré un sujet, un message et un groupe destinataire\n"
					+ "- Vous n'avez pas déjà crée un ticket pour le même groupe avec un sujet similaire";
			JOptionPane.showMessageDialog(null, errMessage, "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}

	public boolean textAndTopicFilled(String message, String topic) {
		String s1 = message.trim();
		String s2 = topic.trim();
		return !s1.isEmpty() && !s2.isEmpty();
	}
}
