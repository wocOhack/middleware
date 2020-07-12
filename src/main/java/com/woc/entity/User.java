package com.woc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
@NamedQuery(name = "User.findById", query = "SELECT u FROM User u where id=?1")
public class User implements Serializable {
    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", phone=" + phone + ", email=" + email + ", type=" + type
                + ", registrationDate=" + registrationDate + ", deviceId=" + deviceId + ", rating=" + rating
                + ", status=" + status + ", bloodGroup=" + bloodGroup + "]";
    }

    private static final long serialVersionUID = 1L;

    @Column(name = "id", unique = true, nullable = false, length = 30)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Name", nullable = false, length = 30)
    private String name;

    @Column(name = "phone", unique = true, nullable = false, length = 15)
    private String phone;

    @Column(name = "email", nullable = true, length = 50)
    private String email;

    @Column(name = "type", nullable = false, length = 10)
    private String type;

    @Column(name = "registration_date", nullable = false)
    private Date registrationDate;

    @Column(name = "device_id", nullable = false, length = 30)
    private String deviceId;

    @Column(name = "ratings", nullable = true)
    private Integer rating;

    @Column(name = "status", nullable = false, length = 15)
    private String status;

    @Column(name = "blood_group", nullable = true, length = 5)
    private String bloodGroup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    
}