package fr.projetl3s5.ui;

import java.util.NavigableSet;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.projetl3s5.groups.Group;

public class Ticket implements Comparable<Ticket> {

	private String title;
	private int totalMember;
	private Group group;
	private NavigableSet<Message> history = new TreeSet<>((Message m1, Message m2) -> m1.compareTo(m2));
	private User creator;
	private String code;
	private boolean titleIsInBold = false;
	private int nbMsgNON_LU;

	public Ticket(String titre, Group groupe, User createur, Message... messages) {
		this.title = titre;
		this.group = groupe;
		this.creator = createur;

		for (Message msg : messages) {
			history.add(msg);
		}
	}

	public Ticket(JSONObject jObject) {
		this.title = jObject.getString("Title");
		this.totalMember = jObject.getInt("TotalMembers");
		this.group = Group.getGroupByID(jObject.getInt("Group"));
		this.code = jObject.getString("Code");

		JSONArray jArray = jObject.getJSONArray("Messages");
		for (int i = 0; i < jArray.length(); i++) {
			JSONObject jO = jArray.getJSONObject(i);
			User user = new User(jO.getString("Id"), jO.getString("Name"), jO.getString("FName"));
			Message msg = new Message(user, jO.getLong("Date"), jO.getString("Content"), jO.getInt("ReadBy"),
					this.totalMember, MsgState.EN_ATTENTE);
			history.add(msg);
		}
	}
	
	public int setNbMsgNON_LU() {
		nbMsgNON_LU=0;
		for(Message msg : getHistory()) {
			if(msg.getReadBy()<msg.getNbTotalMembers()) {
				nbMsgNON_LU++;
			}
		}
		
		if(nbMsgNON_LU>0 && !titleIsInBold) {
			setTitleIsInBold();
		}
		return nbMsgNON_LU;
	}

	public String getTitle() {
		return title;
	}

	public Group getGroup() {
		return group;
	}

	public User getCreateur() {
		return creator;
	}

	public int getTotalMember() {
		return totalMember;
	}

	public boolean getTitleIsInBold() {
		return titleIsInBold;
	}

	public NavigableSet<Message> getHistory() {
		return history;
	}

	public void addHistory(Message m) {
		history.add(m);
	}

	public void setTitleIsInBold() {
		titleIsInBold = !titleIsInBold;
	}

	@Override
	public int compareTo(Ticket t) {
		return history.last().compareTo(t.getHistory().last());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Ticket) {
			Ticket ticket = (Ticket) obj;
			return group.equals(ticket.getGroup()) && title.equals(ticket.getTitle())
					|| group.equals(ticket.getGroup()) && history.pollFirst().equals(ticket.getHistory().pollFirst());
		}
		return false;
	}

	@Override
	public String toString() {
		if (titleIsInBold) {
			return String.format("<b>%s (%d)</b>", title, nbMsgNON_LU);
		}
		return this.getTitle();
	}

	public String getCode() {
		return code;
	}

}