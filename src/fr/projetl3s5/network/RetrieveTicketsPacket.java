package fr.projetl3s5.network;

import java.io.IOException;
import java.util.List;

import org.json.JSONObject;

import fr.projetl3s5.client.ui.Ticket;
import fr.projetl3s5.client.ui.User;
import fr.projetl3s5.db.DatabaseCommunicator;
import fr.projetl3s5.groups.Group;

public class RetrieveTicketsPacket extends Packet {

	private static final long serialVersionUID = 8772625988987468532L;
	
	private String content;
	private List<String> ticketsList;

	public RetrieveTicketsPacket(String content) {
		this.content = content;
	}
	
	public RetrieveTicketsPacket(List<String> content) {
		this.ticketsList = content;
	}

	@Override
	public void execute(Context ctx) throws IOException {
		if (ctx instanceof ServerThread) {
			ServerThread serv = (ServerThread) ctx;
			JSONObject jObject = new JSONObject(content);

			String userId = jObject.getString("username");
			int group = jObject.getInt("group");
			

			List<String> jObjects = DatabaseCommunicator.getTickets(userId, group);
			
			serv.getOut().writeObject(new RetrieveTicketsPacket(jObjects));
		} else if(ctx instanceof User) {
			User user = (User)ctx;
			for(String str : ticketsList) {
				JSONObject jObject = new JSONObject(str);
				Ticket ticket = new Ticket(jObject);
				
				user.addToTicketList(ticket);
				
			}
			user.updateInterface();
		}
	}
}
