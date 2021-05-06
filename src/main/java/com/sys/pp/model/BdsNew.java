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
@Table(name = "`bds_ news`")
@NamedQuery(name = "Bds_new.findAll", query = "SELECT b FROM BdsNew b")
public class BdsNew implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "news_id")
	private int newsId;

	@Column(name = "category_id")
	private int categoryId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_at")
	private Date createAt;

	@Column(name = "create_by")
	private String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_date")
	private Date endDate;

	private BigDecimal price;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "status_flg")
	private int statusFlg;

	@Column(name = "delete_flg")
	private int deleteFlg;

	@Column(name = "level")
	private int level;

	private String title;

	// bi-directional one-to-one association to DetailNew
	@OneToOne(mappedBy = "bdsNew")
	private DetailNew detailNew;

	public BdsNew() {
	}

	public int getDeleteFlg() {
		return deleteFlg;
	}

	public void setDeleteFlg(int deleteFlg) {
		this.deleteFlg = deleteFlg;
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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
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

	public DetailNew getDetailNew() {
		return this.detailNew;
	}

	public void setDetailNew(DetailNew detailNew) {
		this.detailNew = detailNew;
	}

	public int getStatusFlg() {
		return statusFlg;
	}

	public void setStatusFlg(int statusFlg) {
		this.statusFlg = statusFlg;
	}
}