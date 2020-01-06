package com.shifat63.spring_boot_mongodb.services.serviceImpl;

import com.shifat63.spring_boot_mongodb.model.Employee;
import com.shifat63.spring_boot_mongodb.model.Showroom;
import com.shifat63.spring_boot_mongodb.repositories.EmployeeRepository;
import com.shifat63.spring_boot_mongodb.repositories.ShowroomRepository;
import com.shifat63.spring_boot_mongodb.services.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

// Author: Shifat63

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;
    private ShowroomRepository showroomRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ShowroomRepository showroomRepository) {
        this.employeeRepository = employeeRepository;
        this.showroomRepository = showroomRepository;
    }

    @Override
    public Set<Employee> findAll() throws Exception {
        log.info("start: findAll method of EmployeeServiceImpl");
        Set<Employee> employeeSet = new HashSet<>();
        employeeRepository.findAll().forEach(employeeSet::add);
        log.info("end: findAll method of EmployeeServiceImpl");
        return employeeSet;
    }

    @Override
    public Employee findById(String employeeId) throws Exception {
        log.info("start: findById method of EmployeeServiceImpl");
        Employee employee = employeeRepository.findById(employeeId).get();
        log.info("end: findById method of EmployeeServiceImpl");
        return employee;
    }

    @Override
    public Employee saveOrUpdate(Employee employee) throws Exception {
        log.info("start: saveOrUpdate method of EmployeeServiceImpl");
        Employee savedEmployee = new Employee();
        if(employee.getId() != null)
        {
            Employee previousInstanceOfEmployee = employeeRepository.findById(employee.getId()).get();
            if(previousInstanceOfEmployee.getShowroom().getId() != employee.getShowroom().getId())
            {
                Showroom previousShowroom = previousInstanceOfEmployee.getShowroom();
                previousShowroom.getEmployeeSet().remove(employee);
                showroomRepository.save(previousShowroom);

                Showroom newShowroom = employee.getShowroom();
                newShowroom.getEmployeeSet().add(employee);
                showroomRepository.save(newShowroom);
            }
            savedEmployee = employeeRepository.save(employee);
        }
        else
        {
            savedEmployee = employeeRepository.save(employee);
            Showroom newShowroom = savedEmployee.getShowroom();
            newShowroom.getEmployeeSet().add(savedEmployee);
            showroomRepository.save(newShowroom);
        }

        log.info("end: saveOrUpdate method of EmployeeServiceImpl");
        return savedEmployee;
    }

    @Override
    public void deleteById(String employeeId) throws Exception {
        log.info("start: deleteById method of EmployeeServiceImpl");
        Employee toBeDeletedEmployee = employeeRepository.findById(employeeId).get();

        //Removing this employee from his showroom
        Showroom showroom = toBeDeletedEmployee.getShowroom();
        showroom.getEmployeeSet().remove(toBeDeletedEmployee);
        showroomRepository.save(showroom);

        employeeRepository.deleteById(employeeId);
        log.info("end: deleteById method of EmployeeServiceImpl");
    }

    @Override
    public void deleteAll() throws Exception {
        log.info("start: deleteAll method of EmployeeServiceImpl");
        employeeRepository.deleteAll();
        log.info("end: deleteAll method of EmployeeServiceImpl");
    }
}
