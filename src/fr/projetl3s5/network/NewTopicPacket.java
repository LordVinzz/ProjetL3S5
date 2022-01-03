package fr.projetl3s5.network;

import java.io.IOException;

import org.json.JSONObject;

import fr.projetl3s5.db.DatabaseCommunicator;
import fr.projetl3s5.ui.NewTicketListener;

public class NewTopicPacket extends Packet{

	private static final long serialVersionUID = -4592584283349467247L;

	private String content;
	
	public NewTopicPacket(String content) {
		this.content = content;
	}
	
	@Override
	public void execute(Context ctx) throws IOException {
		if(ctx instanceof ServerThread) {
			ServerThread serv = (ServerThread)ctx;
			JSONObject jObject = new JSONObject(content);
			
			boolean isTopicCreated = DatabaseCommunicator.createNewTopic(
					jObject.getString("Topic"), jObject.getInt("Group"), jObject.getString("Id"),
					jObject.getString("Name"), jObject.getString("FName"), jObject.getString("Content")
					);
			
		}else if(ctx instanceof NewTicketListener) {
			
		}
	}

}
