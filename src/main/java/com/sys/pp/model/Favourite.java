package com.sys.pp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * The persistent class for the favourite database table.
 * 
 */
@Entity
@NamedQuery(name = "Favourite.findAll", query = "SELECT f FROM Favourite f")
public class Favourite implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FavouritePK id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_at")
	private Date createAt;

	public Favourite() {
	}

	public Favourite(FavouritePK id) {
		this.id = id;
		this.createAt = new Date();
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public FavouritePK getId() {
		return this.id;
	}

	public void setId(FavouritePK id) {
		this.id = id;
	}

}