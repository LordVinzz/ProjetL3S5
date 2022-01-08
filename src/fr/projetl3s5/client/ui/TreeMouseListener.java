package fr.projetl3s5.client.ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.json.JSONObject;

import fr.projetl3s5.network.SeenPacket;

public class TreeMouseListener implements MouseListener {

	private JTree tree;
	private ClientInterface interfacz;

	public TreeMouseListener(JTree tree, ClientInterface interfacz) {
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
					
					ticket.clearUnreadMessages();
					interfacz.reloadTree();
					
					interfacz.clearMasterPane();
					
					for (Message m : ticket.getHistory()) {
						interfacz.addMessageToTicket(m);
					}
					
					interfacz.setCurrentTicket(ticket);
					
					JSONObject jObject = new JSONObject("{}");
					jObject.put("Code", ticket.getCode());
					jObject.put("Id", interfacz.getUser().getId());
					
					try {
						interfacz.getUser().getClient().getOut().writeObject(new SeenPacket(jObject.toString()));
					} catch (IOException e1) {}

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
