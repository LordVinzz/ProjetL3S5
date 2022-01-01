package fr.projetl3s5.ui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Message implements Comparable<Message> {

	private User creator;
	private long uploadDate;
	private String content;
	private int readBy;
	private int nbTotalMembers;
	private MsgState state;

	public Message(User creator, long uploadDate, String content, int readBy, int nbTotalMembers, MsgState state) {
		this.creator = creator;
		this.uploadDate = uploadDate;
		this.content = content;
		this.readBy=readBy;
		this.nbTotalMembers=nbTotalMembers;
		this.state = state;
	}

	public long getUploadDate() {
		return uploadDate;
	}

	public String getContent() {
		return content;
	}

	public User getCreator() {
		return creator;
	}
	
	public int getReadBy() {
		return readBy;
	}

	public int getNbTotalMembers() {
		return nbTotalMembers;
	}
	
	public MsgState getState() {
		return state;
	}
	
	public void setState(MsgState s) {
		state=s;
	}

	@Override
	public int compareTo(Message m) {
		long compar = -uploadDate + m.getUploadDate();
		if (compar == 0) {
			return content.compareTo(m.getContent());
		}
		return (int) (compar / abs(compar));
	}
	
	private long abs(long l) {
		return l < 0 ? -l : l;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Message) {
			Message message = (Message) obj;
			return creator.equals(message.getCreator()) && uploadDate == message.getUploadDate()
					&& content.equals(message.getContent());
		}
		return false;
	}
	
	public JPanel toJPanel() {
		JPanel affich = new JPanel(new GridBagLayout());
		affich.setBorder(BorderFactory.createLineBorder(Color.black));
		JLabel affichUser = new JLabel("De : " + getCreator().getPrenom() + " " + getCreator().getNom());
		JLabel affichDate = new JLabel("A : " + getUploadDate());
		JLabel affichContent = new JLabel(getContent());
		int readBy=getReadBy();
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
		
		else if(readBy<getNbTotalMembers()) {
			affich.setBackground(Color.ORANGE);
		}
		
		else if(readBy==getNbTotalMembers()) {
			affich.setBackground(Color.GREEN);
		}
		
		else {
			affich.setBackground(Color.GRAY);
		}
		return affich;
	}

}
