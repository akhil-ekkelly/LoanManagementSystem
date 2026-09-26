package com.loanmanagement.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Scanner;

public class AppController {

    private static final Logger logger = LoggerFactory.getLogger(AppController.class);

    private static final UserController userController = new UserController();
    private static final CustomerController customerController = new CustomerController();
    private static final LoanTypeController loanTypeController = new LoanTypeController();
    private static final ApplicationController applicationController = new ApplicationController();
    private static final LoanController loanController = new LoanController();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        logger.info("Application starting up...");
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
            scanner.nextLine();

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
                    logger.info("Application shutting down cleanly.");
                    System.out.println("Exiting system. Goodbye!");
                    break;
                default:
                    logger.warn("User selected invalid main menu option: {}", choice);
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    private static void showDashboard(Scanner scanner) {
        boolean back = false;

        while (!back) {
            System.out.println("\n==========================================");
            System.out.println("             SYSTEM DASHBOARD             ");
            System.out.println("==========================================");

            System.out.println("--- Customer Workflows ---");
            System.out.println("1. Create/Update Profile (KYC)");
            System.out.println("2. Apply for a Loan");

            System.out.println("\n--- Admin / Officer Workflows ---");
            System.out.println("3. Add New Master Loan Product");
            System.out.println("4. Disburse Approved Application to Active Loan");

            System.out.println("\n0. Logout");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    customerController.manageProfile(scanner);
                    break;
                case 2:
                    applicationController.applyForLoan(scanner);
                    break;
                case 3:
                    loanTypeController.addLoanProduct(scanner);
                    break;
                case 4:
                    loanController.createLoan(scanner);
                    break;
                case 0:
                    back = true;
                    logger.info("User logged out of dashboard.");
                    System.out.println("Logged out successfully.");
                    break;
                default:
                    logger.warn("User selected invalid dashboard menu option: {}", choice);
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}