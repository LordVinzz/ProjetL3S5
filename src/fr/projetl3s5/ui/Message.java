package fr.projetl3s5.ui;

public class Message implements Comparable<Message> {

	private User creator;
	private int uploadDate;
	private String content;
	private int readBy;
	private int nbTotalMembers;
	private MsgState state;

	public Message(User creator, int uploadDate, String content, int readBy, int nbTotalMembers, MsgState state) {
		this.creator = creator;
		this.uploadDate = uploadDate;
		this.content = content;
		this.readBy=readBy;
		this.nbTotalMembers=nbTotalMembers;
		this.state = state;
	}

	public int getUploadDate() {
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
		int compar = -uploadDate + m.getUploadDate();
		if (compar == 0) {
			return content.compareTo(m.getContent());
		}
		return compar;
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

}
