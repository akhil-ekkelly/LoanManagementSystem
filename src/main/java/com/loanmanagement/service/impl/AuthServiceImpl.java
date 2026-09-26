package com.loanmanagement.service.impl;

import com.loanmanagement.service.AuthService;
import com.loanmanagement.util.DBConnection;
import com.loanmanagement.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    private static final String LOGIN_SQL = "SELECT role, status FROM users WHERE username = ? AND password_hash = ? AND status = 'ACTIVE'";

    @Override
    public boolean login(String username, String password) {
        if (username == null || password == null) {
            logger.warn("Login validation failed: Username and Password cannot be empty.");
            return false;
        }

        String hashedPassword = PasswordUtil.hashPassword(password);

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(LOGIN_SQL)) {

            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                logger.info("Login successful: username={}, role={}", username, role);
                return true;
            } else {
                logger.warn("Login failed: Invalid credentials or inactive account for username={}", username);
                return false;
            }
        } catch (SQLException e) {
            logger.error("Database error during login for username={}", username, e);
        }

        return false;
    }

    @Override
    public void logout(int userId) {
        logger.info("User ID {} logged out successfully.", userId);
    }
}