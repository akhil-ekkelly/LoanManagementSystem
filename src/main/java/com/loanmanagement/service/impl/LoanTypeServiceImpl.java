package com.loanmanagement.service.impl;

import com.loanmanagement.dao.LoanTypeDao;
import com.loanmanagement.dao.impl.LoanTypeDaoImpl;
import com.loanmanagement.model.LoanType;
import com.loanmanagement.service.LoanTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoanTypeServiceImpl implements LoanTypeService {

    private static final Logger logger = LoggerFactory.getLogger(LoanTypeServiceImpl.class);
    private LoanTypeDao loanTypeDao;

    public LoanTypeServiceImpl() {
        this.loanTypeDao = new LoanTypeDaoImpl();
    }

    @Override
    public void addLoanType(LoanType loanType) {
        if (loanType != null && loanType.getName() != null) {
            loanTypeDao.addLoanType(loanType);
            logger.info("Service: Processed loan product creation for name={}", loanType.getName());
        } else {
            logger.warn("Service Validation failed: Loan product name is required.");
        }
    }

    @Override
    public LoanType getLoanTypeById(int loanTypeId) {
        if (loanTypeId > 0) {
            return loanTypeDao.getLoanTypeById(loanTypeId);
        }
        logger.warn("Service Validation failed: Invalid loanTypeId={}", loanTypeId);
        return null;
    }

    @Override
    public void updateLoanType(LoanType loanType) {
        if (loanType != null && loanType.getLoanTypeId() > 0) {
            loanTypeDao.updateLoanType(loanType);
            logger.info("Service: Processed loan product update for loanTypeId={}", loanType.getLoanTypeId());
        } else {
            logger.warn("Service Validation failed: Invalid loan product data for update.");
        }
    }

    @Override
    public void deleteLoanType(int loanTypeId) {
        if (loanTypeId > 0) {
            loanTypeDao.deleteLoanType(loanTypeId);
            logger.info("Service: Processed loan product deletion for loanTypeId={}", loanTypeId);
        } else {
            logger.warn("Service Validation failed: Invalid loanTypeId={}", loanTypeId);
        }
    }
}