package com.woc.repository;

import java.util.Collections;
import java.util.Comparator;
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

    @Override
    public Vehicle findVehicleByUserId(long userId) {
        List<Vehicle> vehicles = entityManager.createNativeQuery("select * from Vehicle v where v.user_id=" + userId, Vehicle.class).getResultList();
        Collections.sort(vehicles, new Comparator<Vehicle>() {

            @Override
            public int compare(Vehicle v1, Vehicle v2) {
                return (int) (v2.getVerificationDate().getTime() - v1.getVerificationDate().getTime());
            }
        });
        return vehicles.get(0);
    }
}
