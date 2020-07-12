package com.woc.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.woc.entity.Vehicle;

@Repository
public class VehicleRepositoryImpl implements VehicleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Vehicle> findAll() {
        List<Vehicle> vehicles = entityManager.createNamedQuery("Vehicle.findAll").getResultList();
        return vehicles;
    }

    @Transactional
    @Override
    public long addVehcile(Vehicle v) {
        entityManager.persist(v);
        return v.getId();
    }

    @Override
    public long updateVehcile(com.woc.dto.Vehicle v) {
        // TODO Auto-generated method stub
        return 0;
    }
}
