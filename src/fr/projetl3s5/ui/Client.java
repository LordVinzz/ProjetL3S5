package fr.projetl3s5.ui;

import java.util.NavigableSet;
import java.util.TreeSet;

public class Client {
	String id;
	String mdp;
	String nom;
	String prenom;
	String[] groupe;
	NavigableSet<Ticket> listTickets = new TreeSet<>((Ticket t1, Ticket t2) -> t1.compareTo(t2));
	
	
	public Client(String id, String mdp, String nom, String prenom, String ... groupe) {
		this.id=id;
		this.mdp=mdp;
		this.prenom=prenom;
		this.nom=nom;
		this.groupe=groupe;
	}
	
	
	public String getId() {
		return id;
	}
	
	public String getMdp() {
		return mdp;
	}
	
	public String getPrenom() {
		return prenom;
	}
	
	public String getNom() {
		return nom;
	}
	
	public String[] getGroupe() {
		return groupe;
	}
	
	@Override
	public boolean equals(Object obj) {

		if(obj instanceof Client) {
			Client caca = (Client) obj;
			return id.equals(caca.getId()) && mdp.equals(caca.getMdp()) && 
					prenom.equals(caca.getPrenom()) && nom.equals(caca.getNom())
					&& groupe == caca.getGroupe();
		}
		
	
		return false;
	}
	
}
