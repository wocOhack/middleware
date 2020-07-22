package com.woc.repository;

import java.util.List;

import com.woc.entity.ServiceableArea;

public interface ServicableAreaRepository {
    public List<ServiceableArea> findAll();

    public ServiceableArea addArea(ServiceableArea r);
}
