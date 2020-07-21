package com.woc.service.enums;

public enum PushNotificationIdentifierEnum {

    DRIVER_VERIFICATION("DRIVER_VERIFICATION"),
    RIDE_REQUEST_FOUND("RIDE_REQUEST_FOUND"),
    RIDE_CANCELLED("RIDE_CANCELLED"),
    DRIVER_ENROUTE("DRIVER_ENROUTE"),
    TRIP_START("TRIP_START"),
    TRIP_END("TRIP_END");

    private String pushNotificationIdentifier;

    PushNotificationIdentifierEnum(String pushNotificationIdentifier) {this.pushNotificationIdentifier = pushNotificationIdentifier;}

    public String getPushNotificationIdentifier() {
        return pushNotificationIdentifier;
    }

    public void setPushNotificationIdentifier(String pushNotificationIdentifier) {
        this.pushNotificationIdentifier = pushNotificationIdentifier;
    }

    @Override
    public String toString() {
        return this.pushNotificationIdentifier;
    }
}
