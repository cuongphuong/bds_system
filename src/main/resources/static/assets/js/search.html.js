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
		$("#search_condition_id").hide();
	}

	window.addEventListener('resize', function(event) {
		var widthH = window.innerWidth;
		if (widthH < 850) {
			$("#search_condition_id").hide();
		} else {
			$("#search_condition_id").show();
		}
	}, true);
	
	$(document).bind(
		'click',
		function(e) {
			if ($(e.target).parents(".popup_tab_block").length == 0 && (!$(e.target).attr('class') || !$(e.target).attr('class').includes("close_popup_check"))) {
				hideAnimation($(".popup_tab_block"));
				countLocThem();
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
	loadFrontWidth(function() { });
	loadFloorNumList(function() { });
	loadRoomList(function() { });
	loadWayList(function() { });
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
	if ($(obj).text() != 'Hi???n th??? t???t c???') {
		$(obj).parent().parent().removeClass("minishow_showmore");
		$(obj).parent().parent().addClass("mini_show");
		$(obj).text('Hi???n th??? t???t c???')
		$(obj).parent().css("position", "absolute");
		$(obj).parent().css("bottom", "-5px");
		$(obj).parent().css("margin-bottom", "");
	} else {
		$(obj).text('Thu g???n')
		$(obj).parent().parent().removeClass("mini_show");
		$(obj).parent().parent().addClass("minishow_showmore");
		$(obj).parent().css("position", "relative");
		$(obj).parent().css("bottom", "");
		$(obj).parent().css("margin-bottom", "10px");
	}
}

function makeUrlFromForm() {
	countLocThem();
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

	// MAKE ?????A ??I???M
	var provinceId = $('input[name=province_checkbox]:checked').attr("value");
	if (typeof (provinceId) != "undefined") {
		urlParamater.push("location=" + provinceId);
	}

	// MAKE QU???N HUEYEJN
	var districts = [];
	$.each($("input[name='district_checkbox']:checked"), function(K, V) {
		districts.push(V.value);
	});
	if (districts && districts.length != 0) {
		urlParamater.push("district=" + districts.join("t"));
	}

	// MAKE PH?????NG X??
	var wards = [];
	$.each($("input[name='ward_checkbox']:checked"), function(K, V) {
		wards.push(V.value);
	});
	if (wards && wards.length != 0) {
		urlParamater.push("ward=" + wards.join("t"));
	}

	// MAKE ???????NG PH???
	var streets = [];
	$.each($("input[name='street_checkbox']:checked"), function(K, V) {
		streets.push(V.value);
	});
	if (streets && streets.length != 0) {
		urlParamater.push("street=" + streets.join("t"));
	}

	// MAKE PROJECT
	var projectValues = document.querySelector('#selectbox_project_id').value;
	if (typeof (projectValues) != "undefined" && projectValues.length > 0) {
		urlParamater.push("project=" + projectValues.join("t"));
	}

	// MAKE M???C GI??
	var priceValues = document.querySelector('#selectbox_price_id').value;
	if (priceValues && priceValues.length > 0) {
		urlParamater.push("price=" + priceValues);
	}

	// MAKE DI???N T??CH
	var acreageValues = document.querySelector('#selectbox_dientich_id').value;
	if (acreageValues && acreageValues.length > 0) {
		urlParamater.push("acreage=" + acreageValues);
	}

	// MAKE T??? KH??A
	const keywordValue = $("#keyword_id").val();
	if (keywordValue && keywordValue.length > 0) {
		urlParamater.push("keyword=" + keywordValue);
	}

	// MAKE ????? R???NG M???T TI???N
	var frontWidth = document.querySelector('#selectbox_font-width_id').value;
	if (frontWidth && frontWidth.length > 0) {
		urlParamater.push("front=" + frontWidth.join("t"));
	}

	// MAKE ????? S??? T???NG
	var floorNum = document.querySelector('#selectbox_floor_id').value;
	if (floorNum && floorNum.length > 0) {
		urlParamater.push("floor=" + floorNum.join("t"));
	}

	// MAKE S??? PH??NG NG???
	var roomNum = document.querySelector('#selectbox_room_id').value;
	if (roomNum && roomNum.length > 0) {
		urlParamater.push("room=" + roomNum.join("t"));
	}

	// MAKE CHI???U R???NG ???????NG ??I
	var wayWith = document.querySelector('#selectbox_way_id').value;
	if (wayWith && wayWith.length > 0) {
		urlParamater.push("way=" + wayWith.join("t"));
	}

	window.history.replaceState(null, null, "?" + urlParamater.join("&"));
	onSearch("/search?" + urlParamater.join("&"));
}

function onChangeDiaChi() {
	var urlParamater = [];

	// MAKE T???NH
	var provinceIds = [];
	$.each($("input[name='province_checkbox_name']:checked"), function(K, V) {
		provinceIds.push(V.value);
	});
	if (provinceIds && provinceIds.length != 0) {
		urlParamater.push("province=" + provinceIds.join("t"));
	}

	window.history.replaceState(null, null, "?" + urlParamater.join("&"));
	onSearch("/search?" + urlParamater.join("&"));
}

function onChangeCategory() {
	var urlParamater = [];

	// MAKE T???NH
	var categorys = [];
	$.each($("input[name='category_checkbox_name']:checked"), function(K, V) {
		categorys.push(V.value);
	});
	if (categorys && categorys.length != 0) {
		urlParamater.push("ctg=" + categorys.join("t"));
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
				$("#count_result_id").text("C?? " + res.data.length + " b???t ?????ng s???n ???????c t??m th???y.");
				res.data.forEach((item) => {
					let rendered = Mustache.render(tmplPost, item);
					$("#content_search_result > .row").append(rendered);
				});
				setLike();
				$("#ladding_layer_id").hide(500);
			}, 1000);
		}
	});
}

