package com.sys.pp.controller.custommodel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class PostInfomation {
	// basic infomation
	private int newsId;
	private int categoryId;
	private Date createAt;
	private String createBy;
	private Date endDate;
	private BigDecimal pricePost;
	private Date startDate;
	private String statusFlg;
	private String title;
	// ad infomation
	private double acreage;
	private int contactInd;
	private String description;
	private String direction;
	private int districtId;
	private double entranceWidth;
	private int floorsNum;
	private String formality;
	private double frontWidth;
	private String furniture;
	private List<List<String>> images;
	private String juridicalInfo;
	private double lat;
	private double lng;
	private BigDecimal price;
	private int projectId;
	private int provinceId;
	private int roomNum;
	private int streetId;
	private int toiletNum;
	private String videoUrl;
	private int wardId;

	public double getAcreage() {
		return this.acreage;
	}

	public void setAcreage(double acreage) {
		this.acreage = acreage;
	}

	public int getContactInd() {
		return this.contactInd;
	}

	public void setContactInd(int contactInd) {
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

	public int getDistrictId() {
		return this.districtId;
	}

	public void setDistrictId(int districtId) {
		this.districtId = districtId;
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

	public List<List<String>> getImages() {
		return this.images;
	}

	public void setImages(List<List<String>> images) {
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

	public BigDecimal getPricePost() {
		return this.pricePost;
	}

	public void setPricePost(BigDecimal price) {
		this.pricePost = price;
	}

	public int getProjectId() {
		return this.projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getProvinceId() {
		return this.provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

	public int getRoomNum() {
		return this.roomNum;
	}

	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}

	public int getStreetId() {
		return this.streetId;
	}

	public void setStreetId(int streetId) {
		this.streetId = streetId;
	}

	public int getToiletNum() {
		return this.toiletNum;
	}

	public void setToiletNum(int toiletNum) {
		this.toiletNum = toiletNum;
	}

	public String getVideoUrl() {
		return this.videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public int getWardId() {
		return this.wardId;
	}

	public void setWardId(int wardId) {
		this.wardId = wardId;
	}

	public int getNewsId() {
		return this.newsId;
	}

	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}

	public int getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public Date getCreateAt() {
		return this.createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStatusFlg() {
		return statusFlg;
	}

	public void setStatusFlg(String statusFlg) {
		this.statusFlg = statusFlg;
	}
}
