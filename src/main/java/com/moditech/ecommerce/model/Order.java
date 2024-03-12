package com.moditech.ecommerce.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "order")
public class Order {

    @Id
    private String id;

    private String email;

    private String userFullName;

    private Double totalPrice;

    private String orderList;

    private String paymentStatus;

    private String deliveryStatus = "Preparing";

    private String paymentMethod;

    private String receipt;

    private String address;

    private String contactNumber;

    @CreatedDate
    private LocalDateTime orderDate;

}