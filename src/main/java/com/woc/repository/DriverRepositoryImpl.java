package com.woc.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.woc.entity.Driver;

@Repository
public class DriverRepositoryImpl implements DriverRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Driver> findAll() {
            
        List<Driver> drivers = entityManager.createNamedQuery("Driver.findAll", Driver.class).getResultList();
        return drivers;
    }

    @Transactional
    @Override
    public void addDriver(Driver driver) {
        // Driver d= new
        // System.out.println(driver.toString());
        entityManager.persist(driver);
    }
}
