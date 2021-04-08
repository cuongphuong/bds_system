package com.sys.pp.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the roles database table.
 * 
 */
@Entity
@Table(name="roles")
@NamedQuery(name="Roles.findAll", query="SELECT r FROM Roles r")
public class Roles implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RolePK id;

	public Roles() {
	}

	public RolePK getId() {
		return this.id;
	}

	public void setId(RolePK id) {
		this.id = id;
	}

}