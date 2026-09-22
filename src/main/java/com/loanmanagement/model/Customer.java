package com.loanmanagement.model;

public class Customer {
    private int customerId;
    private int userId;
    private String fullName;
    private String email;
    private String phone;
    private String dob;
    private String address;
    private double monthlyIncome;
    private String panNumber;
    private String aadhaarLast4;
    private String employmentType;
    private String accountNumber;
    private String ifscCode;
    private String bankName;
    private String kycStatus;
    private String kycRemarks;
    private int kycVerifiedBy;
    private String kycVerifiedAt;
    private int creditScore;
    private double existingEmi;
    private String status;




    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getAadhaarLast4() {
        return aadhaarLast4;
    }

    public void setAadhaarLast4(String aadhaarLast4) {
        this.aadhaarLast4 = aadhaarLast4;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getKycStatus() {
        return kycStatus;
    }

    public void setKycStatus(String kycStatus) {
        this.kycStatus = kycStatus;
    }

    public String getKycRemarks() {
        return kycRemarks;
    }

    public void setKycRemarks(String kycRemarks) {
        this.kycRemarks = kycRemarks;
    }

    public int getKycVerifiedBy() {
        return kycVerifiedBy;
    }

    public void setKycVerifiedBy(int kycVerifiedBy) {
        this.kycVerifiedBy = kycVerifiedBy;
    }

    public String getKycVerifiedAt() {
        return kycVerifiedAt;
    }

    public void setKycVerifiedAt(String kycVerifiedAt) {
        this.kycVerifiedAt = kycVerifiedAt;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    public double getExistingEmi() {
        return existingEmi;
    }

    public void setExistingEmi(double existingEmi) {
        this.existingEmi = existingEmi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", userId=" + userId +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", dob='" + dob + '\'' +
                ", address='" + address + '\'' +
                ", monthlyIncome=" + monthlyIncome +
                ", panNumber='" + panNumber + '\'' +
                ", aadhaarLast4='" + aadhaarLast4 + '\'' +
                ", employmentType='" + employmentType + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", ifscCode='" + ifscCode + '\'' +
                ", bankName='" + bankName + '\'' +
                ", kycStatus='" + kycStatus + '\'' +
                ", kycRemarks='" + kycRemarks + '\'' +
                ", kycVerifiedBy=" + kycVerifiedBy +
                ", kycVerifiedAt='" + kycVerifiedAt + '\'' +
                ", creditScore=" + creditScore +
                ", existingEmi=" + existingEmi +
                ", status='" + status + '\'' +
                '}';
    }
}
