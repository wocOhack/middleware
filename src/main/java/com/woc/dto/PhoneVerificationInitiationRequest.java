package com.woc.dto;

import java.io.Serializable;

public class PhoneVerificationInitiationRequest implements Serializable {
    private String phoneNumber;
    
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
