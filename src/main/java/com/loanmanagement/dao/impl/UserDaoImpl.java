package com.loanmanagement.dao.impl;

import com.loanmanagement.dao.UserDao;
import com.loanmanagement.model.User;
import com.loanmanagement.util.DBConnection;

import java.sql.*;

public class UserDaoImpl implements UserDao {

    @Override
    public void addUser(User user) {
        String sql = "INSERT INTO users (username, password_hash, role, status) VALUES (?, ?, ?, 'ACTIVE')";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        user.setUserId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password_hash"));
                user.setRole(rs.getString("role"));
                user.setStatus(rs.getString("status"));
                user.setCreatedAt(String.valueOf(rs.getTimestamp("created_at"))); // If your model has this field
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateUser(User user) {
        String sql = "UPDATE users SET username = ?, password_hash = ?, role = ?, status = ? WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            stmt.setString(4, user.getStatus());
            stmt.setInt(5, user.getUserId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(int userId) {
        // Soft delete: Deactivate the user instead of dropping the record
        String sql = "UPDATE users SET status = 'INACTIVE' WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}