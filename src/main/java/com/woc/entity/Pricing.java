package com.woc.entity;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name="PRICING")
public class Pricing implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false, length=20)
	private String city;

	@Column(name="cost_per_km", nullable=false)
	private int costPerKm;

	@Column(name="extra_charges", nullable=false)
	private int extraCharges;

	public Pricing() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getCostPerKm() {
		return this.costPerKm;
	}

	public void setCostPerKm(int costPerKm) {
		this.costPerKm = costPerKm;
	}

	public int getExtraCharges() {
		return this.extraCharges;
	}

	public void setExtraCharges(int extraCharges) {
		this.extraCharges = extraCharges;
	}

}