package com.loanmanagement.dao.impl;

import com.loanmanagement.dao.LoanApplicationDao;
import com.loanmanagement.model.LoanApplication;
import com.loanmanagement.util.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class LoanApplicationDaoImpl implements LoanApplicationDao {

    private static final Logger logger = LoggerFactory.getLogger(LoanApplicationDaoImpl.class);

    private static final String INSERT_APP_SQL = "INSERT INTO loan_applications (customer_id, loan_type_id, requested_amount, tenure_months, purpose, status) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_APP_BY_ID_SQL = "SELECT * FROM loan_applications WHERE application_id = ?";
    private static final String UPDATE_APP_SQL = "UPDATE loan_applications SET requested_amount = ?, tenure_months = ?, purpose = ?, status = ?, remarks = ? WHERE application_id = ?";
    private static final String DELETE_APP_SQL = "DELETE FROM loan_applications WHERE application_id = ?";

    @Override
    public void addLoanApplication(LoanApplication app) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_APP_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, app.getCustomerId());
            stmt.setInt(2, app.getLoanTypeId());
            stmt.setDouble(3, app.getRequestedAmount());
            stmt.setInt(4, app.getTenureMonths());
            stmt.setString(5, app.getPurpose());
            stmt.setString(6, app.getStatus());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        app.setApplicationId(rs.getInt(1));
                        logger.info("Loan application submitted successfully: applicationId={}, customerId={}", app.getApplicationId(), app.getCustomerId());
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Database error while adding application: customerId={}", app.getCustomerId(), e);
        }
    }

    @Override
    public LoanApplication getLoanApplicationById(int applicationId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_APP_BY_ID_SQL)) {

            stmt.setInt(1, applicationId);
            try (ResultSet rs = stmt.executeQuery()) {
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
                    return app;
                }
            }
        } catch (SQLException e) {
            logger.error("Database error while fetching application: applicationId={}", applicationId, e);
        }
        return null;
    }

    @Override
    public void updateLoanApplication(LoanApplication app) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_APP_SQL)) {

            stmt.setDouble(1, app.getRequestedAmount());
            stmt.setInt(2, app.getTenureMonths());
            stmt.setString(3, app.getPurpose());
            stmt.setString(4, app.getStatus());
            stmt.setString(5, app.getRemarks());
            stmt.setInt(6, app.getApplicationId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Loan application updated successfully: applicationId={}, newStatus={}", app.getApplicationId(), app.getStatus());
            } else {
                logger.warn("Update failed. Application not found: applicationId={}", app.getApplicationId());
            }
        } catch (SQLException e) {
            logger.error("Database error while updating application: applicationId={}", app.getApplicationId(), e);
        }
    }

    @Override
    public void deleteLoanApplication(int applicationId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_APP_SQL)) {

            stmt.setInt(1, applicationId);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Loan application deleted successfully: applicationId={}", applicationId);
            } else {
                logger.warn("Deletion failed. Application not found: applicationId={}", applicationId);
            }
        } catch (SQLException e) {
            logger.error("Database error while deleting application: applicationId={}", applicationId, e);
        }
    }
}