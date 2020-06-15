package com.woc.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="ServiceableArea")
@NamedQuery(name="ServiceableArea.findAll", query="SELECT a FROM ServiceableArea a")
public class ServiceableArea {

    @Id
    private Long id;

    @Column(name = "area_name", nullable = true, length = 30)
    private String area_name;

    @Column(name = "is_servicable", nullable = false, length = 30)
    private boolean is_servicable;

    @Column(name = "service_paused_since", nullable = true, length = 30)
    private Date service_paused_since;

    @Column(name = "servicable_since", nullable = true, length = 30)
    private Date servicable_since;

    @Column(name = "state_name", nullable = true, length = 30)
    private String state_name;

    @Column(name = "city_name", nullable = false, length = 30)
    private String city_name;

    @Column(name = "pincode_pattern", nullable = false, length = 30)
    private String pincode_pattern;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public boolean isIs_servicable() {
        return is_servicable;
    }

    public void setIs_servicable(boolean is_servicable) {
        this.is_servicable = is_servicable;
    }

    public Date getService_paused_since() {
        return service_paused_since;
    }

    public void setService_paused_since(Date service_paused_since) {
        this.service_paused_since = service_paused_since;
    }

    public Date getServicable_since() {
        return servicable_since;
    }

    public void setServicable_since(Date servicable_since) {
        this.servicable_since = servicable_since;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getPincode_pattern() {
        return pincode_pattern;
    }

    public void setPincode_pattern(String pincode_pattern) {
        this.pincode_pattern = pincode_pattern;
    }
}
