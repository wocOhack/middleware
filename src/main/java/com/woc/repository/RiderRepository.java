package com.woc.repository;

import java.util.List;

import com.woc.dto.RiderSearchCriteria;
import com.woc.entity.Rider;

public interface RiderRepository {
    public List<Rider> findAll();

    public long addRider(Rider r);

    public com.woc.dto.Rider getRider(RiderSearchCriteria searchRider);

	public Rider findByID(long id);
    public long updateRiderPin(PINUpdateRequestObject updateReq);

    public long updateRiderData(com.woc.dto.Rider r);
}
