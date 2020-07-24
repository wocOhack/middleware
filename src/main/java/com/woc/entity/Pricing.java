package com.woc.entity;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name="PRICING")
@NamedQuery(name="Pricing.findAll", query="SELECT p FROM Pricing p")
public class Pricing implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false, length=20)
	private String city;

	@Column(name="cost_per_km", nullable = false)
	private Double costPerKm;

	@Column(name="cost_per_min", nullable = false)
	private Double costPerMin;

	@Column(name="extra_charges", nullable = false)
	private Double extraCharges;

	public Pricing() {
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Double getCostPerKm() {
		return costPerKm;
	}

	public void setCostPerKm(Double costPerKm) {
		this.costPerKm = costPerKm;
	}

	public Double getCostPerMin() {
		return costPerMin;
	}

	public void setCostPerMin(Double costPerMin) {
		this.costPerMin = costPerMin;
	}

	public Double getExtraCharges() {
		return extraCharges;
	}

	public void setExtraCharges(Double extraCharges) {
		this.extraCharges = extraCharges;
	}
}