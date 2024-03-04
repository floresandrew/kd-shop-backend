package com.moditech.ecommerce.model;

public class VerificationResult {

    private final VerificationStatus status;
    private final String message;

    public VerificationResult(VerificationStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public VerificationStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}