package fr.projetl3s5.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.json.JSONObject;

import fr.projetl3s5.network.Client;
import fr.projetl3s5.network.ConnectionPacket;

public class ValiderId implements ActionListener {
	String idS;
	String mdpS;
	
	JTextField id;
	JPasswordField mdp;
	
	Login login;
	
	private Client client;
	
	
	public ValiderId(JPasswordField mdp, JTextField id, Client client, Login login) {
		this.id = id;
		this.id = mdp;
		this.client = client;
		this.login = login;
	}

	private JSONObject createCredentials(JTextField user, JPasswordField pass) {
		JSONObject jObject = new JSONObject("{}");
		jObject.put("username", user.getText());
		jObject.put("passwordHash", MD5(new String(pass.getPassword())));
		return jObject;
	}

	public String MD5(String md5) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
		}
		return null;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JSONObject jObject = createCredentials(id, mdp);
		try {
			client.getOut().writeObject(new ConnectionPacket(jObject.toString()));
		} catch (IOException ee) {
			ee.printStackTrace();
		}
		client.pendExecution(login);
		
	}
}
