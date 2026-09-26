package com.loanmanagement.service.impl;

import com.loanmanagement.dao.LoanApplicationDao;
import com.loanmanagement.dao.LoanDao;
import com.loanmanagement.dao.LoanTypeDao;
import com.loanmanagement.dao.impl.LoanApplicationDaoImpl;
import com.loanmanagement.dao.impl.LoanDaoImpl;
import com.loanmanagement.dao.impl.LoanTypeDaoImpl;
import com.loanmanagement.model.Loan;
import com.loanmanagement.model.LoanApplication;
import com.loanmanagement.model.LoanType;
import com.loanmanagement.service.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoanServiceImpl implements LoanService {

    private static final Logger logger = LoggerFactory.getLogger(LoanServiceImpl.class);

    private LoanDao loanDao;
    private LoanApplicationDao applicationDao;
    private LoanTypeDao loanTypeDao;

    public LoanServiceImpl() {
        this.loanDao = new LoanDaoImpl();
        this.applicationDao = new LoanApplicationDaoImpl();
        this.loanTypeDao = new LoanTypeDaoImpl();
    }

    @Override
    public void addLoan(Loan loan) {
        LoanApplication app = applicationDao.getLoanApplicationById(loan.getApplicationId());

        if (app == null) {
            logger.warn("Validation failed: Associated loan application not found for applicationId={}", loan.getApplicationId());
            return;
        }

        if (!"APPROVED".equals(app.getStatus())) {
            logger.warn("Validation failed: Loans can only be created from APPROVED applications. applicationId={}", loan.getApplicationId());
            return;
        }

        LoanType loanType = loanTypeDao.getLoanTypeById(loan.getLoanTypeId());
        if (loanType != null) {
            loan.setInterestRate(loanType.getInterestRate());

            double principal = loan.getPrincipalAmount();
            double rate = loan.getInterestRate();
            int tenure = loan.getTenureMonths();

            double interest = principal * rate * (tenure / 12.0) / 100.0;
            double totalPayable = principal + interest;

            loan.setTotalPayable(totalPayable);
            loan.setOutstandingAmount(totalPayable);
        }

        loan.setStatus("ACTIVE");
        loanDao.addLoan(loan);
        logger.info("Service: Processed loan disbursement. loanId={}, outstandingAmount={}", loan.getLoanId(), loan.getOutstandingAmount());
    }

    @Override
    public Loan getLoanById(int loanId) {
        if (loanId > 0) {
            return loanDao.getLoanById(loanId);
        }
        logger.warn("Service Validation failed: Invalid loanId={}", loanId);
        return null;
    }

    @Override
    public void updateLoan(Loan loan) {
        if (loan != null && loan.getLoanId() > 0) {
            loanDao.updateLoan(loan);
            logger.info("Service: Processed loan update for loanId={}", loan.getLoanId());
        } else {
            logger.warn("Service Validation failed: Invalid loan data for update.");
        }
    }

    @Override
    public void deleteLoan(int loanId) {
        if (loanId > 0) {
            loanDao.deleteLoan(loanId);
            logger.info("Service: Processed loan deletion for loanId={}", loanId);
        } else {
            logger.warn("Service Validation failed: Invalid loanId={}", loanId);
        }
    }
}