package com.loanmanagement.model;

public class LoanApplication {
    private int applicationId;
    private int customerId;
    private int loanTypeId;
    private double requestedAmount;
    private int tenureMonths;
    private String purpose;
    private String status;
    private String remarks;
    private int reviewedBy;
    private String appliedAt;
    private String reviewedAt;


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

    public double getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(double requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public int getTenureMonths() {
        return tenureMonths;
    }

    public void setTenureMonths(int tenureMonths) {
        this.tenureMonths = tenureMonths;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(int reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public String getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(String appliedAt) {
        this.appliedAt = appliedAt;
    }

    public String getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(String reviewedAt) {
        this.reviewedAt = reviewedAt;
    }

    @Override
    public String toString() {
        return "LoanApplication{" +
                "applicationId=" + applicationId +
                ", customerId=" + customerId +
                ", loanTypeId=" + loanTypeId +
                ", requestedAmount=" + requestedAmount +
                ", tenureMonths=" + tenureMonths +
                ", purpose='" + purpose + '\'' +
                ", status='" + status + '\'' +
                ", remarks='" + remarks + '\'' +
                ", reviewedBy=" + reviewedBy +
                ", appliedAt='" + appliedAt + '\'' +
                ", reviewedAt='" + reviewedAt + '\'' +
                '}';
    }
}
