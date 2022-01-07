package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DatabaseClass {

    private static Connection databaseConnection;

    public static void lidhuMeDatabazen() {
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded");
            // Establish a connection
            databaseConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_db", "root", "test");
            System.out.println("Database connected");
        } catch (Exception ex) {
            System.out.println("Database connection failed");
            ex.printStackTrace();
        }
    }

    public static PreparedStatement prepareStatement(String queryString) {
        if (databaseConnection != null) {
            try {
                return databaseConnection.prepareStatement(queryString);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else return null;
    }

}
