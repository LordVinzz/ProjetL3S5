package fr.projetl3s5.ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class TreeMouseListener implements MouseListener {

	private JTree tree;
	private Interface interfacz;

	public TreeMouseListener(JTree tree, Interface interfacz) {
		this.tree = tree;
		this.interfacz = interfacz;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

		int selRow = tree.getRowForLocation(e.getX(), e.getY());
		TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
		
		if (selRow != -1) {
			if (e.getClickCount() == 1) {
				if (((DefaultMutableTreeNode) selPath.getLastPathComponent()).getUserObject() instanceof Ticket) {
					Ticket ticket = (Ticket) ((DefaultMutableTreeNode) selPath.getLastPathComponent()).getUserObject();
					
					if(ticket.getTitleIsInBold()) {
						ticket.setTitleIsInBold();
						interfacz.setTicketTree();
					}
					
					interfacz.clearMasterPane();
					
					for (Message m : ticket.getHistory()) {
						interfacz.addMessageToTicket(m);
					}
					
					interfacz.setCurrentTicket(ticket);
					

				} else {
					interfacz.clearMasterPane();
				}
				interfacz.refreshScrollPane();
			} else if (e.getClickCount() == 2) {
			
			}
			
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

}
