package com.shifat63.spring_boot_mongodb.services.service;

import java.util.Set;

// Author: Shifat63

public interface CrudService<T, Id>
{
    Set<T> findAll() throws Exception;
    T findById(Id id) throws Exception;
    T saveOrUpdate(T object) throws Exception;
    void deleteById(Id id) throws Exception;
    void deleteAll() throws Exception;
}
