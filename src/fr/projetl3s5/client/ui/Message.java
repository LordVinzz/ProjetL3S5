package fr.projetl3s5.client.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;

public class Message implements Comparable<Message> {

	private User creator;
	private long uploadDate;
	private String dateAsString;
	private String content;
	private JSONArray readBy;
	private int nbTotalMembers;
	private MsgState state;

	public Message(User creator, long uploadDate, String content, JSONArray readBy, int nbTotalMembers,
			MsgState state) {
		this.creator = creator;
		this.uploadDate = uploadDate;
		this.content = content;
		this.readBy = readBy;
		this.nbTotalMembers = nbTotalMembers;
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

	public JSONArray getReadBy() {
		return readBy;
	}

	public int getNbTotalMembers() {
		return nbTotalMembers;
	}

	public MsgState getState() {
		return state;
	}

	public void setState(MsgState s) {
		state = s;
	}

	@Override
	public int compareTo(Message m) {
		long compar = uploadDate - m.getUploadDate();
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
