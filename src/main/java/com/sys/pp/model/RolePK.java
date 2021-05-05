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
	private String userId;

	private String role;

	public RolePK() {
	}
	
	public RolePK(String userId, String role) {
		super();
		this.userId = userId;
		this.role = role;
	}


	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRole() {
		return this.role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RolePK other = (RolePK) obj;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
}