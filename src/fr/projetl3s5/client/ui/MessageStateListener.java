package fr.projetl3s5.client.ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

public class MessageStateListener implements MouseListener {

	private Message message;

	public MessageStateListener(Message message) {
		this.message = message;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		StringBuilder bld = new StringBuilder();
		switch(message.getState()) {
		case EN_ATTENTE:
			bld.append("Le message est en attente !");
			break;
		case LU:
			bld.append("Le message est lu !");
			break;
		case RECU:
			bld.append("Le message est reçu");
			break;
		}
		JOptionPane.showMessageDialog(null, bld.toString());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
