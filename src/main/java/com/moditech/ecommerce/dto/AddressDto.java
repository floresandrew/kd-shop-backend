package com.moditech.ecommerce.dto;

import lombok.Data;

@Data
public class AddressDto {
    private String id;

    private String fullName;

    private String email;

    private String addressLine1;

    private String city;

    private String country;

    private String postalCode;

    private String contactNumber;

    private Boolean isDefault = false;
}
