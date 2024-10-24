package com.nhnacademy.jdbc.util;

import java.sql.*;

public class DbUtils {
    public DbUtils(){
        throw new IllegalStateException("Utility class");
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            //todo connection.
           // connection = DriverManager.getConnection("jdbc:mysql://ip:133.186.241.167/nhn_academy_45","nhn_academy_45","2pXxZ4OY@]u58-l3");
            connection = DriverManager.getConnection("jdbc:mysql://133.186.241.167:3306/nhn_academy_45","nhn_academy_45","2pXxZ4OY@]u58-l3");

            Statement statement = connection.createStatement();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
        return connection;
    }

}