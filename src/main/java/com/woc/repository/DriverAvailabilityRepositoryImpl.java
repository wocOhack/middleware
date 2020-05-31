package com.woc.repository;

import com.woc.entity.DriverAvailability;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class DriverAvailabilityRepositoryImpl implements DriverAvailabilityRepository{

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<DriverAvailability> findAll() {
        List<DriverAvailability> driverAvailabilityList = entityManager.createNamedQuery("DriverAvailability.findAll").getResultList();
        return driverAvailabilityList;
    }
}
