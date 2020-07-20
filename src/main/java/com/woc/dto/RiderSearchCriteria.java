package com.woc.dto;

import java.io.Serializable;

public class RiderSearchCriteria implements Serializable {

    private static final long serialVersionUID = -6779314507526973982L;

    private String phoneNumber;
    private long riderID;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getRiderId() {
        return riderID;
    }

    public void setRiderId(long riderID) {
        this.riderID = riderID;
    }

}
