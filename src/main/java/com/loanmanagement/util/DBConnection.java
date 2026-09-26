package com.loanmanagement.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/lms_db";

    public static Connection getConnection() throws SQLException {
        // Fetch credentials from the system environment
        String user = System.getenv("LMS_DB_USER");
        String password = System.getenv("LMS_DB_PASSWORD");

        // Fail fast if the variables are missing
        if (user == null || password == null) {
            throw new SQLException("Database credentials missing. Set LMS_DB_USER and LMS_DB_PASSWORD environment variables.");
        }

        return DriverManager.getConnection(URL, user, password);
    }
}