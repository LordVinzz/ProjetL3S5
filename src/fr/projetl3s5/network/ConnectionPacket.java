package fr.projetl3s5.network;

import java.io.IOException;

import org.json.JSONObject;

import fr.project.groups.Group;

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
			Group group = serv.getDb().isUserExisting(jObject.getString("username"), jObject.getString("passwordHash"));
			if(group != null) {
				jObject = new JSONObject("{\r\n"
						+ "  \"Status\": \"valid\",\r\n"
						+ "  \"ConnectionToken\": \""+ serv.getUuid() + "\"\r\n"
						+ "}");
				ConnectionPacket connecPacket = new ConnectionPacket(jObject.toString());
				serv.getOut().writeObject(connecPacket);
			}else {
				jObject = new JSONObject("{\r\n"
						+ "  \"Status\": \"invalid\",\r\n"
						+ "  \"ConnectionToken\": \"null\"\r\n"
						+ "}");
				ConnectionPacket connecPacket = new ConnectionPacket(jObject.toString());
				serv.getOut().writeObject(connecPacket);
			}
		} else if (ctx instanceof Client){
			Client client = (Client)ctx;
			JSONObject jObject = new JSONObject(content);
			String token = jObject.getString("ConnectionToken");
			if(!token.equals("null")) {
				//client.log dans l'interface
			}
		} else {
			ctx.getOut().writeObject(new MalformedPacketException("Exception thrown by server !"));
			ctx.getSocket().close();
		}
	}

}
