package fr.projetl3s5.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.json.JSONObject;

import fr.projetl3s5.groups.Group;
import fr.projetl3s5.network.Client;
import fr.projetl3s5.network.Context;
import fr.projetl3s5.network.RetrieveTicketsPacket;

public class User implements Context{
	
	private String id, name, fName;
	private Client client;
	private int nbTotalTicket = 0;
	private int groupsMask;

	private List<String> group = new ArrayList<>();
	private NavigableMap<String, List<Ticket>> listTickets = new TreeMap<>((String g1, String g2) -> g1.compareTo(g2));
	private Interface interfacz;

	public User(String id, String name, String fName) {
		this.id = id;
		this.fName = fName;
		this.name = name;
	}
	
	public User(String id, String name, String fName, Integer group, Client client) {
		this.id = id;
		this.fName = fName;
		this.name = name;
		this.client = client;
		this.groupsMask = group;

		for (Group g : Group.getGroupsByID(group)) {
			this.group.add(g.toString());
		}

		for (Group g : Group.values()) {
			listTickets.put(g.toString(), new ArrayList<>());
		}
		
		getTicketsFromServer();
		client.pendExecution(this);
	}


	public void getTicketsFromServer() {
		try {
			JSONObject jObject = new JSONObject("{}");
			jObject.put("username", id);
			jObject.put("group", groupsMask);
			client.getOut().writeObject(new RetrieveTicketsPacket(jObject.toString()));
		} catch (IOException e) {
		}
	}
	
	public String getId() {
		return id;
	}

	public String getPrenom() {
		return fName;
	}

	public String getNom() {
		return name;
	}

	public List<String> getGroups() {
		return group;
	}
	
	public Client getClient() {
		return client;
	}

	public int getNbTicketsTotal() {
		return nbTotalTicket;
	}

	public NavigableMap<String, List<Ticket>> getListTicket() {
		return listTickets;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof User) {
			User user = (User) obj;
			return id.equals(user.getId()) && fName.equals(user.getPrenom()) && name.equals(user.getNom())
					&& group.equals(user.getGroups());
		}

		return false;
	}

	public synchronized void addToTicketList(Ticket t) {
		String g = t.getGroup().toString();
		if (group.contains(g)) {
			listTickets.get(g).add(t);
			nbTotalTicket++;
		}
	}

	public void setInterface(Interface interface1) {
		this.interfacz = interface1;
	}

	public void updateInterface() {
		interfacz.setTicketTree();
	}

}
