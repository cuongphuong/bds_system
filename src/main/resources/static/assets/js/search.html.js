// define constans

function init() {
	$("#popup_tab_block").css("top", $("#input_popup_tab").height() + 17);
	$(window).scroll(function() {
		var x1 = $(window).scrollTop();
		var x2 = $("#search_condition_id").offset().top;
		if (x1 >= x2) {
			$("#fixed_condition").addClass("fixed-top");
		} else {
			$("#fixed_condition").removeClass("fixed-top")
		}
	});

	var widthH = window.innerWidth;
	if (widthH < 850) {
		console.log("hide");
		$("#search_condition_id").hide();
	}

	window.addEventListener('resize', function(event) {
		var widthH = window.innerWidth;
		if (widthH < 850) {
			console.log("hide");
			$("#search_condition_id").hide();
		} else {
			$("#search_condition_id").show();
		}
	}, true);

	$(document).bind(
		'click',
		function(e) {
			//
			if ($(e.target).parents(".popup_tab_block").length == 0
				&& !$(e.target).attr('class').includes("close_popup_check")) {
				hideAnimation($(".popup_tab_block"));
			}
		});
}

function addParameterToURL(param) {
	_url = location.href;
	_url += (_url.split('?')[1] ? '&' : '?') + param;
	return _url;
}

function showAdressPopup(obj) {
	var popupElement = $(obj).next();

	if (popupElement.css('display') == 'block') {
		hideAnimation(popupElement);
		return;
	}
	showAnimation(popupElement, obj);
}

function hideAnimation(popupElement, obj) {
	popupElement.animate({
		top: $(obj).height() + 5,
		opacity: '0.1'
	}, 100);

	popupElement.animate({
		top: $(obj).height(),
		opacity: '0'
	}, 10);
	popupElement.hide(100, obj);
}

function showAnimation(popupElement, obj) {
	popupElement.css("top", $(obj).height() - 10);
	popupElement.css("opacity", 0);
	popupElement.show();

	popupElement.animate({
		top: $(obj).height() - 10,
		opacity: '0'
	}, 100);

	popupElement.animate({
		top: $(obj).height() + 17,
		opacity: '1'
	}, 100);
}

function pickUpFormality(doneAction) {
	ajaxRequest("/util/get-formality-la_va", "GET", null, function(data) {
		VirtualSelect.init({
			ele: "#selectbox_hinhthuc_id",
			options: data,
			multiple: true,
		});
		doneAction();
	});
}

function pickUpSortResult(doneAction) {
	ajaxRequest("/util/get-sort_la_va", "GET", null, function(data) {
		VirtualSelect.init({
			ele: "#selectbox_sort",
			options: data,
		});
		doneAction();
	});
}


