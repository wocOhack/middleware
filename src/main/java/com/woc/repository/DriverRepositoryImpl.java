package com.woc.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.woc.dto.DriverSearchCriteria;
import com.woc.dto.PINUpdateRequestObject;
import com.woc.entity.Driver;
import com.woc.entity.Rider;
import com.woc.entity.User;

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
    public Driver addDriver(Driver driver) {
        // Driver d= new
        // System.out.println(driver.toString());
        entityManager.persist(driver);
        return driver;
    }

    @Override
    public com.woc.dto.Driver getDriver(DriverSearchCriteria search) {
        if (search.getPhoneNumber() != null) {
            String number = search.getPhoneNumber();
            List<User> users = entityManager
                    .createNativeQuery("select * from User u where u.phone = " + number, User.class).getResultList();
            List<com.woc.dto.Rider> allRiders = new ArrayList();
            // for (User u : users) {
            System.out.println("user length : " + users.size());

            if (users.size() == 0) {
                return null;
            }
            User u = users.get(0);
            long userId = u.getId();
            List<Driver> drivers = entityManager
                    .createNativeQuery("select * from Driver d where d.user_id = " + userId, Driver.class)
                    .getResultList();
            // for (Rider each : riders) {

            if (drivers.size() == 0) {
                return null;
            }

            Driver each = drivers.get(0);
            com.woc.dto.Driver d = new com.woc.dto.Driver();
            d.setEmail(u.getEmail());
            d.setName(u.getName());
            d.setPhoneNumber(u.getPhone());
            d.setRiderID(each.getId());
            // allRiders.add(r);
            // }

            // }
            return d;

        } else {
            long driverId = search.getDriverId();
            List<Driver> drivers = entityManager
                    .createNativeQuery("select * from Driver d where d.id = " + driverId, Driver.class).getResultList();
            Driver driver = drivers.get(0);
            long userid = driver.getUser_id();

            List<User> users = entityManager
                    .createNativeQuery("select * from User u where u.id = " + userid, User.class).getResultList();

            User u = users.get(0);
            // for (Rider each : riders) {
            com.woc.dto.Driver d = new com.woc.dto.Driver();
            d.setEmail(u.getEmail());
            d.setName(u.getName());
            // r.setPIN(u.get);
            d.setPhoneNumber(u.getPhone());
            d.setRiderID(driver.getId());
            return d;
            // } // return riders;
        }

    }

}
