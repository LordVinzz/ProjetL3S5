package ft.projetl3s5.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
	
	private static Server instance;
	private ServerSocket serverSocket;
	private boolean running = false;
	private List<ServerThread> clients;
	
	public Server() {
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
		instance = new Server();
		instance.listen();
	}
	
	public static Server getInstance() {
		return instance;
	}
	
	public static void main(String[] args) {
		init();
	}
		
}
