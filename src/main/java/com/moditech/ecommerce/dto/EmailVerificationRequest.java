package com.moditech.ecommerce.dto;

import lombok.Data;

@Data
public class EmailVerificationRequest {
    private String email;
    private String enteredOtp;
}
