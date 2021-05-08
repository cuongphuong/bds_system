function initSelectBox() {
	init();

	//init pickup list
	pickUpFormality(function() { });
	loadCategoryList(function() { });
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

function loadCategoryList(doneAction) {
	VirtualSelect.init({
		ele: '#selectbox_category_id',
		options: [
			{ label: 'Thống kê theo loại tin', value: '1' },
			{ label: 'Thốn kê theo loại bất động sản', value: '2' },
			{ label: 'Thống kê theo tỉnh thành', value: '3' },
			{ label: 'Thống kê theo dự án', value: '4' }
		]
	});
	doneAction();
}

function init() {
	$(window).scroll(function() {
		var x1 = $(window).scrollTop();
		var x2 = $("#search_condition_id").offset().top;
		if (x1 >= x2) {
			$("#fixed_condition").addClass("fixed-top");
		} else {
			$("#fixed_condition").removeClass("fixed-top")
		}
	});
}
