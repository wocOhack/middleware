package com.woc.repository;

import com.woc.entity.Vehicle;

import java.util.List;

public interface VehicleRepository {

    public List<Vehicle> findAll();
}
