package com.woc.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "RIDEREQUEST")
@NamedQuery(name = "RideRequest.findAll", query = "SELECT r from RideRequest r")
@NamedQuery(name = "RideRequest.findById", query = "SELECT r from RideRequest r where id = ?1")
@NamedQuery(name  = "RideRequest.findByRider", query = "SELECT r from RideRequest r where riderId = ?1")
public class RideRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "rider_id", referencedColumnName = "id")
	private Rider riderId;

	@ManyToOne
	@JoinColumn(name = "driver_id", referencedColumnName = "id")
	private Driver driverId;

	@Column(name = "start_location", length = 100)
	private String startLocation;

	@Column(name = "end_location", length = 100)
	private String endLocation;
	
	@Column(name = "start_lat")
	private Double startLat;
	
	@Column(name = "end_lat")
	private Double endLat;
	
	@Column(name = "start_long")
	private Double startLong;
	
	@Column(name = "end_long")
	private Double endLong;
	
	@Column(name = "notified_drivers", length = 200)
	private String notifiedDrivers;
	
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Rider getRiderId() {
		return riderId;
	}

	public void setRiderId(Rider riderId) {
		this.riderId = riderId;
	}

	public Driver getDriverId() {
		return driverId;
	}

	public void setDriverId(Driver driverId) {
		this.driverId = driverId;
	}

	public String getStartLocation() {
		return startLocation;
	}

	public void setStartLocation(String startLocation) {
		this.startLocation = startLocation;
	}

	public String getEndLocation() {
		return endLocation;
	}

	public void setEndLocation(String endLocation) {
		this.endLocation = endLocation;
	}
	

	public Double getStartLat() {
		return startLat;
	}

	public void setStartLat(Double startLat) {
		this.startLat = startLat;
	}

	public Double getEndLat() {
		return endLat;
	}

	public void setEndLat(Double endLat) {
		this.endLat = endLat;
	}

	public Double getStartLong() {
		return startLong;
	}

	public void setStartLong(Double startLong) {
		this.startLong = startLong;
	}

	public Double getEndLong() {
		return endLong;
	}

	public void setEndLong(Double endLong) {
		this.endLong = endLong;
	}

	public String getNotifiedDrivers() {
		return notifiedDrivers;
	}

	public void setNotifiedDrivers(String notifiedDrivers) {
		this.notifiedDrivers = notifiedDrivers;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
