package com.loanmanagement.controller;

import com.loanmanagement.model.LoanType;
import com.loanmanagement.service.LoanTypeService;
import com.loanmanagement.service.impl.LoanTypeServiceImpl;

import java.util.Scanner;

public class LoanTypeController {

    private final LoanTypeService loanTypeService;

    public LoanTypeController() {
        this.loanTypeService = new LoanTypeServiceImpl();
    }

    public void addLoanProduct(Scanner scanner) {
        System.out.println("\n--- Add New Loan Product (Admin) ---");

        LoanType loanType = new LoanType();

        System.out.print("Product Name (e.g., Personal Loan): ");
        loanType.setName(scanner.nextLine());

        System.out.print("Description: ");
        loanType.setDescription(scanner.nextLine());

        System.out.print("Interest Rate (e.g., 10.5): ");
        loanType.setInterestRate(scanner.nextDouble());

        System.out.print("Minimum Amount: ");
        loanType.setMinAmount(scanner.nextDouble());

        System.out.print("Maximum Amount: ");
        loanType.setMaxAmount(scanner.nextDouble());

        System.out.print("Maximum Tenure (in months): ");
        loanType.setMaxTenureMonths(scanner.nextInt());
        scanner.nextLine(); // Consume newline

        loanTypeService.addLoanType(loanType);
        System.out.println("Loan product created successfully.");
    }
}