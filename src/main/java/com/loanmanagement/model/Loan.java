package com.loanmanagement.model;

public class Loan {
    private int loanId;
    private int applicationId;
    private int customerId;
    private int loanTypeId;
    private double principalAmount;
    private double interestRate;
    private int tenureMonths;
    private double totalPayable;
    private double outstandingAmount;
    private String startDate;
    private String status;
    private int createdBy;

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getLoanTypeId() {
        return loanTypeId;
    }

    public void setLoanTypeId(int loanTypeId) {
        this.loanTypeId = loanTypeId;
    }

    public double getPrincipalAmount() {
        return principalAmount;
    }

    public void setPrincipalAmount(double principalAmount) {
        this.principalAmount = principalAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getTenureMonths() {
        return tenureMonths;
    }

    public void setTenureMonths(int tenureMonths) {
        this.tenureMonths = tenureMonths;
    }

    public double getTotalPayable() {
        return totalPayable;
    }

    public void setTotalPayable(double totalPayable) {
        this.totalPayable = totalPayable;
    }

    public double getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setOutstandingAmount(double outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }


    @Override
    public String toString() {
        return "Loan{" +
                "loanId=" + loanId +
                ", applicationId=" + applicationId +
                ", customerId=" + customerId +
                ", loanTypeId=" + loanTypeId +
                ", principalAmount=" + principalAmount +
                ", interestRate=" + interestRate +
                ", tenureMonths=" + tenureMonths +
                ", totalPayable=" + totalPayable +
                ", outstandingAmount=" + outstandingAmount +
                ", startDate='" + startDate + '\'' +
                ", status='" + status + '\'' +
                ", createdBy=" + createdBy +
                '}';
    }
}
