package com.woc.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.woc.dto.DriverAvailability;
import com.woc.dto.DriverLocationUpdateRequest;
import com.woc.dto.DriverSearchCriteria;
import com.woc.dto.DrivingLicense;
import com.woc.entity.Driver;
import com.woc.entity.User;
import com.woc.entity.Vehicle;

@Repository
public class DriverRepositoryImpl implements DriverRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Driver> findAll() {

        List<Driver> drivers = entityManager.createNamedQuery("Driver.findAll", Driver.class).getResultList();
        return drivers;
    }

    @Override
    public Driver findByID(long id) {
        List<Driver> drivers = entityManager.createNamedQuery("Driver.findById").setParameter(1, id).getResultList();
        if (!drivers.isEmpty()) {
            return drivers.get(0);
        }
        return null;

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

            List<Vehicle> vehciles = entityManager
                    .createNativeQuery("select * from Vehicle v where v.user_id = " + userId, Vehicle.class)
                    .getResultList();

            Map<String, String> vehiclesData = new HashMap<String, String>();
            for (Vehicle each : vehciles) {
                vehiclesData.put("vehicle1", each.toString());
            }
            Driver each = drivers.get(0);
            com.woc.dto.Driver d = new com.woc.dto.Driver();
            d.setEmail(u.getEmail());
            d.setName(u.getName());
            d.setPhoneNumber(u.getPhone());
            d.setDriverID(each.getId());
            d.setUserID(userId);
            d.setDocuments(vehiclesData);
            d.setAddress(each.getAddress());
            d.setStatus(each.getStatus());
            d.setDeviceID(each.getDeviceID());
            // allRiders.add(r);
            // }

            // }
            return d;

        } else {
            long driverId = search.getDriverID();
            List<Driver> drivers = entityManager
                    .createNativeQuery("select * from Driver d where d.id = " + driverId, Driver.class).getResultList();
            Driver driver = drivers.get(0);
            long userid = driver.getUser_id();

            List<User> users = entityManager
                    .createNativeQuery("select * from User u where u.id = " + userid, User.class).getResultList();

            User u = users.get(0);

            List<Vehicle> vehciles = entityManager
                    .createNativeQuery("select * from Vehicle v where v.user_id = " + u.getId(), Vehicle.class)
                    .getResultList();

            Map<String, String> vehiclesData = new HashMap<String, String>();
            for (Vehicle each : vehciles) {
                vehiclesData.put("vehicle1", each.toString());
            }
            // for (Rider each : riders) {
            com.woc.dto.Driver d = new com.woc.dto.Driver();
            d.setEmail(u.getEmail());
            d.setName(u.getName());
            // r.setPIN(u.get);
            d.setPhoneNumber(u.getPhone());
            d.setDriverID(driver.getId());
            d.setUserID(u.getId());
            d.setDocuments(vehiclesData);
            d.setDeviceID(driver.getDeviceID());
            return d;
            // } // return riders;
        }

    }

    @Transactional
    @Override
    public long updateDriverData(com.woc.dto.Driver d, DrivingLicense license) {
        // TODO Auto-generated method stub
        // One thing to be updated at a time.
        long driver_id = 0l;

        if (d.getPhoneNumber() != null && !d.getPhoneNumber().trim().isEmpty()) {
            DriverSearchCriteria search = new DriverSearchCriteria();
            search.setPhoneNumber(d.getPhoneNumber());
            com.woc.dto.Driver fetched_driver = getDriver(search);
            driver_id = fetched_driver.getDriverID();
        } else {
            driver_id = d.getDriverID();
        }

        long rowsUpdated = 0;
        if (d.getAddress() != null && !d.getAddress().trim().isEmpty()) {
            Query q = entityManager
                    .createNativeQuery("Update Driver d set d.address = :address where d.id = " + driver_id);
            q.setParameter("address", d.getAddress());

            rowsUpdated = q.executeUpdate();
            // return rowsUpdated;
        }
        if (license.getExpiryDate() != null
                && (license.getLicenseDocumentLink() != null && !license.getLicenseDocumentLink().trim().isEmpty())
                && license.getLicenceNumber() != null && !license.getLicenceNumber().trim().isEmpty()) {
            Query q = entityManager.createNativeQuery(
                    "Update Driver d set d.license_doc = :license_doc, d.license_expiry_date = :expiry_date, d.lcense_number = :license_number where d.id = "
                            + driver_id);
            q.setParameter("license_doc", license.getLicenseDocumentLink());
            q.setParameter("expiry_date", license.getExpiryDate());
            q.setParameter("license_number", license.getLicenceNumber());
            rowsUpdated = q.executeUpdate();
            return rowsUpdated;
        }

        if (license.getExpiryDate() != null) {
            Query q = entityManager.createNativeQuery(
                    "Update Driver d set d.license_expiry_date = :expiry_date where d.id = " + driver_id);
            // q.setParameter("license_doc", license.getLicenseDocumentLink());
            q.setParameter("expiry_date", license.getExpiryDate());
            // q.setParameter("license_number", license.getLicenceNumber());
            rowsUpdated = q.executeUpdate();
//            return rowsUpdated;
        }
       

        if ((license.getLicenseDocumentLink() != null && !license.getLicenseDocumentLink().trim().isEmpty())) {
            Query q = entityManager.createNativeQuery(
                    "Update Driver d set d.license_doc = :license_doc where d.id = "
                            + driver_id);
            q.setParameter("license_doc", license.getLicenseDocumentLink());
//            q.setParameter("expiry_date", license.getExpiryDate());
            rowsUpdated = q.executeUpdate();
//            return rowsUpdated;
        }
        
        if ((license.getLicenceNumber() != null && !license.getLicenceNumber().trim().isEmpty())) {
            Query q = entityManager.createNativeQuery(
                    "Update Driver d set d.lcense_number = :license_num where d.id = "
                            + driver_id);
            q.setParameter("license_num", license.getLicenceNumber());
           
            rowsUpdated = q.executeUpdate();
//            return rowsUpdated;
        }
        return rowsUpdated;
    }

    public List<Driver> getAllDriversWithStatus(String status) {

        List<Driver> drivers = entityManager.createNamedQuery("Driver.findAllWithStatus", Driver.class)
                .setParameter(1, status).getResultList();
        return drivers;
    }

    @Transactional
    @Override
    public long updateDriverStatus(DriverAvailability availability) {
        long updated = 0;
        Query q = entityManager
                .createNativeQuery("Update Driver d set d.status = :status where d.id = " + availability.getDriverID());
        q.setParameter("status", availability.getStatus());
        updated = q.executeUpdate();
        return updated;
    }
    
    @Transactional
    @Override
    public long updateDriverLocation(DriverLocationUpdateRequest request) {
        long updated = 0;
        Query q = entityManager
                .createNativeQuery("Update Driver d set d.location = :location where d.id = " + request.getDriverId());
        q.setParameter("location", request.getLocation());
        updated = q.executeUpdate();
        return updated;
    }

    @Transactional
    @Override
	public void updateDriversStatus(String status, List<Long> driverIds) {
		
		for (Long driverid : driverIds) {
			Driver driver = findByID(driverid);
			driver.setStatus("status");
			entityManager.merge(driver);
		}

	}
  
}
