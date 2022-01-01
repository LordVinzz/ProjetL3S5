package fr.projetl3s5.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.Clock;
import java.time.ZoneId;

import javax.swing.BorderFactory;
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
import javax.swing.JTree;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import fr.projetl3s5.groups.Group;

public class Interface {

	private JFrame jFrame;
	private JPanel masterPane = new JPanel();
	private SpringLayout layout = new SpringLayout();
	private JScrollPane scrollPane = new JScrollPane(masterPane);
	private DefaultMutableTreeNode root, leafGroup[];
	private DefaultTreeModel model;
	
	private JTextArea writingZone = new JTextArea(3, 50);
	private JTextField nameT = new JTextField(25);
	private JTextArea msgT = new JTextArea(5, 50);
	
	private Ticket currentTicket;
	
	private User user;

	public Interface(User user) {
		this.user = user;
		this.user.setInterface(this);
		jFrame = new JFrame();
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTabbedPane ticketsTab = new JTabbedPane();
		jFrame.getContentPane().add(ticketsTab, BorderLayout.CENTER);

		addComponentsToTabbedPane(ticketsTab);

		jFrame.setSize(new Dimension(800, 500));
		jFrame.setVisible(true);
	}

	// ---------------------------------------------------------------------------

	public void refreshScrollPane() {
		scrollPane.revalidate();
		scrollPane.repaint();
	}
	
	public Ticket getCurrentTicket() {
		return currentTicket;
	}
	
	public void setCurrentTicket(Ticket currentTicket) {
		this.currentTicket = currentTicket;
	}
	
	public void setTicketTree() {
		root.removeAllChildren();

		leafGroup = new DefaultMutableTreeNode[Group.values().length];
		int indGroup = 0;

		for (String group : user.getGroups()) {

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

	public JTree createTicketTree() {
		root = new DefaultMutableTreeNode("Liste Tickets");

		setTicketTree();

		JTree tree = new JTree(root);
		model = (DefaultTreeModel) tree.getModel();

		tree.addMouseListener(new TreeMouseListener(tree, this));
		return tree;
	}

	public void addMessageToTicket(Message message) {
		JPanel childPane = new JPanel();
		JPanel lastPane = getLastMasterPaneComp();		
		JLabel emailLabel = new JLabel(String.format("De : %s", message.getCreator().getId()));
		JLabel dateLabel = new JLabel(String.format("A : %s", message.getStringDate()));
		//textArea necessaire pour avoir le multiline contrairement a un JLabel ;)
		JTextArea textArea = new JTextArea(String.format("\n%s", message.getContent()));
		
		textArea.setEditable(false);
		textArea.setBackground(UIManager.getColor("Panel.background"));

		GridBagConstraints constraints = new GridBagConstraints();
		
		if (lastPane != null) {
			layout.putConstraint(SpringLayout.NORTH, childPane, 6, SpringLayout.SOUTH, lastPane);
		}

		layout.putConstraint(SpringLayout.EAST, childPane, 0, SpringLayout.EAST, masterPane);
		layout.putConstraint(SpringLayout.WEST, childPane, 0, SpringLayout.WEST, masterPane);

		childPane.setBorder(BorderFactory.createLineBorder(Color.black));
		childPane.setLayout(setNewGridBagLayout());

		masterPane.add(childPane);

		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(0, 0, 5, 5);
		constraints.gridx = 0;
		constraints.gridy = 0;
		childPane.add(emailLabel, constraints);

		constraints.anchor = GridBagConstraints.EAST;
		constraints.gridx = 1;
		childPane.add(dateLabel, constraints);

		constraints.gridwidth = 2;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 1;
		childPane.add(textArea, constraints);

		setColorMsg(message.getReadBy(), childPane, message);

		layout.putConstraint(SpringLayout.SOUTH, masterPane, 0, SpringLayout.SOUTH, getLastMasterPaneComp());
		
		writingZone.setText("");
	}
	
	public GridBagLayout setNewGridBagLayout() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 570, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 75, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		return gridBagLayout;
	}
	
	public void setColorMsg(int readBy, JPanel childPane, Message msg) {
		
		if (readBy == 0) {
			childPane.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
		}

		else if (readBy < msg.getNbTotalMembers()) {
			childPane.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3));
		}

		else if (readBy == msg.getNbTotalMembers()) {
			childPane.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
		}

		else {
			childPane.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));
		}
	}

	// ---------------------------------------------------------------------------

	public JPanel getLastMasterPaneComp() {
		boolean hasChilds = masterPane.getComponents().length > 0;
		if (hasChilds) {
			return (JPanel) masterPane.getComponent(masterPane.getComponents().length - 1);
		}
		return null;
	}

	public void clearMasterPane() {
		masterPane.removeAll();
	}

	private void addComponentsToTabbedPane(JTabbedPane ticketsTab) {
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		JButton btnNewButton = new JButton("Envoyer");
		JPanel writePanel = new JPanel();
		JPanel msgZone = new JPanel(new BorderLayout());
		ticketsTab.addTab("Liste de Tickets", splitPane);
		root = new DefaultMutableTreeNode("List de Tickets");
		JTree tree = createTicketTree();
		model = (DefaultTreeModel) tree.getModel();

		splitPane.setOneTouchExpandable(false);
		writePanel.add(writingZone);
		writePanel.add(btnNewButton);
		splitPane.setLeftComponent(tree);
		splitPane.setRightComponent(msgZone);

		btnNewButton.addActionListener(new SendMessageListener(writingZone , this, user));

		masterPane.setLayout(layout);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		msgZone.add(scrollPane, BorderLayout.CENTER);
		msgZone.add(writePanel, BorderLayout.PAGE_END);

		createSecondTab(ticketsTab);
	}

	private void createSecondTab(JTabbedPane ticketsTab) {
		JPanel selectGr = new JPanel();
		JPanel selectS = new JPanel();
		JLabel title = new JLabel("Creer un fil de discussion");
		JPanel msg = new JPanel();
		title.setFont(new Font("Lucida Console", Font.BOLD, 20));

		JComboBox<String> listGroupes = new JComboBox<>(allGroupes());

		JButton sendButton = new JButton("Nouveau Ticket");
		sendButton.addActionListener(new SendNewTicket(msgT, nameT, listGroupes, this));

		selectGr.add(new JLabel("Selection de groupe :"));
		selectGr.add(listGroupes);

		selectS.add(new JLabel("Nom du sujet :"));
		selectS.add(nameT);

		msg.add(new JLabel("Message :"));
		msg.add(msgT);

		ticketsTab.add("Nouveau Ticket", addCompWithLayout(title, selectGr, selectS, msg, sendButton));
	}

	public JPanel addCompWithLayout(JLabel title, JPanel selectGr, JPanel selectS, JPanel msg, JButton sendButton) {
		JPanel newPanel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(10, 10, 10, 10);

		constraints.gridx = 0;
		constraints.gridy = 1;
		newPanel.add(selectGr, constraints);

		constraints.gridx = 0;
		constraints.gridy = 2;
		newPanel.add(selectS, constraints);

		constraints.gridx = 0;
		constraints.gridy = 3;
		newPanel.add(msg, constraints);

		constraints.gridx = 0;
		constraints.gridy = 4;

		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.CENTER;
		newPanel.add(sendButton, constraints);
		constraints.gridx = 0;
		constraints.gridy = 0;
		newPanel.add(title, constraints);
		return newPanel;
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
	
	public User getUser() {
		return user;
	}
}
