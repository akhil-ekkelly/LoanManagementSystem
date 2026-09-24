package com.loanmanagement.dao.impl;

import com.loanmanagement.dao.LoanDao;
import com.loanmanagement.model.Loan;
import com.loanmanagement.util.DBConnection;

import java.sql.*;

public class LoanDaoImpl implements LoanDao {

    @Override
    public void addLoan(Loan loan) {
        String sql = "INSERT INTO loans (application_id, customer_id, loan_type_id, principal_amount, " +
                "interest_rate, tenure_months, total_payable, outstanding_amount, start_date, created_by) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, loan.getApplicationId());
            stmt.setInt(2, loan.getCustomerId());
            stmt.setInt(3, loan.getLoanTypeId());
            stmt.setDouble(4, loan.getPrincipalAmount());
            stmt.setDouble(5, loan.getInterestRate()); // BR-16: Frozen rate
            stmt.setInt(6, loan.getTenureMonths());
            stmt.setDouble(7, loan.getTotalPayable());
            stmt.setDouble(8, loan.getOutstandingAmount());
            stmt.setDate(9, java.sql.Date.valueOf(loan.getStartDate())); // Fixed for String
            stmt.setInt(10, loan.getCreatedBy());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        loan.setLoanId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Loan getLoanById(int loanId) {
        String sql = "SELECT * FROM loans WHERE loan_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, loanId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Loan loan = new Loan();
                loan.setLoanId(rs.getInt("loan_id"));
                loan.setApplicationId(rs.getInt("application_id"));
                loan.setCustomerId(rs.getInt("customer_id"));
                loan.setLoanTypeId(rs.getInt("loan_type_id"));
                loan.setPrincipalAmount(rs.getDouble("principal_amount"));
                loan.setInterestRate(rs.getDouble("interest_rate"));
                loan.setTenureMonths(rs.getInt("tenure_months"));
                loan.setTotalPayable(rs.getDouble("total_payable"));
                loan.setOutstandingAmount(rs.getDouble("outstanding_amount"));
                loan.setStartDate(String.valueOf(rs.getDate("start_date"))); // Fixed for String
                loan.setStatus(rs.getString("status"));
                loan.setCreatedBy(rs.getInt("created_by"));
                return loan;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateLoan(Loan loan) {
        String sql = "UPDATE loans SET outstanding_amount = ?, status = ?, start_date = ? WHERE loan_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, loan.getOutstandingAmount());
            stmt.setString(2, loan.getStatus());
            stmt.setDate(3, java.sql.Date.valueOf(loan.getStartDate())); // Fixed for String
            stmt.setInt(4, loan.getLoanId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteLoan(int loanId) {
        String sql = "DELETE FROM loans WHERE loan_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, loanId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}