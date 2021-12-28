package fr.projetl3s5.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client extends Thread implements Context {

	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private List<Packet> packets = new ArrayList<Packet>();
	private List<Context> contexts = new ArrayList<Context>();
	
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
				while(contexts.size() != 0) {
					pollPacket().execute(pollContext());
				}
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
	
	public synchronized int getPacketQueueSize() {
		return packets.size();
	}
	
	public synchronized Packet pollPacket() {
		Packet p = packets.get(getPacketQueueSize() - 1);
		packets.remove(p);
		return p;
	}
	
	public synchronized int getContextQueueSize() {
		return contexts.size();
	}
	
	public synchronized Context pollContext() {
		Context p = contexts.get(getContextQueueSize() - 1);
		contexts.remove(p);
		return p;
	}
	
	public Socket getSocket() {
		return socket;
	}

	public void pendExecution(Context ctx) {
		contexts.add(ctx);
	}	
}
