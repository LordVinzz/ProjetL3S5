
import javax.swing.*;

public class Main {

	public static void verifLogin() { //Verifie si les logins sont corrects
		
		// Je sais pas comment faire aled
	}
	
	
	public static void login() { // Affichage de la fen�tre de connection

		JPanel panel = new JPanel();
		
		JLabel labeluser = new JLabel("Identifiant:"); //affichage du texte
		JTextField user = new JTextField(20); //affichage de la zone � taper

		
		JLabel labelpassword = new JLabel("Mot de passe:");
		JPasswordField pass = new JPasswordField(20);

		panel.add(labeluser);
		panel.add(user);	//on ajoute tout le bordel
		panel.add(labelpassword);
		panel.add(pass);
		
		String[] options = new String[] { "Valider", "Mot de passe oubli�", "Quitter" };
		
		int option = JOptionPane.showOptionDialog(null, panel, "Connection au serveur", JOptionPane.NO_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		
		if(option==1) { //Mot de passe oubli�
			JOptionPane.showMessageDialog(null, "Tant pis pour toi", "Mot de passe oubli�", JOptionPane.WARNING_MESSAGE);
		}
		
		else if(option==0) { // Valider
			verifLogin();
		}
		
	}

	public static void main(String[] args) {
		login();
	}

}
