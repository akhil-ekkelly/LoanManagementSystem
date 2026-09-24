package com.loanmanagement.dao.impl;

import com.loanmanagement.dao.LoanTypeDao;
import com.loanmanagement.model.LoanType;
import com.loanmanagement.util.DBConnection;

import java.sql.*;

public class LoanTypeDaoImpl implements LoanTypeDao {

    @Override
    public void addLoanType(LoanType loanType) {
        String sql = "INSERT INTO loan_types (name, description, interest_rate, min_amount, " +
                "max_amount, max_tenure_months, status) VALUES (?, ?, ?, ?, ?, ?, 'ACTIVE')";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

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
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public LoanType getLoanTypeById(int loanTypeId) {
        String sql = "SELECT * FROM loan_types WHERE loan_type_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, loanTypeId);
            ResultSet rs = stmt.executeQuery();

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateLoanType(LoanType loanType) {
        String sql = "UPDATE loan_types SET name=?, description=?, interest_rate=?, min_amount=?, " +
                "max_amount=?, max_tenure_months=? WHERE loan_type_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, loanType.getName());
            stmt.setString(2, loanType.getDescription());
            stmt.setDouble(3, loanType.getInterestRate());
            stmt.setDouble(4, loanType.getMinAmount());
            stmt.setDouble(5, loanType.getMaxAmount());
            stmt.setInt(6, loanType.getMaxTenureMonths());
            stmt.setInt(7, loanType.getLoanTypeId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteLoanType(int loanTypeId) {
        // BR-15: Soft delete (deactivate) instead of dropping the record
        String sql = "UPDATE loan_types SET status = 'INACTIVE' WHERE loan_type_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, loanTypeId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}