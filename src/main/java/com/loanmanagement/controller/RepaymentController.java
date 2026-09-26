package com.loanmanagement.controller;

import com.loanmanagement.model.Repayment;
import com.loanmanagement.service.RepaymentService;
import com.loanmanagement.service.impl.RepaymentServiceImpl;

import java.util.Scanner;

public class RepaymentController {

    private final RepaymentService repaymentService;

    public RepaymentController() {
        this.repaymentService = new RepaymentServiceImpl();
    }

    public void makeRepayment(Scanner scanner) {
        System.out.println("\n--- Make a Loan Repayment ---");

        Repayment repayment = new Repayment();

        System.out.print("Enter your Active Loan ID: ");
        repayment.setLoanId(scanner.nextInt());

        System.out.print("Enter Repayment Amount: ");
        repayment.setAmount(scanner.nextDouble());
        scanner.nextLine(); // Consume newline

        System.out.print("Enter Payment Date (YYYY-MM-DD): ");
        repayment.setPaymentDate(scanner.nextLine());

        System.out.print("Enter Payment Mode (UPI/BANK_TRANSFER/CASH): ");
        repayment.setPaymentMode(scanner.nextLine().toUpperCase());

        System.out.print("Enter Transaction Reference Number: ");
        repayment.setReferenceNo(scanner.nextLine());

        System.out.print("Enter Remarks (Optional): ");
        repayment.setRemarks(scanner.nextLine());

        // Setting recordedBy to the customer's User ID (hardcoded for simulation)
        repayment.setRecordedBy(1);

        repaymentService.addRepayment(repayment);
    }
}