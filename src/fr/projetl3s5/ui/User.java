package fr.projetl3s5.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;


import fr.projetl3s5.groups.Group;

public class User {
	
	private String id, name, fName;
	private int nbTotalTicket = 0;

	private List<String> groupe = new ArrayList<>();
	private NavigableMap<String, List<Ticket>> listTickets = new TreeMap<>((String g1, String g2) -> g1.compareTo(g2));

	public User(String id, String name, String fName, Integer group) {
		this.id = id;
		this.fName = fName;
		this.name = name;

		for (Group g : Group.getGroupsByID(group)) {
			this.groupe.add(g.toString());
		}

		for (Group g : Group.values()) {
			listTickets.put(g.toString(), new ArrayList<>());
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

	public List<String> getGroupe() {
		return groupe;
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
					&& groupe.equals(user.getGroupe());
		}

		return false;
	}

	public void setTicketList(Ticket t) {
		String g = t.getGroup().toString();
		if (groupe.contains(g)) {
			listTickets.get(g).add(t);
			nbTotalTicket++;
		}
	}

}
