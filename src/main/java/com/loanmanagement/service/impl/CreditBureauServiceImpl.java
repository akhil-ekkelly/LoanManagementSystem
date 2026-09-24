package com.loanmanagement.service.impl;

import com.loanmanagement.dao.CustomerDao;
import com.loanmanagement.dao.impl.CustomerDaoImpl;
import com.loanmanagement.model.Customer;
import com.loanmanagement.service.CreditBureauService;

public class CreditBureauServiceImpl implements CreditBureauService {

    private final CustomerDao customerDao;

    public CreditBureauServiceImpl() {
        this.customerDao = new CustomerDaoImpl();
    }

    @Override
    public int getCreditScore(int customerId) {
        Customer customer = customerDao.getCustomerById(customerId);

        // If the customer doesn't exist or has no PAN, they are "New to credit" (No score)
        if (customer == null || customer.getPanNumber() == null || customer.getPanNumber().trim().isEmpty()) {
            System.out.println("No PAN on file. Customer is new to credit.");
            return 0;
        }

        String pan = customer.getPanNumber();

        // Simulated Bureau Call: Deterministically generate a score between 300 and 900 based on the PAN string
        int score = Math.abs(pan.hashCode()) % 601 + 300;

        System.out.println("Simulated Bureau fetch for PAN " + pan + " returned score: " + score);
        return score;
    }

    @Override
    public boolean isEligible(int customerId) {
        int score = getCreditScore(customerId);

        // As per the BRD, scores 700-900 (Good to Excellent) are designated as "Eligible"
        // Scores below 700 fall into "Refer" (650-699) or "Decline recommended" (300-649)
        boolean eligible = score >= 700;

        System.out.println("Eligibility check for customer ID " + customerId + ": " + (eligible ? "Eligible" : "Not Eligible"));
        return eligible;
    }
}