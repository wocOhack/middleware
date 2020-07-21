package com.woc.dto;

public class SinglePushNotificationRequestBodyDto {

    private String to;
    private NotificationBodyDto data;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public NotificationBodyDto getData() {
        return data;
    }

    public void setData(NotificationBodyDto data) {
        this.data = data;
    }
}
