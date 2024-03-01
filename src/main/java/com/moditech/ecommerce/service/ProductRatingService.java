package com.moditech.ecommerce.service;

import com.moditech.ecommerce.dto.ProductRatingDto;
import com.moditech.ecommerce.model.ProductRating;
import com.moditech.ecommerce.repository.ProductRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.OptionalDouble;

@Service
public class ProductRatingService {

    @Autowired
    ProductRatingRepository productRatingRepository;

    public ProductRating addRating(ProductRating productRating) {
        return productRatingRepository.save(productRating);
    }

    public ProductRating getProductRatingByEmailAndProductId(String email, String productId, String orderId) {
        return productRatingRepository.findByEmailAndProductIdAndOrderId(email, productId, orderId);
    }

    public float getAverageRatingPercentage() {
        List<ProductRating> productRatings = productRatingRepository.findAll();
        float sumRatings = productRatings.stream().map(ProductRating::getRating).reduce(0, Integer::sum);
        float averageRating = sumRatings / productRatings.size();
        return averageRating * 100 / 5;
    }

    public ProductRatingDto calculateTotalRating(String productId) {
        List<ProductRating> productRatings = productRatingRepository.findByProductId(productId);
        OptionalDouble averageRating = productRatings.stream().map(ProductRating::getRating).mapToDouble(value -> value)
                .average();
        Double totalRating = averageRating.orElse(0.0);
        return new ProductRatingDto(productId, totalRating);
    }

    public String deleteProductRatingById(String id) {
        productRatingRepository.deleteById(id);
        return "Deleted id: " + id;
    }

}
