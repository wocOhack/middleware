package com.woc.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "DriverAvailability")
@NamedQuery(name = "DriverAvailability.findAll", query = "SELECT d FROM DriverAvailability d")
public class DriverAvailability implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "id", unique = true, nullable = false, length = 30)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false, length = 20)
    private long user;

    @Column(name = "status",  nullable = false,length = 20)
    private String status;

    @Column(name = "created_time" ,nullable = true, length = 30)
    private Date createdTime;

    @Column(name = "updated_time",  nullable = true, length = 30)
    private Date updatedTime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user_id) {
        this.user = user_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
}
