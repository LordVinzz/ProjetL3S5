package fr.projetl3s5.server.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import fr.projetl3s5.db.DatabaseCommunicator;

public class NewUserListener implements ActionListener {

	private ServerInterface serverInterface;
	
	public NewUserListener(ServerInterface serverInterface) {
		this.serverInterface = serverInterface;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!DatabaseCommunicator.insertDummyUser()) {
			JOptionPane.showInternalMessageDialog(null, "Erreur lors de l'insertion d'un utilisateur", "Erreur", JOptionPane.ERROR_MESSAGE);
		}else {
			JOptionPane.showInternalMessageDialog(null, "Utilisateur bien inséré", "Succes", JOptionPane.INFORMATION_MESSAGE);
		}
		serverInterface.refreshUsers();
	}

}
