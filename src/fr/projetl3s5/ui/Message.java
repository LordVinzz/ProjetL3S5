package fr.projetl3s5.ui;

public class Message implements Comparable<Message>{
	private Client createur;
	private int dateEmise;
	private String contenu;
	
	public Message(Client createur, int dateEmise, String contenu) {
		this.createur=createur;
		this.dateEmise=dateEmise;
		this.contenu=contenu;
	}
	
	public int getDateEmise() {
		return dateEmise;
	}
	
	public String getContenu() {
		return contenu;
	}
	
	public Client getCreateur() {
		return createur;
	}
	
	@Override
	public int compareTo(Message m) {
		int compar = dateEmise-m.dateEmise;
		if(compar==0) {
			return contenu.compareTo(m.contenu);
		}
		return compar;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Message) {
			Message caca = (Message) obj;
			return createur.equals(caca.getCreateur()) && dateEmise==caca.getDateEmise()
					&& contenu.equals(caca.getContenu());
		}
		return false;
	}
	
}
