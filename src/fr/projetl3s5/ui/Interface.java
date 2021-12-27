package fr.projetl3s5.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

public class Interface {

	private User user;

	
	public JPanel affichMessage(Message t) {
		JPanel affich = new JPanel();
		
		return affich;
	}
	
	public JPanel affichListTickets() {
		
		JPanel listTicket = new JPanel();
		listTicket.setLayout(new BoxLayout(listTicket, BoxLayout.Y_AXIS));

		JButton buttons[] = new JButton[30];
		for (int i = 0; i < 10; i++) {
			buttons[i] = new JButton();
			listTicket.add(buttons[i]);
			//listTicket.add(Box.createRigidArea(new Dimension(20, 0)));
		}

		return listTicket;
	}

	public JPanel zoneEcrire() {
		JPanel ecrire = new JPanel();
		JTextArea textArea = new JTextArea(3, 70);
		JButton envoyer = new JButton("Envoyer");

		ecrire.setBorder(BorderFactory.createCompoundBorder(
			       BorderFactory.createEmptyBorder(10, 10, 10, 10),
			       BorderFactory.createMatteBorder(2, 0, 0, 0, Color.LIGHT_GRAY)));
		ecrire.add(textArea);
		ecrire.add(envoyer);
		
		return ecrire;
	}

	public String[] allGroupes() {
		String[] listeGroupe = new String[6];
		
		listeGroupe[0] = "  professeurs  ";
		listeGroupe[1] = "  electricite  ";
		listeGroupe[2] = "  mon cul  ";
		listeGroupe[3] = "  TDA1  ";
		listeGroupe[4] = "  TDA2  ";
		listeGroupe[5] = "  secrétariat  ";
		
		return listeGroupe;
	}
	
	public JPanel createTicket() {
		Font font = new Font("aaa", Font.BOLD, 15);
		JLabel titre = new JLabel("Creer un fil de discussion");
		titre.setFont(new Font("aaa", Font.BOLD, 20));
		
		JLabel selectG = new JLabel("Selection de groupe :");
		selectG.setFont(font);
		
		JLabel nomSujet = new JLabel("Nom du sujet :");
		nomSujet.setFont(font);
		
		JLabel message = new JLabel("Message");
		message.setFont(font);
		
		JComboBox<String> listGroupes = new JComboBox<>(allGroupes());
		JTextField nomS = new JTextField(20);
		JTextArea msg = new JTextArea(5, 50);
		JButton envoyer = new JButton("Nouveau Ticket");
		
        JPanel newPanel = new JPanel(new GridBagLayout());
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(50, 10, 10, 10);
        
        constraints.gridx = 0;
        constraints.gridy = 1;     
        newPanel.add(selectG, constraints);
 
        constraints.gridx = 1;
        newPanel.add(listGroupes, constraints);
         
        constraints.gridx = 0;
        constraints.gridy = 2;     
        newPanel.add(nomSujet, constraints);
         
        constraints.gridx = 1;
        newPanel.add(nomS, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;     
        newPanel.add(message, constraints);
 
        constraints.gridx = 1;
        newPanel.add(msg, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 4;
        
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        newPanel.add(envoyer, constraints);
        constraints.gridx = 0;
        constraints.gridy = 0;
        newPanel.add(titre, constraints);
		
		return newPanel;
	}

	public JPanel histMsg() {
		
		JLabel titre = new JLabel("Historique des messages");
		JPanel histMsg = new JPanel();

		histMsg.add(titre);
		DefaultMutableTreeNode racine = new DefaultMutableTreeNode("Liste Tickets");
		
		DefaultMutableTreeNode categorie;
		for(String group : user.getGroupe()) {
			categorie = new DefaultMutableTreeNode(group);
			racine.add(group);
		}
		
		/*
		List<JPanel> listMsg = user.get;
		
		JList<JPanel> jListMsg = new JList<>((JPanel[]) listMsg.toArray());*/
		
		return histMsg;
	}
	
	public void addCompToFrame(JFrame frame) {
		JTabbedPane onglet = new JTabbedPane();
		
		JPanel listTicket = affichListTickets();
		JPanel ecrire = zoneEcrire();
		JPanel histMsg = histMsg();
		JPanel center = new JPanel(new BorderLayout());

		center.add(histMsg, BorderLayout.PAGE_START);
		center.add(ecrire, BorderLayout.CENTER);
		
		JSplitPane onglet1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listTicket, center);
		onglet1.setDividerLocation(100);

		onglet.add("Messages", onglet1);
		onglet.add("Nouveau Ticket", createTicket());

		frame.add(onglet);
	}

	public Interface(User user) {
		this.user=user;
		
		JFrame frame = new JFrame("Facebook");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addCompToFrame(frame);

		frame.pack();
		frame.setLocation(400, 150);
		frame.setSize(900, 600);
		frame.setVisible(true);
	}
}
