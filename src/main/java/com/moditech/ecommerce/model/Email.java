package com.moditech.ecommerce.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "email")
public class Email {

    @Id
    private String id;

    private String email;

    private String otp;

    @CreatedDate
    private LocalDateTime createdAt;
}
