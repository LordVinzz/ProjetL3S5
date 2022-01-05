package fr.projetl3s5.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.projetl3s5.groups.Group;
import fr.projetl3s5.ui.User;

public class DatabaseCommunicator {

	private static final String DATABASE_URL = "jdbc:mysql://localhost/new_schema";
	private static final String USER = "admin";
	private static final String PWD = "admin";
	
	private static Connection connection = getConnectionWrapper();
	private static Statement statement;
	private static ResultSet resultSet;
	
	private static Connection getConnectionWrapper() {
		try {
			return DriverManager.getConnection(DATABASE_URL, USER, PWD);
		}catch(SQLException e) {
			return null;
		}
	}
	
	public static synchronized JSONObject getUser(String username, String passwordHash) {
		JSONObject jObject = new JSONObject("{}");
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(String.format(
					"SELECT * FROM userstable WHERE userstable.username = '%s' AND userstable.passwordHash = '%s'", username, passwordHash
					)
			);
			resultSet.next();
			
			jObject = new JSONObject("{}");
			jObject.put("Status", "valid");
			jObject.put("Name", resultSet.getString("name"));
			jObject.put("FName", resultSet.getString("fname"));
			jObject.put("Group", resultSet.getInt("_group"));
			jObject.put("Id", resultSet.getString("username"));
		} catch (SQLException e) {
			e.printStackTrace();
			jObject.put("Status", "invalid");
			jObject.put("ConnectionToken", "null");
		}
		return jObject;
	}
	
	public static synchronized List<String> getTickets(String id, int group) {
		try {
			int mask = 1;
			List<String> jList = new ArrayList<>();
			
			while(mask <= 0b1000000000000000) {
				if((mask & group) != 0) {
					
					statement = connection.createStatement();
					resultSet = statement.executeQuery(String.format(
							"SELECT * FROM threadstable WHERE threadstable.fPoster = '%s' OR threadstable._group = '%d'", id, mask));
					
					while(resultSet.next()) {
						File file = new File( String.format("serverhistory/%s.json", resultSet.getString("filename")) );
						JSONObject jObject = new JSONObject(new String(Files.readAllBytes(file.toPath())));
						jList.add(jObject.toString());
					}
				}
				
				mask <<= 1;
			}
			
			return jList;
			
		}catch (SQLException | IOException e) {}
		return null;
	}
	
	public static synchronized int getGroupLength(int group) {
		
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT userstable._group, count(userstable._group) FROM userstable GROUP by _group");
			
			while(resultSet.next()) {
				if(group == resultSet.getInt("_group")) {
					return resultSet.getInt(2);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static synchronized boolean createNewTopic(String topic, int group, String id, String name, String fname, String content) {

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT MAX(pKey) FROM threadstable");
			
			resultSet.next();
			
			int max = resultSet.getInt(1) + 1;
			
			statement = connection.createStatement();
			statement.executeUpdate(String.format(
					"INSERT INTO threadstable (pKey, thread, _group, fPoster) VALUES (%d, '%s', %d, '%s')"
					, max, topic, group, id));
			
			statement = connection.createStatement();
			resultSet = statement.executeQuery(String.format("SELECT * FROM threadstable WHERE pKey = %d", max));
			
			resultSet.next();
			String filename = resultSet.getString("filename");
			
			File f = new File(String.format("serverhistory/%s.json", filename));
			FileOutputStream fos = new FileOutputStream(f);
			f.createNewFile();

			JSONObject jObject = new JSONObject("{}");
			jObject.put("Group", group);
			jObject.put("Title", topic);
			jObject.put("TotalMembers", getGroupLength(group));
			jObject.put("Code", filename);
			
			JSONArray jArray = new JSONArray();
			
			JSONObject message = new JSONObject("{}");
			message.put("Content", content);
			message.put("ReadBy", new JSONArray(String.format("[%s]", id)));
			message.put("Id", id);
			message.put("Name", name);
			message.put("FName", fname);
			message.put("Date", System.currentTimeMillis());
			
			jArray.put(message);
			
			jObject.put("Messages", jArray);
			
			fos.write(jObject.toString().getBytes());
			fos.flush();
			fos.close();
			return true;
		}catch(SQLException | IOException e) {}
		return false;
	}
	
}
