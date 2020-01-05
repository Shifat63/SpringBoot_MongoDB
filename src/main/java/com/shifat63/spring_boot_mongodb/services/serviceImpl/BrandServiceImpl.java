package com.shifat63.spring_boot_mongodb.services.serviceImpl;

import com.shifat63.spring_boot_mongodb.model.Brand;
import com.shifat63.spring_boot_mongodb.model.Product;
import com.shifat63.spring_boot_mongodb.model.Showroom;
import com.shifat63.spring_boot_mongodb.repositories.BrandRepository;
import com.shifat63.spring_boot_mongodb.repositories.ProductRepository;
import com.shifat63.spring_boot_mongodb.repositories.ShowroomRepository;
import com.shifat63.spring_boot_mongodb.services.service.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

// Author: Shifat63

@Slf4j
@Service
public class BrandServiceImpl implements BrandService {
    private BrandRepository brandRepository;
    private ShowroomRepository showroomRepository;
    private ProductRepository productRepository;

    public BrandServiceImpl(BrandRepository brandRepository, ShowroomRepository showroomRepository, ProductRepository productRepository) {
        this.brandRepository = brandRepository;
        this.showroomRepository = showroomRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Set<Brand> findAll() throws Exception {
        log.info("start: findAll method of BrandServiceImpl");
        Set<Brand> brandSet = new HashSet<>();
        brandRepository.findAll().forEach(brandSet::add);
        log.info("end: findAll method of BrandServiceImpl");
        return brandSet;
    }

    @Override
    public Brand findById(String brandId) throws Exception {
        log.info("start: findById method of BrandServiceImpl");
        Brand brand = brandRepository.findById(brandId).get();
        log.info("end: findById method of BrandServiceImpl");
        return brand;
    }

    @Override
    public Brand saveOrUpdate(Brand brand) throws Exception {
        log.info("start: saveOrUpdate method of BrandServiceImpl");
        Brand savedBrand = brandRepository.save(brand);
        log.info("end: saveOrUpdate method of BrandServiceImpl");
        return savedBrand;
    }

    @Override
    public void deleteById(String brandId) throws Exception {
        log.info("start: deleteById method of BrandServiceImpl");
        Brand toBeDeletedBrand = brandRepository.findById(brandId).get();

        //Deleting each product of this brand
        for (Product eachProduct : toBeDeletedBrand.getProductSet())
        {
            //Deleting this product from each showroom ProductSet
            for (Showroom eachShowroom : eachProduct.getShowroomSet())
            {
                eachShowroom.getProductSet().remove(eachProduct);
                showroomRepository.save(eachShowroom);
            }
            productRepository.deleteById(eachProduct.getId());
        }
        brandRepository.deleteById(brandId);
        log.info("end: deleteById method of BrandServiceImpl");
    }
}
