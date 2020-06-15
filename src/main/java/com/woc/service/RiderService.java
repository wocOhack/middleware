package com.woc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.woc.entity.ServiceableArea;
import com.woc.repository.ServicableAreaRepository;

@Component
public class RiderService {

    @Autowired
    ServicableAreaRepository servicableAreaRepository;

    public ServiceableArea addArea(ServiceableArea area) {
        return servicableAreaRepository.addArea(area);
    }

    public Iterable<ServiceableArea> getAllAreas() {
        return servicableAreaRepository.findAll();
    }

    RiderService() {
        super();
    }

}
