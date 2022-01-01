package fr.projetl3s5.ui;

import java.util.NavigableSet;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.projetl3s5.groups.Group;


public class Ticket implements Comparable<Ticket>{
	
	private String title;
	private int totalMember;
	private Group group;
	private NavigableSet<Message> history = new TreeSet<>((Message m1, Message m2) -> m1.compareTo(m2));
	private User creator;
	private String code;
	
	public Ticket(String titre, Group groupe, User createur, Message... messages) {
		this.title=titre;
		this.group=groupe;
		this.creator=createur;
		
		for(Message msg : messages) {
			history.add(msg);
		}
	}
	
	public Ticket(JSONObject jObject) {
		this.title = jObject.getString("Title");
		this.totalMember = jObject.getInt("TotalMembers");
		this.group = Group.getGroupByID(jObject.getInt("Group"));
		this.code = jObject.getString("Code");
		
		JSONArray jArray = jObject.getJSONArray("Messages");
		for(int i = 0; i < jArray.length(); i++) {
			JSONObject jO = jArray.getJSONObject(i);
			User user = new User(
				jO.getString("Id"),
				jO.getString("Name"),
				jO.getString("FName")
			);
			Message msg = new Message(user, jO.getInt("Date"), jO.getString("Content"),  jO.getInt("ReadBy"), this.totalMember, MsgState.EN_ATTENTE);
			history.add(msg);
		}
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
	
	public NavigableSet<Message> getHistory() {
		return history;
	}
	
	public NavigableSet<Message> setHistory(Message m){
		history.add(m);
		return history;
	}
	
	@Override
	public int compareTo(Ticket t) {
		return history.pollLast().compareTo(t.getHistory().pollLast());
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Ticket) {
			Ticket ticket = (Ticket) obj;
			return group.equals(ticket.getGroup()) && title.equals(ticket.getTitle());
		}
		return false;
	}
	
	@Override
	public String toString() {
		return this.getTitle();
	}

	public String getCode() {
		return code;
	}
	
	
	
	
	
}