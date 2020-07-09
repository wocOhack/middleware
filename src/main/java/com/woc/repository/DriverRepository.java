package com.woc.repository;

import java.util.List;

import com.woc.dto.DriverSearchCriteria;
import com.woc.entity.Driver;

public interface DriverRepository {

    public List<Driver> findAll();

    public Driver addDriver(Driver driver);

    public com.woc.dto.Driver getDriver(DriverSearchCriteria search);

}
