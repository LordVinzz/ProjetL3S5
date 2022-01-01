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

	JComboBox<String> listG;
	Interface interfacz;

	public SendNewTicket(JComboBox<String> listG, Interface interfacz) {
		this.listG = listG;
		this.interfacz = interfacz;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		msgSaisi = interfacz.getMsgT().getText();
		sujet = interfacz.getNameT().getText();

		if (textAndTopicFilled()) {
			JOptionPane.showMessageDialog(null, "Ticket cr�e avec succ�s !", "Nouveau Ticket", JOptionPane.INFORMATION_MESSAGE);
			// i.getUser().addToTicketList(new Ticket(sujet, comboBox.getSelectedItem);
			//TODO ; ajout dans la base de donn�es
			interfacz.setNameT();
			interfacz.setMsgT();
			
		} else {
			String errMessage = "Erreur dans la cr�ation du ticket! V�rifiez les conditions suivantes ;\n\n"
					+ "- Vous avez ins�r� un sujet, un message et un groupe destinataire\n"
					+ "- Vous n'avez pas d�j� cr�e un ticket pour le m�me groupe avec un sujet similaire";
			JOptionPane.showMessageDialog(null, errMessage, "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}

	public boolean textAndTopicFilled() {
		String s1 = msgSaisi.trim();
		String s2 = sujet.trim();
		return !s1.isEmpty() && !s2.isEmpty();
	}
}
