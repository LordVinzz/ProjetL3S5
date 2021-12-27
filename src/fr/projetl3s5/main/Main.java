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
			User Adolph = new User("adolph.hitler@gmail.com", "hitler", "adolph", 0b1010, null);
			Ticket t = new Ticket("Probleme nazi", Group.PROFS, null , Adolph);
			Adolph.setListTicket(t);
			t.setHistorique(new Message(Adolph, 52, "Hallo\nWir haben ein Gross Problem."));
			
			new Interface(Adolph);
		}
	}
	
}
