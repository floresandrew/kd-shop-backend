package com.moditech.ecommerce.controller;

import com.moditech.ecommerce.dto.ProductRatingDto;
import com.moditech.ecommerce.model.ProductRating;
import com.moditech.ecommerce.repository.ProductRepository;
import com.moditech.ecommerce.service.ProductRatingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/productRating")
public class ProductRatingController {

    @Autowired
    ProductRatingService productRatingService;

    @Autowired
    ProductRepository productRepository;

    @PostMapping("/rate")
    public ResponseEntity<ProductRating> rateProduct(@RequestBody ProductRating productRating) {
        ProductRating productRating1 = productRatingService.addRating(productRating);
        return ResponseEntity.ok(productRating1);
    }

    @GetMapping("/{email}/{productId}/{orderId}")
    public ResponseEntity<ProductRating> calculateAverageRating(@PathVariable String email,
            @PathVariable String productId, @PathVariable String orderId) {
        ProductRating productRating = productRatingService.getProductRatingByEmailAndProductId(email, productId,
                orderId);
        return ResponseEntity.ok(productRating);
    }

    @GetMapping("/customerRating")
    public ResponseEntity<Float> getAverageRatingPercentage() {
        float averageRatingPercentage = productRatingService.getAverageRatingPercentage();
        return ResponseEntity.ok(averageRatingPercentage);
    }

    @GetMapping("/calculate-total-rating/{productId}")
    public ResponseEntity<ProductRatingDto> calculateTotalRating(@PathVariable String productId) {
        ProductRatingDto productRating = productRatingService.calculateTotalRating(productId);
        return new ResponseEntity<>(productRating, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProductRatingById(@PathVariable String id) {
        productRatingService.deleteProductRatingById(id);
        return ResponseEntity.ok("Deleted id: " + id);
    }
}
