package com.loanmanagement.model;

public class LoanType {
    private int loanTypeId;
    private String name;
    private String description;
    private double interestRate;
    private double minAmount;
    private double maxAmount;
    private int maxTenureMonths;
    private String status;


    public int getLoanTypeId() {
        return loanTypeId;
    }

    public void setLoanTypeId(int loanTypeId) {
        this.loanTypeId = loanTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(double minAmount) {
        this.minAmount = minAmount;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public int getMaxTenureMonths() {
        return maxTenureMonths;
    }

    public void setMaxTenureMonths(int maxTenureMonths) {
        this.maxTenureMonths = maxTenureMonths;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LoanType{" +
                "loanTypeId=" + loanTypeId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", interestRate=" + interestRate +
                ", minAmount=" + minAmount +
                ", maxAmount=" + maxAmount +
                ", maxTenureMonths=" + maxTenureMonths +
                ", status='" + status + '\'' +
                '}';
    }
}
