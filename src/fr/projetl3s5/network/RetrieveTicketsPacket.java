package fr.projetl3s5.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import fr.projetl3s5.db.DatabaseCommunicator;
import fr.projetl3s5.ui.Ticket;

public class RetrieveTicketsPacket extends Packet {

	private static final long serialVersionUID = 8772625988987468532L;

	private String content;

	public RetrieveTicketsPacket(String content) {
		this.content = content;
	}

	@Override
	public JSONObject execute(Context ctx) throws IOException {
		if (ctx instanceof ServerThread) {
			ServerThread serv = (ServerThread) ctx;
			JSONObject jObject = new JSONObject(content);

			String userId = jObject.getString("UserID");
			int group = jObject.getInt("Group");

			List<JSONObject> jObjects = DatabaseCommunicator.getTickets(userId, group);
			List<Ticket> tickets = new ArrayList<>();

			while (jObjects.size() > 0) {

			}
		}
		return null;
	}

}
