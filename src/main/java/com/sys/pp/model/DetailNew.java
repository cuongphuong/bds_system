package com.sys.pp.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The persistent class for the detail_news database table.
 * 
 */
@Entity
@Table(name = "detail_news")
@NamedQuery(name = "DetailNew.findAll", query = "SELECT d FROM DetailNew d")
public class DetailNew implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "news_id")
	private Integer newsId;

	private double acreage;

	@Column(name = "contact_ind")
	private Integer contactInd;

	private String description;

	private String direction;

	@Column(name = "district_id")
	private Integer districtId;

	@Column(name = "entrance_width")
	private double entranceWidth;

	@Column(name = "floors_num")
	private Integer floorsNum;

	private String formality;

	@Column(name = "front_width")
	private double frontWidth;

	private String furniture;

	private String images;

	@Column(name = "juridical_info")
	private String juridicalInfo;

	private double lat;

	private double lng;

	private BigDecimal price;

	@Column(name = "project_id")
	private Integer projectId;

	@Column(name = "province_id")
	private Integer provinceId;

	@Column(name = "room_num")
	private Integer roomNum;

	@Column(name = "street_id")
	private Integer streetId;

	@Column(name = "toilet_num")
	private Integer toiletNum;

	@Column(name = "video_url")
	private String videoUrl;

	@Column(name = "ward_id")
	private Integer wardId;

	@Column(name = "unit")
	private Integer unit;

	// bi-directional one-to-one association to Bds_new
	@OneToOne
	@JoinColumn(name = "news_id")
	private BdsNew bdsNew;

	public DetailNew() {
	}

	public Integer getNewsId() {
		return this.newsId;
	}

	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}

	public double getAcreage() {
		return this.acreage;
	}

	public void setAcreage(double acreage) {
		this.acreage = acreage;
	}

	public Integer getContactInd() {
		return this.contactInd;
	}

	public void setContactInd(Integer contactInd) {
		this.contactInd = contactInd;
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

	public Integer getDistrictId() {
		return this.districtId;
	}

	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}

	public double getEntranceWidth() {
		return this.entranceWidth;
	}

	public void setEntranceWidth(double entranceWidth) {
		this.entranceWidth = entranceWidth;
	}

	public Integer getFloorsNum() {
		return this.floorsNum;
	}

	public void setFloorsNum(Integer floorsNum) {
		this.floorsNum = floorsNum;
	}

	public String getFormality() {
		return this.formality;
	}

	public void setFormality(String formality) {
		this.formality = formality;
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

	public Integer getProjectId() {
		return this.projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getProvinceId() {
		return this.provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public Integer getRoomNum() {
		return this.roomNum;
	}

	public void setRoomNum(Integer roomNum) {
		this.roomNum = roomNum;
	}

	public Integer getStreetId() {
		return this.streetId;
	}

	public void setStreetId(Integer streetId) {
		this.streetId = streetId;
	}

	public Integer getToiletNum() {
		return this.toiletNum;
	}

	public void setToiletNum(Integer toiletNum) {
		this.toiletNum = toiletNum;
	}

	public String getVideoUrl() {
		return this.videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public Integer getWardId() {
		return this.wardId;
	}

	public void setWardId(Integer wardId) {
		this.wardId = wardId;
	}

	public BdsNew getBdsNew() {
		return this.bdsNew;
	}

	public void setBdsNew(BdsNew bdsNew) {
		this.bdsNew = bdsNew;
	}

	public Integer getUnit() {
		return unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

}