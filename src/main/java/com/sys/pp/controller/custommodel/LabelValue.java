package com.sys.pp.controller.custommodel;

import com.sys.pp.constant.GemRealtyConst.AcreageScope;
import com.sys.pp.constant.GemRealtyConst.FontWidth;
import com.sys.pp.constant.GemRealtyConst.PriceScope;
import com.sys.pp.constant.GemRealtyConst.SortResult;
import com.sys.pp.model.Category;
import com.sys.pp.model.District;
import com.sys.pp.model.Project;
import com.sys.pp.model.Province;
import com.sys.pp.model.Street;
import com.sys.pp.model.Ward;

public class LabelValue {
	private String label;
	private String value;

	public LabelValue(KeyValue item) {
		this.label = item.getValue();
		this.value = item.getKey();
	}

	public LabelValue(Category item) {
		this.label = item.getCategoryName();
		this.value = String.valueOf(item.getCategoryId());
	}

	public LabelValue(Province item) {
		this.label = item.getName();
		this.value = String.valueOf(item.getId());
	}

	public LabelValue(Project item) {
		this.label = item.getName();
		this.value = String.valueOf(item.getId());
	}

	public LabelValue(District item) {
		this.label = item.getName();
		this.value = String.valueOf(item.getId());
	}

	public LabelValue(Street item) {
		this.label = item.getName();
		this.value = String.valueOf(item.getId());
	}

	public LabelValue(Ward item) {
		this.label = item.getName();
		this.value = String.valueOf(item.getId());
	}

	public LabelValue(PriceScope item) {
		this.label = item.getLable();
		this.value = String.valueOf(item.getId());
	}

	public LabelValue(AcreageScope item) {
		this.label = item.getLable();
		this.value = String.valueOf(item.getId());
	}

	public LabelValue(SortResult item) {
		this.label = item.getLabel();
		this.value = String.valueOf(item.getId());
	}

	public LabelValue(FontWidth item) {
		this.label = item.getLable();
		this.value = String.valueOf(item.getId());
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
