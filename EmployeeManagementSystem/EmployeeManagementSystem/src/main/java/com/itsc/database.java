package com.itsc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class database {
    public static void main(String[] args) throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/employee";
        String username = "root";
        String pwd = "baba11@";
        Connection connection = DriverManager.getConnection(jdbcURL, username, pwd);
        Statement statement = connection.createStatement();

        String createTableSQL = "CREATE TABLE employe (" +
                "id int auto_increment primary key," +
                "name varchar(255)," +
                "designation varchar(255)," +
                "salary varchar(255))";
        statement.executeUpdate(createTableSQL);
        System.out.println("Table 'employe' created successfully.");
    }
}