package com.sys.pp.controller.custommodel;

import java.util.Date;
import java.util.List;

import com.sys.pp.util.StringUtils;

public class PostInfomation {
	// basic infomation
	private String newsId;
	private String categoryId;
	private Date createAt;
	private String createBy;
	private String endDate;
	private String pricePost;
	private String startDate;
	private String statusFlg;
	private String title;
	private List<String> breadcrumbItems;

	// ad infomation
	private String address = StringUtils.EMPTY;
	private String acreage = StringUtils.EMPTY;
	private String contactInd = StringUtils.EMPTY;
	private String description = StringUtils.EMPTY;
	private String direction = StringUtils.EMPTY;
	private String districtId = StringUtils.EMPTY;
	private String entranceWidth = StringUtils.EMPTY;
	private String floorsNum = StringUtils.EMPTY;
	private String formality = StringUtils.EMPTY;
	private String frontWidth = StringUtils.EMPTY;
	private String furniture = StringUtils.EMPTY;
	private boolean hasImage;
	private List<List<String>> images;
	private String juridicalInfo;
	private double lat;
	private double lng;
	private String price = StringUtils.EMPTY;
	private String projectId = StringUtils.EMPTY;
	private String provinceId = StringUtils.EMPTY;
	private String roomNum = StringUtils.EMPTY;
	private String streetId = StringUtils.EMPTY;
	private String toiletNum = StringUtils.EMPTY;
	private String videoUrl = StringUtils.EMPTY;
	private boolean hasVideoUrl;
	private String wardId = StringUtils.EMPTY;

	// contact
	private String contactName = StringUtils.EMPTY;
	private String diaChi = StringUtils.EMPTY;
	private String email = StringUtils.EMPTY;
	private String phone = StringUtils.EMPTY;

	// other
	private List<KeyValue> moreBds;
	private List<KeyValue> moreByCategory;
	private String thumnail;
	private String urlPost;
	private int level;
	private boolean isLiked;
	private boolean isApproved;
	private boolean isCanceled;
	private boolean isAccessByOwner;

	public String getAcreage() {
		return this.acreage;
	}

	public boolean isCanceled() {
		return isCanceled;
	}

	public boolean isAccessByOwner() {
		return isAccessByOwner;
	}

	public void setAccessByOwner(boolean isAccessByOwner) {
		this.isAccessByOwner = isAccessByOwner;
	}

	public void setCanceled(boolean isCanceled) {
		this.isCanceled = isCanceled;
	}

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	public boolean isLiked() {
		return isLiked;
	}

	public void setLiked(boolean isLiked) {
		this.isLiked = isLiked;
	}

	public void setAcreage(String acreage) {
		this.acreage = acreage;
	}

	public String getContactInd() {
		return this.contactInd;
	}

	public void setContactInd(String contactInd) {
		this.contactInd = contactInd;
	}

	public String getDescription() {
		return this.description;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
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

	public String getDistrictId() {
		return this.districtId;
	}

	public String getThumnail() {
		return thumnail;
	}

	public void setThumnail(String thumnail) {
		this.thumnail = thumnail;
	}

	public String getUrlPost() {
		return urlPost;
	}

	public void setUrlPost(String urlPost) {
		this.urlPost = urlPost;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	public String getEntranceWidth() {
		return this.entranceWidth;
	}

	public void setEntranceWidth(String entranceWidth) {
		this.entranceWidth = entranceWidth;
	}

	public String getFloorsNum() {
		return this.floorsNum;
	}

	public void setFloorsNum(String floorsNum) {
		this.floorsNum = floorsNum;
	}

	public List<KeyValue> getMoreByCategory() {
		return moreByCategory;
	}

	public void setMoreByCategory(List<KeyValue> moreByCategory) {
		this.moreByCategory = moreByCategory;
	}

	public String getFormality() {
		return this.formality;
	}

	public void setFormality(String formality) {
		this.formality = formality;
	}

	public String getFrontWidth() {
		return this.frontWidth;
	}

	public void setFrontWidth(String frontWidth) {
		this.frontWidth = frontWidth;
	}

	public List<KeyValue> getMoreBds() {
		return moreBds;
	}

	public void setMoreBds(List<KeyValue> moreBds) {
		this.moreBds = moreBds;
	}

	public String getFurniture() {
		return this.furniture;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getPricePost() {
		return this.pricePost;
	}

	public void setPricePost(String price) {
		this.pricePost = price;
	}

	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProvinceId() {
		return this.provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getRoomNum() {
		return this.roomNum;
	}

	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}

	public String getStreetId() {
		return this.streetId;
	}

	public void setStreetId(String streetId) {
		this.streetId = streetId;
	}

	public String getToiletNum() {
		return this.toiletNum;
	}

	public void setToiletNum(String toiletNum) {
		this.toiletNum = toiletNum;
	}

	public String getVideoUrl() {
		return this.videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getWardId() {
		return this.wardId;
	}

	public void setWardId(String wardId) {
		this.wardId = wardId;
	}

	public String getNewsId() {
		return this.newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	public String getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(String categoryId) {
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

	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getStartDate() {
		return this.startDate;
	}

	public void setStartDate(String startDate) {
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

	public boolean isHasImage() {
		return hasImage;
	}

	public void setHasImage(boolean hasImage) {
		this.hasImage = hasImage;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isHasVideoUrl() {
		return hasVideoUrl;
	}

	public void setHasVideoUrl(boolean hasVideoUrl) {
		this.hasVideoUrl = hasVideoUrl;
	}

	public List<String> getBreadcrumbItems() {
		return breadcrumbItems;
	}

	public void setBreadcrumbItems(List<String> breadcrumbItems) {
		this.breadcrumbItems = breadcrumbItems;
	}
}
