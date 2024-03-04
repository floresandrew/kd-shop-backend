package com.moditech.ecommerce.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.moditech.ecommerce.model.Email;

@Repository
public interface EmailRepository extends MongoRepository<Email, String> {
    Email findTopByEmailOrderByCreatedAtDesc(String email);
}
