package com.itsc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class database {
    public static void main(String[] args) throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/usersdb";
        String username = "root";
        String password = "baba11@";
        Connection connection = null;
        Statement statement = null;

        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            statement = connection.createStatement();

            // Add the 'completed' column to the 'users' table
            String alterTableSQL = "ALTER TABLE users ADD COLUMN completed BOOLEAN DEFAULT false";
            statement.executeUpdate(alterTableSQL);
            System.out.println("Column 'completed' added to the 'users' table successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the statement and connection
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}