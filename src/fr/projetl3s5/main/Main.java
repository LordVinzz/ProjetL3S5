package fr.projetl3s5.main;

import fr.projetl3s5.network.Client;

public class Main {

	public static void main(String[] args) {
		
		Client client = new Client("localhost", 13337);
		client.start();
	}
	
}
