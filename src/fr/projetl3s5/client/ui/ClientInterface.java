package fr.projetl3s5.client.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;

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
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import fr.projetl3s5.groups.Group;
import sun.misc.Unsafe;

public class ClientInterface {

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

	private JTree tree;

	private User user;

	public ClientInterface(User user) {
		this.user = user;
		this.user.setInterface(this);
		jFrame = new JFrame("Connexion: " + user.getName() + " " + user.getFName());
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTabbedPane ticketsTab = new JTabbedPane();
		jFrame.getContentPane().add(ticketsTab, BorderLayout.CENTER);

		addComponentsToTabbedPane(ticketsTab);

		jFrame.setSize(new Dimension(900, 500));
		jFrame.setLocationRelativeTo(null);
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
		int groupLength = Group.getGroupForJTree(user).length;

		leafGroup = new DefaultMutableTreeNode[groupLength];

		for (int i = 0; i < groupLength; i++) {
			leafGroup[i] = new DefaultMutableTreeNode(Group.getGroupForJTree(user)[i]);

			for (Ticket t : user.getTickets()) {
				if (t.getGroup() == Group.getGroupForJTree(user)[i]) {
					t.computeUnreadMessages(user);
					DefaultMutableTreeNode dmtn = new DefaultMutableTreeNode(t);

					leafGroup[i].add(dmtn);
				}
			}

			root.add(leafGroup[i]);
		}

		if (model != null)
			reloadTree();
	}

	public void reloadTree() {
		model.reload();
		expandAllNodes(tree, 0, tree.getRowCount());
	}

	private void expandAllNodes(JTree tree, int startingIndex, int rowCount) {
		for (int i = startingIndex; i < rowCount; ++i) {
			tree.expandRow(i);
		}

		if (tree.getRowCount() != rowCount) {
			expandAllNodes(tree, rowCount, tree.getRowCount());
		}
	}

	public JTree createTicketTree() {
		root = new DefaultMutableTreeNode("Liste Tickets");

		setTicketTree();

		tree = new JTree(root);
		model = (DefaultTreeModel) tree.getModel();

		tree.addMouseListener(new TreeMouseListener(tree, this));
		return tree;
	}

	public void addMessageToTicket(Message message) {
		JPanel childPane = new JPanel();
		JPanel lastPane = getLastMasterPaneComp();
		JLabel emailLabel = new JLabel(String.format("De : %s %s, via %s", message.getCreator().getName(),
				message.getCreator().getFName(), message.getCreator().getId()));
		JLabel dateLabel = new JLabel(String.format("A : %s", message.getStringDate()));
		// textArea necessaire pour avoir le multiline contrairement a un JLabel ;)
		JTextArea textArea = new JTextArea(String.format("\n%s", message.getContent()));

		textArea.addMouseListener(new MessageStateListener(message));

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

		setMessageColor(message.getReadBy().length(), childPane, message);

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

	public void setMessageColor(int readBy, JPanel childPane, Message message) {

		if (readBy == 1) {
			childPane.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
			message.setState(MsgState.RECU);
		} else if (readBy < message.getNbTotalMembers()) {
			childPane.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3));
			message.setState(MsgState.RECU);
		} else if (readBy == message.getNbTotalMembers()) {
			childPane.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
			message.setState(MsgState.LU);
		} else {
			childPane.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));
			message.setState(MsgState.EN_ATTENTE);
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
		splitPane.setDividerLocation(222);
		JButton btnNewButton = new JButton("Envoyer");
		JPanel writePanel = new JPanel();
		JPanel msgZone = new JPanel(new BorderLayout());
		ticketsTab.addTab("Liste de Tickets", splitPane);
		root = new DefaultMutableTreeNode("List de Tickets");
		JTree tree = createTicketTree();
		JScrollPane jsc = new JScrollPane(tree);
		model = (DefaultTreeModel) tree.getModel();

		splitPane.setOneTouchExpandable(false);
		writePanel.add(writingZone);
		writePanel.add(btnNewButton);
		splitPane.setLeftComponent(jsc);
		splitPane.setRightComponent(msgZone);

		btnNewButton.addActionListener(new SendMessageListener(this));

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
		JLabel title = new JLabel("Cr�er un fil de discussion");
		JPanel msg = new JPanel();
		title.setFont(new Font("Lucida Console", Font.BOLD, 20));

		JComboBox<Group> listGroupes = new JComboBox<>(Group.getGroupForComboBox(user));

		JButton sendButton = new JButton("Nouveau Ticket");
		sendButton.addActionListener(new NewTicketListener(listGroupes, this));

		selectGr.add(new JLabel("Selection de groupe :"));
		selectGr.add(listGroupes);

		selectS.add(new JLabel("Sujet :"));
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

	public User getUser() {
		return user;
	}

	public JTextField getTextFieldName() {
		return nameT;
	}

	public JTextArea getMessageTextArea() {
		return msgT;
	}

	public void clearTicketTopic() {
		nameT.setText("");
	}

	public void clearMessageTopic() {
		msgT.setText("");

	}

	public JTextArea getWritingZone() {
		return writingZone;
	}
}
