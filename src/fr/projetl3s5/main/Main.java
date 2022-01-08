package fr.projetl3s5.main;

import fr.projetl3s5.client.ui.Login;
import fr.projetl3s5.network.Client;
import fr.projetl3s5.network.Server;
import fr.projetl3s5.server.ui.ServerInterface;

public class Main {

	public static void main(String[] args) {
		
//		Thread t = new Thread() {
//			@Override
//			public void run() {
//				Server.init();
//			}
//		};
//		t.start();
//		
////		try {
////			Thread.sleep(1000);
////		} catch (InterruptedException e) {
////			e.printStackTrace();
////		}
////
//		Client client = new Client("localhost", 13337);
//		client.start();
//
//		Login logPage = new Login(client);
//		logPage.start();
		
		ServerInterface si = new ServerInterface();
		
		
	}

}
