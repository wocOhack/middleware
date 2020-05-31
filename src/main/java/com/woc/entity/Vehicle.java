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
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "vehicle_number", nullable = false, length = 20)
    private String vehicleNumber;

    @Column(name = "insurance_doc", nullable = false, length = 200)
    private String insuranceDoc;

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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "verification_date")
    private Date verificationDate;

    public Vehicle() { }

    public Integer getId(){ return this.id; }

    public void setId(Integer id) { this.id = id; }

    public User getUser(){ return this.user; }

    public void setUser(User user){ this.user = user; }

    public String getVehicleNumber(){ return this.vehicleNumber; }

    public void setVehicleNumber(String vehicleNumber){ this.vehicleNumber = vehicleNumber; }

    public String getInsuranceDoc(){ return this.insuranceDoc; }

    public void setInsuranceDoc(String insuranceDoc){ this.insuranceDoc = insuranceDoc; }

    public String getVehicleModel(){ return this.vehicleModel; }

    public void setVehicleModel(String vehicleModel){ this.vehicleModel = vehicleModel; }

    public String getVehicleType(){ return this.vehicleType; }

    public void setVehicleType(String vehicleType){ this.vehicleType = vehicleType; }

    public String getVehicleDoc(){ return this.vehicleDoc; }

    public void setVehicleDoc(String vehicleDoc){ this.vehicleDoc = vehicleDoc; }

    public Boolean getIsVerified(){ return this.isVerified; }

    public void setIsVerified(Boolean isVerified){ this.isVerified = isVerified; }

    public String getVerifiedBy(){ return this.verifiedBy; }

    public void setVerifiedBy(String verifiedBy){ this.verifiedBy = verifiedBy; }

    public Date getVerificationDate(){ return this.verificationDate; }

    public void setVerificationDate(Date verificationDate){this.verificationDate = verificationDate; }
}
