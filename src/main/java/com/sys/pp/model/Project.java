package com.sys.pp.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the project database table.
 * 
 */
@Entity
@NamedQuery(name="Project.findAll", query="SELECT p FROM Project p")
public class Project implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name="_district_id")
	private int districtId;

	@Column(name="_lat")
	private double lat;

	@Column(name="_lng")
	private double lng;

	@Column(name="_name")
	private String name;

	@Column(name="_province_id")
	private int provinceId;

	public Project() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDistrictId() {
		return this.districtId;
	}

	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}

	public double getLat() {
		return this.lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return this.lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getProvinceId() {
		return this.provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

}