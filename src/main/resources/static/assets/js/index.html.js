function initSelectBox() {
	init();

	//init pickup list
	loadProvinceId(function() { });
	loadAcreageScope(function() { });
	loadCaterory(function() { });
	loadPriceScope(function() { });
	loadFormality(function() { });
}

function loadProvinceId(doneAction) {
	ajaxRequest("/util/get-province_la_va", "GET", null, function(data) {
		VirtualSelect.init({
			ele: "#selectbox_province_id",
			options: data,
		});
		doneAction();
	});
}

function loadAcreageScope(doneAction) {
	ajaxRequest("/util/get-acreage-scope-la_va", "GET", null, function(data) {
		VirtualSelect.init({
			ele: "#selectbox_dientich_id",
			options: data,
		});
		doneAction();
	});
}

function loadPriceScope(doneAction) {
	ajaxRequest("/util/get-prices-scope-la_va", "GET", null, function(data) {
		VirtualSelect.init({
			ele: "#selectbox_price_id",
			options: data,
		});
		doneAction();
	});
}

function loadFormality(doneAction) {
	ajaxRequest("/util/get-formality-la_va", "GET", null, function(data) {
		VirtualSelect.init({
			ele: "#selectbox_hinhthuc_id",
			options: data,
			multiple: true,
		});
		doneAction();
	});
}

function loadCaterory(doneAction) {
	ajaxRequest("/util/get-category-la_va", "GET", null, function(data) {
		VirtualSelect.init({
			ele: "#selectbox_category_id",
			options: data,
			multiple: true,
		});
		doneAction();
	});
}

function init() {
	/** in jquery */
	$('#selectbox_province_id').change(function() {
		var provinceId = this.value;
		ajaxRequest("/util/get-district_la_va/" + provinceId, "GET", null, function(data) {
			$("#selectbox_district_id").removeClass("trucncate_inline");
			$("#selectbox_district_id").removeClass("fix_display_center");
			VirtualSelect.init({
				ele: "#selectbox_district_id",
				options: data,
				multiple: true,
			});
		});
	});

	$('#selectbox_district_id').change(function() {
		var districtIds = this.value;
		ajaxRequest("/util/get-project_la_va/" + districtIds.join(), "GET", null, function(data) {
			$("#selectbox_project_id").removeClass("fix_display_center");
			$("#selectbox_project_id").removeClass("trucncate_inline");
			VirtualSelect.init({
				ele: "#selectbox_project_id",
				options: data,
				multiple: true,
			});
		});
	});
}

function makeUrlFromForm() {
	var urlParamater = [];

	// MAKE FORMALITY
	var fomalityValues = document.querySelector('#selectbox_hinhthuc_id').value;
	if (fomalityValues && fomalityValues.length > 0) {
		urlParamater.push("formality=" + fomalityValues.join("_"));
	}

	// MAKE CATEGORY
	var categoryValues = document.querySelector('#selectbox_category_id').value;
	if (categoryValues && categoryValues.length > 0) {
		urlParamater.push("category=" + categoryValues.join("t"));
	}

	// MAKE ĐỊA ĐIỂM
	var provinceId = document.querySelector('#selectbox_province_id').value;
	if (typeof (provinceId) != "undefined" && provinceId.length > 0) {
		urlParamater.push("location=" + provinceId);
	}

	// MAKE QUẬN HUEYEJN
	var districts = document.querySelector('#selectbox_district_id').value;
	if (districts && districts.length != 0) {
		urlParamater.push("district=" + districts.join("t"));
	}

	// MAKE PROJECT
	var projectValues = document.querySelector('#selectbox_project_id').value;
	if (typeof (projectValues) != "undefined" && projectValues.length > 0) {
		urlParamater.push("project=" + projectValues.join("t"));
	}

	// MAKE MỨC GIÁ
	var priceValues = document.querySelector('#selectbox_price_id').value;
	if (priceValues && priceValues.length > 0) {
		urlParamater.push("price=" + priceValues);
	}

	// MAKE DIỆN TÍCH
	var acreageValues = document.querySelector('#selectbox_dientich_id').value;
	if (acreageValues && acreageValues.length > 0) {
		urlParamater.push("acreage=" + acreageValues);
	}

	// MAKE TỪ KHÓA
	const keywordValue = $("#keyword_id").val();
	if (keywordValue && keywordValue.length > 0) {
		urlParamater.push("keyword=" + keywordValue);
	}
	window.location = "/search?" + urlParamater.join("&");
}

function ajaxRequest(url, mt, data, calBackFuntion) {
	var config = {
		method: mt,
		url: url,
		context: document.body,
	};

	if (data) {
		config.data = data;
		config.contentType = "application/json; charset=utf-8";
	}

	$.ajax(config).done(function(data) {
		calBackFuntion(data);
	});
}
