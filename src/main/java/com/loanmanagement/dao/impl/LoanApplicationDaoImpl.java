package com.loanmanagement.dao.impl;

import com.loanmanagement.dao.LoanApplicationDao;
import com.loanmanagement.model.LoanApplication;
import com.loanmanagement.util.DBConnection;

import java.sql.*;

public class LoanApplicationDaoImpl implements LoanApplicationDao {

    @Override
    public void addLoanApplication(LoanApplication app) {
        String sql = "INSERT INTO loan_applications (customer_id, loan_type_id, requested_amount, " +
                "tenure_months, purpose, status) VALUES (?, ?, ?, ?, ?, 'PENDING')";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, app.getCustomerId());
            stmt.setInt(2, app.getLoanTypeId());
            stmt.setDouble(3, app.getRequestedAmount());
            stmt.setInt(4, app.getTenureMonths());
            stmt.setString(5, app.getPurpose());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        app.setApplicationId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public LoanApplication getLoanApplicationById(int applicationId) {
        String sql = "SELECT * FROM loan_applications WHERE application_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, applicationId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                LoanApplication app = new LoanApplication();
                app.setApplicationId(rs.getInt("application_id"));
                app.setCustomerId(rs.getInt("customer_id"));
                app.setLoanTypeId(rs.getInt("loan_type_id"));
                app.setRequestedAmount(rs.getDouble("requested_amount"));
                app.setTenureMonths(rs.getInt("tenure_months"));
                app.setPurpose(rs.getString("purpose"));
                app.setStatus(rs.getString("status"));
                app.setRemarks(rs.getString("remarks"));
                app.setReviewedBy(rs.getInt("reviewed_by"));
                return app;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateLoanApplication(LoanApplication app) {
        String sql = "UPDATE loan_applications SET status = ?, remarks = ?, reviewed_by = ?, reviewed_at = CURRENT_TIMESTAMP WHERE application_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, app.getStatus());
            stmt.setString(2, app.getRemarks());
            stmt.setInt(3, app.getReviewedBy());
            stmt.setInt(4, app.getApplicationId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteLoanApplication(int applicationId) {
        String sql = "DELETE FROM loan_applications WHERE application_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, applicationId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}