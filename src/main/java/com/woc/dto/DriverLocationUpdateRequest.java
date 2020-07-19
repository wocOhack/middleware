package com.woc.dto;

public class DriverLocationUpdateRequest {

    long driverId;
    String location;
    public long getDriverId() {
        return driverId;
    }
    public void setDriverId(long driverId) {
        this.driverId = driverId;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    
    
}
