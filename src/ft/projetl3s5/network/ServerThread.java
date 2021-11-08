package ft.projetl3s5.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread{
	
	private Socket clientSocket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
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
			while((o = in.readObject()) != null) {
				System.out.println(((Integer)o).intValue());
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
}
