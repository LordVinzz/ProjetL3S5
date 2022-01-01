package fr.projetl3s5.db;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
							"SELECT * FROM threadstable WHERE threadstable.fPoster = '%s' AND threadstable._group = '%d'", id, mask));
					
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
}
