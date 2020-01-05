package com.shifat63.spring_boot_mongodb.services.serviceImpl;

import com.shifat63.spring_boot_mongodb.model.Brand;
import com.shifat63.spring_boot_mongodb.model.Product;
import com.shifat63.spring_boot_mongodb.model.Showroom;
import com.shifat63.spring_boot_mongodb.repositories.BrandRepository;
import com.shifat63.spring_boot_mongodb.repositories.ProductRepository;
import com.shifat63.spring_boot_mongodb.repositories.ShowroomRepository;
import com.shifat63.spring_boot_mongodb.services.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

// Author: Shifat63

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private BrandRepository brandRepository;
    private ShowroomRepository showroomRepository;

    public ProductServiceImpl(ProductRepository productRepository, BrandRepository brandRepository, ShowroomRepository showroomRepository) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.showroomRepository = showroomRepository;
    }

    @Override
    public Set<Product> findAll() throws Exception {
        log.info("start: findAll method of ProductServiceImpl");
        Set<Product> productSet = new HashSet<>();
        productRepository.findAll().forEach(productSet::add);
        log.info("end: findAll method of ProductServiceImpl");
        return productSet;
    }

    @Override
    public Product findById(String productId) throws Exception {
        log.info("start: findById method of ProductServiceImpl");
        Product product = productRepository.findById(productId).get();
        log.info("end: findById method of ProductServiceImpl");
        return product;
    }

    @Override
    public Product saveOrUpdate(Product product) throws Exception {
        log.info("start: saveOrUpdate method of ProductServiceImpl");
        Product savedProduct = new Product();
        Brand oldBrandOfProduct = new Brand();
        Brand newBrandOfProduct = new Brand();

        if(product.getId() != null) //Update existing product
        {
            Product oldProduct = productRepository.findById(product.getId()).get();
            if(product.getBrand() != null) //Updated product has brand
            {
                if(oldProduct.getBrand() != null) //previous product had brand
                {
                    if (product.getBrand().getId() != oldProduct.getBrand().getId()) // Updated product bandId and previous product bandId do not match
                    {
                        //Updating previous brand reference
                        oldBrandOfProduct = oldProduct.getBrand();
                        oldBrandOfProduct.getProductSet().remove(product);
                        brandRepository.save(oldBrandOfProduct);

                        //Updating new brand reference
                        newBrandOfProduct = product.getBrand();
                        newBrandOfProduct.getProductSet().add(product);
                        brandRepository.save(newBrandOfProduct);
                    }
                }
                else //previous product does not have brand
                {
                    //Updating new brand reference
                    newBrandOfProduct = product.getBrand();
                    newBrandOfProduct.getProductSet().add(product);
                    brandRepository.save(newBrandOfProduct);
                }
            }
            else //Updated product does not have brand
            {
                if(oldProduct.getBrand() != null) //previous product had brand
                {
                    //Updating previous brand reference
                    oldBrandOfProduct = oldProduct.getBrand();
                    oldBrandOfProduct.getProductSet().remove(product);
                    brandRepository.save(oldBrandOfProduct);
                }
            }

            if(product.getShowroomSet()!=null && product.getShowroomSet().size()!=0) //updated product has showroom
            {
                if(oldProduct.getShowroomSet()!=null && oldProduct.getShowroomSet().size()!=0) //previous product had showroom
                {
                    for (Showroom eachShowroomOld : oldProduct.getShowroomSet())
                    {
                        if(!product.getShowroomSet().contains(eachShowroomOld))
                        {
                            eachShowroomOld.getProductSet().remove(product);
                            showroomRepository.save(eachShowroomOld);
                        }
                    }
                    for (Showroom eachShowroomNew : product.getShowroomSet())
                    {
                        if(!oldProduct.getShowroomSet().contains(eachShowroomNew))
                        {
                            eachShowroomNew.getProductSet().add(product);
                            showroomRepository.save(eachShowroomNew);
                        }
                    }
                }
                else //previous product does not have showroom
                {
                    for (Showroom eachShowroomNew : product.getShowroomSet())
                    {
                        eachShowroomNew.getProductSet().add(product);
                        showroomRepository.save(eachShowroomNew);
                    }
                }
            }
            else //updated product does not have showroom
            {
                for (Showroom eachShowroomOld : oldProduct.getShowroomSet())
                {
                    eachShowroomOld.getProductSet().remove(product);
                    showroomRepository.save(eachShowroomOld);
                }
            }

            savedProduct = productRepository.save(product);
        }
        else //Adding new product
        {
            savedProduct = productRepository.save(product);

            //If new product has brand
            if(savedProduct.getBrand() != null) {
                //Updating new brand reference
                newBrandOfProduct = savedProduct.getBrand();
                newBrandOfProduct.getProductSet().add(savedProduct);
                brandRepository.save(newBrandOfProduct);
            }

            //If new product has showrooms
            if(savedProduct.getShowroomSet()!=null && savedProduct.getShowroomSet().size()!=0)
            {
                //Updating new showroom reference
                for (Showroom eachShowroom : savedProduct.getShowroomSet()) {
                    eachShowroom.getProductSet().add(savedProduct);
                    showroomRepository.save(eachShowroom);
                }
            }
        }

        log.info("end: saveOrUpdate method of ProductServiceImpl");
        return savedProduct;
    }

    @Override
    public void deleteById(String productId) throws Exception {
        log.info("start: deleteById method of ProductServiceImpl");
        Product toBeDeletedProduct = productRepository.findById(productId).get();

        //Removing this product from it's brand's productSet
        Brand brandOfProduct = toBeDeletedProduct.getBrand();
        brandOfProduct.getProductSet().remove(toBeDeletedProduct);
        brandRepository.save(brandOfProduct);

        //Removing this product from each showroom's productSet
        for (Showroom eachShowroom : toBeDeletedProduct.getShowroomSet())
        {
            eachShowroom.getProductSet().remove(toBeDeletedProduct);
            showroomRepository.save(eachShowroom);
        }

        productRepository.deleteById(productId);
        log.info("end: deleteById method of ProductServiceImpl");
    }
}
