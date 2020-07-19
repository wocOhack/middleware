package com.woc.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "OTP")
public class OTP implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "phone", unique = true, nullable = false, length = 15)
    private String phone;

    @Column(name = "otp", unique = false, nullable = false, length = 4)
    private String otp;

    @Column(name = "created_time", unique = false, nullable = false, length = 4)
    private Date createdTime;

    public Date getCreatedTime() {
        return createdTime;
    }

    public String getPhone() {
        return phone;
    }

    public String getOtp() {
        return otp;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
