package com.woc.dto;

public class StartRideResponseDto {

    private Long tripId;

    public StartRideResponseDto() {}

    public StartRideResponseDto(Long tripId) {
        this.tripId = tripId;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }
}
