package com.shifat63.spring_boot_mongodb.repositories;

import com.shifat63.spring_boot_mongodb.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

// Author: Shifat63

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
}
