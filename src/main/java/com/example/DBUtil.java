package com.example;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {

    private static final String URL =
        "jdbc:mysql://localhost:3306/healthcare";

    private static final String USER = "root";
    private static final String PASSWORD = "YOUR_DB_PASSWORD";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
