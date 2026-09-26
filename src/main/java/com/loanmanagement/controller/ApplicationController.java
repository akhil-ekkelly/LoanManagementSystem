package com.loanmanagement.controller;

import com.loanmanagement.model.LoanApplication;
import com.loanmanagement.service.ApplicationService;
import com.loanmanagement.service.impl.ApplicationServiceImpl;

import java.util.Scanner;

public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController() {
        this.applicationService = new ApplicationServiceImpl();
    }

    public void applyForLoan(Scanner scanner) {
        System.out.println("\n--- Apply for a Loan ---");

        LoanApplication application = new LoanApplication();

        // In a real app, customerId would be fetched from the logged-in session. Hardcoding to 1 for testing.
        System.out.print("Enter your Customer ID: ");
        application.setCustomerId(scanner.nextInt());

        System.out.print("Enter the Loan Type ID you are applying for: ");
        application.setLoanTypeId(scanner.nextInt());

        System.out.print("Requested Amount: ");
        application.setRequestedAmount(scanner.nextDouble());

        System.out.print("Requested Tenure (in months): ");
        application.setTenureMonths(scanner.nextInt());
        scanner.nextLine(); // Consume newline

        System.out.print("Purpose of Loan: ");
        application.setPurpose(scanner.nextLine());

        applicationService.addApplication(application);
    }
}