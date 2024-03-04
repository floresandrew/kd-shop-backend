package com.moditech.ecommerce.controller;

import com.moditech.ecommerce.dto.EmailVerificationRequest;
import com.moditech.ecommerce.model.Email;
import com.moditech.ecommerce.model.VerificationResult;
import com.moditech.ecommerce.service.EmailService;
import com.moditech.ecommerce.service.OrderService;
import com.moditech.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/email")
@CrossOrigin("*")
public class EmailController {

    @Autowired
    EmailService emailService;

    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;

    @Value("${frontend.base.url}")
    String frontEndBaseUrl;

    @PostMapping("/sendEmail/{email}")
    private ResponseEntity<String> sendEmail(@PathVariable String email, HttpServletResponse response)
            throws IOException {
        emailService.sendEmail(email);
        response.sendRedirect(frontEndBaseUrl);
        return ResponseEntity.ok("Email sent");
    }

    @PostMapping("/send")
    private ResponseEntity<String> sendEmail(@RequestBody Email email) {
        emailService.sendOtpEmail(email.getEmail());
        return ResponseEntity.ok("Email sent");
    }

    @PostMapping("/verifyOtp")
    private ResponseEntity<String> verifyOtp(@RequestBody EmailVerificationRequest request) {
        String email = request.getEmail();
        String enteredOtp = request.getEnteredOtp();

        System.out.println("enteredOtp: " + enteredOtp);

        VerificationResult verificationResult = emailService.verifyOtp(email, enteredOtp);

        switch (verificationResult.getStatus()) {
            case SUCCESS:
                return ResponseEntity.ok(verificationResult.getMessage());
            case FAILURE:
                return ResponseEntity.status(401).body(verificationResult.getMessage());
            case ERROR:
                return ResponseEntity.status(500).body(verificationResult.getMessage());
            default:
                return ResponseEntity.status(500).body("Unknown error");
        }
    }
}
