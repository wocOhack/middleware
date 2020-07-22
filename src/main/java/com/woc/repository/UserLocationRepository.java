package com.woc.repository;

import com.woc.entity.UserLocation;

import java.util.List;

public interface UserLocationRepository {

    public List<UserLocation> findAll();
}
