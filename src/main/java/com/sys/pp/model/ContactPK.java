package com.sys.pp.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the contacts database table.
 * 
 */
@Embeddable
public class ContactPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="user_id")
	private int userId;

	private int ind;

	public ContactPK() {
	}
	public int getUserId() {
		return this.userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getInd() {
		return this.ind;
	}
	public void setInd(int ind) {
		this.ind = ind;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ContactPK)) {
			return false;
		}
		ContactPK castOther = (ContactPK)other;
		return 
			(this.userId == castOther.userId)
			&& (this.ind == castOther.ind);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId;
		hash = hash * prime + this.ind;
		
		return hash;
	}
}