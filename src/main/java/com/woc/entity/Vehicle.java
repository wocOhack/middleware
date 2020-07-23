package com.woc.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "VEHICLE")
@NamedQuery(name = "Vehicle.findAll", query = "SELECT v from Vehicle v")
public class Vehicle implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "user_id", nullable = false, length = 20)
    private long user_id;

    @Column(name = "vehicle_number", nullable = false, length = 20)
    private String vehicleNumber;

    @Column(name = "insurance_doc", nullable = false, length = 200)
    private String insuranceDoc;

    @Column(name = "insurance_number", nullable = false, length = 200)
    private String insuranceNumber;

    @Column(name = "vehicle_model", length = 20)
    private String vehicleModel;

    @Column(name = "vehicle_type", length = 20)
    private String vehicleType;

    @Column(name = "vehicle_doc", length = 200)
    private String vehicleDoc;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @Column(name = "verifiedby", length = 50)
    private String verifiedBy;

    @Column(name = "verification_date")
    private Date verificationDate;

    public Vehicle() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser() {
        return this.user_id;
    }

    public void setUser(long user) {
        this.user_id = user;
    }

    public String getVehicleNumber() {
        return this.vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getInsuranceDoc() {
        return this.insuranceDoc;
    }

    public void setInsuranceDoc(String insuranceDoc) {
        this.insuranceDoc = insuranceDoc;
    }

    public String getInsuranceNumber() {
        return this.insuranceNumber;
    }

    public void setInsuranceNumber(String insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public String getVehicleModel() {
        return this.vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getVehicleType() {
        return this.vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleDoc() {
        return this.vehicleDoc;
    }

    public void setVehicleDoc(String vehicleDoc) {
        this.vehicleDoc = vehicleDoc;
    }

    public Boolean getIsVerified() {
        return this.isVerified;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public String getVerifiedBy() {
        return this.verifiedBy;
    }

    public void setVerifiedBy(String verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public Date getVerificationDate() {
        return this.verificationDate;
    }

    public void setVerificationDate(Date verificationDate) {
        this.verificationDate = verificationDate;
    }

    @Override
    public String toString() {
        return "Vehicle [id=" + id + ", user_id=" + user_id + ", vehicleNumber=" + vehicleNumber + ", insuranceDoc="
                + insuranceDoc + ", insuranceNumber=" + insuranceNumber + ", vehicleModel=" + vehicleModel
                + ", vehicleType=" + vehicleType + ", vehicleDoc=" + vehicleDoc + ", isVerified=" + isVerified
                + ", verifiedBy=" + verifiedBy + ", verificationDate=" + verificationDate + "]";
    }

}
