package fr.projetl3s5.network;

import java.io.ObjectOutputStream;
import java.net.Socket;

public interface Context {
	
	public ObjectOutputStream getOut();
	public Socket getSocket();
	
}