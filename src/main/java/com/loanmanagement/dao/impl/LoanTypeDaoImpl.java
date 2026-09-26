package com.loanmanagement.dao.impl;

import com.loanmanagement.dao.LoanTypeDao;
import com.loanmanagement.model.LoanType;
import com.loanmanagement.util.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class LoanTypeDaoImpl implements LoanTypeDao {

    private static final Logger logger = LoggerFactory.getLogger(LoanTypeDaoImpl.class);

    private static final String INSERT_LOAN_TYPE_SQL = "INSERT INTO loan_types (name, description, interest_rate, min_amount, max_amount, max_tenure_months, status) VALUES (?, ?, ?, ?, ?, ?, 'ACTIVE')";
    private static final String SELECT_LOAN_TYPE_BY_ID_SQL = "SELECT * FROM loan_types WHERE loan_type_id = ?";
    private static final String UPDATE_LOAN_TYPE_SQL = "UPDATE loan_types SET name = ?, description = ?, interest_rate = ?, min_amount = ?, max_amount = ?, max_tenure_months = ?, status = ? WHERE loan_type_id = ?";
    private static final String DELETE_LOAN_TYPE_SQL = "DELETE FROM loan_types WHERE loan_type_id = ?";

    @Override
    public void addLoanType(LoanType loanType) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_LOAN_TYPE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, loanType.getName());
            stmt.setString(2, loanType.getDescription());
            stmt.setDouble(3, loanType.getInterestRate());
            stmt.setDouble(4, loanType.getMinAmount());
            stmt.setDouble(5, loanType.getMaxAmount());
            stmt.setInt(6, loanType.getMaxTenureMonths());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        loanType.setLoanTypeId(rs.getInt(1));
                        logger.info("Loan type created successfully: loanTypeId={}, name={}", loanType.getLoanTypeId(), loanType.getName());
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Database error while adding loan type: name={}", loanType.getName(), e);
        }
    }

    @Override
    public LoanType getLoanTypeById(int loanTypeId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_LOAN_TYPE_BY_ID_SQL)) {

            stmt.setInt(1, loanTypeId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    LoanType type = new LoanType();
                    type.setLoanTypeId(rs.getInt("loan_type_id"));
                    type.setName(rs.getString("name"));
                    type.setDescription(rs.getString("description"));
                    type.setInterestRate(rs.getDouble("interest_rate"));
                    type.setMinAmount(rs.getDouble("min_amount"));
                    type.setMaxAmount(rs.getDouble("max_amount"));
                    type.setMaxTenureMonths(rs.getInt("max_tenure_months"));
                    type.setStatus(rs.getString("status"));
                    return type;
                }
            }
        } catch (SQLException e) {
            logger.error("Database error while fetching loan type: loanTypeId={}", loanTypeId, e);
        }
        return null;
    }

    @Override
    public void updateLoanType(LoanType loanType) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_LOAN_TYPE_SQL)) {

            stmt.setString(1, loanType.getName());
            stmt.setString(2, loanType.getDescription());
            stmt.setDouble(3, loanType.getInterestRate());
            stmt.setDouble(4, loanType.getMinAmount());
            stmt.setDouble(5, loanType.getMaxAmount());
            stmt.setInt(6, loanType.getMaxTenureMonths());
            stmt.setString(7, loanType.getStatus());
            stmt.setInt(8, loanType.getLoanTypeId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Loan type updated successfully: loanTypeId={}", loanType.getLoanTypeId());
            } else {
                logger.warn("Update failed. Loan type not found: loanTypeId={}", loanType.getLoanTypeId());
            }
        } catch (SQLException e) {
            logger.error("Database error while updating loan type: loanTypeId={}", loanType.getLoanTypeId(), e);
        }
    }

    @Override
    public void deleteLoanType(int loanTypeId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_LOAN_TYPE_SQL)) {

            stmt.setInt(1, loanTypeId);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Loan type deleted successfully: loanTypeId={}", loanTypeId);
            } else {
                logger.warn("Deletion failed. Loan type not found: loanTypeId={}", loanTypeId);
            }
        } catch (SQLException e) {
            logger.error("Database error while deleting loan type: loanTypeId={}", loanTypeId, e);
        }
    }
}