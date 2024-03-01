package com.moditech.ecommerce.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "address")
public class Address {

    @Id
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
