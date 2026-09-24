package com.loanmanagement.service.impl;

import com.loanmanagement.service.AuthService;
import com.loanmanagement.util.DBConnection;
import com.loanmanagement.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthServiceImpl implements AuthService {

    @Override
    public boolean login(String username, String password) {
        if (username == null || password == null) {
            System.out.println("Validation failed: Username and Password cannot be empty.");
            return false;
        }

        // Hash the input password to match what is stored in the database
        String hashedPassword = PasswordUtil.hashPassword(password);

        // Query enforces BR-14: User must exist, passwords must match, and status must be ACTIVE
        String sql = "SELECT role, status FROM users WHERE username = ? AND password_hash = ? AND status = 'ACTIVE'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);

            ResultSet rs = stmt.executeQuery();

            // If rs.next() is true, a match was found and the user is allowed to log in
            if (rs.next()) {
                String role = rs.getString("role");
                System.out.println("Login successful. Welcome, " + role);
                return true;
            } else {
                System.out.println("Login failed: Invalid credentials or inactive account.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void logout(int userId) {
        // Since we are using raw JDBC without a web session framework yet,
        // the actual "logout" state is usually managed in your Controller layer
        // (e.g., by setting a currentUser variable to null).
        // This method serves as a hook if you want to add audit logging later.
        System.out.println("User ID " + userId + " logged out successfully.");
    }
}