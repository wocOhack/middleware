package com.woc.dto;

import java.io.Serializable;

public class DriverAvailability implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private long driverID;
    private boolean status;

    public long getDriverID() {
        return driverID;
    }

    public void setDriverID(long driverID) {
        this.driverID = driverID;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


}
