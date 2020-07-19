package com.woc.dto;

import java.io.Serializable;

public class PhoneVerificationCompletionRequest implements Serializable {
    private String phoneNumber;
    private String OTP;
    
    public void setOTP(String OTP) {
        this.OTP = OTP;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOTP() {
        return OTP;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

}
