package com.loanmanagement.dao.impl;

import com.loanmanagement.dao.CustomerDao;
import com.loanmanagement.model.Customer;
import com.loanmanagement.util.DBConnection;

import java.sql.*;

public class CustomerDaoImpl implements CustomerDao {

    @Override
    public void addCustomer(Customer customer) {
        // The database schema automatically defaults kyc_status to PENDING, existing_emi to 0, and status to ACTIVE
        String sql = "INSERT INTO customers (user_id, full_name, email, phone, dob, address, " +
                "monthly_income, pan_number, aadhaar_last4, employment_type) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, customer.getUserId());
            stmt.setString(2, customer.getFullName());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getPhone());
            stmt.setString(5, customer.getDob()); // Assuming dob is a String in yyyy-MM-dd format
            stmt.setString(6, customer.getAddress());
            stmt.setDouble(7, customer.getMonthlyIncome());
            stmt.setString(8, customer.getPanNumber());
            stmt.setString(9, customer.getAadhaarLast4());
            stmt.setString(10, customer.getEmploymentType());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        customer.setCustomerId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Customer getCustomerById(int customerId) {
        String sql = "SELECT * FROM customers WHERE customer_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToCustomer(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateCustomer(Customer customer) {
        String sql = "UPDATE customers SET full_name=?, email=?, phone=?, dob=?, address=?, " +
                "monthly_income=?, employment_type=? WHERE customer_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getFullName());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getPhone());
            stmt.setString(4, customer.getDob());
            stmt.setString(5, customer.getAddress());
            stmt.setDouble(6, customer.getMonthlyIncome());
            stmt.setString(7, customer.getEmploymentType());
            stmt.setInt(8, customer.getCustomerId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCustomer(int customerId) {
        // Soft delete: Deactivate the customer to preserve loan/application history
        String sql = "UPDATE customers SET status = 'INACTIVE' WHERE customer_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Helper method to keep getCustomerById clean
    private Customer mapResultSetToCustomer(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setCustomerId(rs.getInt("customer_id"));
        customer.setUserId(rs.getInt("user_id"));
        customer.setFullName(rs.getString("full_name"));
        customer.setEmail(rs.getString("email"));
        customer.setPhone(rs.getString("phone"));

        Date dob = rs.getDate("dob");
        if (dob != null) customer.setDob(dob.toString());

        customer.setAddress(rs.getString("address"));
        customer.setMonthlyIncome(rs.getDouble("monthly_income"));
        customer.setPanNumber(rs.getString("pan_number"));
        customer.setAadhaarLast4(rs.getString("aadhaar_last4"));
        customer.setEmploymentType(rs.getString("employment_type"));
        customer.setKycStatus(rs.getString("kyc_status"));
        customer.setKycRemarks(rs.getString("kyc_remarks"));
        customer.setKycVerifiedBy(rs.getInt("kyc_verified_by"));

        Timestamp verifiedAt = rs.getTimestamp("kyc_verified_at");
        if (verifiedAt != null) customer.setKycVerifiedAt(verifiedAt.toString());

        customer.setCreditScore(rs.getInt("credit_score"));
        customer.setExistingEmi(rs.getDouble("existing_emi"));
        customer.setStatus(rs.getString("status"));

        return customer;
    }
}