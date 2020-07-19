package com.woc.dto;

import java.io.Serializable;

public class RiderVerificationCompletionReply implements Serializable {
    private Boolean isVerified;
    private Boolean isExistingUser;
    private Rider rider;

    public RiderVerificationCompletionReply(Boolean isVerified,Boolean isExistingUser,Rider rider){
        this.isVerified=isVerified;
        this.isExistingUser=isExistingUser;
        this.rider=rider;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }

    public Boolean getIsExistingUser() {
        return isExistingUser;
    }

    public Rider getUser() {
        return rider;
    }
}
