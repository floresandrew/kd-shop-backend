package com.moditech.ecommerce.repository;

import com.moditech.ecommerce.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByCreatedAtAfter(LocalDateTime timestamp);

    List<Product> findByIsPreOrder(Boolean isPreOrder);

    List<Product> findByCategoryName(String categoryName);
}
