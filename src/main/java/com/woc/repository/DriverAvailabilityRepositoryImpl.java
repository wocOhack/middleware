package com.woc.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.woc.entity.Driver;
import com.woc.entity.DriverAvailability;

@Repository
public class DriverAvailabilityRepositoryImpl implements DriverAvailabilityRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<DriverAvailability> findAll() {
        List<DriverAvailability> driverAvailabilityList = entityManager
                .createNamedQuery("DriverAvailability.findAll", DriverAvailability.class).getResultList();
        return driverAvailabilityList;
    }

    @Transactional
    @Override
    public DriverAvailability addDriverAvailabiliy(DriverAvailability driverAvail) {
        // Driver d= new
        // System.out.println(driver.toString());
        entityManager.persist(driverAvail);
        return driverAvail;
    }

    @Transactional
    @Override
    public long toggleDriverAvailability(long driverId, String value) {
        // TODO Auto-generated method stub
        List<Driver> drivers = entityManager
                .createNativeQuery("select * from Driver d where d.id = " + driverId, Driver.class).getResultList();
        if (drivers.size() == 0) {
            // Create driver availability row.
            // DriverAvailability availability = new DriverAvailability();
            // availability.setStatus(String.valueOf(value));
            // availability.setUser(user_id);
            return -1;
        }
        long user_id = drivers.get(0).getUser_id();
        System.out.println("user_id : " + user_id);

        List<DriverAvailability> das = entityManager
                .createNativeQuery("select * from Driver_Availability da where da.user_id = " + user_id,
                        DriverAvailability.class)
                .getResultList();
        if (das.size() == 0) {
            DriverAvailability da = new DriverAvailability();
            da.setStatus(String.valueOf(value));
            da.setUser(user_id);
            da.setCreatedTime(new Date(System.currentTimeMillis()));
            DriverAvailability created = addDriverAvailabiliy(da);
            if (da.getId() == 0) {
                return -1l;
            }
            return 0l;
        }
        // entityManager.getTransaction().begin();
        Date updated_time = new Date(System.currentTimeMillis());

        Query q = entityManager.createNativeQuery("Update Driver_Availability da set da.status = "
                + String.valueOf(value) + ",da.updated_time = :updated_time" + " where user_id = " + user_id);
        q.setParameter("updated_time", updated_time);
        int rowsUpdated = q.executeUpdate();
        System.out.println("entities Updated: " + rowsUpdated);
        // entityManager.getTransaction().commit();
        // entityManager.close();
        return 0l;
    }
}
