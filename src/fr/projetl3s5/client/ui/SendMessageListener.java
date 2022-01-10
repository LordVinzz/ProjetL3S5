package fr.projetl3s5.client.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.projetl3s5.network.Context;
import fr.projetl3s5.network.NewMessagePacket;

public class SendMessageListener implements ActionListener, Context {
	
	private User user;
	private ClientInterface interfacz;

	public SendMessageListener(ClientInterface interfacz) {
		this.interfacz = interfacz;
		this.user = interfacz.getUser();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String userMessage = interfacz.getWritingZone().getText();

		if (!isMessageEmpty(userMessage)) {
			JSONObject jObject = new JSONObject("{}");
			jObject.put("Content", userMessage);
			jObject.put("Code", interfacz.getCurrentTicket().getCode());
			try {
				user.getClient().getOut().writeObject(new NewMessagePacket(jObject.toString()));
				user.getClient().pendExecution(this);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		} else {
			String msg = "Impossible d'envoyer un message vide !";
			JOptionPane.showMessageDialog(null, msg, "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}

	public boolean isMessageEmpty(String message) {
		String s1 = message.trim();
		return s1.isEmpty();
	}

	public void addMessageFromServer(JSONObject jObject) {
		Ticket ticket = interfacz.getCurrentTicket();
		Message msg = new Message(user, jObject.getLong("Date"), jObject.getString("Content"), new JSONArray(String.format("[%s]", user.getId())), ticket.getTotalMember(), MsgState.RECU);
		ticket.addHistory(msg);
		interfacz.clearMasterPane();
		
		for (Message m : ticket.getHistory()) {
			interfacz.addMessageToTicket(m);
		}
		
		interfacz.refreshScrollPane();
	}
	
}
