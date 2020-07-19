package com.woc.dto;

import java.io.Serializable;

public class PhoneVerificationCompletionRequest implements Serializable {
    private String phoneNumber;
    private String OTP;

    public PhoneVerificationCompletionRequest(String phoneNumber,String OTP){
        this.phoneNumber=phoneNumber;
        this.OTP=OTP;
    }

    public String getOTP() {
        return OTP;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

}
