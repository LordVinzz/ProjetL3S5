package fr.projetl3s5.main;

import fr.projetl3s5.groups.Group;
import fr.projetl3s5.ui.Interface;
import fr.projetl3s5.ui.Login;
import fr.projetl3s5.ui.Message;
import fr.projetl3s5.ui.Ticket;
import fr.projetl3s5.ui.User;

public class Main {

	public static void main(String[] args) {
		
		//Client client = new Client("localhost", 13337);
		//client.start();
		
		Login logs= new Login();
		
		//logs.start()==1
		if(true) {
			User adolph = new User("adolph.hitler@gmail.com", "hitler", "adolph", 0b1010);
			Message m1 = new Message(adolph, 52, "Hallo\nWir haben ein Gross Problem.");
			Message m2 = new Message(adolph, 56, "Halsdfsdf");
			Message m3 = new Message(adolph, 60, "Hallosersdflem.");
			Ticket t1 = new Ticket("Probleme nazi", Group.PROFS, adolph, m1);
			Ticket t2 = new Ticket("Les juifs", Group.PROFS, adolph, m2);
			//t1.setHistorique(m2);
			//t2.setHistorique(m3);
			adolph.setListTicket(t1);
			adolph.setListTicket(t2);
			new Interface(adolph);
		}
	}
	
}
