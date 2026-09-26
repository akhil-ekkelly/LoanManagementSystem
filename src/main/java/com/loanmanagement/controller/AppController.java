package com.loanmanagement.controller;

import java.util.Scanner;

public class AppController {

    // Initialize all module controllers to orchestrate the entire workflow
    private static final UserController userController = new UserController();
    private static final CustomerController customerController = new CustomerController();
    private static final LoanTypeController loanTypeController = new LoanTypeController();
    private static final ApplicationController applicationController = new ApplicationController();
    private static final LoanController loanController = new LoanController();
    private static final RepaymentController repaymentController = new RepaymentController();


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        System.out.println("==========================================");
        System.out.println("   Welcome to the Loan Management System  ");
        System.out.println("==========================================");

        while (!exit) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Login");
            System.out.println("2. Register New User");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the leftover newline

            switch (choice) {
                case 1:
                    boolean loggedIn = userController.loginUser(scanner);
                    if (loggedIn) {
                        showDashboard(scanner);
                    }
                    break;
                case 2:
                    userController.registerUser(scanner);
                    break;
                case 3:
                    exit = true;
                    System.out.println("Exiting system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    private static void showDashboard(Scanner scanner) {
        boolean back = false;

        // A unified dashboard that categorizes actions by role
        while (!back) {
            System.out.println("\n==========================================");
            System.out.println("             SYSTEM DASHBOARD             ");
            System.out.println("==========================================");

            System.out.println("--- Customer Workflows ---");
            System.out.println("1. Create/Update Profile (KYC)");
            System.out.println("2. Apply for a Loan");
            System.out.println("3. Make a Repayment");

            System.out.println("\n--- Admin / Officer Workflows ---");
            System.out.println("4. Add New Master Loan Product");
            System.out.println("5. Disburse Approved Application to Active Loan");

            System.out.println("\n0. Logout");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the leftover newline

            switch (choice) {
                case 1:
                    customerController.manageProfile(scanner);
                    break;
                case 2:
                    applicationController.applyForLoan(scanner);
                    break;
                case 3:
                    repaymentController.makeRepayment(scanner);
                    break;
                case 4:
                    loanTypeController.addLoanProduct(scanner);
                    break;
                case 5:
                    loanController.createLoan(scanner);
                    break;
                case 0:
                    back = true;
                    System.out.println("Logged out successfully.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}