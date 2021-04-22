package com.sys.pp.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the contacts database table.
 * 
 */
@Entity
@Table(name = "contacts")
@NamedQuery(name = "Contact.findAll", query = "SELECT c FROM Contact c")
public class Contact implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ContactPK id;

	@Column(name = "contact_name")
	private String contactName;

	private String email;

	@Column(name = "mobile_phone")
	private String mobilePhone;

	@Column(name = "dia_chi")
	private String diaChi;

	private String phone;

	public Contact() {
	}

	public ContactPK getId() {
		return this.id;
	}

	public void setId(ContactPK id) {
		this.id = id;
	}

	public String getContactName() {
		return this.contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}