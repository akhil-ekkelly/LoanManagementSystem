package com.loanmanagement.dao.impl;

import com.loanmanagement.dao.CustomerDao;
import com.loanmanagement.model.Customer;
import com.loanmanagement.util.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class CustomerDaoImpl implements CustomerDao {

    private static final Logger logger = LoggerFactory.getLogger(CustomerDaoImpl.class);

    private static final String INSERT_CUSTOMER_SQL = "INSERT INTO customers (user_id, full_name, email, phone, dob, address, pan_number, aadhaar_last4, employment_type, monthly_income, kyc_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'PENDING')";
    private static final String SELECT_CUSTOMER_BY_ID_SQL = "SELECT * FROM customers WHERE customer_id = ?";
    private static final String UPDATE_CUSTOMER_SQL = "UPDATE customers SET full_name = ?, email = ?, phone = ?, dob = ?, address = ?, pan_number = ?, aadhaar_last4 = ?, employment_type = ?, monthly_income = ?, kyc_status = ? WHERE customer_id = ?";
    private static final String DELETE_CUSTOMER_SQL = "DELETE FROM customers WHERE customer_id = ?";

    @Override
    public void addCustomer(Customer customer) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_CUSTOMER_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, customer.getUserId());
            stmt.setString(2, customer.getFullName());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getPhone());
            stmt.setString(5, customer.getDob());
            stmt.setString(6, customer.getAddress());
            stmt.setString(7, customer.getPanNumber());
            stmt.setString(8, customer.getAadhaarLast4());
            stmt.setString(9, customer.getEmploymentType());
            stmt.setDouble(10, customer.getMonthlyIncome());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        customer.setCustomerId(rs.getInt(1));
                        logger.info("Customer profile created successfully: customerId={}, userId={}", customer.getCustomerId(), customer.getUserId());
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Database error while adding customer: userId={}", customer.getUserId(), e);
        }
    }

    @Override
    public Customer getCustomerById(int customerId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_CUSTOMER_BY_ID_SQL)) {

            stmt.setInt(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Customer customer = new Customer();
                    customer.setCustomerId(rs.getInt("customer_id"));
                    customer.setUserId(rs.getInt("user_id"));
                    customer.setFullName(rs.getString("full_name"));
                    customer.setEmail(rs.getString("email"));
                    customer.setPhone(rs.getString("phone"));
                    customer.setDob(rs.getString("dob"));
                    customer.setAddress(rs.getString("address"));
                    customer.setPanNumber(rs.getString("pan_number"));
                    customer.setAadhaarLast4(rs.getString("aadhaar_last4"));
                    customer.setEmploymentType(rs.getString("employment_type"));
                    customer.setMonthlyIncome(rs.getDouble("monthly_income"));
                    customer.setKycStatus(rs.getString("kyc_status"));
                    return customer;
                }
            }
        } catch (SQLException e) {
            logger.error("Database error while fetching customer: customerId={}", customerId, e);
        }
        return null;
    }

    @Override
    public void updateCustomer(Customer customer) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_CUSTOMER_SQL)) {

            stmt.setString(1, customer.getFullName());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getPhone());
            stmt.setString(4, customer.getDob());
            stmt.setString(5, customer.getAddress());
            stmt.setString(6, customer.getPanNumber());
            stmt.setString(7, customer.getAadhaarLast4());
            stmt.setString(8, customer.getEmploymentType());
            stmt.setDouble(9, customer.getMonthlyIncome());
            stmt.setString(10, customer.getKycStatus());
            stmt.setInt(11, customer.getCustomerId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Customer profile updated successfully: customerId={}", customer.getCustomerId());
            } else {
                logger.warn("Update failed. Customer not found: customerId={}", customer.getCustomerId());
            }
        } catch (SQLException e) {
            logger.error("Database error while updating customer: customerId={}", customer.getCustomerId(), e);
        }
    }

    @Override
    public void deleteCustomer(int customerId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_CUSTOMER_SQL)) {

            stmt.setInt(1, customerId);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Customer deleted successfully: customerId={}", customerId);
            } else {
                logger.warn("Deletion failed. Customer not found: customerId={}", customerId);
            }
        } catch (SQLException e) {
            logger.error("Database error while deleting customer: customerId={}", customerId, e);
        }
    }
}