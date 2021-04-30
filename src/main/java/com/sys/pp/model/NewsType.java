package com.sys.pp.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the news_type database table.
 * 
 */
@Entity
@Table(name = "news_type")
@NamedQuery(name = "NewsType.findAll", query = "SELECT n FROM NewsType n")
public class NewsType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String description;

	private String name;

	private double price;

	private int level;

	public NewsType() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}