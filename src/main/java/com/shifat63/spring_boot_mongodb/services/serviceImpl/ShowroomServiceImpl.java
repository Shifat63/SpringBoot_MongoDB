package com.shifat63.spring_boot_mongodb.services.serviceImpl;

import com.shifat63.spring_boot_mongodb.model.Employee;
import com.shifat63.spring_boot_mongodb.model.Product;
import com.shifat63.spring_boot_mongodb.model.Showroom;
import com.shifat63.spring_boot_mongodb.repositories.EmployeeRepository;
import com.shifat63.spring_boot_mongodb.repositories.ProductRepository;
import com.shifat63.spring_boot_mongodb.repositories.ShowroomRepository;
import com.shifat63.spring_boot_mongodb.services.service.ShowroomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

// Author: Shifat63

@Slf4j
@Service
public class ShowroomServiceImpl implements ShowroomService {
    private ShowroomRepository showroomRepository;
    private ProductRepository productRepository;
    private EmployeeRepository employeeRepository;

    public ShowroomServiceImpl(ShowroomRepository showroomRepository, ProductRepository productRepository, EmployeeRepository employeeRepository) {
        this.showroomRepository = showroomRepository;
        this.productRepository = productRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Set<Showroom> findAll() throws Exception {
        log.info("start: findAll method of ShowroomServiceImpl");
        Set<Showroom> showroomSet = new HashSet<>();
        showroomRepository.findAll().forEach(showroomSet::add);
        log.info("end: findAll method of ShowroomServiceImpl");
        return showroomSet;
    }

    @Override
    public Showroom findById(String showroomId) throws Exception {
        log.info("start: findById method of ShowroomServiceImpl");
        Showroom showroom = showroomRepository.findById(showroomId).get();
        log.info("end: findById method of ShowroomServiceImpl");
        return showroom;
    }

    @Override
    public Showroom saveOrUpdate(Showroom showroom) throws Exception {
        log.info("start: saveOrUpdate method of ShowroomServiceImpl");
        Showroom savedShowroom = showroomRepository.save(showroom);
        log.info("end: saveOrUpdate method of ShowroomServiceImpl");
        return savedShowroom;
    }

    @Override
    public void deleteById(String showroomId) throws Exception {
        log.info("start: deleteById method of ShowroomServiceImpl");
        Showroom toBeDeletedShowroom = showroomRepository.findById(showroomId).get();

        //Removing this showroom for each product ShowroomSet
        for (Product eachProduct : toBeDeletedShowroom.getProductSet())
        {
            eachProduct.getShowroomSet().remove(toBeDeletedShowroom);
            productRepository.save(eachProduct);
        }

        //Deleting each employee of this showroom
        for (Employee eachEmployee : toBeDeletedShowroom.getEmployeeSet())
        {
            employeeRepository.deleteById(eachEmployee.getId());
        }
        showroomRepository.deleteById(showroomId);
        log.info("end: deleteById method of ShowroomServiceImpl");
    }

    @Override
    public void deleteAll() throws Exception {
        log.info("start: deleteAll method of ShowroomServiceImpl");
        showroomRepository.deleteAll();
        log.info("end: deleteAll method of ShowroomServiceImpl");
    }
}
