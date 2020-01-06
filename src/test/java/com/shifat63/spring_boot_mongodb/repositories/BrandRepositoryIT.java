package com.shifat63.spring_boot_mongodb.repositories;

import com.shifat63.spring_boot_mongodb.bootstrap.InitialDataLoader;
import com.shifat63.spring_boot_mongodb.model.Brand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BrandRepositoryIT {

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    InitialDataLoader initialDataLoader;

    @BeforeEach
    void setUp() throws Exception {
        initialDataLoader.run();
    }

    @Test
    void findAllTest(){
        int brandSize = brandRepository.findAll().size();
        assertEquals(4, brandSize);
    }

    @Test
    void findByIdTest(){
        Brand brand = new Brand();
        brand.setName("TestBrand");
        brand = brandRepository.save(brand);
        assertNotEquals(Optional.empty(), brandRepository.findById(brand.getId()));
        assertEquals(Optional.empty(), brandRepository.findById("any wrong id"));
    }

    @Test
    void saveTest(){
        Brand brand = new Brand();
        brand.setName("TestBrand");
        brand = brandRepository.save(brand);
        assertNotEquals("", brand.getId()); //While new insertion id should be generated

        brand.setName("TestBrand2");
        Brand editedBrand = brandRepository.save(brand);
        assertEquals(brand.getId(), editedBrand.getId()); //While editing id remains same
    }

    @Test
    void deleteByIdTest(){
        Brand brand = new Brand();
        brand.setName("TestBrand");
        brand = brandRepository.save(brand);
        assertEquals(5, brandRepository.findAll().size());
        brandRepository.deleteById(brand.getId());
        assertEquals(4, brandRepository.findAll().size());
    }

    @Test
    void deleteAllTest(){
        assertEquals(4, brandRepository.findAll().size());
        brandRepository.deleteAll();
        assertEquals(0, brandRepository.findAll().size());
    }
}