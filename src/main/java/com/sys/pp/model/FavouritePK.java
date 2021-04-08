package com.sys.pp.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the favourite database table.
 * 
 */
@Embeddable
public class FavouritePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="user_id")
	private int userId;

	@Column(name="news_id")
	private String newsId;

	public FavouritePK() {
	}
	public int getUserId() {
		return this.userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getNewsId() {
		return this.newsId;
	}
	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FavouritePK)) {
			return false;
		}
		FavouritePK castOther = (FavouritePK)other;
		return 
			(this.userId == castOther.userId)
			&& this.newsId.equals(castOther.newsId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId;
		hash = hash * prime + this.newsId.hashCode();
		
		return hash;
	}
}