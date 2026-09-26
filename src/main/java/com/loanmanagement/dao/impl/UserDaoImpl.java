package com.loanmanagement.dao.impl;

import com.loanmanagement.dao.UserDao;
import com.loanmanagement.model.User;
import com.loanmanagement.util.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class UserDaoImpl implements UserDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    private static final String INSERT_USER_SQL = "INSERT INTO users (username, password_hash, role, status) VALUES (?, ?, ?, 'ACTIVE')";
    private static final String SELECT_USER_BY_ID_SQL = "SELECT * FROM users WHERE user_id = ?";
    private static final String UPDATE_USER_SQL = "UPDATE users SET username = ?, password_hash = ?, role = ?, status = ? WHERE user_id = ?";
    private static final String DELETE_USER_SQL = "DELETE FROM users WHERE user_id = ?";

    @Override
    public void addUser(User user) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_USER_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        user.setUserId(rs.getInt(1));
                        logger.info("User created successfully: userId={}, username={}", user.getUserId(), user.getUsername());
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Database error while adding user: username={}", user.getUsername(), e);
        }
    }

    @Override
    public User getUserById(int userId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_USER_BY_ID_SQL)) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password_hash"));
                    user.setRole(rs.getString("role"));
                    user.setStatus(rs.getString("status"));
                    return user;
                }
            }
        } catch (SQLException e) {
            logger.error("Database error while fetching user: userId={}", userId, e);
        }
        return null;
    }

    @Override
    public void updateUser(User user) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_USER_SQL)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            stmt.setString(4, user.getStatus());
            stmt.setInt(5, user.getUserId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("User updated successfully: userId={}", user.getUserId());
            } else {
                logger.warn("Update failed. User not found: userId={}", user.getUserId());
            }
        } catch (SQLException e) {
            logger.error("Database error while updating user: userId={}", user.getUserId(), e);
        }
    }

    @Override
    public void deleteUser(int userId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_USER_SQL)) {

            stmt.setInt(1, userId);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("User deleted successfully: userId={}", userId);
            } else {
                logger.warn("Deletion failed. User not found: userId={}", userId);
            }
        } catch (SQLException e) {
            logger.error("Database error while deleting user: userId={}", userId, e);
        }
    }
}