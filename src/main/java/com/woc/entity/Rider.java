package com.woc.entity;

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
@Table(name="RIDER")
@NamedQuery(name="Rider.findAll", query="SELECT r FROM Rider r")
@NamedQuery(name = "Rider.findById", query = "SELECT r FROM Rider r where id=?1")

public class Rider {

    @Column(name = "id", unique = true, nullable = false, length = 30)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pin", nullable = false, length = 30)
    private String pin;

    @Column(name = "is_challenged", nullable = false, length = 30)
    private boolean is_challenged;

    @Column(name = "is_verified", nullable = true, length = 30)
    private boolean is_verified;

    @Column(name = "verification_date", nullable = true, length = 30)
    private Date verification_date;

    @Column(name = "verified_date", nullable = true, length = 30)
    private Date verified_date;

    @Column(name = "proof_of_challenge", nullable = true, length = 30)
    private String proof_of_challenge;

    @Column(name = "user_id", nullable = false, length = 30)
    private long user_id;
    
    @Column(name = "deviceID", nullable = false, length = 30)
    private long deviceID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public boolean isIs_challenged() {
        return is_challenged;
    }

    public void setIs_challenged(boolean is_challenged) {
        this.is_challenged = is_challenged;
    }

    public boolean isIs_verified() {
        return is_verified;
    }

    public void setIs_verified(boolean is_verified) {
        this.is_verified = is_verified;
    }

    public Date getVerification_date() {
        return verification_date;
    }

    public void setVerification_date(Date verification_date) {
        this.verification_date = verification_date;
    }

    public Date getVerified_date() {
        return verified_date;
    }

    public void setVerified_date(Date verified_date) {
        this.verified_date = verified_date;
    }

    public String getProof_of_challenge() {
        return proof_of_challenge;
    }

    public void setProof_of_challenge(String proof_of_challenge) {
        this.proof_of_challenge = proof_of_challenge;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

	public long getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(long deviceID) {
		this.deviceID = deviceID;
	}
    @Override
    public String toString() {
        return "Rider [id=" + id + ", pin=" + pin + ", is_challenged=" + is_challenged + ", is_verified=" + is_verified
                + ", verification_date=" + verification_date + ", verified_date=" + verified_date
                + ", proof_of_challenge=" + proof_of_challenge + ", user_id=" + user_id + "]";
    }
    

}
