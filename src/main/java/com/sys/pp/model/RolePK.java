package com.sys.pp.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the roles database table.
 * 
 */
@Embeddable
public class RolePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="user_id")
	private int userId;

	private String role;

	public RolePK() {
	}
	public int getUserId() {
		return this.userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getRole() {
		return this.role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RolePK)) {
			return false;
		}
		RolePK castOther = (RolePK)other;
		return 
			(this.userId == castOther.userId)
			&& this.role.equals(castOther.role);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId;
		hash = hash * prime + this.role.hashCode();
		
		return hash;
	}
}