function pickUpDanhMuc(urlParams) {
	// PICKUP DANH M???C
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
	//PICKUP T???NH TH??NH
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
	//PICKUP ?????A ??I???M
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
		// PICKUP PH????NG X??
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
		// PICKUP ???????NG
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
		// PICKUP D??? ??N
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
		// PICKUP QU???N HUY???N
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
			pickUpDienTich(urlParams);
		});
	} else {
		pickUpDienTich(urlParams);
	}
}

function pickUpDienTich(urlParams) {
	//PICKUP DI???N T??CH
	const acreageParamater = urlParams.get('acreage');
	if (acreageParamater != null) {
		var acreageList = acreageParamater.split("t");
		pickUpAcreageScope(function() {
			document.querySelector('#selectbox_dientich_id').setValue(acreageList);
			pickUpTuKhoa(urlParams);
		});
	} else {
		pickUpTuKhoa(urlParams);
	}

}

function pickUpTuKhoa(urlParams) {
	// PICKUP T??? KH??A
	const keywordParamater = urlParams.get('keyword');
	if (keywordParamater != null) {
		$("#keyword_id").val(keywordParamater);
	}
	pickUpFrontWidth(urlParams);
}

function pickUpFrontWidth(urlParams) {
	// PICKUP FRONT WIDTH
	const frontWidthParamater = urlParams.get('front');

	if (frontWidthParamater != null) {
		var fontWidthList = frontWidthParamater.split("t");
		loadFrontWidth(function() {
			document.querySelector('#selectbox_font-width_id').setValue(fontWidthList);
			pickUpSoTang(urlParams);
		});
	} else {
		pickUpSoTang(urlParams);
	}
}

function pickUpSoTang(urlParams) {
	// PICKUP S??? T???NG
	const floorNumParamater = urlParams.get('floor');

	if (floorNumParamater != null) {
		var floorList = floorNumParamater.split("t");
		loadFloorNumList(function() {
			document.querySelector('#selectbox_floor_id').setValue(floorList);
			pickUpSoPhong(urlParams);
		});
	} else {
		pickUpSoPhong(urlParams);
	}
}

function pickUpSoPhong(urlParams) {
	// PICKUP S??? PH??NG
	const roomNumParamater = urlParams.get('room');

	if (roomNumParamater != null) {
		var roomList = roomNumParamater.split("t");
		loadRoomList(function() {
			document.querySelector('#selectbox_room_id').setValue(roomList);
			pickUpDuongDi(urlParams);
		});
	} else {
		pickUpDuongDi(urlParams);
	}
}

