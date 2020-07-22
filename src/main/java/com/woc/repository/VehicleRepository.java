package com.woc.repository;

import com.woc.entity.Vehicle;

import java.util.List;

public interface VehicleRepository {

    public List<Vehicle> findAll();

    public long addVehcile(Vehicle v);

    public long updateVehcile(com.woc.dto.Vehicle v);

    public Vehicle findVehicleByUserId(long userId);
}
