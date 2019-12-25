package com.shifat63.spring_boot_mongodb.repositories;

import com.shifat63.spring_boot_mongodb.model.Product;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, ObjectId> {
}
