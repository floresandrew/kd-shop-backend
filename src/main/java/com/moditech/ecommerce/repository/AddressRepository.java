package com.moditech.ecommerce.repository;

import com.moditech.ecommerce.model.Address;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends MongoRepository<Address, String> {
    List<Address> findByEmail(String email);

    Address findByEmailAndIsDefault(String email, Boolean isDefault);
}
