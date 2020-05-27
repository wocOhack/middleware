package com.woc.repository;

import com.woc.entity.Trip;

import java.util.List;

public interface TripRepository {

    public List<Trip> findAll();
}
