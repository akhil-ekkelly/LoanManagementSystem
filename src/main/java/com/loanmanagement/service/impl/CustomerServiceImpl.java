package com.loanmanagement.service.impl;

import com.loanmanagement.dao.CustomerDao;
import com.loanmanagement.dao.impl.CustomerDaoImpl;
import com.loanmanagement.model.Customer;
import com.loanmanagement.service.CustomerService;

public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;

    public CustomerServiceImpl() {
        this.customerDao = new CustomerDaoImpl();
    }

    @Override
    public void addCustomer(Customer customer) {
        if (validateCustomerFormat(customer)) {
            customerDao.addCustomer(customer);
        } else {
            System.out.println("Validation failed: Cannot add customer due to invalid email or phone format.");
        }
    }

    @Override
    public Customer getCustomerById(int customerId) {
        if (customerId > 0) {
            return customerDao.getCustomerById(customerId);
        }
        return null;
    }

    @Override
    public void updateCustomer(Customer customer) {
        if (validateCustomerFormat(customer)) {
            customerDao.updateCustomer(customer);
        } else {
            System.out.println("Validation failed: Cannot update customer due to invalid email or phone format.");
        }
    }

    @Override
    public void deleteCustomer(int customerId) {
        if (customerId > 0) {
            customerDao.deleteCustomer(customerId);
        }
    }

    // Enforces BR-10: Email and phone number must pass format validation
    private boolean validateCustomerFormat(Customer customer) {
        // Standard email format check
        if (customer.getEmail() == null || !customer.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            System.out.println("Invalid email format.");
            return false;
        }

        // Phone must be exactly 10 digits starting with 6 to 9
        if (customer.getPhone() == null || !customer.getPhone().matches("^[6-9]\\d{9}$")) {
            System.out.println("Invalid phone format. Enter a valid 10-digit mobile number.");
            return false;
        }

        return true;
    }
}