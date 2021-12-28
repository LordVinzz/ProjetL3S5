package fr.projetl3s5.network;

import java.io.IOException;
import java.io.Serializable;

import org.json.JSONObject;

public abstract class Packet implements Serializable {

	private static final long serialVersionUID = -5940764935189626511L;
	
	public abstract JSONObject execute(Context ctx) throws IOException;
}
