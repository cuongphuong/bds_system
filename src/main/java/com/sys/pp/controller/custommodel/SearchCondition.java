package com.sys.pp.controller.custommodel;

import java.util.List;

import com.sys.pp.constant.GemRealtyConst.AcreageScope;
import com.sys.pp.constant.GemRealtyConst.FontWidth;
import com.sys.pp.constant.GemRealtyConst.PriceScope;

public class SearchCondition {
	private String keyword;
	private List<String> formalityList;
	private List<Integer> categoryList;
	private Integer location;
	private List<Integer> provinceList;
	private List<Integer> districtList;
	private List<Integer> wardList;
	private List<Integer> streetList;
	private List<Integer> projectList;
	private PriceScope price;
	private AcreageScope acreage;
	private List<FontWidth> frontWidthList;
	private List<Integer> floorList;
	private List<Integer> roomList;
	private List<Integer> wayList;

	public String getKeyword() {
		return keyword;
	}

	public List<Integer> getProvinceList() {
		return provinceList;
	}

	public void setProvinceList(List<Integer> provinceList) {
		this.provinceList = provinceList;
	}

	public List<FontWidth> getFrontWidthList() {
		return frontWidthList;
	}

	public void setFrontWidthList(List<FontWidth> frontWidthList) {
		this.frontWidthList = frontWidthList;
	}

	public List<Integer> getFloorList() {
		return floorList;
	}

	public void setFloorList(List<Integer> floorList) {
		this.floorList = floorList;
	}

	public List<Integer> getRoomList() {
		return roomList;
	}

	public void setRoomList(List<Integer> roomList) {
		this.roomList = roomList;
	}

	public List<Integer> getWayList() {
		return wayList;
	}

	public void setWayList(List<Integer> wayList) {
		this.wayList = wayList;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public List<String> getFormalityList() {
		return formalityList;
	}

	public void setFormalityList(List<String> formalityList) {
		this.formalityList = formalityList;
	}

	public List<Integer> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Integer> categoryList) {
		this.categoryList = categoryList;
	}

	public Integer getLocation() {
		return location;
	}

	public void setLocation(Integer location) {
		this.location = location;
	}

	public List<Integer> getDistrictList() {
		return districtList;
	}

	public void setDistrictList(List<Integer> districtList) {
		this.districtList = districtList;
	}

	public List<Integer> getWardList() {
		return wardList;
	}

	public void setWardList(List<Integer> wardList) {
		this.wardList = wardList;
	}

	public List<Integer> getStreetList() {
		return streetList;
	}

	public void setStreetList(List<Integer> streetList) {
		this.streetList = streetList;
	}

	public List<Integer> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<Integer> projectList) {
		this.projectList = projectList;
	}

	public PriceScope getPrice() {
		return price;
	}

	public void setPrice(PriceScope price) {
		this.price = price;
	}

	public AcreageScope getAcreage() {
		return acreage;
	}

	public void setAcreage(AcreageScope acreage) {
		this.acreage = acreage;
	}

}
