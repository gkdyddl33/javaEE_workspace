package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
	
	public static Connection getConnection() {
		
		try {
			String dbURL = "jdbc:mysql://localhost:3306/tutorial";
			String dbID = "root";
			String dbPassword="1234";
			Class.forName("org.mariadb.jdbc.Driver");
			return DriverManager.getConnection(dbURL, dbID, dbPassword);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