function pickUpDuongDi(urlParams) {
	// PICKUP CHI???U R???NG ???????NG ??I
	const wayParamater = urlParams.get('way');
	if (wayParamater != null) {
		var wayList = wayParamater.split("t");
		loadWayList(function() {
			document.querySelector('#selectbox_way_id').setValue(wayList);
			// pickup xong form load ng?????c l???i form l??n request v?? g???i l??n server
			makeUrlFromForm();
		});
	}
	makeUrlFromForm();
}

function loadWayList(doneAction) {
	VirtualSelect.init({
		ele: '#selectbox_way_id',
		options: [
			{ label: 'M???t ph??? - M???t ???????ng', value: '10' },
			{ label: 'Ng?? ng??ch', value: '1' },
			{ label: 'Ng?? ?? t?? ????? c???a', value: '4' },
			{ label: 'Ng?? 1 ?? t??', value: '3' },
			{ label: 'Ng?? 2 ?? t?? tr??nh', value: '5' },
			{ label: 'Ng?? 3 ?? t?? tr??nh', value: '6' },
			{ label: 'Ng?? 4 ?? t?? tr??nh', value: '7' },
			{ label: 'Ng?? 4 ?? t?? tr??? l??n', value: '8' },
		],
		multiple: true
	});
	doneAction();
}

function loadRoomList(doneAction) {
	VirtualSelect.init({
		ele: '#selectbox_room_id',
		options: [
			{ label: '5 ph??ng', value: '5' },
			{ label: '1 ph??ng', value: '1' },
			{ label: '2 ph??ng', value: '2' },
			{ label: '3 ph??ng', value: '3' },
			{ label: '4 ph??ng', value: '4' },
			{ label: '6 ph??ng', value: '6' },
			{ label: '7 ph??ng', value: '7' },
			{ label: '8 ph??ng', value: '8' },
			{ label: '9 ph??ng', value: '9' },
			{ label: '10 ph??ng', value: '10' },
			{ label: 'L???n h??n 10 ph??ng', value: '-1' },
		],
		multiple: true
	});
	doneAction();
}

function loadFloorNumList(doneAction) {
	VirtualSelect.init({
		ele: '#selectbox_floor_id',
		options: [
			{ label: '1 t???ng', value: '1' },
			{ label: '2 t???ng', value: '2' },
			{ label: '3 t???ng', value: '3' },
			{ label: '4 t???ng', value: '4' },
			{ label: '5 t???ng', value: '5' },
			{ label: '6 t???ng', value: '6' },
			{ label: '7 t???ng', value: '7' },
			{ label: '8 t???ng', value: '8' },
			{ label: '9 t???ng', value: '9' },
			{ label: '10 t???ng', value: '10' },
			{ label: 'L???n h??n 10 t???ng', value: '-1' },
		],
		multiple: true
	});
	doneAction();
}

function loadFrontWidth(doneAction) {
	ajaxRequest("/util/get-front-width-scope-la_va", "GET", null, function(data) {
		VirtualSelect.init({
			ele: "#selectbox_font-width_id",
			options: data,
			multiple: true,
		});
		doneAction();
	});
}

function countLocThem() {
	var i = 0;
	
	// COUNT ????? R???NG M???T TI???N
	var frontWidth = document.querySelector('#selectbox_font-width_id').value;
	if (frontWidth && frontWidth.length > 0) {
		i = i + 1;
	}

	// COUNT ????? S??? T???NG
	var floorNum = document.querySelector('#selectbox_floor_id').value;
	if (floorNum && floorNum.length > 0) {
		i = i + 1;
	}

	// COUNT S??? PH??NG NG???
	var roomNum = document.querySelector('#selectbox_room_id').value;
	if (roomNum && roomNum.length > 0) {
		i = i + 1;
	}

	// COUNT CHI???U R???NG ???????NG ??I
	var wayWith = document.querySelector('#selectbox_way_id').value;
	if (wayWith && wayWith.length > 0) {
		i = i + 1;
	}
	
	if (i > 0) {
		$("#count_fillter_id").text(i + " ??i???u ki???n");
	} else {
		$("#count_fillter_id").text("");
	}
}


function pickupFromUrl() {
	var urlParams = getUrlParamt();

	// PICKUP H??NH TH???C
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
