package com.moditech.ecommerce.service;

import lombok.RequiredArgsConstructor;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.moditech.ecommerce.model.Email;
import com.moditech.ecommerce.model.VerificationResult;
import com.moditech.ecommerce.model.VerificationStatus;
import com.moditech.ecommerce.repository.EmailRepository;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Autowired
    private final JavaMailSender javaMailSender;

    @Autowired
    EmailRepository emailRepository;

    @Value("${spring.mail.username}")
    String adminEmail;

    @Value("${backend.base.url}")
    String backendUrl;

    @Async
    public void sendEmail(String userEmail) {
        String subject = "Account Verification";
        String message = "Please click the following link to verify your account:" + backendUrl
                + "/api/user/isEnable/userID/" + userEmail;
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailMessage.setFrom(adminEmail);
        javaMailSender.send(mailMessage);
    }

    @Async
    public void sendOtpEmail(String email) {
        String subject = "OTP Verification Code";
        String otp = generateRandomOtp();
        String message = "Hi, \n \n Your OTP code from KDShop is here: " + otp + "\n \n Regards, \n KDShop";
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailMessage.setFrom(adminEmail);

        Email emailObject = new Email();
        emailObject.setEmail(email);
        emailObject.setOtp(otp);
        emailRepository.save(emailObject);

        javaMailSender.send(mailMessage);
    }

    private String generateRandomOtp() {
        String characters = "0123456789";
        Random random = new Random();
        StringBuilder otp = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            otp.append(characters.charAt(random.nextInt(characters.length())));
        }
        return otp.toString();
    }

    public VerificationResult verifyOtp(String email, String enteredOtp) {
        try {
            Email emailObject = emailRepository.findTopByEmailOrderByCreatedAtDesc(email);

            System.out.println(emailObject.getEmail());

            assert emailObject != null;

            if (enteredOtp.equals(emailObject.getOtp())) {
                return new VerificationResult(VerificationStatus.SUCCESS, "OTP is valid");
            } else {
                return new VerificationResult(VerificationStatus.FAILURE, "Invalid OTP");
            }

        } catch (Exception e) {

            e.printStackTrace();
            return new VerificationResult(VerificationStatus.ERROR, "Error verifying OTP");
        }
    }
}
