package com.woc.dto;

import java.io.Serializable;

public class DriverVerificationCompletionReply implements Serializable {
    private Boolean isVerified;
    private Boolean isExistingUser;
    private Driver driver;

    public DriverVerificationCompletionReply(Boolean isVerified,Boolean isExistingUser,Driver rider){
        this.isVerified=isVerified;
        this.isExistingUser=isExistingUser;
        this.driver =rider;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }

    public Boolean getIsExistingUser() {
        return isExistingUser;
    }

    public Driver getUser() {
        return driver;
    }
}
