package com.moditech.ecommerce.dto;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDto {

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
}
