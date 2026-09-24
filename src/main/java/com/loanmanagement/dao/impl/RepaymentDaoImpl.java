package com.loanmanagement.dao.impl;

import com.loanmanagement.dao.RepaymentDao;
import com.loanmanagement.model.Repayment;
import com.loanmanagement.util.DBConnection;

import java.sql.*;

public class RepaymentDaoImpl implements RepaymentDao {

    @Override
    public void addRepayment(Repayment repayment) {
        String sql = "INSERT INTO repayments (loan_id, amount, payment_date, payment_mode, reference_no, remarks, recorded_by) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, repayment.getLoanId());
            stmt.setDouble(2, repayment.getAmount());
            stmt.setDate(3, java.sql.Date.valueOf(repayment.getPaymentDate())); // Fixed for String
            stmt.setString(4, repayment.getPaymentMode());
            stmt.setString(5, repayment.getReferenceNo());
            stmt.setString(6, repayment.getRemarks());
            stmt.setInt(7, repayment.getRecordedBy());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        repayment.setRepaymentId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Repayment getRepaymentById(int repaymentId) {
        String sql = "SELECT * FROM repayments WHERE repayment_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, repaymentId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Repayment rep = new Repayment();
                rep.setRepaymentId(rs.getInt("repayment_id"));
                rep.setLoanId(rs.getInt("loan_id"));
                rep.setAmount(rs.getDouble("amount"));
                rep.setPaymentDate(String.valueOf(rs.getDate("payment_date"))); // Fixed for String
                rep.setPaymentMode(rs.getString("payment_mode"));
                rep.setReferenceNo(rs.getString("reference_no"));
                rep.setRemarks(rs.getString("remarks"));
                rep.setRecordedBy(rs.getInt("recorded_by"));
                return rep;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateRepayment(Repayment repayment) {
        String sql = "UPDATE repayments SET amount = ?, payment_mode = ?, reference_no = ?, remarks = ? WHERE repayment_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, repayment.getAmount());
            stmt.setString(2, repayment.getPaymentMode());
            stmt.setString(3, repayment.getReferenceNo());
            stmt.setString(4, repayment.getRemarks());
            stmt.setInt(5, repayment.getRepaymentId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRepayment(int repaymentId) {
        String sql = "DELETE FROM repayments WHERE repayment_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, repaymentId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}