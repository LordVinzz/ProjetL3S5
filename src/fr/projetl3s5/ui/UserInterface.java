package fr.projetl3s5.ui;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class UserInterface {

	public static int verifButton(int option, JPanel panel, JTextField user, JPasswordField pass) {

		switch (option) {
		case 1: // mdp oublié
			JOptionPane.showMessageDialog(null, "Tant pis pour toi", "Mot de passe oublié",
					JOptionPane.WARNING_MESSAGE);
			return 0;

		case JOptionPane.CLOSED_OPTION: // bouton rouge
			return 2;

		case 2:
			return 2; // Quitter
		}
		// Valider
		JOptionPane.showMessageDialog(null, "Tu t'es trompé mdrr", "Identifiants invalides", JOptionPane.ERROR_MESSAGE);
		return 0;
	}

	public int login() { // Affichage de la fenêtre de connection

		JPanel panel = new JPanel();

		JLabel labeluser = new JLabel("Identifiant:"); // affichage du texte
		JTextField user = new JTextField(20); // affichage de la zone à taper

		JLabel labelpassword = new JLabel("Mot de passe:");
		JPasswordField pass = new JPasswordField(20);

		panel.add(labeluser);
		panel.add(user); // on ajoute tout le bordel
		panel.add(labelpassword);
		panel.add(pass);

		String[] options = new String[] { "Valider", "Mot de passe oublié", "Quitter" };

		int option = JOptionPane.showOptionDialog(null, panel, "Connection au serveur", JOptionPane.NO_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[0]); // Retourne le bouton sur lequel on a cliqué

		return verifButton(option, panel, user, pass);
	}
	
	public int start() {
		int valRetour=login();
		
		while (valRetour == 0) {
			valRetour = login();
		}

		return valRetour;
	}
	
}
