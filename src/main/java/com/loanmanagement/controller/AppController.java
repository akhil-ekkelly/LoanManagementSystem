package com.loanmanagement.controller;

import com.loanmanagement.model.Customer;
import com.loanmanagement.model.Loan;
import com.loanmanagement.model.LoanApplication;
import com.loanmanagement.model.LoanType;
import com.loanmanagement.model.User;
import com.loanmanagement.service.ApplicationService;
import com.loanmanagement.service.AuthService;
import com.loanmanagement.service.CustomerService;
import com.loanmanagement.service.LoanService;
import com.loanmanagement.service.LoanTypeService;
import com.loanmanagement.service.UserService;
import com.loanmanagement.service.impl.ApplicationServiceImpl;
import com.loanmanagement.service.impl.AuthServiceImpl;
import com.loanmanagement.service.impl.CustomerServiceImpl;
import com.loanmanagement.service.impl.LoanServiceImpl;
import com.loanmanagement.service.impl.LoanTypeServiceImpl;
import com.loanmanagement.service.impl.UserServiceImpl;

import java.util.Scanner;

public class AppController {

    private static final Scanner scanner = new Scanner(System.in);

    // Initialize all services
    private static final AuthService authService = new AuthServiceImpl();
    private static final UserService userService = new UserServiceImpl();
    private static final CustomerService customerService = new CustomerServiceImpl();
    private static final ApplicationService applicationService = new ApplicationServiceImpl();
    private static final LoanTypeService loanTypeService = new LoanTypeServiceImpl();
    private static final LoanService loanService = new LoanServiceImpl();

    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("   Welcome to the Loan Management System  ");
        System.out.println("==========================================");

