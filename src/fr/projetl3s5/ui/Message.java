package fr.projetl3s5.ui;

public class Message implements Comparable<Message> {

	private User creator;
	private int uploadDate;
	private String content;

	public Message(User creator, int uploadDate, String content) {
		this.creator = creator;
		this.uploadDate = uploadDate;
		this.content = content;
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
