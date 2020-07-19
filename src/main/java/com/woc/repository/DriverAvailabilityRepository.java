package com.woc.repository;

import com.woc.entity.DriverAvailability;

import java.util.List;

public interface DriverAvailabilityRepository {

    public List<DriverAvailability> findAll();

    public DriverAvailability addDriverAvailabiliy(DriverAvailability driverAvail);

    public long toggleDriverAvailability(long driver_id, String value);

}
