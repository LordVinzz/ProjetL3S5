package fr.projetl3s5.ui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Message implements Comparable<Message> {

	private User creator;
	private long uploadDate;
	private String dateAsString;
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
		DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy");
		this.dateAsString = dateFormat.format(new Date(uploadDate));
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
		long compar = m.getUploadDate() - uploadDate;
		if (compar == 0) {
			return content.compareTo(m.getContent());
		}
		return (int) compar;
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

	public Object getStringDate() {
		return dateAsString;
	}
	
}