function initSelectBox() {
	pickUpFormality(function() { });
	pickUpSortResult(function() { });
	pickUpCaterory(function() { });
	pickUpPriceScope(function() { });
	pickUpAcreageScope(function() { });

	ajaxRequest("/util/get-front-width-scope-la_va", "GET", null, function(data) {
		VirtualSelect.init({
			ele: "#selectbox_font-width_id",
			options: data,
			multiple: true,
		});
	});

	VirtualSelect.init({
		ele: '#selectbox_room_id',
		options: [
			{ label: '1 phòng', value: '1' },
			{ label: '2 phòng', value: '2' },
			{ label: '3 phòng', value: '3' },
			{ label: '4 phòng', value: '4' },
			{ label: '5 phòng', value: '5' },
			{ label: '6 phòng', value: '6' },
			{ label: '7 phòng', value: '7' },
			{ label: '8 phòng', value: '8' },
			{ label: '9 phòng', value: '9' },
			{ label: '10 phòng', value: '10' },
			{ label: 'Lớn hơn 10 phòng', value: '-1' },
		],
		multiple: true
	});

	VirtualSelect.init({
		ele: '#selectbox_floor_id',
		options: [
			{ label: '1 tầng', value: '1' },
			{ label: '2 tầng', value: '2' },
			{ label: '3 tầng', value: '3' },
			{ label: '4 tầng', value: '4' },
			{ label: '5 tầng', value: '5' },
			{ label: '6 tầng', value: '6' },
			{ label: '7 tầng', value: '7' },
			{ label: '8 tầng', value: '8' },
			{ label: '9 tầng', value: '9' },
			{ label: '10 tầng', value: '10' },
			{ label: 'Lớn hơn 10 tầng', value: '-1' },
		],
		multiple: true
	});

	VirtualSelect.init({
		ele: '#selectbox_way_id',
		options: [
			{ label: 'Mặt phố - Mặt đường', value: '10' },
			{ label: 'Ngõ ngách', value: '1' },
			{ label: 'Ngõ ô tô đỗ cửa', value: '4' },
			{ label: 'Ngõ 1 ô tô', value: '3' },
			{ label: 'Ngõ 2 ô tô tránh', value: '5' },
			{ label: 'Ngõ 3 ô tô tránh', value: '6' },
			{ label: 'Ngõ 4 ô tô tránh', value: '7' },
			{ label: 'Ngõ 4 ô tô trở lên', value: '8' },
		],
		multiple: true
	});

	onLoadProvince(function() { });
}

function onLoadProvince(doneAction) {
	// bindding data province
	let tmplProvince = $('#province-template').html();
	Mustache.parse(tmplProvince);

	ajaxRequest("/util/get-province_la_va", "GET", null, function(data) {
		$("#province_tab_id").empty();
		data.forEach((item) => {
			let rendered = Mustache.render(tmplProvince, item);
			$("#province_tab_id").append(rendered);
		});
		doneAction();
	});
}

function pickUpCaterory(doneAction) {
	ajaxRequest("/util/get-category-la_va", "GET", null, function(data) {
		VirtualSelect.init({
			ele: "#selectbox_category_id",
			options: data,
			multiple: true,
		});
		doneAction();
	});
}

function pickUpAcreageScope(doneAction) {
	ajaxRequest("/util/get-acreage-scope-la_va", "GET", null, function(data) {
		VirtualSelect.init({
			ele: "#selectbox_dientich_id",
			options: data,
		});
		doneAction();
	});
}

function pickUpPriceScope(doneAction) {
	ajaxRequest("/util/get-prices-scope-la_va", "GET", null, function(data) {
		VirtualSelect.init({
			ele: "#selectbox_price_id",
			options: data,
		});
		doneAction();
	});
}

function searchProvince() {
	$('#province_tab_id > .province_fillter').hide();
	var txt = $('#province_search_input_id').val();
	$('#province_tab_id > .province_fillter > label:contains("' + txt + '")').parent().show();
}

function searchDistrict() {
	$('#district_tab_id > .district_fillter').hide();
	var txt = $('#district_search_input_id').val();
	$('#district_tab_id > .district_fillter > label:contains("' + txt + '")').parent().show();
}

function searchWard() {
	$('#ward_tab_id > .ward_fillter').hide();
	var txt = $('#ward_search_input_id').val();
	$('#ward_tab_id > .ward_fillter > label:contains("' + txt + '")').parent().show();
}

function searchStreet() {
	$('#street_tab_id > .street_fillter').hide();
	var txt = $('#street_search_input_id').val();
	$('#street_tab_id > .street_fillter > label:contains("' + txt + '")').parent().show();
}

function onChangeProvince() {
	var provinceId = $('input[name=province_checkbox]:checked').attr("value");
	if (typeof (provinceId) === "undefined") {
		return;
	}
	$("#input_popup_tab").val($('input[name=province_checkbox]:checked').next().text().trim());

	onLoadDistrict(function() { });
}

