package com.woc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "DRIVER")
@NamedQuery(name = "Driver.findAll", query = "SELECT d FROM Driver d")
public class Driver implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, length = 30)
    private long user_id;

    @Column(name = "lcense_number", nullable = false, length = 30)
    private String lcense_number;

    @Column(name = "license_expiry_date", nullable = true, length = 30)
    private Date license_expiry_date;

    @Column(name = "license_doc", nullable = true, length = 30)
    private String license_doc;

    @Column(name = "is_verified", nullable = true, length = 30)
    private Boolean is_verified;

    @Column(name = "verification_date", nullable = true, length = 30)
    private Date verification_date;

    @Column(name = "verified_by", nullable = true, length = 30)
    private String verified_by;

    @Column(name = "address", nullable = false, length = 30)
    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getLcense_number() {
        return lcense_number;
    }

    public void setLcense_number(String lcense_number) {
        this.lcense_number = lcense_number;
    }

    public Date getLicense_expiry_date() {
        return license_expiry_date;
    }

    public void setLicense_expiry_date(Date license_expiry_date) {
        this.license_expiry_date = license_expiry_date;
    }

    public String getLicense_doc() {
        return license_doc;
    }

    public void setLicense_doc(String license_doc) {
        this.license_doc = license_doc;
    }

    public Boolean getIs_verified() {
        return is_verified;
    }

    public void setIs_verified(Boolean is_verified) {
        this.is_verified = is_verified;
    }

    public Date getVerification_date() {
        return verification_date;
    }

    public void setVerification_date(Date verification_date) {
        this.verification_date = verification_date;
    }

    public String getVerified_by() {
        return verified_by;
    }

    public void setVerified_by(String verified_by) {
        this.verified_by = verified_by;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
