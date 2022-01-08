package fr.projetl3s5.client.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.json.JSONObject;

import fr.projetl3s5.network.Client;
import fr.projetl3s5.network.ConnectionPacket;
import fr.projetl3s5.utils.Utils;

public class IDValidator implements ActionListener {
	
	private JTextField textField;
	private JPasswordField passwordField;
	
	private Login login;
	
	private Client client;
	
	
	public IDValidator(JPasswordField passwordField, JTextField textField, Client client, Login login) {
		this.textField = textField;
		this.passwordField = passwordField;
		this.client = client;
		this.login = login;
	}

	private JSONObject createCredentials(JTextField user, JPasswordField pass) {
		JSONObject jObject = new JSONObject("{}");
		jObject.put("username", user.getText());
		jObject.put("passwordHash", Utils.MD5(new String(pass.getPassword())));
		return jObject;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		login.getValidateButton().setEnabled(false);
		JSONObject jObject = createCredentials(textField, passwordField);
		try {
			client.getOut().writeObject(new ConnectionPacket(jObject.toString()));
		} catch (IOException ee) {
			ee.printStackTrace();
		}
		client.pendExecution(login);
		
	}
}
