package com.woc.dto;

public class StartRideRequestDto {

    private long rideRequestId;
    private String pin;

    public long getRideRequestId() {
        return rideRequestId;
    }

    public void setRideRequestId(long rideRequestId) {
        this.rideRequestId = rideRequestId;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
