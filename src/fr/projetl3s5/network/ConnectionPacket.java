package fr.projetl3s5.network;

import java.io.IOException;

import org.json.JSONObject;

import fr.projetl3s5.client.ui.Login;
import fr.projetl3s5.client.ui.User;
import fr.projetl3s5.db.DatabaseCommunicator;

public class ConnectionPacket extends Packet {

	private static final long serialVersionUID = -5243095094071838290L;

	private String content;

	public ConnectionPacket(String str) {
		this.content = str;
	}

	@Override
	public void execute(Context ctx) throws IOException {
		if (ctx instanceof ServerThread) {
			ServerThread serv = (ServerThread) ctx;
			JSONObject jObject = new JSONObject(content);
			jObject = DatabaseCommunicator.getUser(jObject.getString("username"), jObject.getString("passwordHash"));
			
			if(jObject.getString("Status").equals("valid"))
				serv.setUser(new User(jObject.getString("Id"), jObject.getString("Name"), jObject.getString("FName")));
			ConnectionPacket connecPacket = new ConnectionPacket(jObject.toString());

			serv.getOut().writeObject(connecPacket);
		} else if (ctx instanceof Login) {
			Login login = (Login) ctx;
			login.checkCredentials(new JSONObject(content));
		}
	}

}
