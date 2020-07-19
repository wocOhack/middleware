package com.woc.service.enums;

public enum TripStatus {
    TRIP_CREATED("C"),
    TRIP_ACCEPTED("A"),
    TRIP_IN_PROGRESS("P"),
    TRIP_ENDED("E"),
    TRIP_CANCELLED_BY_DRIVER("CD"),
    TRIP_CANCELLED_BY_RIDER("CR");

    private String tripStatus;

    TripStatus(String tripStatus) {
        this.tripStatus = tripStatus;
    }

    public String getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(String tripStatus) {
        this.tripStatus = tripStatus;
    }

    @Override
    public String toString() {
        return this.tripStatus;
    }
}
