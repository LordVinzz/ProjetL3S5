package fr.projetl3s5.client.ui;

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
import fr.projetl3s5.network.Packet;
import fr.projetl3s5.network.RetrieveTicketsPacket;

public class User implements Context{
	
	private String id, name, fName;
	private Client client;
	private int nbTotalTicket = 0;
	private int uuid = 0;
	private String passwordHash = null;
	
	private Group group;
	private NavigableSet<Ticket> tickets = new TreeSet<>();
	private ClientInterface interfacz;

	public User(String id, String name, String fName) {
		this.id = id;
		this.fName = fName;
		this.name = name;
	}
	
	public User(String id, String name, String fName, int group) {
		this(id,name,fName);
		this.group = Group.getGroupByID(group);
	}
	
	public User(String id, String name, String fName, int group, int uuid) {
		this(id,name,fName,group);
		this.uuid = uuid;
	}
	
	public User(String id, String name, String fName, int group, Client client) {
		this(id,name,fName);
		this.client = client;
		this.group = Group.getGroupByID(group);

		getTicketsFromServer();
	}


	public void getTicketsFromServer() {
		try {
			JSONObject jObject = new JSONObject("{}");
			jObject.put("username", id);
			jObject.put("group", group.getId());
			client.getOut().writeObject(new RetrieveTicketsPacket(jObject.toString()));
			client.pendExecution(this);
		} catch (IOException e) {
		}
	}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getFName() {
		return fName;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	public String getPasswordHash() {
		return passwordHash;
	}
	
	public int getUuid() {
		return uuid;
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
			return id.equals(user.getId()) && fName.equals(user.getName()) && name.equals(user.getFName())
					&& group.equals(user.getGroup());
		}

		return false;
	}

	public synchronized void addToTicketList(Ticket t) {
		tickets.add(t);
		nbTotalTicket++;
	}

	public void setInterface(ClientInterface interface1) {
		this.interfacz = interface1;
	}

	public void updateInterface() {
		interfacz.setTicketTree();
	}
	
	public void setfName(String fName) {
		this.fName = fName;
	}
	
	public void setGroup(Group group) {
		this.group = group;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}
