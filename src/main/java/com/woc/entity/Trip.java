package com.woc.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "TRIP")
@NamedQueries(value = { 
		@NamedQuery(name = "Trip.findAll", query = "SELECT t from Trip t"),
		@NamedQuery(name = "Trip.findById", query = "SELECT t from Trip t where id = ?1")})
public class Trip implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "driver_id", nullable = false, length = 6)
    private Long driverId;

    @Column(name = "rider_id", nullable = false, length = 6)
    private Long riderId;

    @Column(name = "start_location", nullable = false, length = 20)
    private String startLocation;

    @Column(name = "end_location", nullable = false, length = 20)
    private String endLocation;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "trip_start_time")
    private Date tripStartTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "trip_end_time")
    private Date tripEndTime;

    @Temporal(TemporalType.TIME)
    @Column(name = "duration")
    private Date duration;

    @Column(name = "distance")
    private Double distance;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "cancelled_by")
    private Integer cancelledBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time", nullable = false)
    private Date createdTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_time", nullable = false)
    private Date updatedTime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public Long getRiderId() {
        return riderId;
    }

    public void setRiderId(Long riderId) {
        this.riderId = riderId;
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

    public Date getTripStartTime() {
        return tripStartTime;
    }

    public void setTripStartTime(Date tripStartTime) {
        this.tripStartTime = tripStartTime;
    }

    public Date getTripEndTime() {
        return tripEndTime;
    }

    public void setTripEndTime(Date tripEndTime) {
        this.tripEndTime = tripEndTime;
    }

    public Date getDuration() {
        return duration;
    }

    public void setDuration(Date duration) {
        this.duration = duration;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCancelledBy() {
        return cancelledBy;
    }

    public void setCancelledBy(Integer cancelledBy) {
        this.cancelledBy = cancelledBy;
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
