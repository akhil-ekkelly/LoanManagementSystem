package com.loanmanagement.service.impl;

import com.loanmanagement.dao.LoanTypeDao;
import com.loanmanagement.dao.impl.LoanTypeDaoImpl;
import com.loanmanagement.model.LoanType;
import com.loanmanagement.service.LoanTypeService;

public class LoanTypeServiceImpl implements LoanTypeService {

    private final LoanTypeDao loanTypeDao;

    public LoanTypeServiceImpl() {
        this.loanTypeDao = new LoanTypeDaoImpl();
    }

    @Override
    public void addLoanType(LoanType loanType) {
        if (validateLoanType(loanType)) {
            loanTypeDao.addLoanType(loanType);
        } else {
            System.out.println("Validation failed: Invalid loan type parameters.");
        }
    }

    @Override
    public LoanType getLoanTypeById(int loanTypeId) {
        if (loanTypeId > 0) {
            return loanTypeDao.getLoanTypeById(loanTypeId);
        }
        return null;
    }

    @Override
    public void updateLoanType(LoanType loanType) {
        if (validateLoanType(loanType)) {
            loanTypeDao.updateLoanType(loanType);
        } else {
            System.out.println("Validation failed: Cannot update loan type with invalid parameters.");
        }
    }

    @Override
    public void deleteLoanType(int loanTypeId) {
        if (loanTypeId > 0) {
            // BR-15: The DAO handles setting the status to 'INACTIVE'
            loanTypeDao.deleteLoanType(loanTypeId);
        }
    }

    // Enforces the validation rules for Loan Types
    private boolean validateLoanType(LoanType type) {
        if (type.getInterestRate() < 0.1 || type.getInterestRate() > 50.0) {
            System.out.println("Interest rate must be between 0.1 and 50.");
            return false;
        }
        if (type.getMinAmount() <= 0) {
            System.out.println("Minimum amount must be greater than zero.");
            return false;
        }
        if (type.getMaxAmount() < type.getMinAmount()) {
            System.out.println("Maximum amount cannot be less than minimum amount.");
            return false;
        }
        if (type.getMaxTenureMonths() < 1 || type.getMaxTenureMonths() > 360) {
            System.out.println("Tenure must be between 1 and 360 months.");
            return false;
        }
        return true;
    }
}