package com.library.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for managing database connections
 */
public class DatabaseConnection {
    
    private static final String URL = "jdbc:mysql://localhost:3306/library_management";
    private static final String USERNAME = "root"; // Change as needed
    private static final String PASSWORD = ""; // Change as needed
    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());
    
    private static Connection connection = null;
    
    // Private constructor to prevent instantiation
    private DatabaseConnection() {}
    
    /**
     * Get a connection to the database
     * @return Connection object
     */
    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                LOGGER.log(Level.INFO, "Database connection established successfully");
            } catch (ClassNotFoundException e) {
                LOGGER.log(Level.SEVERE, "MySQL JDBC Driver not found", e);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Failed to connect to database", e);
            }
        }
        return connection;
    }
    
    /**
     * Close the database connection
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                LOGGER.log(Level.INFO, "Database connection closed successfully");
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing database connection", e);
            }
        }
    }
}