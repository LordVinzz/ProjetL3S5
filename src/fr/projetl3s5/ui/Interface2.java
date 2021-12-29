package fr.projetl3s5.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import fr.projetl3s5.groups.Group;

public class Interface2 {

	private JFrame frame;
	private JPanel ticketList, writingZone, messageHistory, center;
	private JTabbedPane tab;
	private JSplitPane tab1;
	
	DefaultMutableTreeNode root, leafGroup[];
	DefaultTreeModel model;
	private JScrollPane scrollPane;

	public JPanel displayTicketList() {
		JPanel panel = new JPanel();
		root = new DefaultMutableTreeNode("Liste Tickets");

		updateTicketList();

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
//							setMsgHistory(ticket);
//							updateFrame();
						}
					} else if (e.getClickCount() == 2) {
					}
				}
			}
		};
		arbre.addMouseListener(ml);

		return panel;
	}
	
	public void updateTicketList() {
		root.removeAllChildren();
		leafGroup = new DefaultMutableTreeNode[Group.values().length];
		int indGroup = 0;

//		for (String group : user.getGroupe()) {
		for (Group group : Group.values()) {

			leafGroup[indGroup] = new DefaultMutableTreeNode(group);

//			for (Ticket t : user.getListTicket().get(group)) {
//				leafGroup[indGroup].add(new DefaultMutableTreeNode(t));
//			}
			root.add(leafGroup[indGroup]);
			indGroup++;
		}
		if (model != null)
			model.reload();
	}
	
	public JPanel defaultWritingZone() {
		JPanel panel = new JPanel();
		JTextArea textArea = new JTextArea(3, 70);
		JButton sendButton = new JButton("Envoyer");

		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10),
				BorderFactory.createMatteBorder(2, 0, 0, 0, Color.LIGHT_GRAY)));
		panel.add(textArea);
		panel.add(sendButton);

		return panel;
	}
	
	public JPanel setupLayout(Font font, JLabel titre, JLabel selectG, JLabel nomSujet, JLabel message,
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
		JLabel createTicketLabel = new JLabel("Creer un fil de discussion");
		createTicketLabel.setFont(new Font("Lucida Console", Font.BOLD, 20));

		JLabel selectGroupLabel = new JLabel("Selection de groupe :");
		selectGroupLabel.setFont(font);

		JLabel topicLabel = new JLabel("Nom du sujet :");
		topicLabel.setFont(font);

		JLabel messageLabel = new JLabel("Message");
		messageLabel.setFont(font);

		JComboBox<String> groupsList = new JComboBox<>(groupsToStringArray());
		JTextField topicTextBox = new JTextField(20);
		JTextArea messageTextArea = new JTextArea(5, 50);
		JButton sendButton = new JButton("Nouveau Ticket");

//		sendButton.addActionListener(new EnvoiNewTicket(messageTextArea, topicTextBox, groupsList, user));

		return setupLayout(font, createTicketLabel, selectGroupLabel, topicLabel, messageLabel, groupsList, topicTextBox, messageTextArea, sendButton);
	}
	
	public String[] groupsToStringArray() {
		Group[] listeGroupes = Group.values();
		String[] lGroupes = new String[listeGroupes.length];
		int ind = 0;
		for (Group g : listeGroupes) {
			lGroupes[ind] = g.toString();
			ind++;
		}
		return lGroupes;
	}
	
	public void addComponentsToFrame() {
		tab = new JTabbedPane();

		ticketList = displayTicketList();
		messageHistory = new JPanel(new GridBagLayout());
		scrollPane = new JScrollPane(messageHistory);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		writingZone = defaultWritingZone();
		center = new JPanel(new BorderLayout());
		
		{
			GridBagConstraints gbc = new GridBagConstraints();
			
	        gbc.fill = GridBagConstraints.HORIZONTAL;
	        gbc.gridx = 0;
	        gbc.gridy = 0;
	        gbc.weightx = 1.0;
	        gbc.weighty = 0.3;
	        
	        gbc.insets = new Insets(10,10,10,10);
	        gbc.ipadx = 10;
	        gbc.ipady = 10;
	        for(int i = 0; i < 50; i++) {
	        gbc.gridy = i;
	        JPanel test = new JPanel(new GridBagLayout());
			GridBagConstraints gbcPanel = new GridBagConstraints();
			
			test.setBorder(BorderFactory.createLineBorder(Color.black));
			
			gbcPanel.anchor = GridBagConstraints.LINE_START;
			gbcPanel.gridx = 0;
			gbcPanel.gridy = 0;
			gbcPanel.weightx = 0;
			gbcPanel.weighty = 0.5;
			test.add(new JLabel("jeanmichel@univ-tlse3f.r________________"), gbcPanel);
			
			gbcPanel.anchor = GridBagConstraints.LINE_END;
			gbcPanel.gridx = 0;
			gbcPanel.gridy = 0;
			gbcPanel.weightx = 0;
			gbcPanel.weighty = 0;
			test.add(new JLabel("12h12m12s 12/12/12"), gbcPanel);
			
			gbcPanel.insets = new Insets(10,0,0,0);
			gbcPanel.anchor = GridBagConstraints.LINE_START;
			gbcPanel.fill = GridBagConstraints.HORIZONTAL;
			gbcPanel.gridx = 0;
			gbcPanel.gridy = 1;
			gbcPanel.weightx = 1;
			gbcPanel.weighty =1;
			JTextArea j = new JTextArea("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean in vehicula lectus. Integer ornare erat pharetra, sodales ante at, suscipit orci. Cras sed mauris accumsan, lobortis leo non, fringilla elit. Vestibulum tristique iaculis ultricies. Aliquam erat volutpat. Aenean vehicula ante et venenatis aliquam. Nam ac fermentum orci. Morbi ipsum sem, fringilla at varius sit amet, condimentum aliquet nisl. Duis vulputate justo ante, non dapibus sem aliquam non. Cras vestibulum, eros vitae vulputate vehicula, nisl nibh lobortis nisi, elementum rutrum mauris tortor sit amet sapien.\r\n"
					+ "\r\n" + i
					+ "Praesent porta eleifend quam, in blandit justo bibendum quis. Nullam egestas nibh id nibh mollis, eu tincidunt orci mattis. Quisque quis ex at dui ornare tempus eu at sem. Pellentesque varius lectus augue, vitae posuere justo mattis eget. Mauris ultricies luctus purus, et porta leo laoreet eu. Proin id lacus consectetur, ornare dolor id, eleifend velit. Duis consectetur nibh in sapien tempor, vel finibus diam pulvinar. Nam tristique tempor risus, quis tristique sem molestie eget.");
			j.setEditable(false);
			j.setLineWrap(true);
			j.setWrapStyleWord(true);
			j.setBackground(new Color(0xEE, 0xEE, 0xEE));
			test.add(j, gbcPanel);			

			messageHistory.add(test, gbc);
	        }
		}
		
		center.add(scrollPane, BorderLayout.PAGE_START);
		center.add(writingZone, BorderLayout.CENTER);
		
		tab1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, ticketList, center);
		tab1.setDividerLocation(150);
		
		tab.add("Messages", tab1);
		tab.add("Nouveau Ticket", createTicket());
		
		frame.add(tab);
	}
	
	public Interface2() {
		frame = new JFrame("Facebook (même si c'est meta mdr");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		addComponentsToFrame();
		
		frame.pack();
		frame.setLocation(400, 150);
		frame.setSize(900, 600);
		frame.setVisible(true);
	}

}
