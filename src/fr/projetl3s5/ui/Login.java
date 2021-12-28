package fr.projetl3s5.ui;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.json.JSONObject;

import fr.projetl3s5.network.Client;
import fr.projetl3s5.network.ConnectionPacket;
import fr.projetl3s5.network.Context;

public class Login implements Context {

	private Client client;
	private boolean isUserAllowed = false;
	private JSONObject lastCredentials = null;
	private static final String[] OPTIONS = new String[] { "Valider", "Quitter" };

	public Login(Client client) {
		this.client = client;
	}

	public void checkChoice(int choice, JTextField user, JPasswordField pass) {
		if(choice == 1 || choice == JOptionPane.CLOSED_OPTION) {
			System.exit(0);
		}
		JSONObject jObject = createCredentials(user, pass);
		try {
			client.getOut().writeObject(new ConnectionPacket(jObject.toString()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		client.pendExecution(this);
	}
	
	public void checkCredentials(JSONObject jsonObject) {
		lastCredentials = jsonObject;
		isUserAllowed = jsonObject.getString("Status").equals("valid");
		System.out.println(lastCredentials);
	}
	
	public JSONObject getLastCredentials() {
		return lastCredentials;
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
		} catch (NoSuchAlgorithmException e) {}
		return null;
	}

	private void createWindow() {
		JPanel panel = new JPanel();
		JFrame frame = new JFrame("Facebook");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel labelUser = new JLabel("Identifiant : ");
		JTextField usernameTextbox = new JTextField(20);
		JLabel labelPassword = new JLabel("Mot de passe : ");
		JPasswordField passwordTextbox = new JPasswordField(20);

		panel.add(labelUser);
		panel.add(usernameTextbox);
		panel.add(labelPassword);
		panel.add(passwordTextbox);
		
		frame.add(panel);
		panel.setV
		frame.pack();
		frame.setLocation(400, 150);
		frame.setSize(900, 600);
		frame.setVisible(true);
		
//		int choice = JOptionPane.showOptionDialog(null, panel, "Connexion au serveur", JOptionPane.NO_OPTION,
//				JOptionPane.PLAIN_MESSAGE, null, OPTIONS, OPTIONS[0]);
		
//		checkChoice(choice, usernameTextbox, passwordTextbox);
	}

	public void start() {
//		while(!isUserAllowed) {
			createWindow();
//		}
	}

}
