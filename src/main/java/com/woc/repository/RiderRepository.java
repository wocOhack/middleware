package com.woc.repository;

import java.util.List;

import com.woc.entity.Rider;

public interface RiderRepository {
    public List<Rider> findAll();
    public void addRider(Rider r);
}
