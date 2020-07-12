package com.woc.repository;

import java.util.List;

import com.woc.dto.DriverSearchCriteria;
import com.woc.entity.Driver;
import com.woc.entity.User;

public interface DriverRepository {

    public List<Driver> findAll();
    
	public Driver findByID(long id);

    public Driver addDriver(Driver driver);

    public com.woc.dto.Driver getDriver(DriverSearchCriteria search);

	public List<Driver> getAllDriversWithStatus(String status);
}