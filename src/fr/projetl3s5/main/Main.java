package fr.projetl3s5.main;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import ft.projetl3s5.network.Server;

public class Main {

	public static void main(String[] args) {
		
		try {
			Socket socket = new Socket("localhost", 13337);
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(new Integer(42));
			oos.writeObject(new Integer(43));
			oos.writeObject(new Integer(44));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
