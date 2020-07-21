package com.woc.dto;

public class NotificationBodyDto {

    private String identifier;
    private Object payload;

    public NotificationBodyDto(String identifier, Object payload) {
        this.identifier = identifier;
        this.payload = payload;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }
}
