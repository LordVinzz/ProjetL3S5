package fr.projetl3s5.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
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
import javax.swing.tree.TreePath;

import fr.projetl3s5.groups.Group;

public class Interface {

	User user;
	JFrame frame = new JFrame("Facebook (même si mnt c'est meta mdr");;
	DefaultMutableTreeNode root, leafGroup[];
	DefaultTreeModel model;

	JPanel msgHistory = new JPanel();
	JPanel writeZone = createWriteZone();
	JPanel center = createCenter();

	JPanel ticketTree = createTicketTree();

	JSplitPane onglet1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, ticketTree, center);
	JPanel onglet2 = createTicket();

	JTabbedPane onglet = new JTabbedPane();
	Ticket ticketCourant;

	public JPanel createCenter() {
		center = new JPanel(new BorderLayout());
		center.add(writeZone, BorderLayout.SOUTH);
		center.add(msgHistory, BorderLayout.CENTER);
		return center;
	}

	public JPanel createWriteZone() {
		
		JTextArea textZone = new JTextArea(5, 20);
		JButton sendButton = new JButton("Envoyer");
		sendButton.addActionListener(new EnvoiNewMsg(textZone, ticketCourant, user));

		writeZone.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10),
				BorderFactory.createMatteBorder(2, 0, 0, 0, Color.LIGHT_GRAY)));
		writeZone.add(textZone);
		writeZone.add(sendButton);

		return writeZone;
	}

	public JPanel createHistMsg() {
		msgHistory.add(new JLabel("Sélectionner un ticket !"));
		return msgHistory;
	}

	public JPanel createTicketTree() {
		JPanel panel = new JPanel();
		root = new DefaultMutableTreeNode("Liste Tickets");

		setTicketTree();

		JTree arbre = new JTree(root);
		model = (DefaultTreeModel) arbre.getModel();
		panel.add(arbre);

		MouseListener ml = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int selRow = arbre.getRowForLocation(e.getX(), e.getY());
				TreePath selPath = arbre.getPathForLocation(e.getX(), e.getY());
				if (selRow != -1) {
					if (e.getClickCount() == 1) {
						if (((DefaultMutableTreeNode) selPath.getLastPathComponent())
								.getUserObject() instanceof Ticket) {
							Ticket ticket = (Ticket) ((DefaultMutableTreeNode) selPath.getLastPathComponent())
									.getUserObject();
							ticketCourant=ticket;
							setMsgHistory(ticket);
						}
					} else if (e.getClickCount() == 2) {
					}
				}
			}
		};
		arbre.addMouseListener(ml);

		return panel;
	}

	public JPanel createTicket() {
		Font font = new Font("Lucida Console", Font.BOLD, 15);
		JLabel title = new JLabel("Creer un fil de discussion");
		title.setFont(new Font("Lucida Console", Font.BOLD, 20));

		JLabel selectG = new JLabel("Selection de groupe :");
		selectG.setFont(font);

		JLabel nameS = new JLabel("Nom du sujet :");
		nameS.setFont(font);

		JLabel msgL = new JLabel("Message");
		msgL.setFont(font);

		JComboBox<String> listGroupes = new JComboBox<>(allGroupes());
		JTextField nameT = new JTextField(20);
		JTextArea msgT = new JTextArea(5, 50);
		JButton sendButton = new JButton("Nouveau Ticket");

		sendButton.addActionListener(new EnvoiNewTicket(msgT, nameT, listGroupes, user));

		return addCompWithLayout(title, selectG, nameS, msgL, listGroupes, nameT, msgT, sendButton);
	}
	
	public void setMsgHistory(Ticket t) {
		msgHistory.removeAll();
		for (Message msg : t.getHistory()) {
			msgHistory.add(msg.toJPanel());
		}
		updateMsgHisPanel();

	}

	public void setTicketTree() {
		root.removeAllChildren();

		leafGroup = new DefaultMutableTreeNode[Group.values().length];
		int indGroup = 0;

		for (String group : user.getGroupe()) {

			leafGroup[indGroup] = new DefaultMutableTreeNode(group);

			for (Ticket t : user.getListTicket().get(group)) {
				leafGroup[indGroup].add(new DefaultMutableTreeNode(t));
			}
			root.add(leafGroup[indGroup]);
			indGroup++;
		}
		if (model != null)
			model.reload();

	}

	public void updateMsgHisPanel() {
		frame.getContentPane().removeAll();
		onglet.removeAll();
		onglet1.removeAll();
		center.remove(msgHistory);

		center.add(msgHistory, BorderLayout.CENTER);
		onglet1.add(ticketTree, center);
		onglet.add("Messages", onglet1);
		onglet.add("Nouveau Ticket", onglet2);
		frame.add(onglet);
	}

	public void updateTreePanel() {
		frame.getContentPane().removeAll();
		onglet.removeAll();
		onglet1.removeAll();
		ticketTree = createTicketTree();

		onglet1.add(ticketTree, center);
		onglet.add("Messages", onglet1);
		onglet.add("Nouveau Ticket", onglet2);
		frame.add(onglet);
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

	public JPanel addCompWithLayout(JLabel titre, JLabel selectG, JLabel nomSujet, JLabel message,
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

	

	public void addCompToFrame() {
		onglet1.setDividerLocation(150);

		onglet.add("Messages", onglet1);
		onglet.add("Nouveau Ticket", onglet2);

		frame.add(onglet);
	}

	public Interface(User user) {
		this.user = user;
		this.user.setInterface(this);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addCompToFrame();

		frame.pack();
		frame.setLocation(400, 150);
		frame.setSize(900, 600);
		frame.setVisible(true);
	}
}
