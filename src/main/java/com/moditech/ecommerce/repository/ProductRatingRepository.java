package com.moditech.ecommerce.repository;

import com.moditech.ecommerce.model.ProductRating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRatingRepository extends MongoRepository<ProductRating, String> {

    ProductRating findByEmailAndProductIdAndOrderId(String email, String productId, String orderId);

    List<ProductRating> findByProductId(String productId);
}