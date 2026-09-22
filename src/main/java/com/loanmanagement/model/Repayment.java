package com.loanmanagement.model;

public class Repayment {
    private int repaymentId;
    private int loanId;
    private double amount;
    private String paymentDate;
    private String paymentMode;
    private String referenceNo;
    private String remarks;
    private int recordedBy;
    private String createdAt;


    public int getRepaymentId() {
        return repaymentId;
    }

    public void setRepaymentId(int repaymentId) {
        this.repaymentId = repaymentId;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getRecordedBy() {
        return recordedBy;
    }

    public void setRecordedBy(int recordedBy) {
        this.recordedBy = recordedBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Repayment{" +
                "repaymentId=" + repaymentId +
                ", loanId=" + loanId +
                ", amount=" + amount +
                ", paymentDate='" + paymentDate + '\'' +
                ", paymentMode='" + paymentMode + '\'' +
                ", referenceNo='" + referenceNo + '\'' +
                ", remarks='" + remarks + '\'' +
                ", recordedBy=" + recordedBy +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
