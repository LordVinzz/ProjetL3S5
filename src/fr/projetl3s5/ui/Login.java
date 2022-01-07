package fr.projetl3s5.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.json.JSONObject;

import fr.projetl3s5.network.Client;
import fr.projetl3s5.network.Context;

public class Login implements Context {

	private Client client;
	private JSONObject lastCredentials = null;
	private JFrame frame;
	private JButton validateButton;

	public Login(Client client) {
		this.client = client;
	}

	public void checkChoice(int choice, JTextField user, JPasswordField pass) {
		if (choice == 1 || choice == JOptionPane.CLOSED_OPTION) {
			System.exit(0);
		}

	}

	public void checkCredentials(JSONObject jsonObject) {
		lastCredentials = jsonObject;
		if (lastCredentials.getString("Status").equals("valid")) {
			User user = new User(lastCredentials.getString("Id"), lastCredentials.getString("Name"),
					lastCredentials.getString("FName"), lastCredentials.getInt("Group"), client);
			new Interface(user);
			frame.dispose();
		} else {
			JOptionPane.showMessageDialog(null, "Identifiants invalides !", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
		validateButton.setEnabled(true);
	}

	public JSONObject getLastCredentials() {
		return lastCredentials;
	}

	private void createWindow() {
		frame = new JFrame("Connexion au serveur");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel labelUser = new JLabel("Identifiant: ");
		JLabel labelPass = new JLabel("Mot de Passe: ");
		JTextField textUser = new JTextField(20);
		textUser.setText("elevetest@univ-tlse3.fr");
		JPasswordField fieldPass = new JPasswordField(20);
		fieldPass.setText("eleve");
		validateButton = new JButton("Valider");
		validateButton.addActionListener(new IDValidator(fieldPass, textUser, client, this));

		// create a new panel with GridBagLayout manager
		JPanel newPanel = new JPanel(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();
		setValues(labelUser, labelPass, textUser, fieldPass, validateButton, newPanel, constraints);

		frame.add(newPanel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public JButton getValidateButton() {
		return validateButton;
	}

	private void setValues(JLabel labelUser, JLabel labelPass, JTextField textUser, JPasswordField fieldPass,
			JButton valider, JPanel newPanel, GridBagConstraints constraints) {
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(10, 10, 10, 10);

		// add components to the panel
		constraints.gridx = 0;
		constraints.gridy = 0;
		newPanel.add(labelUser, constraints);

		constraints.gridx = 1;
		newPanel.add(textUser, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		newPanel.add(labelPass, constraints);

		constraints.gridx = 1;
		newPanel.add(fieldPass, constraints);

		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.CENTER;
		newPanel.add(valider, constraints);
	}

	public void start() {
		createWindow();
	}

}
