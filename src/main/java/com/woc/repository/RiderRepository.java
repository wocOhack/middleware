package com.woc.repository;

import java.util.List;

import com.woc.dto.PINUpdateRequestObject;
import com.woc.dto.RiderSearchCriteria;
import com.woc.entity.Rider;

public interface RiderRepository {
    public List<Rider> findAll();

    public void addRider(Rider r);

    public com.woc.dto.Rider getRider(RiderSearchCriteria searchRider);

    public void updateRiderPin(PINUpdateRequestObject updateReq);

	public Rider findByID(long id);
}
