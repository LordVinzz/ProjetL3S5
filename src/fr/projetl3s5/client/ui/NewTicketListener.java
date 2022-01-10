package fr.projetl3s5.client.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import org.json.JSONObject;

import fr.projetl3s5.groups.Group;
import fr.projetl3s5.network.Context;
import fr.projetl3s5.network.NewTicketPacket;

public class NewTicketListener implements ActionListener, Context{
	
	JComboBox<Group> groupListComboBox;
	ClientInterface interfacz;

	public NewTicketListener(JComboBox<Group> listG, ClientInterface interfacz) {
		this.groupListComboBox = listG;
		this.interfacz = interfacz;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String message = interfacz.getMessageTextArea().getText();
		String topic = interfacz.getTextFieldName().getText();

		if (textAndTopicFilled(message, topic)) {
			
			User u = interfacz.getUser();
			JSONObject jObject = new JSONObject("{}");
			jObject.put("Topic", topic);
			jObject.put("Group", ((Group)groupListComboBox.getSelectedItem()).getId());
			jObject.put("UserGroup", interfacz.getUser().getGroup().getId());
			jObject.put("Id", u.getId());
			jObject.put("Name", u.getName());
			jObject.put("FName", u.getFName());
			jObject.put("Content", message);
			
			try {
				interfacz.getUser().getClient().getOut().writeObject(new NewTicketPacket(jObject.toString()));
				interfacz.getUser().getClient().pendExecution(this);
			} catch (IOException e1) {}
			
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
	
	public User getUser() {
		return interfacz.getUser();
	}
	
	public ClientInterface getInterface() {
		return interfacz;
	}
}
