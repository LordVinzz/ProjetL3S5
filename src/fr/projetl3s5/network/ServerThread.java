package fr.projetl3s5.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fr.projetl3s5.client.ui.User;
import fr.projetl3s5.db.DatabaseCommunicator;

public class ServerThread extends Thread implements Context{
	
	private Socket clientSocket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private List<Packet> packets = new ArrayList<Packet>();
	private User user;
	
	public ServerThread(Socket s) {
		clientSocket = s;
		try {
			in = new ObjectInputStream(clientSocket.getInputStream());
			out = new ObjectOutputStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		Object o = null;
		try {
			while(clientSocket != null && (o = in.readObject()) != null) {
				if(o instanceof Packet) ((Packet)o).execute(this);
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public ObjectOutputStream getOut() {
		return out;
	}
	
	public ObjectInputStream getIn() {
		return in;
	}
	
	public int getQueueSize() {
		return packets.size();
	}
	
	public Packet pollPacket() {
		Packet p = packets.get(getQueueSize() - 1);
		packets.remove(p);
		return p;
	}
	
	public DatabaseCommunicator getDatabaseInstance() {
		return Server.getDatabaseInstance();
	}
	
	public Socket getSocket() {
		return clientSocket;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}
}