function onLoadDistrict(doneAction) {
	var provinceId = $('input[name=province_checkbox]:checked').attr("value");
	// bindding data district
	let tmplDistrict = $('#district-template').html();
	Mustache.parse(tmplDistrict);
	ajaxRequest("/util/get-district_la_va/" + provinceId, "GET", null, function(data) {
		$("#district_tab_id").empty();
		$('#district-tab-head').tab('show');
		data.forEach((item) => {
			let rendered = Mustache.render(tmplDistrict, item);
			$("#district_tab_id").append(rendered);
		});
		doneAction();
	});
}

function onLoadWard(districts, doneAction) {
	// bindding data ward
	let tmplWard = $('#ward-template').html();
	Mustache.parse(tmplWard);
	ajaxRequest("/util/get-ward_la_va/" + districts.join(), "GET", null, function(data) {
		$("#ward_tab_id").empty();
		data.forEach((item) => {
			let rendered = Mustache.render(tmplWard, item);
			$("#ward_tab_id").append(rendered);
		});
		doneAction();
	});
}

function onLoadStreet(districts, doneAction) {
	// bindding data street
	let tmplStreet = $('#street-template').html();
	Mustache.parse(tmplStreet);
	ajaxRequest("/util/get-street_la_va/" + districts.join(), "GET", null, function(data) {
		$("#street_tab_id").empty();
		data.forEach((item) => {
			let rendered = Mustache.render(tmplStreet, item);
			$("#street_tab_id").append(rendered);
		});
		doneAction();
	});
}

function onLoadProject(districts, doneAction) {
	// bindding data project
	ajaxRequest("/util/get-project_la_va/" + districts.join(), "GET", null, function(data) {
		$("#selectbox_project_id").removeClass("fix_display_center");
		$("#selectbox_project_id").removeClass("trucncate_inline");
		VirtualSelect.init({
			ele: "#selectbox_project_id",
			options: data,
			multiple: true,
		});
		doneAction();
	});
}

function onChangeDistrict() {
	var districts = [];
	$.each($("input[name='district_checkbox']:checked"), function(K, V) {
		districts.push(V.value);
	});
	if (districts.length == 0) {
		return;
	}
	onLoadWard(districts, function() { });
	onLoadStreet(districts, function() { });
	onLoadProject(districts, function() { });
}


