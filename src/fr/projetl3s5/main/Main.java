package fr.projetl3s5.main;

import fr.projetl3s5.groups.Group;
import fr.projetl3s5.network.Client;
import fr.projetl3s5.network.Server;
import fr.projetl3s5.ui.Interface;
import fr.projetl3s5.ui.Login;
import fr.projetl3s5.ui.Message;
import fr.projetl3s5.ui.Ticket;
import fr.projetl3s5.ui.User;

public class Main {

	public static void main(String[] args) {
		
		Thread t = new Thread() {
			@Override
			public void run() {
				Server server = Server.getInstance();
				Server.init();
			}
		};
		t.start();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Client client = new Client("localhost", 13337);
		client.start();
		User user = new User("root@univ-tlse3.fr", "Root", "Root", 15, client);
		Interface interfacz = new Interface(user);
//		
//		Login logs= new Login(client);
//		
//		logs.start();
//		if(true) {
//			User adolph = new User("adolph.hitler@gmail.com", "hitler", "adolph", 0b1010);
//			Message m1 = new Message(adolph, 52, "Hallo\nWir haben ein Gross Problem.");
//			Message m2 = new Message(adolph, 56, "Halsdfsdf");
//			Message m3 = new Message(adolph, 60, "Hallosersdflem.");
//			Ticket t1 = new Ticket("Probleme nazi", Group.PROFS, adolph, m1);
//			Ticket t2 = new Ticket("Les juifs", Group.PROFS, adolph, m2);
//			//t1.setHistorique(m2);
//			//t2.setHistorique(m3);
//			adolph.setListTicket(t1);
//			adolph.setListTicket(t2);
//			new Interface(adolph);
//		}
		
		
	}
	
}