        boolean running = true;
        while (running) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Login");
            System.out.println("2. Register New User");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");

            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            switch (choice) {
                case 1:
                    User loggedInUser = handleLogin();
                    if (loggedInUser != null) {
                        showSystemDashboard(loggedInUser);
                    }
                    break;
                case 2:
                    handleRegistration();
                    break;
                case 3:
                    running = false;
                    System.out.println("Exiting System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void showSystemDashboard(User user) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n==========================================");
            System.out.println("             SYSTEM DASHBOARD             ");
            System.out.println("==========================================");
            System.out.println("          Welcome, " + user.getUsername() + " (" + user.getRole() + ")");

            System.out.println("\n--- Customer Workflows ---");
            System.out.println("1. Create Profile (KYC)");
            System.out.println("2. Apply for a Loan");
            System.out.println("3. View My Status (KYC & Loans)");

            // Role-based menu rendering
            if (isAdminOrOfficer(user)) {
                System.out.println("\n--- Admin / Officer Workflows ---");
                System.out.println("4. Add New Master Loan Product");
                System.out.println("5. Disburse Approved Application to Active Loan");
                System.out.println("6. Verify Customer KYC");
                System.out.println("7. Review Loan Application");
            }

            System.out.println("\n0. Logout");
            System.out.print("Select an option: ");

            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            // Block standard customers from entering admin option numbers
            if (choice >= 4 && choice <= 7 && !isAdminOrOfficer(user)) {
                System.out.println("\n[ERROR] Access Denied. You do not have permission to perform this action.");
                continue;
            }

            switch (choice) {
                case 1:
                    handleKycCreation(user);
                    break;
                case 2:
                    handleLoanApplication();
                    break;
                case 3:
                    viewMyStatus(user);
                    break;
                case 4:
                    handleAddLoanProduct();
                    break;
                case 5:
                    handleLoanDisbursement();
                    break;
                case 6:
                    verifyCustomerKyc();
                    break;
                case 7:
                    reviewLoanApplication(user);
                    break;
                case 0:
                    loggedIn = false;
                    System.out.println("Logged out successfully.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // --- MAIN WORKFLOW IMPLEMENTATIONS ---

    private static User handleLogin() {
        System.out.println("\n--- User Login ---");
        System.out.print("Enter Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine().trim();

        boolean isAuthenticated = authService.login(username, password);

        if (isAuthenticated) {
            try {
                System.out.print("[SUCCESS] Authenticated. Enter your User ID to load profile: ");
                int userId = Integer.parseInt(scanner.nextLine().trim());
                User user = userService.getUserById(userId);

                if (user != null) {
                    return user;
                } else {
                    System.out.println("[ERROR] User profile not found.");
                    return null;
                }
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Invalid User ID format.");
                return null;
            }
        } else {
            System.out.println("[ERROR] Invalid credentials or inactive account.");
            return null;
        }
    }

    private static void handleRegistration() {
        System.out.println("\n--- Register New User ---");
        System.out.print("Enter Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine().trim();
        System.out.print("Enter Role (CUSTOMER/ADMIN/LOAN_OFFICER): ");
        String role = scanner.nextLine().trim().toUpperCase();

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setRole(role);

        userService.addUser(newUser);
        System.out.println("[SUCCESS] Registration complete. You can now login.");
    }

    private static void handleKycCreation(User user) {
        System.out.println("\n--- Create Customer Profile (KYC) ---");
        Customer customer = new Customer();
        customer.setUserId(user.getUserId());

        try {
            System.out.print("Enter Full Name: ");
            customer.setFullName(scanner.nextLine().trim());

            System.out.print("Enter Email Address: ");
            customer.setEmail(scanner.nextLine().trim());

            System.out.print("Enter Phone Number: ");
            customer.setPhone(scanner.nextLine().trim());

            System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
            customer.setDob(scanner.nextLine().trim());

            System.out.print("Enter Full Address: ");
            customer.setAddress(scanner.nextLine().trim());

            System.out.print("Enter Monthly Income (e.g., 50000): ");
            customer.setMonthlyIncome(Double.parseDouble(scanner.nextLine().trim()));

            System.out.print("Enter PAN Number: ");
            customer.setPanNumber(scanner.nextLine().trim().toUpperCase());

            System.out.print("Enter Aadhaar Last 4 Digits: ");
            customer.setAadhaarLast4(scanner.nextLine().trim());

            System.out.print("Enter Employment Type (e.g., SALARIED, SELF_EMPLOYED): ");
            customer.setEmploymentType(scanner.nextLine().trim().toUpperCase());

            System.out.print("Enter Existing Monthly EMI (Enter 0 if none): ");
            customer.setExistingEmi(Double.parseDouble(scanner.nextLine().trim()));

            // Hidden default values to prevent MySQL crashes if bank fields are NOT NULL
            customer.setBankName("PENDING_SETUP");
            customer.setAccountNumber("000000000");
            customer.setIfscCode("XXXX0000000");

            // Set required system defaults for a new profile
            customer.setKycStatus("PENDING");
            customer.setStatus("ACTIVE");
            customer.setCreditScore(0);

            customerService.addCustomer(customer);
            System.out.println("[SUCCESS] Profile submitted. Your KYC is PENDING verification.");

        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Invalid number format entered for Income or EMI. Profile creation aborted.");
        } catch (Exception e) {
            System.out.println("[ERROR] An unexpected error occurred: " + e.getMessage());
        }
    }

    private static void handleLoanApplication() {
        System.out.println("\n--- Apply for a Loan ---");
        try {
            System.out.print("Enter your Customer ID: ");
            int customerId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter Loan Product Type ID: ");
            int loanTypeId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter Requested Amount: ");
            double amount = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Enter Tenure (in months): ");
            int tenure = Integer.parseInt(scanner.nextLine().trim());

            LoanApplication app = new LoanApplication();
            app.setCustomerId(customerId);
            app.setLoanTypeId(loanTypeId);
            app.setRequestedAmount(amount);
            app.setTenureMonths(tenure);

            applicationService.addApplication(app);
            System.out.println("[INFO] Application request sent. Check logs for validation result.");

        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Invalid number format entered.");
        }
    }

    private static void handleAddLoanProduct() {
        System.out.println("\n--- Add New Master Loan Product ---");
        try {
            LoanType type = new LoanType();

            System.out.print("Enter Product Name (e.g., Home Loan): ");
            type.setName(scanner.nextLine().trim());

            System.out.print("Enter Interest Rate (e.g., 8.5): ");
            type.setInterestRate(Double.parseDouble(scanner.nextLine().trim()));

            System.out.print("Enter Minimum Amount: ");
            type.setMinAmount(Double.parseDouble(scanner.nextLine().trim()));

            System.out.print("Enter Maximum Amount: ");
            type.setMaxAmount(Double.parseDouble(scanner.nextLine().trim()));

            System.out.print("Enter Maximum Tenure (months): ");
            type.setMaxTenureMonths(Integer.parseInt(scanner.nextLine().trim()));

            type.setStatus("ACTIVE");

            loanTypeService.addLoanType(type);
            System.out.println("[SUCCESS] Master Loan Product created.");

        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Invalid number format entered.");
        }
    }

    private static void handleLoanDisbursement() {
        System.out.println("\n--- Disburse Approved Application ---");
        try {
            System.out.print("Enter Approved Application ID: ");
            int appId = Integer.parseInt(scanner.nextLine().trim());

            LoanApplication app = applicationService.getApplicationById(appId);
            if (app == null) {
                System.out.println("[ERROR] Application not found.");
                return;
            }
            if (!"APPROVED".equalsIgnoreCase(app.getStatus())) {
                System.out.println("[ERROR] Cannot disburse. Application status is: " + app.getStatus());
                return;
            }

            Loan loan = new Loan();
            loan.setApplicationId(appId);
            loan.setCustomerId(app.getCustomerId());
            loan.setLoanTypeId(app.getLoanTypeId());
            loan.setPrincipalAmount(app.getRequestedAmount());
            loan.setTenureMonths(app.getTenureMonths());

            loanService.addLoan(loan);
            System.out.println("[INFO] Disbursement request sent. Check logs for calculations and confirmation.");

        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Invalid number format entered.");
        }
    }

    private static void verifyCustomerKyc() {
        System.out.println("\n--- Verify Customer KYC ---");
        try {
            System.out.print("Enter Customer ID: ");
            int customerId = Integer.parseInt(scanner.nextLine().trim());

            Customer customer = customerService.getCustomerById(customerId);
            if (customer == null) {
                System.out.println("[ERROR] Customer not found with ID: " + customerId);
                return;
            }

            System.out.println("Current KYC Status: " + customer.getKycStatus());
            System.out.print("Enter new Status (VERIFIED / REJECTED): ");
            String newStatus = scanner.nextLine().trim().toUpperCase();

            customer.setKycStatus(newStatus);
            customerService.updateCustomer(customer);
            System.out.println("[SUCCESS] Customer KYC update request sent.");

        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Invalid Customer ID format.");
        }
    }

    private static void reviewLoanApplication(User officer) {
        System.out.println("\n--- Review Loan Application ---");
        try {
            System.out.print("Enter Application ID: ");
            int appId = Integer.parseInt(scanner.nextLine().trim());

            LoanApplication app = applicationService.getApplicationById(appId);
            if (app == null) {
                System.out.println("[ERROR] Application not found with ID: " + appId);
                return;
            }

            System.out.println("Requested Amount: " + app.getRequestedAmount());
            System.out.println("Current Status: " + app.getStatus());

            System.out.print("Enter Decision (APPROVED / REJECTED): ");
            String decision = scanner.nextLine().trim().toUpperCase();

            app.setStatus(decision);
            app.setReviewedBy(officer.getUserId());

            if ("REJECTED".equals(decision)) {
                System.out.print("Enter mandatory Rejection Remarks: ");
                String remarks = scanner.nextLine().trim();
                app.setRemarks(remarks);
            } else {
                app.setRemarks("Approved by Officer ID: " + officer.getUserId());
            }

            applicationService.updateApplication(app);
            System.out.println("[SUCCESS] Application update request sent.");

        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Invalid Application ID format.");
        }
    }

    private static void viewMyStatus(User user) {
        System.out.println("\n--- My Account Status ---");
        try {
            System.out.print("Enter your Customer ID to view status: ");
            int customerId = Integer.parseInt(scanner.nextLine().trim());

            Customer customer = customerService.getCustomerById(customerId);

            if (customer == null) {
                System.out.println("No profile found. Please complete KYC first.");
                return;
            }

            if (customer.getUserId() != user.getUserId()) {
                System.out.println("[ERROR] Unauthorized. This Customer ID belongs to another user.");
                return;
            }

            System.out.println("\n--- Profile Details ---");
            System.out.println("User ID: " + customer.getUserId());
            System.out.println("Customer ID: " + customer.getCustomerId());
            System.out.println("Name: " + customer.getFullName());
            System.out.println("KYC Status: " + customer.getKycStatus());
            System.out.println("Credit Score: " + customer.getCreditScore());

            // 1. Application Check
            System.out.print("\nWould you like to check a specific Loan Application status? (Y/N): ");
            String checkApp = scanner.nextLine().trim().toUpperCase();

            if ("Y".equals(checkApp)) {
                System.out.print("Enter Application ID: ");
                int appId = Integer.parseInt(scanner.nextLine().trim());

                LoanApplication app = applicationService.getApplicationById(appId);

                if (app == null) {
                    System.out.println("[ERROR] Application not found.");
                } else if (app.getCustomerId() != customerId) {
                    System.out.println("[ERROR] This application does not belong to your profile.");
                } else {
                    System.out.println("\n--- Loan Application Details ---");
                    System.out.println("Application ID: " + app.getApplicationId());
                    System.out.println("Requested Amount: " + app.getRequestedAmount());
                    System.out.println("Status: " + app.getStatus());
                    System.out.println("Remarks: " + (app.getRemarks() != null ? app.getRemarks() : "Pending Officer Review"));
                }
            }

            // 2. Disbursed Loan Check
            System.out.print("\nWould you like to check an Active Disbursed Loan? (Y/N): ");
            String checkLoan = scanner.nextLine().trim().toUpperCase();

            if ("Y".equals(checkLoan)) {
                System.out.print("Enter Loan ID: ");
                int loanId = Integer.parseInt(scanner.nextLine().trim());

                // Uses the existing loanService that is already initialized at the top of AppController
                Loan loan = loanService.getLoanById(loanId);

                if (loan == null) {
                    System.out.println("[ERROR] Loan not found or not yet disbursed.");
                } else if (loan.getCustomerId() != customerId) {
                    System.out.println("[ERROR] This loan does not belong to your profile.");
                } else {
                    System.out.println("\n--- Active Loan Details ---");
                    System.out.println("Loan ID: " + loanId);
                    System.out.println("Originated from Application ID: " + loan.getApplicationId());
                    System.out.println("Principal Amount: " + loan.getPrincipalAmount());
                    System.out.println("Tenure: " + loan.getTenureMonths() + " months");
                    System.out.println("Status: ACTIVE");
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Invalid input. Please enter numbers only.");
        }
    }
    // --- HELPER METHODS ---

    private static boolean isAdminOrOfficer(User user) {
        return "ADMIN".equalsIgnoreCase(user.getRole()) || "LOAN_OFFICER".equalsIgnoreCase(user.getRole());
    }
}