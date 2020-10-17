package com.woc.service.enums;

public enum DriverStatusEnum {
    AVAILABLE("Available"),
    UNAVAILABLE("UnAvailable"),
    NOTIFIED("Notified");

    private String driverStatus;

    DriverStatusEnum(String driverStatus) {
        this.driverStatus = driverStatus;
    }

    public String getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(String driverStatus) {
        this.driverStatus = driverStatus;
    }

    @Override
    public String toString() {
        return this.driverStatus;
    }
}
