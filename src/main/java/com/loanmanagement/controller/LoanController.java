package com.loanmanagement.controller;

import com.loanmanagement.model.Loan;
import com.loanmanagement.service.LoanService;
import com.loanmanagement.service.impl.LoanServiceImpl;

import java.util.Scanner;

public class LoanController {

    private final LoanService loanService;

    public LoanController() {
        this.loanService = new LoanServiceImpl();
    }

    public void createLoan(Scanner scanner) {
        System.out.println("\n--- Disburse Approved Loan (Officer/Admin) ---");

        Loan loan = new Loan();

        System.out.print("Enter the Approved Application ID: ");
        loan.setApplicationId(scanner.nextInt());

        System.out.print("Enter the Customer ID: ");
        loan.setCustomerId(scanner.nextInt());

        System.out.print("Enter the Loan Type ID: ");
        loan.setLoanTypeId(scanner.nextInt());

        System.out.print("Enter Principal Amount to Disburse: ");
        loan.setPrincipalAmount(scanner.nextDouble());

        System.out.print("Enter Tenure (in months): ");
        loan.setTenureMonths(scanner.nextInt());
        scanner.nextLine(); // Consume newline

        System.out.print("Enter Start Date (YYYY-MM-DD): ");
        loan.setStartDate(scanner.nextLine());

        // In a real system, createdBy would be the logged-in Officer's User ID
        loan.setCreatedBy(1);

        loanService.addLoan(loan);
    }
}