package com.woc.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

public class UserCredentials {

    @Id
    private Long id;

    @Column(name = "user_name", nullable = false, length = 30)
    private String user_name;

    @Column(name = "user_pin", nullable = false, length = 30)
    private int user_pin;

    @Column(name = "created_time", nullable = false, length = 30)
    private Date created_time;

    @Column(name = "updated_time", nullable = true, length = 30)
    private Date updated_time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getUser_pin() {
        return user_pin;
    }

    public void setUser_pin(int user_pin) {
        this.user_pin = user_pin;
    }

    public Date getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }

    public Date getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(Date updated_time) {
        this.updated_time = updated_time;
    }
    
}
