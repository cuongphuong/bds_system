package com.sys.pp.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the `bds_ news` database table.
 * 
 */
@Entity
@Table(name="`bds_ news`")
@NamedQuery(name="BdsNew.findAll", query="SELECT b FROM BdsNew b")
public class BdsNew implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="news_id")
	private int newsId;

	@Column(name="category_id")
	private int categoryId;

	@Column(name="contact_ind")
	private String contactInd;

	@Column(name="create_at")
	private String createAt;

	@Column(name="create_by")
	private int createBy;

	@Column(name="district_id")
	private int districtId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="end_date")
	private Date endDate;

	private int formality;

	private BigDecimal price;

	private int project;

	@Column(name="province_id")
	private int provinceId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="start_date")
	private Date startDate;

	@Column(name="street_id")
	private int streetId;

	private String title;

	@Column(name="ward_id")
	private int wardId;

	//bi-directional one-to-one association to DetailNew
	@OneToOne
	@JoinColumn(name="news_id")
	private DetailNew detailNew;

	public BdsNew() {
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

	public String getContactInd() {
		return this.contactInd;
	}

	public void setContactInd(String contactInd) {
		this.contactInd = contactInd;
	}

	public String getCreateAt() {
		return this.createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	public int getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(int createBy) {
		this.createBy = createBy;
	}

	public int getDistrictId() {
		return this.districtId;
	}

	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getFormality() {
		return this.formality;
	}

	public void setFormality(int formality) {
		this.formality = formality;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getProject() {
		return this.project;
	}

	public void setProject(int project) {
		this.project = project;
	}

	public int getProvinceId() {
		return this.provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public int getStreetId() {
		return this.streetId;
	}

	public void setStreetId(int streetId) {
		this.streetId = streetId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getWardId() {
		return this.wardId;
	}

	public void setWardId(int wardId) {
		this.wardId = wardId;
	}

	public DetailNew getDetailNew() {
		return this.detailNew;
	}

	public void setDetailNew(DetailNew detailNew) {
		this.detailNew = detailNew;
	}

}