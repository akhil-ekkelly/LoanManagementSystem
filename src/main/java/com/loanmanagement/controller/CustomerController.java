package com.loanmanagement.controller;

import com.loanmanagement.model.Customer;
import com.loanmanagement.service.CustomerService;
import com.loanmanagement.service.impl.CustomerServiceImpl;

import java.util.Scanner;

public class CustomerController {

    private final CustomerService customerService;

    public CustomerController() {
        this.customerService = new CustomerServiceImpl();
    }

    public void manageProfile(Scanner scanner) {
        System.out.println("\n--- Create/Update Customer Profile ---");

        Customer customer = new Customer();

        // In a real flow, you'd fetch the logged-in User's ID. Hardcoding 1 for this demonstration.
        customer.setUserId(1);

        System.out.print("Full Name: ");
        customer.setFullName(scanner.nextLine());

        System.out.print("Email: ");
        customer.setEmail(scanner.nextLine());

        System.out.print("Phone (10 digits): ");
        customer.setPhone(scanner.nextLine());

        System.out.print("Date of Birth (YYYY-MM-DD): ");
        customer.setDob(scanner.nextLine());

        System.out.print("Address: ");
        customer.setAddress(scanner.nextLine());

        System.out.print("Monthly Income: ");
        customer.setMonthlyIncome(scanner.nextDouble());
        scanner.nextLine(); // Consume newline

        System.out.print("PAN Number: ");
        customer.setPanNumber(scanner.nextLine());

        System.out.print("Aadhaar Last 4 Digits: ");
        customer.setAadhaarLast4(scanner.nextLine());

        System.out.print("Employment Type (SALARIED/SELF_EMPLOYED): ");
        customer.setEmploymentType(scanner.nextLine().toUpperCase());

        customerService.addCustomer(customer);
        System.out.println("Profile submission complete.");
    }
}