package com.woc.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.woc.entity.Driver;

@Repository
public class DriverRepositoryImpl implements DriverRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Driver> findAll() {
        List<Driver> drivers = entityManager.createNamedQuery("Driver.findAll").getResultList();
        return drivers;
    }

}
