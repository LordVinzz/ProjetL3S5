package fr.projetl3s5.main;

import fr.projetl3s5.network.Client;
import fr.projetl3s5.network.Server;
import fr.projetl3s5.ui.Interface;
import fr.projetl3s5.ui.Interface3;
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
//
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//
		Client client = new Client("localhost", 13337);
		client.start();
		User user = new User("root@univ-tlse3.fr", "Root", "Root", 15, client);
		Interface3 i2 = new Interface3(user);
//		
//		Login logs= new Login(client);
//		
//		logs.start();
	}

}
