package com.woc.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private Long id;
	
	@Column(name="Name",nullable=false, length=30)
	private String name;
	
	@Column(name="phone",nullable=false, length=15)
	private String phone;
	
	@Column(name="email",nullable=true, length=50)
	private String email;
	
	@Column(name="type",nullable=false, length=10)
	private String type;
	
	@Column(name="registration_date",nullable=false)
	private Date registrationDate;
	
	@Column(name="device_id",nullable=false, length=30)
	private String deviceId;
	
	@Column(name="ratings",nullable=true)
	private Integer rating;
	
	@Column(name="status",nullable=false, length=15)
	private String status;
	
	@Column(name="blood_group", nullable=true, length=5)
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