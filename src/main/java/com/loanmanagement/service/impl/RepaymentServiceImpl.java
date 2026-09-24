package com.loanmanagement.service.impl;

import com.loanmanagement.dao.LoanDao;
import com.loanmanagement.dao.RepaymentDao;
import com.loanmanagement.dao.impl.LoanDaoImpl;
import com.loanmanagement.dao.impl.RepaymentDaoImpl;
import com.loanmanagement.model.Loan;
import com.loanmanagement.model.Repayment;
import com.loanmanagement.service.RepaymentService;

public class RepaymentServiceImpl implements RepaymentService {

    private final RepaymentDao repaymentDao;
    private final LoanDao loanDao;

    public RepaymentServiceImpl() {
        this.repaymentDao = new RepaymentDaoImpl();
        this.loanDao = new LoanDaoImpl();
    }

    @Override
    public void addRepayment(Repayment repayment) {
        // Fetch the active loan associated with this repayment
        Loan loan = loanDao.getLoanById(repayment.getLoanId());

        if (loan == null) {
            System.out.println("Validation failed: Associated loan not found.");
            return;
        }

        // BR-13: No repayment can be recorded against a CLOSED loan
        if ("CLOSED".equals(loan.getStatus())) {
            System.out.println("Validation failed: Cannot record a repayment against a CLOSED loan (BR-13).");
            return;
        }

        // BR-5: A repayment cannot be greater than the outstanding amount
        if (repayment.getAmount() > loan.getOutstandingAmount()) {
            System.out.println("Validation failed: The payment cannot exceed the outstanding amount of "
                    + loan.getOutstandingAmount() + " (BR-5).");
            return;
        }

        // Process the payment
        double newOutstandingAmount = loan.getOutstandingAmount() - repayment.getAmount();
        loan.setOutstandingAmount(newOutstandingAmount);

        // BR-6: When the outstanding amount becomes zero, the loan becomes CLOSED
        if (newOutstandingAmount <= 0) {
            loan.setStatus("CLOSED");
            System.out.println("Loan fully repaid. Status updated to CLOSED.");
        }

        // Save the repayment record
        repaymentDao.addRepayment(repayment);

        // Update the loan's outstanding balance and status in the database
        loanDao.updateLoan(loan);

        System.out.println("Repayment of " + repayment.getAmount() + " recorded successfully. Remaining balance: " + newOutstandingAmount);
    }

    @Override
    public Repayment getRepaymentById(int repaymentId) {
        if (repaymentId > 0) {
            return repaymentDao.getRepaymentById(repaymentId);
        }
        return null;
    }

    @Override
    public void updateRepayment(Repayment repayment) {
        if (repayment != null && repayment.getRepaymentId() > 0) {
            repaymentDao.updateRepayment(repayment);
            System.out.println("Repayment updated successfully.");
        }
    }

    @Override
    public void deleteRepayment(int repaymentId) {
        if (repaymentId > 0) {
            repaymentDao.deleteRepayment(repaymentId);
            System.out.println("Repayment deleted.");
        }
    }
}