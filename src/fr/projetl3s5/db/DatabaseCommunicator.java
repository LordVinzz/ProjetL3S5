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
		} catch (SQLException e) {
			jObject.put("Status", "invalid");
			jObject.put("ConnectionToken", "null");
		}
		return jObject;
	}
	
	public static synchronized List<JSONObject> getTickets(String username, int group) {
		try {
			
			List<JSONObject> jList = new ArrayList<>();
			
			statement = connection.createStatement();
			resultSet = statement.executeQuery(String.format(
					"SELECT * FROM threadstable WHERE threadstable.fPoster = '%s' AND threadstable.group = '%i'", username, group));
			
			while(resultSet.next()) {
				File file = new File(String.format("serverhistory/%s.json", resultSet.getString("filename")));
				JSONObject jObject = new JSONObject(new String(Files.readAllBytes(file.toPath())));
				jList.add(jObject);
			}
			
			return jList;
			
		}catch (SQLException | IOException e) {}
		return null;
	}
	
}
