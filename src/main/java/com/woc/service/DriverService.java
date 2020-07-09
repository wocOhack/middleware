package com.woc.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.woc.dto.Driver;
import com.woc.dto.DriverAvailability;
import com.woc.dto.DriverSearchCriteria;
import com.woc.dto.DrivingLicense;
import com.woc.dto.Rider;
import com.woc.dto.RiderSearchCriteria;
import com.woc.dto.Vehicle;
import com.woc.entity.User;
import com.woc.entity.UserCredentials;
import com.woc.repository.DriverAvailabilityRepository;
import com.woc.repository.DriverRepository;
import com.woc.repository.UserCredentialsRepository;
import com.woc.repository.UserRepository;
import com.woc.repository.VehicleRepository;

@Component
public class DriverService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    DriverAvailabilityRepository driverAvailabilityRepository;

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    UserCredentialsRepository userCredRepository;

    public void toggleDriverAvailability(long user_id, boolean status) {
        driverAvailabilityRepository.toggleDriverAvailability(user_id, status);
    }

    public long addDriver(Driver driver, Vehicle vehcile, DrivingLicense license) {

        com.woc.entity.Driver d = new com.woc.entity.Driver();
        com.woc.entity.Vehicle v = new com.woc.entity.Vehicle();
        UserCredentials cred = new UserCredentials();
        User u = new User();

        DriverSearchCriteria search = new DriverSearchCriteria();
        search.setPhoneNumber(driver.getPhoneNumber());
        Driver ifexisting = driverRepository.getDriver(search);
        if (ifexisting != null) {
            // send 400 bad request as user already exist....
            System.out.println("User already exist.....");
            return -1;
        }
        Date now = new Date(System.currentTimeMillis());
        // int userId = Math.abs(new Random().nextInt());
        // int driverId = Math.abs(new Random().nextInt());
        u.setEmail(driver.getEmail());
        // u.setId((long) userId);
        u.setName(driver.getName());
        u.setPhone(driver.getPhoneNumber());
        u.setRegistrationDate(now);
        u.setType("DRIVER");
        // System.out.println("userId : " + userId);
        // System.out.println("driverId : " + driverId);
        User createdUser = userRepository.addUser(u);

        System.out.println("userId : " + createdUser.getId());

        d.setVerified_by("Admin");
        d.setVerification_date(now);
        d.setLcense_number(license.getLicenceNumber());
        d.setLicense_doc(license.getLicenseDocumentLink());
        d.setLicense_expiry_date(license.getExpiryDate());
        d.setIs_verified(true);
        d.setAddress("NA");
        // d.setId((long) driverId);
        d.setUser_id(createdUser.getId());

        com.woc.entity.Driver createdDriver = driverRepository.addDriver(d);
        System.out.println("driverId : " + createdDriver.getId());

        v.setInsuranceDoc(vehcile.getInsuranceDocumentLink());
        v.setVehicleDoc(vehcile.getRCDocumentLink());
        v.setIsVerified(true);
        v.setVehicleNumber(vehcile.getRegistrationNumber());
        v.setVehicleType("4-Wheeler");
        v.setUser(createdUser.getId());
        vehicleRepository.addVehcile(v);

        cred.setUser_name(driver.getName());
        cred.setUser_pin(driver.getPIN());
        cred.setUser_id(createdUser.getId());
        userCredRepository.addUserCredentails(cred);
        return createdDriver.getId();

    }

    public Driver getDriver(DriverSearchCriteria search) {
        Driver d = driverRepository.getDriver(search);
        return d;
    }

    public long toggleDriverAvailability(DriverAvailability availability) {
        long result = driverAvailabilityRepository.toggleDriverAvailability(availability.getDriverID(),
                availability.getStatus());
        return result;
    }
}
