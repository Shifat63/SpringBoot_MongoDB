package com.shifat63.spring_boot_mongodb.services;

import com.shifat63.spring_boot_mongodb.model.Product;
import org.bson.types.ObjectId;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product findById(ObjectId id);

    Product saveOrUpdate(Product product);

    void delete(ObjectId id);
}
