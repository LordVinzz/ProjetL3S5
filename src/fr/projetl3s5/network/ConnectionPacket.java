package fr.projetl3s5.network;

import java.io.IOException;

import org.json.JSONObject;

import fr.projetl3s5.db.DatabaseCommunicator;

public class ConnectionPacket extends Packet{

	private static final long serialVersionUID = -5243095094071838290L;

	private String content;
	
	public ConnectionPacket(String str) {
		this.content = str;
	}
	
	@Override
	public void execute(Context ctx) throws IOException {
		if(ctx instanceof ServerThread) {
			ServerThread serv = (ServerThread)ctx;
			JSONObject jObject = new JSONObject(content);
			boolean user = DatabaseCommunicator.isUserExisting(jObject.getString("username"), jObject.getString("passwordHash"));
			if(user != false) { // TODO
				jObject = new JSONObject("{}");
				
				jObject.put("Status", "valid");
				jObject.put("ConnectionToken", serv.getUuid());
				
				ConnectionPacket connecPacket = new ConnectionPacket(jObject.toString());
				serv.getOut().writeObject(connecPacket);
			}else {
				jObject = new JSONObject("{}");
				
				jObject.put("Status", "invalid");
				jObject.put("ConnectionToken", "null");
				
				ConnectionPacket connecPacket = new ConnectionPacket(jObject.toString());
				serv.getOut().writeObject(connecPacket);
			}
		} else if (ctx instanceof Client){
			Client client = (Client)ctx;
			JSONObject jObject = new JSONObject(content);
			String token = jObject.getString("ConnectionToken");
			if(!token.equals("null")) {
				//client.log dans l'interface
			}else {
				//client.error
			}
		} else {
			ctx.getOut().writeObject(new MalformedPacketException("Exception thrown by server !"));
			ctx.getSocket().close();
		}
	}

}
