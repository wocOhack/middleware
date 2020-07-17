package com.woc.dto;

import java.io.Serializable;

public class PhoneVerificationInitiationRequest implements Serializable {
    private String phoneNumber;

    public PhoneVerificationInitiationRequest(String phoneNumber){
        this.phoneNumber=phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
