package fr.projetl3s5.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import fr.projetl3s5.db.DatabaseCommunicator;

public class Server {
	
	private static Server instance;
	private ServerSocket serverSocket;
	private boolean running = false;
	private List<ServerThread> clients;
	private static DatabaseCommunicator db;
	
	private Server() {
		try {
			serverSocket = new ServerSocket(13337);
			clients = new ArrayList<ServerThread>();
			running = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void listen() {
		while(running) {
			try {
				Socket s = serverSocket.accept();
				ServerThread thd = new ServerThread(s);
				clients.add(thd);
				thd.run();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void init() {
		if(instance == null) {			
			instance = new Server();
			instance.listen();
		}
	}
	
	public static Server getInstance() {
		return instance;
	}
	
	public static DatabaseCommunicator getDatabaseInstance() {
		return db;
	}
			
}
