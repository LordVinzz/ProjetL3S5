package fr.projetl3s5.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.TreeSet;

import org.json.JSONObject;

import fr.projetl3s5.groups.Group;
import fr.projetl3s5.network.Client;
import fr.projetl3s5.network.Context;
import fr.projetl3s5.network.RetrieveTicketsPacket;

public class User implements Context{
	
	private String id, name, fName;
	private Client client;
	private int nbTotalTicket = 0;

	private Group group;
	private NavigableSet<Ticket> tickets = new TreeSet<>();
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
		this.group = Group.getGroupByID(group);

		getTicketsFromServer();
		client.pendExecution(this);
	}


	public void getTicketsFromServer() {
		try {
			JSONObject jObject = new JSONObject("{}");
			jObject.put("username", id);
			jObject.put("group", group.getId());
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

	public Group getGroup() {
		return group;
	}
	
	public Client getClient() {
		return client;
	}

	public int getNbTicketsTotal() {
		return nbTotalTicket;
	}

	public NavigableSet<Ticket> getTickets() {
		return tickets;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof User) {
			User user = (User) obj;
			return id.equals(user.getId()) && fName.equals(user.getPrenom()) && name.equals(user.getNom())
					&& group.equals(user.getGroup());
		}

		return false;
	}

	public synchronized void addToTicketList(Ticket t) {
		tickets.add(t);
		nbTotalTicket++;
		updateInterface();
	}

	public void setInterface(Interface interface1) {
		this.interfacz = interface1;
	}

	public void updateInterface() {
		interfacz.setTicketTree();
	}

}
