package fr.projetl3s5.server.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import fr.projetl3s5.client.ui.User;
import fr.projetl3s5.utils.Utils;

public class ChangePasswordListener implements ActionListener {

	private User user;
	
	public ChangePasswordListener(User user) {
		this.user = user;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JPasswordField pf = new JPasswordField();
		int opt = JOptionPane.showConfirmDialog(null, pf, "Nouveau Mot de Passe :", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if(opt == JOptionPane.OK_OPTION) {
			user.setPasswordHash(Utils.MD5(new String(pf.getPassword())));
		}
	}

}
