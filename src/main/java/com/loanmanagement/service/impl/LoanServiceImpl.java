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

public class LoanServiceImpl implements LoanService {

    private final LoanDao loanDao;
    private final LoanApplicationDao applicationDao;
    private final LoanTypeDao loanTypeDao;

    public LoanServiceImpl() {
        this.loanDao = new LoanDaoImpl();
        this.applicationDao = new LoanApplicationDaoImpl();
        this.loanTypeDao = new LoanTypeDaoImpl();
    }

    @Override
    public void addLoan(Loan loan) {
        // Fetch the associated application
        LoanApplication app = applicationDao.getLoanApplicationById(loan.getApplicationId());

        if (app == null) {
            System.out.println("Validation failed: Associated loan application not found.");
            return;
        }

        // BR-4 & BR-7: A loan can only be created from an APPROVED application
        if (!"APPROVED".equals(app.getStatus())) {
            System.out.println("Validation failed: Loans can only be created from APPROVED applications (BR-4, BR-7).");
            return;
        }

        // Fetch the loan type to freeze the interest rate (BR-16)
        LoanType loanType = loanTypeDao.getLoanTypeById(loan.getLoanTypeId());
        if (loanType != null) {
            // Copy the rate from the master product so it never changes for this specific loan
            loan.setInterestRate(loanType.getInterestRate());

            // Calculate flat simple interest: principal * rate * (tenure_months / 12) / 100
            double principal = loan.getPrincipalAmount();
            double rate = loan.getInterestRate();
            int tenure = loan.getTenureMonths();

            double interest = principal * rate * (tenure / 12.0) / 100.0;
            double totalPayable = principal + interest;

            // Set the calculated financial totals
            loan.setTotalPayable(totalPayable);

            // At creation, the outstanding amount equals the total payable
            loan.setOutstandingAmount(totalPayable);
        }

        // New loans always start as ACTIVE
        loan.setStatus("ACTIVE");

        // Save the loan
        // (Note: BR-12 is inherently enforced here because the database schema has a UNIQUE constraint on application_id)
        loanDao.addLoan(loan);
        System.out.println("Loan created successfully. Outstanding amount: " + loan.getOutstandingAmount());
    }

    @Override
    public Loan getLoanById(int loanId) {
        if (loanId > 0) {
            return loanDao.getLoanById(loanId);
        }
        return null;
    }

    @Override
    public void updateLoan(Loan loan) {
        if (loan != null && loan.getLoanId() > 0) {
            loanDao.updateLoan(loan);
            System.out.println("Loan updated successfully.");
        }
    }

    @Override
    public void deleteLoan(int loanId) {
        if (loanId > 0) {
            loanDao.deleteLoan(loanId);
            System.out.println("Loan deleted.");
        }
    }
}