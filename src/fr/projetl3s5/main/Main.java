package fr.projetl3s5.main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.projetl3s5.network.Client;
import fr.projetl3s5.network.Server;
import fr.projetl3s5.ui.Login;

public class Main {

	public static void main(String[] args) {
		Thread t = new Thread() {
			@Override
			public void run() {
				Server.init();
			}
		};
		t.start();
		
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//
		Client client = new Client("localhost", 13337);
		client.start();
		
		Login logPage = new Login(client);
		logPage.start();
		
	}

}
