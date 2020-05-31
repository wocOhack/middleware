package com.woc.repository;

import java.util.List;

import com.woc.entity.Driver;

public interface DriverRepository {

    public List<Driver> findAll();

    public void addDriver(Driver driver);
}
