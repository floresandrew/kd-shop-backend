package com.moditech.ecommerce.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "product")
public class Product {

    @Id
    private String id;

    private String productName;

    private String categoryName;

    private String brandName;

    private String productImage;

    private String description;

    private Double price;

    private Integer quantity;

    private Integer sold = 0;

    private Boolean isPreOrder = false;

    private String preOrderEta;

    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;
}
