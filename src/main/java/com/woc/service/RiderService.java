package com.woc.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.woc.dto.Rider;
import com.woc.dto.RiderSearchCriteria;
import com.woc.entity.ServiceableArea;
import com.woc.entity.User;
import com.woc.repository.RiderRepository;
import com.woc.repository.ServicableAreaRepository;
import com.woc.repository.UserRepository;

@Component
public class RiderService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RiderRepository riderRepository;

    @Autowired
    ServicableAreaRepository servicableAreaRepository;

    public ServiceableArea addArea(ServiceableArea area) {
        return servicableAreaRepository.addArea(area);
    }

    public Iterable<ServiceableArea> getAllAreas() {
        return servicableAreaRepository.findAll();
    }

    public void addRider(Rider rider) {
        System.out.println("Adding rider");
        Date now = new Date(System.currentTimeMillis());

        com.woc.entity.Rider r = new com.woc.entity.Rider();
        User u = new User();
        
        RiderSearchCriteria search =  new RiderSearchCriteria();
        search.setPhoneNumber(rider.getPhoneNumber());
        Rider ifexisting = riderRepository.getRider(search);
        if (ifexisting == null) {
            // send 400 bad request as user already exist.... 
            System.out.println("User already exist.....");
        }
        u.setPhone(rider.getPhoneNumber());
        u.setEmail(rider.getEmail());
        u.setName(rider.getName());
        u.setType("RIDER");
        u.setRegistrationDate(now);

        userRepository.addUser(u);
        System.out.println("userId : " + u.getId());

        r.setIs_verified(true);
        r.setPin(rider.getPIN());
        // Map<String, String> documents = new HashMap<String, String>();
        // r.setProof_of_challenge(rider.get);
        r.setVerification_date(now);
        r.setUser_id(u.getId());
        r.setIs_challenged(true);
        riderRepository.addRider(r);
    }

    public Rider getRider(RiderSearchCriteria search) {
        Rider rider = riderRepository.getRider(search);
        return rider;
    }

    RiderService() {
        super();
    }

}
