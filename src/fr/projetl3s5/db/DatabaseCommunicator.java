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
	
	private static Connection connection;
	private static Statement statement;
	private static ResultSet resultSet;
	
	public DatabaseCommunicator() {
		try {
			connection = DriverManager.getConnection(DATABASE_URL, USER, PWD);
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public static synchronized boolean isUserExisting(String username, String passwordHash) {
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(String.format(
					"SELECT * FROM userstable WHERE userstable.username = %s AND userstable.passwordHash = %s", username, passwordHash
					)
			);
			resultSet.next();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
	public static synchronized List<JSONObject> getTickets(String username, int group) {
		try {
			
			List<JSONObject> jList = new ArrayList<>();
			
			statement = connection.createStatement();
			resultSet = statement.executeQuery(String.format(
					"SELECT * FROM threadstable WHERE threadstable.fPoster = %s AND threadstable.group = %i", username, group));
			
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
