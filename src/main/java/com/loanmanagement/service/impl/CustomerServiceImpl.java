package com.loanmanagement.service.impl;

import com.loanmanagement.dao.CustomerDao;
import com.loanmanagement.dao.impl.CustomerDaoImpl;
import com.loanmanagement.model.Customer;
import com.loanmanagement.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerServiceImpl implements CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
    private CustomerDao customerDao;

    public CustomerServiceImpl() {
        this.customerDao = new CustomerDaoImpl();
    }

    @Override
    public void addCustomer(Customer customer) {
        if (customer != null && customer.getUserId() > 0) {
            customerDao.addCustomer(customer);
            logger.info("Service: Processed customer profile creation for userId={}", customer.getUserId());
        } else {
            logger.warn("Service Validation failed: Invalid customer data.");
        }
    }

    @Override
    public Customer getCustomerById(int customerId) {
        if (customerId > 0) {
            return customerDao.getCustomerById(customerId);
        }
        logger.warn("Service Validation failed: Invalid customerId={}", customerId);
        return null;
    }

    @Override
    public void updateCustomer(Customer customer) {
        if (customer != null && customer.getCustomerId() > 0) {
            customerDao.updateCustomer(customer);
            logger.info("Service: Processed customer profile update for customerId={}", customer.getCustomerId());
        } else {
            logger.warn("Service Validation failed: Invalid customer data for update.");
        }
    }

    @Override
    public void deleteCustomer(int customerId) {
        if (customerId > 0) {
            customerDao.deleteCustomer(customerId);
            logger.info("Service: Processed customer profile deletion for customerId={}", customerId);
        } else {
            logger.warn("Service Validation failed: Invalid customerId={}", customerId);
        }
    }
}