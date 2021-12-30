package fr.projetl3s5.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
import javax.swing.tree.TreePath;

import fr.projetl3s5.groups.Group;

public class Interface3 {

	private JFrame jFrame;
	private JPanel masterPane;
	private SpringLayout layout;
	private JScrollPane scrollPane;

	private DefaultMutableTreeNode root, leafGroup[];
	private DefaultTreeModel model;

	private User user;

	public Interface3(User user) {
		this.user = user;
		this.user.setInterface(this);
		jFrame = new JFrame();
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JTabbedPane ticketsTab = new JTabbedPane(JTabbedPane.TOP);
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

	public void addMessageToTicket(Message msg) {
		JPanel childPane = new JPanel();

		JPanel lastPane = getLastMasterPaneComponent();
		if (lastPane != null) {
			layout.putConstraint(SpringLayout.NORTH, childPane, 6, SpringLayout.SOUTH, lastPane);
		}

		layout.putConstraint(SpringLayout.EAST, childPane, 0, SpringLayout.EAST, masterPane);
		layout.putConstraint(SpringLayout.WEST, childPane, 0, SpringLayout.WEST, masterPane);

		childPane.setBorder(BorderFactory.createLineBorder(Color.black));

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 570, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 75, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		childPane.setLayout(gridBagLayout);

		masterPane.add(childPane);

		JLabel emailLabel = new JLabel(msg.getCreator().getId());
		GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
		gridBagConstraints_1.anchor = GridBagConstraints.WEST;
		gridBagConstraints_1.insets = new Insets(0, 0, 5, 5);
		gridBagConstraints_1.gridx = 0;
		gridBagConstraints_1.gridy = 0;
		childPane.add(emailLabel, gridBagConstraints_1);

		JLabel dateLabel = new JLabel(String.format("%d", msg.getUploadDate()));
		GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
		gridBagConstraints_2.anchor = GridBagConstraints.EAST;
		gridBagConstraints_2.insets = new Insets(0, 0, 5, 0);
		gridBagConstraints_2.gridx = 1;
		gridBagConstraints_2.gridy = 0;
		childPane.add(dateLabel, gridBagConstraints_2);

		JTextArea textArea = new JTextArea(msg.getContent());
		textArea.setBackground(UIManager.getColor("Panel.background"));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);

		GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
		gridBagConstraints_3.gridwidth = 2;
		gridBagConstraints_3.fill = GridBagConstraints.BOTH;
		gridBagConstraints_3.gridx = 0;
		gridBagConstraints_3.gridy = 1;
		childPane.add(textArea, gridBagConstraints_3);

		int readBy = msg.getReadBy();

		if (readBy == 0) {
			childPane.setBackground(Color.RED);
			textArea.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
		}

		else if (readBy < msg.getNbTotalMembers()) {
			childPane.setBackground(Color.ORANGE);
			textArea.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3));
		}

		else if (readBy == msg.getNbTotalMembers()) {
			childPane.setBackground(Color.GREEN);
			textArea.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
		}

		else {
			childPane.setBackground(Color.GRAY);
			textArea.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));
		}

		layout.putConstraint(SpringLayout.SOUTH, masterPane, 0, SpringLayout.SOUTH, getLastMasterPaneComponent());
	}

	// ---------------------------------------------------------------------------

	public JPanel getLastMasterPaneComponent() {
		boolean hasChilds = masterPane.getComponents().length > 0;
		if (hasChilds) {
			return (JPanel) masterPane.getComponent(masterPane.getComponents().length - 1);
		}
		return null;
	}

	public void clearMessagesFromTicket() {
		masterPane.removeAll();
	}

	private void addComponentsToTabbedPane(JTabbedPane ticketsTab) {
		JSplitPane splitPane_0 = splitPane0Method(ticketsTab);

		root = new DefaultMutableTreeNode("List de Tickets");

		JTree tree = createTicketTree();
		model = (DefaultTreeModel) tree.getModel();

		splitPane_0.setLeftComponent(tree);

		JSplitPane splitPane_1 = splitPane1Method(splitPane_0);

		JSplitPane splitPane_2 = splitPane2Method(splitPane_1);

		JTextArea writingZone = new JTextArea();
		splitPane_2.setLeftComponent(writingZone);

		JButton btnNewButton = new JButton("Envoyer");

//		btnNewButton.addActionListener(); TODO

		splitPane_2.setRightComponent(btnNewButton);

		createMasterPane();

		splitPane_1.setLeftComponent(scrollPane);

		createSecondTab(ticketsTab);
	}

	private void createMasterPane() {
		masterPane = new JPanel();
		layout = new SpringLayout();
		masterPane.setLayout(layout);

		scrollPane = new JScrollPane(masterPane);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	}

	private void createSecondTab(JTabbedPane ticketsTab) {
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.rowHeights = new int[] { 50, 0, 0, 0, 145, 0, 50 };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0 };
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0, 100, 0, 0, 265, 155 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0 };
		JPanel panel = new JPanel(gbl_panel);
		ticketsTab.addTab("Créer Ticket", null, panel, null);

		JLabel lblNewLabel = new JLabel("Objet du Ticket : ");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 6;
		gbc_lblNewLabel.gridy = 1;
		panel.add(lblNewLabel, gbc_lblNewLabel);

		JTextField createTicketTextField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 8;
		gbc_textField.gridy = 1;
		panel.add(createTicketTextField, gbc_textField);
		createTicketTextField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Groupe : ");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 6;
		gbc_lblNewLabel_1.gridy = 2;
		panel.add(lblNewLabel_1, gbc_lblNewLabel_1);

		JComboBox<Group> comboBox = new JComboBox<>();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.gridx = 8;
		gbc_comboBox.gridy = 2;
		panel.add(comboBox, gbc_comboBox);

		JLabel lblNewLabel_2 = new JLabel("Message : ");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.NORTH;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 6;
		gbc_lblNewLabel_2.gridy = 4;
		panel.add(lblNewLabel_2, gbc_lblNewLabel_2);

		JTextArea newTicketWritingZone = new JTextArea();
		GridBagConstraints gbc_textArea_1 = new GridBagConstraints();
		gbc_textArea_1.insets = new Insets(0, 0, 5, 5);
		gbc_textArea_1.fill = GridBagConstraints.BOTH;
		gbc_textArea_1.gridx = 8;
		gbc_textArea_1.gridy = 4;
		panel.add(newTicketWritingZone, gbc_textArea_1);

		JButton btnNewButton_1 = new JButton("Créer un Ticket");
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridx = 8;
		gbc_btnNewButton_1.gridy = 5;
		panel.add(btnNewButton_1, gbc_btnNewButton_1);
	}

	private JSplitPane splitPane2Method(JSplitPane splitPane_1) {
		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setEnabled(false);
		splitPane_2.setResizeWeight(0.95);
		splitPane_1.setRightComponent(splitPane_2);
		return splitPane_2;
	}

	private JSplitPane splitPane1Method(JSplitPane splitPane_0) {
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setResizeWeight(0.75);
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_1.setOneTouchExpandable(true);
		splitPane_0.setRightComponent(splitPane_1);
		return splitPane_1;
	}

	private JSplitPane splitPane0Method(JTabbedPane ticketsTab) {
		JSplitPane splitPane_0 = new JSplitPane();
		splitPane_0.setResizeWeight(0.1);
		splitPane_0.setOneTouchExpandable(true);
		ticketsTab.addTab("Liste de Tickets", null, splitPane_0, null);
		return splitPane_0;
	}

}
