package com.woc.dto;

public class MultiplePushNotificationRequestBodyDto {

    private String[] registration_ids;
    private NotificationBodyDto data;

    public String[] getRegistrationIds() {
        return registration_ids;
    }

    public void setRegistrationIds(String[] registration_ids) {
        this.registration_ids = registration_ids;
    }


    public NotificationBodyDto getData() {
        return data;
    }

    public void setData(NotificationBodyDto data) {
        this.data = data;
    }

}
