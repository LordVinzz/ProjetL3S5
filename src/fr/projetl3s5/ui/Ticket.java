package fr.projetl3s5.ui;

import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;


public class Ticket implements Comparable<Ticket>{
	
	private String titre;
	private String groupe;
	private NavigableSet<Message> historique = new TreeSet<>((Message m1, Message m2) -> m1.compareTo(m2));
	private User createur;
	
	public Ticket(String titre, String groupe, List<Message> historique, User createur) {
		this.titre=titre;
		this.groupe=groupe;
		this.createur=createur;
		
		for(Message msg : historique) {
			historique.add(msg);
		}
	}
	
	
	public String getTitre() {
		return titre;
	}
	
	public String getGroupe() {
		return groupe;
	}
	
	public User getCreateur() {
		return createur;
	}
	
	public NavigableSet<Message> getHistorique() {
		return historique;
	}
	
	public Message getPremMessage() {
		return historique.pollLast();
	}
	
	public NavigableSet<Message> setHistorique(Message m){
		historique.add(m);
		return historique;
	}
	
	@Override
	public int compareTo(Ticket t) {
		return titre.compareTo(t.getGroupe());
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Ticket) {
			Ticket caca = (Ticket) obj;
			return groupe.equals(caca.getGroupe()) && titre.equals(caca.getTitre());
		}
		return false;
	}
	
	
	
}