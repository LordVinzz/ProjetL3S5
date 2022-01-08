package fr.projetl3s5.server.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import fr.projetl3s5.client.ui.User;
import fr.projetl3s5.db.DatabaseCommunicator;
import fr.projetl3s5.groups.Group;

public class ValidateListener implements ActionListener {
	
	private User user;
	private JTextField emailTextField, nameTextField, fNameTextField;
	private JComboBox<Group> jComboBox;
	private ServerInterface serverInterface;
	
	public ValidateListener(User user, JTextField emailTextField, JTextField nameTextField, JTextField fNameTextField, JComboBox<Group> jComboBox, ServerInterface serverInterface) {
		this.user = user;
		this.emailTextField=emailTextField;
		this.nameTextField=nameTextField;
		this.fNameTextField = fNameTextField;
		this.jComboBox = jComboBox;
		this.serverInterface = serverInterface;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		user.setId(emailTextField.getText());
		user.setfName(fNameTextField.getText());
		user.setName(nameTextField.getText());
		user.setGroup((Group)jComboBox.getSelectedItem());
		
		int confirmation = JOptionPane.OK_OPTION;
		boolean result = false;
		
		if(user.getGroup() == Group.INVALIDE) {
			confirmation = JOptionPane.showConfirmDialog(null, "Laisser comme groupe invalide entrainera la suppression du profil utilisateur,\ncliquez sur OK pour confirmer", "Warning", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
		}
		if(confirmation == JOptionPane.OK_OPTION)
			result = DatabaseCommunicator.updateUser(user);
		if(!result)
			JOptionPane.showInternalMessageDialog(null, "Aucune modification n'a été apportée");
		else
			JOptionPane.showInternalMessageDialog(null, "La modification a bien été prise en compte");
		
		serverInterface.refreshUsers();
	}

}
