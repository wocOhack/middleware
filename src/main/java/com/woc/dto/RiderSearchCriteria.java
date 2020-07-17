package com.woc.dto;

import java.io.Serializable;

public class RiderSearchCriteria implements Serializable {

    private static final long serialVersionUID = -6779314507526973982L;

    private String phoneNumber;
    private long riderId;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getRiderId() {
        return riderId;
    }

    public void setRiderId(long riderId) {
        this.riderId = riderId;
    }

}
