package fr.projetl3s5.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.UUID;

import fr.projetl3s5.db.DatabaseCommunicator;

public class ServerThread extends Thread implements Context{
	
	private Socket clientSocket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Queue<Packet> packets = new PriorityQueue<Packet>();
	private UUID uuid;
	
	public ServerThread(Socket s) {
		clientSocket = s;
		try {
			in = new ObjectInputStream(clientSocket.getInputStream());
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			uuid = UUID.randomUUID();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		Object o = null;
		try {
			while(clientSocket != null && (o = in.readObject()) != null) {
				if(o instanceof Packet)packets.add((Packet)o);
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
	
	public Packet pollPacket() {
		return packets.poll();
	}
	
	public DatabaseCommunicator getDatabaseInstance() {
		return Server.getInstance().getDatabaseInstance();
	}
	
	public UUID getUuid() {
		return uuid;
	}
	
	@Override
	public Socket getSocket() {
		return clientSocket;
	}

}
