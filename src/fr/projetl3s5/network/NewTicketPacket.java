package fr.projetl3s5.network;

import java.io.IOException;

import org.json.JSONObject;

import fr.projetl3s5.client.ui.NewTicketListener;
import fr.projetl3s5.client.ui.User;
import fr.projetl3s5.db.DatabaseCommunicator;

public class NewTicketPacket extends Packet{

	private static final long serialVersionUID = -4592584283349467247L;

	private String content;
	
	public NewTicketPacket(String content) {
		this.content = content;
	}
	
	@Override
	public void execute(Context ctx) throws IOException {
		if(ctx instanceof ServerThread) {
			ServerThread serv = (ServerThread)ctx;
			JSONObject jObject = new JSONObject(content);
			
			boolean isTopicCreated = DatabaseCommunicator.createNewTopic(
					jObject.getString("Topic"), jObject.getInt("Group"), jObject.getString("Id"),
					jObject.getString("Name"), jObject.getString("FName"), jObject.getString("Content"),
					jObject.getInt("UserGroup")
					);
			
			jObject.clear();
			jObject.put("Succeed", isTopicCreated);
			
			serv.getOut().writeObject(new NewTicketPacket(jObject.toString()));
			
		}else if(ctx instanceof NewTicketListener) {
			NewTicketListener ntl = (NewTicketListener)ctx;
			User u = ntl.getUser();
			
			u.getTicketsFromServer();
			
			ntl.getInterface().reloadTree();
		}
	}

}
