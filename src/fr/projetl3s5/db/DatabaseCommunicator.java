package fr.projetl3s5.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.project.groups.Group;
import fr.project.groups.User;
import fr.projetl3s5.network.Context;

public class DatabaseCommunicator {

	private static final String DATABASE_URL = "jdbc:mysql://localhost/new_schema";
	private static final String USER = "admin";
	private static final String PWD = "admin";
	
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	
	public DatabaseCommunicator() {
		try {
			connection = DriverManager.getConnection(DATABASE_URL, USER, PWD);
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public User isUserExisting(String username, String passwordHash) {
		try {
			resultSet = statement.executeQuery(String.format(
					"SELECT * FROM userstable WHERE userstable.username = %s and userstable.passwordHash = %s", username, passwordHash
					)
			);
			resultSet.next();
			return new User(Group.getGroupByID(resultSet.getInt("group")), username, resultSet.getString("name"), resultSet.getString("fname"));
		} catch (SQLException e) {
			return null;
		}
	}
	
}
