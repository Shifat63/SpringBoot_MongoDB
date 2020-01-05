package com.shifat63.spring_boot_mongodb.repositories;

import com.shifat63.spring_boot_mongodb.model.Brand;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

// Author: Shifat63

@Repository
public interface BrandRepository extends MongoRepository<Brand, String> {
}
