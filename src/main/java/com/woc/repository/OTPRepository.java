package com.woc.repository;


import com.woc.entity.OTP;

public interface OTPRepository {
    public void addOTP(OTP otp);

    public OTP getOTP(String phoneNumber);

    public void removeOTP(OTP otp);
}
