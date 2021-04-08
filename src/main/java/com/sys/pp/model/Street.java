package com.sys.pp.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the street database table.
 * 
 */
@Entity
@NamedQuery(name="Street.findAll", query="SELECT s FROM Street s")
public class Street implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name="_district_id")
	private int districtId;

	@Column(name="_name")
	private String name;

	@Column(name="_prefix")
	private String prefix;

	@Column(name="_province_id")
	private int provinceId;

	public Street() {
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrefix() {
		return this.prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public int getProvinceId() {
		return this.provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

}