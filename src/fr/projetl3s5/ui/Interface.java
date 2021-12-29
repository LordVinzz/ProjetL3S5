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
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import fr.projetl3s5.groups.Group;

public class Interface {

	private User user;
	DefaultMutableTreeNode root, leafGroup[];
	DefaultTreeModel model;
	JPanel msgHistory = new JPanel();


	public JPanel affichMessage(Message t) {
		JPanel affich = new JPanel(new GridBagLayout());
		affich.setBorder(BorderFactory.createLineBorder(Color.black));
		JLabel affichUser = new JLabel("De : " + t.getCreator().getPrenom() + " " + t.getCreator().getNom());
		JLabel affichDate = new JLabel("A : " + t.getUploadDate());
		JLabel affichContent = new JLabel(t.getContent());
		int readBy=t.getReadBy();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(10, 10, 10, 10);

		constraints.gridx = 0;
		constraints.gridy = 0;

		affich.add(affichUser, constraints);

		constraints.gridx = 1;
		affich.add(affichDate, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		affich.add(affichContent, constraints);

		constraints.gridx = 1;
		constraints.gridy = 2;
		
		// constraints.gridwidth = 2;
		
		if(readBy==0) {
			affich.setBackground(Color.RED);
		}
		
		else if(readBy<t.getNbTotalMembers()) {
			affich.setBackground(Color.ORANGE);
		}
		
		else if(readBy==t.getNbTotalMembers()) {
			affich.setBackground(Color.GREEN);
		}
		
		else {
			affich.setBackground(Color.GRAY);
		}
		return affich;
	}
	
	public void setMsgHistory(Ticket t) {
		for(Message msg : t.getHistory()) {
			msgHistory.add(affichMessage(msg));
		}
	}
	
	public JPanel affichListTickets() {
		JPanel panel = new JPanel();
		root = new DefaultMutableTreeNode("Liste Tickets");

		updateTicketList();

		JTree arbre = new JTree(root);
		model = (DefaultTreeModel) arbre.getModel();
		panel.add(arbre);
		return panel;
	}

	public void updateTicketList() {
		root.removeAllChildren();
		leafGroup = new DefaultMutableTreeNode[Group.values().length];
		int indGroup = 0;
		JButton[] ticketButton = new JButton[user.getNbTicketsTotal()];
		int indButton=0;
		
		for (String group : user.getGroupe()) {
			
			leafGroup[indGroup] = new DefaultMutableTreeNode(group);

			for (Ticket t : user.getListTicket().get(group)) {
				ticketButton[indButton]= new JButton(t.getTitre());
				ticketButton[indButton].addActionListener(new AffichHistory(this, t));
				
				leafGroup[indGroup].add(new DefaultMutableTreeNode(ticketButton[indButton]));
				indButton++;
			}
			root.add(leafGroup[indGroup]);
			indGroup++;
		}
		if(model != null) model.reload();
	}

	public JPanel zoneEcrire() {
		JPanel ecrire = new JPanel();
		JTextArea textArea = new JTextArea(3, 70);
		JButton envoyer = new JButton("Envoyer");

		ecrire.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10),
				BorderFactory.createMatteBorder(2, 0, 0, 0, Color.LIGHT_GRAY)));
		ecrire.add(textArea);
		ecrire.add(envoyer);

		return ecrire;
	}

	public String[] allGroupes() {
		Group[] listeGroupes = Group.values();
		String[] lGroupes = new String[listeGroupes.length];
		int ind = 0;
		for (Group g : listeGroupes) {
			lGroupes[ind] = g.toString();
			ind++;
		}
		return lGroupes;
	}

	public JPanel addCompToLayout(Font font, JLabel titre, JLabel selectG, JLabel nomSujet, JLabel message,
			JComboBox<String> listGroupes, JTextField nomS, JTextArea msg, JButton envoyer) {
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

	public JPanel createTicket() {
		Font font = new Font("Lucida Console", Font.BOLD, 15);
		JLabel titre = new JLabel("Creer un fil de discussion");
		titre.setFont(new Font("Lucida Console", Font.BOLD, 20));

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

		envoyer.addActionListener(new EnvoiNewTicket(msg, nomS, listGroupes, user));

		return addCompToLayout(font, titre, selectG, nomSujet, message, listGroupes, nomS, msg, envoyer);
	}

	public JPanel histMsg() {

		JLabel titre = new JLabel("Sélectionner un ticket !");
		JPanel histMsg = new JPanel();
		histMsg.add(titre);

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
		onglet1.setDividerLocation(150);

		onglet.add("Messages", onglet1);
		onglet.add("Nouveau Ticket", createTicket());

		frame.add(onglet);
	}

	public Interface(User user) {
		this.user = user;
		this.user.setInterface(this);
		msgHistory.setLayout(new BoxLayout(msgHistory, BoxLayout.Y_AXIS));
		
		JFrame frame = new JFrame("Facebook (même si mnt c'est meta mdr");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addCompToFrame(frame);

		frame.pack();
		frame.setLocation(400, 150);
		frame.setSize(900, 600);
		frame.setVisible(true);
	}
}
