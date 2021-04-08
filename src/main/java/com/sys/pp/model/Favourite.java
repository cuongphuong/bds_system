package com.sys.pp.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the favourite database table.
 * 
 */
@Entity
@NamedQuery(name="Favourite.findAll", query="SELECT f FROM Favourite f")
public class Favourite implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FavouritePK id;

	public Favourite() {
	}

	public FavouritePK getId() {
		return this.id;
	}

	public void setId(FavouritePK id) {
		this.id = id;
	}

}