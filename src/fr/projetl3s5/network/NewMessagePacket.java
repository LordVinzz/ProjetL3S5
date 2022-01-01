package fr.projetl3s5.network;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.projetl3s5.ui.SendMessageListener;
import fr.projetl3s5.ui.User;

public class NewMessagePacket extends Packet {

	private static final long serialVersionUID = -5242058500345004836L;

	private String content;
	
	public NewMessagePacket(String content) {
		this.content = content;
	}

	@Override
	public void execute(Context ctx) throws IOException {
		if(ctx instanceof ServerThread) {
			ServerThread serv = (ServerThread)ctx;
			
			JSONObject jObject = new JSONObject(content);
			JSONObject userMessage = new JSONObject("{}");
			JSONObject ticketData = null;
			JSONArray messagesData = null;

			User user = serv.getUser();
			
			File file = new File(String.format("serverhistory/%s.json", jObject.getString("Code")));
			ticketData = new JSONObject(new String(Files.readAllBytes(file.toPath())));
			messagesData = ticketData.getJSONArray("Messages");
			
			
			userMessage.put("Name", user.getPrenom());
			userMessage.put("FName", user.getNom());
			userMessage.put("Id", user.getId());
			userMessage.put("Date", System.nanoTime());
			userMessage.put("Content", jObject.get("Content"));
			userMessage.put("ReadBy", 1);
			
			messagesData.put(userMessage);
			
			FileWriter fileWriter = new FileWriter(file, false);
			fileWriter.write(ticketData.toString());
			fileWriter.close();
			
			serv.getOut().writeObject(new NewMessagePacket(userMessage.toString()));
			
		}else if(ctx instanceof SendMessageListener) {
			SendMessageListener enm = (SendMessageListener)ctx;
			enm.addMessageFromServer(new JSONObject(content));
		}
	}

}
