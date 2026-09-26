package com.loanmanagement.util;

import com.loanmanagement.exception.ValidationException;

public class ValidationUtil {

    private ValidationUtil() {
        // Prevent instantiation of utility class
    }

    public static void requireNonEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException(fieldName + " cannot be null or empty.");
        }
    }

    public static void validatePositive(double value, String fieldName) {
        if (value <= 0) {
            throw new ValidationException(fieldName + " must be greater than zero.");
        }
    }

    public static void validateEmail(String email) {
        requireNonEmpty(email, "Email");
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new ValidationException("Invalid email format.");
        }
    }

    public static void validatePhone(String phone) {
        requireNonEmpty(phone, "Phone");
        if (!phone.matches("^\\d{10}$")) {
            throw new ValidationException("Phone number must be exactly 10 digits.");
        }
    }

    public static void validatePan(String pan) {
        requireNonEmpty(pan, "PAN Number");
        if (!pan.matches("^[A-Z]{5}[0-9]{4}[A-Z]{1}$")) {
            throw new ValidationException("Invalid PAN format. Must be 5 letters, 4 digits, 1 letter.");
        }
    }
}