package fr.projetl3s5.server.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import fr.projetl3s5.client.ui.User;
import fr.projetl3s5.db.DatabaseCommunicator;
import fr.projetl3s5.groups.Group;

public class ServerInterface {

	private JFrame jFrame;
	private JPanel masterPane = new JPanel();
	private JScrollPane scrollPane = new JScrollPane(masterPane);
	private SpringLayout layout = new SpringLayout();
	private List<User> usersList;
	public void addUserToPane(User user) {
		JPanel childPane = new JPanel(new BorderLayout());
		JPanel lastPane = getLastMasterPaneComp();
		JLabel infosLabel = new JLabel(String.format("Nom : %s, Prenom : %s, E-mail : %s, Groupe : %s", user.getFName(), user.getName(), user.getId(), user.getGroup()));
		
		
		if (lastPane != null) {
			layout.putConstraint(SpringLayout.NORTH, childPane, 18, SpringLayout.SOUTH, lastPane);
		}

		layout.putConstraint(SpringLayout.EAST, childPane, -10, SpringLayout.EAST, masterPane);
		layout.putConstraint(SpringLayout.WEST, childPane, 10, SpringLayout.WEST, masterPane);

		childPane.setBorder(BorderFactory.createLineBorder(Color.black));

		infosLabel.setHorizontalAlignment(JLabel.CENTER);
		infosLabel.setVerticalAlignment(JLabel.CENTER);
		childPane.add(infosLabel, BorderLayout.PAGE_START);
		
		JPanel jPanel = new JPanel();
		
		addButtons(jPanel, user);
		
		childPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

		childPane.add(jPanel, BorderLayout.PAGE_END);
		masterPane.add(childPane);
		
		layout.putConstraint(SpringLayout.SOUTH, masterPane, 0, SpringLayout.SOUTH, getLastMasterPaneComp());
	}
	
	public void addButtons(JPanel buttonPanel, User user) {
		JTextField emailTextField = new JTextField(user.getId());
		emailTextField.setPreferredSize(new Dimension(150,20));
		JTextField nameTextField = new JTextField(user.getName());
		nameTextField.setPreferredSize(new Dimension(100,20));
		JTextField fNameTextField = new JTextField(user.getFName());
		fNameTextField.setPreferredSize(new Dimension(100,20));
		JComboBox<Group> jComboBox = new JComboBox<>(Group.values());
		jComboBox.setSelectedItem(user.getGroup());
		JButton jButton4 = new JButton("Modifier Mot de Passe");
		jButton4.addActionListener(new ChangePasswordListener(user));
		JButton jButton5 = new JButton("Valider");
		jButton5.addActionListener(new ValidateListener(user, emailTextField, nameTextField, fNameTextField, jComboBox, this));
		
		buttonPanel.add(emailTextField, BorderLayout.PAGE_END);
		buttonPanel.add(nameTextField, BorderLayout.PAGE_END);
		buttonPanel.add(fNameTextField, BorderLayout.PAGE_END);
		buttonPanel.add(jComboBox, BorderLayout.PAGE_END);
		buttonPanel.add(jButton4, BorderLayout.PAGE_END);
		buttonPanel.add(jButton5, BorderLayout.PAGE_END);
	}
	
	
	public JPanel getLastMasterPaneComp() {
		Object result = null;
		for(int i = masterPane.getComponents().length - 1; i >= 0; i--) {
			if((result = masterPane.getComponent(i)) instanceof JPanel) {
				return (JPanel)result;
			}
		}
		return null;
	}
	
	public ServerInterface() {
		jFrame = new JFrame("Interface Serveur");
		
		jFrame.setJMenuBar(createJMenuBar());
		jFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		masterPane.setLayout(layout);
		
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		
		jFrame.setSize(new Dimension(800, 800));
		jFrame.setLocationRelativeTo(null);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setVisible(true);
		
		refreshUsers();
		
		refreshScrollPane();
	}
	
	public void refreshScrollPane() {
		scrollPane.revalidate();
		scrollPane.repaint();
	}
	
	public void clearMasterPane() {
		masterPane.removeAll();
	}
	
	public void refreshUsers() {
		clearMasterPane();
		usersList = DatabaseCommunicator.getUsers();
		for(User user : usersList) {
			addUserToPane(user);
		}
		refreshScrollPane();
	}
	
	public JMenuBar createJMenuBar() {
		JMenuBar jmb = new JMenuBar();
		JMenu jm = new JMenu("Fichier");
		JMenuItem jmi0 = new JMenuItem("Créer Nouveau Utilisateur");
		JMenuItem jmi1 = new JMenuItem("Rafraichir");
		jmi0.addActionListener(new NewUserListener(this));
		jmi1.addActionListener(new RefreshListener(this));
		jm.add(jmi0);
		jm.add(jmi1);
		jmb.add(jm);
		return jmb;
	}

	public Component getJFrame() {
		return jFrame;
	}

}
