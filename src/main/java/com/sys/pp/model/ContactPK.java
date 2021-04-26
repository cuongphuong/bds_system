package com.sys.pp.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the contacts database table.
 * 
 */
@Embeddable
public class ContactPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "user_id")
	private String userId;

	private int ind;

	public ContactPK() {
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getInd() {
		return this.ind;
	}

	public void setInd(int ind) {
		this.ind = ind;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ind;
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
		ContactPK other = (ContactPK) obj;
		if (ind != other.ind)
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

}