function ajaxRequest(url, method, data, calBackFuntion) {
	var config = {
		method: method,
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

function categoryOnShowMore(obj) {
	if ($(obj).text() != 'Hiển thị tất cả') {
		$(obj).parent().parent().removeClass("minishow_showmore");
		$(obj).parent().parent().addClass("mini_show");
		$(obj).text('Hiển thị tất cả')
		$(obj).parent().css("position", "absolute");
		$(obj).parent().css("bottom", "-5px");
		$(obj).parent().css("margin-bottom", "");
	} else {
		$(obj).text('Thu gọn')
		$(obj).parent().parent().removeClass("mini_show");
		$(obj).parent().parent().addClass("minishow_showmore");
		$(obj).parent().css("position", "relative");
		$(obj).parent().css("bottom", "");
		$(obj).parent().css("margin-bottom", "10px");
	}
}

function makeUrlFromForm() {
	var urlParamater = [];

	// MAKE FORMALITY
	var fomalityValues = document.querySelector('#selectbox_hinhthuc_id').value;
	if (fomalityValues.length > 0) {
		urlParamater.push("formality=" + fomalityValues.join("_"));
	}

	// MAKE CATEGORY
	var categoryValues = document.querySelector('#selectbox_category_id').value;
	if (categoryValues.length > 0) {
		urlParamater.push("category=" + categoryValues.join("t"));
	}

	// MAKE ĐỊA ĐIỂM
	var provinceId = $('input[name=province_checkbox]:checked').attr("value");
	if (typeof (provinceId) != "undefined") {
		urlParamater.push("location=" + provinceId);
	}

	// MAKE QUẬN HUEYEJN
	var districts = [];
	$.each($("input[name='district_checkbox']:checked"), function(K, V) {
		districts.push(V.value);
	});
	if (districts.length != 0) {
		urlParamater.push("district=" + districts.join("t"));
	}

	// MAKE PHƯỜNG XÃ
	var wards = [];
	$.each($("input[name='ward_checkbox']:checked"), function(K, V) {
		wards.push(V.value);
	});
	if (wards.length != 0) {
		urlParamater.push("ward=" + wards.join("t"));
	}

	// MAKE ĐƯỜNG PHỐ
	var streets = [];
	$.each($("input[name='street_checkbox']:checked"), function(K, V) {
		streets.push(V.value);
	});
	if (wards.length != 0) {
		urlParamater.push("street=" + streets.join("t"));
	}

	// MAKE PROJECT
	var projectValues = document.querySelector('#selectbox_project_id').value;
	if (typeof (projectValues) != "undefined" && projectValues.length > 0) {
		urlParamater.push("project=" + projectValues.join("t"));
	}

	// MAKE MỨC GIÁ
	var priceValues = document.querySelector('#selectbox_price_id').value;
	if (priceValues.length > 0) {
		urlParamater.push("price=" + priceValues);
	}

	// MAKE DIỆN TÍCH
	var acreageValues = document.querySelector('#selectbox_dientich_id').value;
	if (acreageValues.length > 0) {
		urlParamater.push("acreage=" + acreageValues);
	}


	// MAKE TỪ KHÓA
	const keywordValue = $("#keyword_id").val();
	if (keywordValue.length > 0) {
		urlParamater.push("keyword=" + keywordValue);
	}

	window.history.replaceState(null, null, "?" + urlParamater.join("&"));
	onSearch("/search?" + urlParamater.join("&"));
}

function onSearch(url) {
	let tmplPost = $('#post-result-template').html();
	Mustache.parse(tmplPost);

	$("#ladding_layer_id").show(500);
	ajaxRequest(url, "POST", null, function(res) {
		$("#content_search_result > .row").empty();
		if (res.status == true) {
			setTimeout(function() {
				$("#count_result_id").text("Có " + res.data.length + " bất động sản được tìm thấy.");
				res.data.forEach((item) => {
					let rendered = Mustache.render(tmplPost, item);
					$("#content_search_result > .row").append(rendered);
				});
				$("#ladding_layer_id").hide(500);
			}, 1000);
		}
	});
}

function pickUpDanhMuc(urlParams) {
	// PICKUP DANH MỤC
	const categoryParamater = urlParams.get('category');
	if (categoryParamater != null) {
		var categoryList = categoryParamater.split("t");
		$("input[name='category_checkbox_name']").each(function() {
			if (categoryList.includes($(this).val())) {
				this.checked = true;
			}
		});

		pickUpCaterory(function() {
			document.querySelector('#selectbox_category_id').setValue(categoryList);
			pickUpTinhThanh(urlParams);
			pickUpDiaDiem(urlParams);
		});
	} else {
		pickUpTinhThanh(urlParams);
		pickUpDiaDiem(urlParams);
	}
}

function pickUpTinhThanh(urlParams) {
	//PICKUP TỈNH THÀNH
	const provinceParamater = urlParams.get('province');

	if (provinceParamater != null) {
		var provinceList = provinceParamater.split("t");
		$("input[name='province_checkbox_name']").each(function() {
			if (provinceList.includes($(this).val())) {
				this.checked = true;
			}
		});
	}
}

function pickUpDiaDiem(urlParams) {
	//PICKUP ĐỊA ĐIỂM
	const locationParamater = urlParams.get('location');
	if (locationParamater != null) {
		var locationList = locationParamater.split("t");

		if (locationList.length > 1) return;
		onLoadProvince(function() {
			$("input[name=province_checkbox][value=" + locationParamater + "]").prop('checked', true);
			$("#input_popup_tab").val($('input[name=province_checkbox]:checked').next().text().trim());
			pickUpQuanHuyen(urlParams);
		});
	} else {
		pickUpMucGia(urlParams);
	}
}

function pickUpXaPhuong(urlParams, districtList) {
	onLoadWard(districtList, function() {
		// PICKUP PHƯƠNG XÃ
		const wardParamater = urlParams.get('ward');
		if (wardParamater != null) {
			var wardList = wardParamater.split("t");
			$("input[name='ward_checkbox']").each(function() {
				if (wardList.includes($(this).val())) {
					this.checked = true;
				}
			});
			pickUpTenDuong(urlParams, districtList);
		} else {
			pickUpTenDuong(urlParams, districtList);
		}
	});
}

function pickUpTenDuong(urlParams, districtList) {
	onLoadStreet(districtList, function() {
		// PICKUP ĐƯỜNG
		const streetParamater = urlParams.get('street');
		if (streetParamater != null) {
			var streetList = streetParamater.split("t");
			$("input[name='street_checkbox']").each(function() {
				if (streetList.includes($(this).val())) {
					this.checked = true;
				}
			});
			pickUpDuAn(urlParams, districtList);
		} else {
			pickUpDuAn(urlParams, districtList)
		}
	});
}

function pickUpDuAn(urlParams, districtList) {
	onLoadProject(districtList, function() {
		// PICKUP DỰ ÁN
		const projectParamater = urlParams.get('project');
		if (projectParamater != null) {
			var streetList = projectParamater.split("t");
			document.querySelector('#selectbox_project_id').setValue(streetList);
			pickUpMucGia(urlParams);
		} else {
			pickUpMucGia(urlParams);
		}
		
	});
}

function pickUpQuanHuyen(urlParams) {
	onLoadDistrict(function() {
		// PICKUP QUẬN HUYỆN
		const districtParamater = urlParams.get('district');
		if (districtParamater != null) {
			var districtList = districtParamater.split("t");
			$("input[name='district_checkbox']").each(function() {
				if (districtList.includes($(this).val())) {
					this.checked = true;
				}
			});
			pickUpXaPhuong(urlParams, districtList);
		} else {
			pickUpMucGia(urlParams);
		}
	});
}

function pickUpMucGia(urlParams) {
	//PICKUP PRICE SCOPE
	const priceParamater = urlParams.get('price');
	if (priceParamater != null) {
		var priceList = priceParamater.split("t");
		pickUpPriceScope(function() {
			document.querySelector('#selectbox_price_id').setValue(priceList);
		});
		pickUpDienTich(urlParams);
	} else {
		pickUpDienTich(urlParams);
	}
}

function pickUpDienTich(urlParams) {
	//PICKUP DIỆN TÍCH
	const acreageParamater = urlParams.get('acreage');
	if (acreageParamater != null) {
		var acreageList = acreageParamater.split("t");
		pickUpAcreageScope(function() {
			document.querySelector('#selectbox_dientich_id').setValue(acreageList);
		});
		pickUpTuKhoa(urlParams);
	} else {
		pickUpTuKhoa(urlParams);
	}

}

function pickUpTuKhoa(urlParams) {
	// PICKUP TỪ KHÓA
	const keywordParamater = urlParams.get('keyword');
	if (keywordParamater != null) {
		$("#keyword_id").val(keywordParamater);
	}
	makeUrlFromForm();
}

function pickupFromUrl() {
	var urlParams = getUrlParamt();

	// PICKUP HÌNH THỨC
	const formalityParamater = urlParams.get('formality');
	if (formalityParamater != null) {
		var formalityList = formalityParamater.split("_");

		pickUpFormality(function() {
			document.querySelector('#selectbox_hinhthuc_id').setValue(formalityList);
			pickUpDanhMuc(urlParams);
		});
	} else {
		pickUpDanhMuc(urlParams);
	}
}

function getUrlParamt() {
	const queryString = window.location.search;
	const urlParams = new URLSearchParams(queryString);
	return urlParams;
}
