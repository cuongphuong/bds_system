package com.sys.pp.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the favourite database table.
 * 
 */
@Embeddable
public class FavouritePK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "news_id")
	private Integer newsId;

	public FavouritePK() {
	}

	public FavouritePK(String userId, Integer newsId) {
		super();
		this.userId = userId;
		this.newsId = newsId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getNewsId() {
		return newsId;
	}

	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((newsId == null) ? 0 : newsId.hashCode());
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
		FavouritePK other = (FavouritePK) obj;
		if (newsId == null) {
			if (other.newsId != null)
				return false;
		} else if (!newsId.equals(other.newsId))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
}