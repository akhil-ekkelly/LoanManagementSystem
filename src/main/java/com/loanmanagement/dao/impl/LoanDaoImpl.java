package com.loanmanagement.dao.impl;

import com.loanmanagement.dao.LoanDao;
import com.loanmanagement.model.Loan;
import com.loanmanagement.util.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class LoanDaoImpl implements LoanDao {

    private static final Logger logger = LoggerFactory.getLogger(LoanDaoImpl.class);

    private static final String INSERT_LOAN_SQL = "INSERT INTO loans (application_id, customer_id, loan_type_id, principal_amount, interest_rate, tenure_months, start_date, status, total_payable, outstanding_amount, created_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_LOAN_BY_ID_SQL = "SELECT * FROM loans WHERE loan_id = ?";
    private static final String UPDATE_LOAN_SQL = "UPDATE loans SET status = ?, outstanding_amount = ? WHERE loan_id = ?";
    private static final String DELETE_LOAN_SQL = "DELETE FROM loans WHERE loan_id = ?";

    @Override
    public void addLoan(Loan loan) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_LOAN_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, loan.getApplicationId());
            stmt.setInt(2, loan.getCustomerId());
            stmt.setInt(3, loan.getLoanTypeId());
            stmt.setDouble(4, loan.getPrincipalAmount());
            stmt.setDouble(5, loan.getInterestRate());
            stmt.setInt(6, loan.getTenureMonths());
            stmt.setString(7, loan.getStartDate());
            stmt.setString(8, loan.getStatus());
            stmt.setDouble(9, loan.getTotalPayable());
            stmt.setDouble(10, loan.getOutstandingAmount());
            stmt.setInt(11, loan.getCreatedBy());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        loan.setLoanId(rs.getInt(1));
                        logger.info("Loan disbursed successfully: loanId={}, customerId={}", loan.getLoanId(), loan.getCustomerId());
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Database error while adding loan: applicationId={}", loan.getApplicationId(), e);
        }
    }

    @Override
    public Loan getLoanById(int loanId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_LOAN_BY_ID_SQL)) {

            stmt.setInt(1, loanId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Loan loan = new Loan();
                    loan.setLoanId(rs.getInt("loan_id"));
                    loan.setApplicationId(rs.getInt("application_id"));
                    loan.setCustomerId(rs.getInt("customer_id"));
                    loan.setLoanTypeId(rs.getInt("loan_type_id"));
                    loan.setPrincipalAmount(rs.getDouble("principal_amount"));
                    loan.setInterestRate(rs.getDouble("interest_rate"));
                    loan.setTenureMonths(rs.getInt("tenure_months"));
                    loan.setStartDate(rs.getString("start_date"));
                    loan.setStatus(rs.getString("status"));
                    loan.setTotalPayable(rs.getDouble("total_payable"));
                    loan.setOutstandingAmount(rs.getDouble("outstanding_amount"));
                    loan.setCreatedBy(rs.getInt("created_by"));
                    return loan;
                }
            }
        } catch (SQLException e) {
            logger.error("Database error while fetching loan: loanId={}", loanId, e);
        }
        return null;
    }

    @Override
    public void updateLoan(Loan loan) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_LOAN_SQL)) {

            stmt.setString(1, loan.getStatus());
            stmt.setDouble(2, loan.getOutstandingAmount());
            stmt.setInt(3, loan.getLoanId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Loan updated successfully: loanId={}, outstandingAmount={}", loan.getLoanId(), loan.getOutstandingAmount());
            } else {
                logger.warn("Update failed. Loan not found: loanId={}", loan.getLoanId());
            }
        } catch (SQLException e) {
            logger.error("Database error while updating loan: loanId={}", loan.getLoanId(), e);
        }
    }

    @Override
    public void deleteLoan(int loanId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_LOAN_SQL)) {

            stmt.setInt(1, loanId);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Loan deleted successfully: loanId={}", loanId);
            } else {
                logger.warn("Deletion failed. Loan not found: loanId={}", loanId);
            }
        } catch (SQLException e) {
            logger.error("Database error while deleting loan: loanId={}", loanId, e);
        }
    }
}