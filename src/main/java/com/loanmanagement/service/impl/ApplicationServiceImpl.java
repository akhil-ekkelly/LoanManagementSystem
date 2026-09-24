package com.loanmanagement.service.impl;

import com.loanmanagement.dao.CustomerDao;
import com.loanmanagement.dao.LoanApplicationDao;
import com.loanmanagement.dao.LoanTypeDao;
import com.loanmanagement.dao.impl.CustomerDaoImpl;
import com.loanmanagement.dao.impl.LoanApplicationDaoImpl;
import com.loanmanagement.dao.impl.LoanTypeDaoImpl;
import com.loanmanagement.model.Customer;
import com.loanmanagement.model.LoanApplication;
import com.loanmanagement.model.LoanType;
import com.loanmanagement.service.ApplicationService;

public class ApplicationServiceImpl implements ApplicationService {

    private final LoanApplicationDao applicationDao;
    private final CustomerDao customerDao;
    private final LoanTypeDao loanTypeDao;

    public ApplicationServiceImpl() {
        this.applicationDao = new LoanApplicationDaoImpl();
        this.customerDao = new CustomerDaoImpl();
        this.loanTypeDao = new LoanTypeDaoImpl();
    }

    @Override
    public void addApplication(LoanApplication application) {
        // BR-17: Customer KYC must be VERIFIED before applying
        Customer customer = customerDao.getCustomerById(application.getCustomerId());
        if (customer == null || !"VERIFIED".equals(customer.getKycStatus())) {
            System.out.println("Validation failed: Your KYC is pending verification. You cannot apply yet (BR-17).");
            return;
        }

        // BR-1: Loan type must be ACTIVE
        LoanType loanType = loanTypeDao.getLoanTypeById(application.getLoanTypeId());
        if (loanType == null || !"ACTIVE".equals(loanType.getStatus())) {
            System.out.println("Validation failed: This product is not available (BR-1).");
            return;
        }

        // Validate requested amount and tenure against product limits
        if (application.getRequestedAmount() < loanType.getMinAmount() ||
                application.getRequestedAmount() > loanType.getMaxAmount()) {
            System.out.println("Validation failed: Enter an amount between the product limits.");
            return;
        }
        if (application.getTenureMonths() < 1 || application.getTenureMonths() > loanType.getMaxTenureMonths()) {
            System.out.println("Validation failed: Requested tenure exceeds maximum allowed.");
            return;
        }

        // BR-2: Every new application starts with PENDING
        application.setStatus("PENDING");

        // Fixed: Calling the correct DAO method name
        applicationDao.addLoanApplication(application);
        System.out.println("Loan application submitted successfully.");
    }

    @Override
    public LoanApplication getApplicationById(int applicationId) {
        if (applicationId > 0) {
            return applicationDao.getLoanApplicationById(applicationId);
        }
        return null;
    }

    @Override
    public void updateApplication(LoanApplication application) {
        // Fetch the existing application state to enforce status transitions
        LoanApplication existingApp = applicationDao.getLoanApplicationById(application.getApplicationId());
        if (existingApp == null) {
            System.out.println("Validation failed: Application not found.");
            return;
        }

        // BR-11: Only PENDING applications can be approved or rejected
        if (!"PENDING".equals(existingApp.getStatus()) && !existingApp.getStatus().equals(application.getStatus())) {
            System.out.println("Validation failed: Only PENDING applications can be approved or rejected (BR-11).");
            return;
        }

        // Validation Rule: Rejection remarks are mandatory
        if ("REJECTED".equals(application.getStatus()) &&
                (application.getRemarks() == null || application.getRemarks().trim().isEmpty())) {
            System.out.println("Validation failed: Enter the reason for rejecting this application.");
            return;
        }

        // Fixed: Calling the correct DAO method name
        applicationDao.updateLoanApplication(application);
        System.out.println("Application updated successfully.");
    }

    @Override
    public void deleteApplication(int applicationId) {
        if (applicationId > 0) {
            // Fixed: Calling the correct DAO method name
            applicationDao.deleteLoanApplication(applicationId);
            System.out.println("Application deleted.");
        }
    }
}