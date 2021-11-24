package fr.projetl3s5.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.PriorityQueue;
import java.util.Queue;

public class Client extends Thread {

	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Queue<Packet> packets = new PriorityQueue<Packet>();
	
	public Client(String address, int port) {
 		try {
			this.socket = new Socket(address, port);
			this.out = new ObjectOutputStream(this.socket.getOutputStream());
			this.in = new ObjectInputStream(this.socket.getInputStream());
		} catch (IOException e) {
			this.socket = null;
		}
	}
	
	@Override
	public void run() {
		Object o = null;
		try {
			while(this.socket != null && (o = in.readObject()) != null) {
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
}
