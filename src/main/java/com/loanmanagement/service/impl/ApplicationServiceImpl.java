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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationServiceImpl implements ApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationServiceImpl.class);

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
        Customer customer = customerDao.getCustomerById(application.getCustomerId());
        if (customer == null || !"VERIFIED".equals(customer.getKycStatus())) {
            logger.warn("Validation failed: KYC is pending verification for customerId={}", application.getCustomerId());
            return;
        }

        LoanType loanType = loanTypeDao.getLoanTypeById(application.getLoanTypeId());
        if (loanType == null || !"ACTIVE".equals(loanType.getStatus())) {
            logger.warn("Validation failed: Loan product is not active for loanTypeId={}", application.getLoanTypeId());
            return;
        }

        if (application.getRequestedAmount() < loanType.getMinAmount() ||
                application.getRequestedAmount() > loanType.getMaxAmount()) {
            logger.warn("Validation failed: Amount {} is outside product limits for customerId={}", application.getRequestedAmount(), application.getCustomerId());
            return;
        }

        if (application.getTenureMonths() < 1 || application.getTenureMonths() > loanType.getMaxTenureMonths()) {
            logger.warn("Validation failed: Requested tenure {} exceeds maximum for customerId={}", application.getTenureMonths(), application.getCustomerId());
            return;
        }

        application.setStatus("PENDING");
        applicationDao.addLoanApplication(application);
        logger.info("Service: Processed loan application submission for customerId={}", application.getCustomerId());
    }

    @Override
    public LoanApplication getApplicationById(int applicationId) {
        if (applicationId > 0) {
            return applicationDao.getLoanApplicationById(applicationId);
        }
        logger.warn("Service Validation failed: Invalid applicationId={}", applicationId);
        return null;
    }

    @Override
    public void updateApplication(LoanApplication application) {
        LoanApplication existingApp = applicationDao.getLoanApplicationById(application.getApplicationId());
        if (existingApp == null) {
            logger.warn("Validation failed: Application not found for applicationId={}", application.getApplicationId());
            return;
        }

        if (!"PENDING".equals(existingApp.getStatus()) && !existingApp.getStatus().equals(application.getStatus())) {
            logger.warn("Validation failed: Only PENDING applications can be changed. applicationId={}", application.getApplicationId());
            return;
        }

        if ("REJECTED".equals(application.getStatus()) &&
                (application.getRemarks() == null || application.getRemarks().trim().isEmpty())) {
            logger.warn("Validation failed: Rejection remarks are mandatory for applicationId={}", application.getApplicationId());
            return;
        }

        applicationDao.updateLoanApplication(application);
        logger.info("Service: Processed application status update to {} for applicationId={}", application.getStatus(), application.getApplicationId());
    }

    @Override
    public void deleteApplication(int applicationId) {
        if (applicationId > 0) {
            applicationDao.deleteLoanApplication(applicationId);
            logger.info("Service: Processed application deletion for applicationId={}", applicationId);
        } else {
            logger.warn("Service Validation failed: Invalid applicationId={}", applicationId);
        }
    }
}