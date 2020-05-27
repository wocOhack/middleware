package com.woc.repository;

import com.woc.entity.DriverAvailability;

import java.util.List;

public interface DriverAvailabilityRepository {

    public List<DriverAvailability> findAll();
}
