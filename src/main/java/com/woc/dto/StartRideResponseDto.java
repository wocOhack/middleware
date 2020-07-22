package com.woc.dto;

public class StartRideResponseDto {

    private long tripId;
    private String code;
    private String message;

    public StartRideResponseDto() {}

    public StartRideResponseDto(long tripId, String code, String message) {
        this.tripId = tripId;
        this.code = code;
        this.message = message;
    }

    public StartRideResponseDto(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getTripId() {
        return tripId;
    }

    public void setTripId(long tripId) {
        this.tripId = tripId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
