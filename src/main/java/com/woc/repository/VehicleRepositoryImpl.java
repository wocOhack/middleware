package com.woc.repository;

import com.woc.entity.Vehicle;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class VehicleRepositoryImpl implements VehicleRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Vehicle> findAll() {
        List<Vehicle> vehicles = entityManager.createNamedQuery("Vehicle.findAll").getResultList();
        return vehicles;
    }
}
