package com.moditech.ecommerce.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDto {

    private String id;

    private String email;

    private String userFullName;

    private Double totalPrice;

    private String orderList;

    private String paymentStatus;

    private String deliveryStatus;

    private String paymentMethod;

    private String receipt;

    private String address;

    private String contactNumber;

    private LocalDateTime orderDate;
}