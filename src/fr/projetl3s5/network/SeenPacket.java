package fr.projetl3s5.network;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import org.json.JSONArray;
import org.json.JSONObject;

public class SeenPacket extends Packet{

	private static final long serialVersionUID = -7859458661875328774L;
	
	private String content;
	
	public SeenPacket(String content) {
		this.content = content;
	}
	
	@Override
	public void execute(Context ctx) throws IOException {
		if(ctx instanceof ServerThread) {
			
			JSONObject jObject = new JSONObject(content);
			JSONObject fileContent;
			File f = new File(String.format("serverhistory/%s.json",jObject.getString("Code")));
			fileContent = new JSONObject(new String(Files.readAllBytes(f.toPath())));
			JSONArray jArray = new JSONArray(fileContent.getJSONArray("Messages"));
			
			for(int i = 0; i < jArray.length(); i++) {
				JSONObject singleMessage = jArray.getJSONObject(i);
				JSONArray readby = singleMessage.getJSONArray("ReadBy");
				
				String id = jObject.getString("Id");
				
				if(!readby.toList().contains(id)) {
					readby.put(id);
				}
			}
			
			FileOutputStream fos = new FileOutputStream(f);
			
			fos.write(fileContent.toString().getBytes());
			
			fos.close();
			
		}
	}
}
