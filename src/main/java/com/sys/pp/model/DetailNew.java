package com.sys.pp.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the detail_news database table.
 * 
 */
@Entity
@Table(name="detail_news")
@NamedQuery(name="DetailNew.findAll", query="SELECT d FROM DetailNew d")
public class DetailNew implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="news_id")
	private int newsId;

	private double acreage;

	private String description;

	private String direction;

	@Column(name="entrance_width")
	private double entranceWidth;

	@Column(name="floors_num")
	private int floorsNum;

	@Column(name="front_width")
	private double frontWidth;

	private String furniture;

	private String images;

	@Column(name="juridical_info")
	private String juridicalInfo;

	private double lat;

	private double lng;

	private BigDecimal price;

	@Column(name="room_num")
	private int roomNum;

	@Column(name="toilet_num")
	private int toiletNum;

	//bi-directional one-to-one association to BdsNew
	@OneToOne(mappedBy="detailNew")
	private BdsNew bdsNew;

	public DetailNew() {
	}

	public int getNewsId() {
		return this.newsId;
	}

	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}

	public double getAcreage() {
		return this.acreage;
	}

	public void setAcreage(double acreage) {
		this.acreage = acreage;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDirection() {
		return this.direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public double getEntranceWidth() {
		return this.entranceWidth;
	}

	public void setEntranceWidth(double entranceWidth) {
		this.entranceWidth = entranceWidth;
	}

	public int getFloorsNum() {
		return this.floorsNum;
	}

	public void setFloorsNum(int floorsNum) {
		this.floorsNum = floorsNum;
	}

	public double getFrontWidth() {
		return this.frontWidth;
	}

	public void setFrontWidth(double frontWidth) {
		this.frontWidth = frontWidth;
	}

	public String getFurniture() {
		return this.furniture;
	}

	public void setFurniture(String furniture) {
		this.furniture = furniture;
	}

	public String getImages() {
		return this.images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getJuridicalInfo() {
		return this.juridicalInfo;
	}

	public void setJuridicalInfo(String juridicalInfo) {
		this.juridicalInfo = juridicalInfo;
	}

	public double getLat() {
		return this.lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return this.lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getRoomNum() {
		return this.roomNum;
	}

	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}

	public int getToiletNum() {
		return this.toiletNum;
	}

	public void setToiletNum(int toiletNum) {
		this.toiletNum = toiletNum;
	}

	public BdsNew getBdsNew() {
		return this.bdsNew;
	}

	public void setBdsNew(BdsNew bdsNew) {
		this.bdsNew = bdsNew;
	}

}