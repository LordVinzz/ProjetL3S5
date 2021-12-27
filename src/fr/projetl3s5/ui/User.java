package fr.projetl3s5.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

import fr.projetl3s5.groups.Group;

public class User {
	private String id;
	private String nom;
	private String prenom;
	private int nbTicketsTotal=0;
	
	private List<String> groupe = new ArrayList<>();
	private NavigableMap<String, List<Ticket>> listTickets = new TreeMap<>((String g1, String g2) -> g1.compareTo(g2));
	
	
	public User(String id, String nom, String prenom, Integer groupe, Ticket ... tickets) {
		this.id=id;
		this.prenom=prenom;
		this.nom=nom;
		
		for(Group g : Group.getGroupByID(groupe)) {
			this.groupe.add(g.toString());
		}
		
		for(Ticket t : tickets) {
			listTickets.get(t.getGroupe()).add(t);
		}
	}

	
	public String getId() {
		return id;
	}
	
	public String getPrenom() {
		return prenom;
	}
	
	public String getNom() {
		return nom;
	}
	
	public List<String> getGroupe() {
		return groupe;
	}
	
	public int getNbTicketsTotal() {
		return nbTicketsTotal;
	}
	
	public NavigableMap<String, List<Ticket>> getListTicket() {
		return listTickets;
	}
	
	@Override
	public boolean equals(Object obj) {

		if(obj instanceof User) {
			User caca = (User) obj;
			return id.equals(caca.getId()) && 
					prenom.equals(caca.getPrenom()) && nom.equals(caca.getNom())
					&& groupe == caca.getGroupe();
		}
		
	
		return false;
	}
	
	public void setListTicket(Ticket t) {
		String g = t.getGroupe();
		if(groupe.contains(g)) {
			listTickets.get(g).add(t);
			nbTicketsTotal++;
		}
	}
}
