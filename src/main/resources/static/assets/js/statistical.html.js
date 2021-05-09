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
}

function getData() {
	clearChart();
	var dateStr = $('#daterange_id').val();
	var dateArr = dateStr.split("-");
	var data = {
		dateFrom: dateArr[0].trim(),
		dateTo: dateArr[1].trim(),
		type: $("#selectbox_category_id").val(),
		formality: $("#selectbox_hinhthuc_id").val().join("_")
	}

	let tmpl = $('#my-template').html();
	Mustache.parse(tmpl);
	var newItem = null;
	var finalItem = {
		label: "--",
		newsNumber: 0,
		price: 0,
		vat: 0,
		finalPrice: 0
	};

	$("#ladding_layer_id").show(500);
	ajaxRequest("/admin/statistical/province", "POST", JSON.stringify(data), function(res) {
		$("#content_data").empty();
		setTimeout(function() {
			res.forEach((item) => {
				newItem = {};

				newItem.label = item.label;
				newItem.newsNumber = item.newsNumber + " bài";
				newItem.price = Number((parseFloat(item.price)).toFixed(1)).toLocaleString() + " VND";
				newItem.vat = Number((parseFloat(item.vat)).toFixed(1)).toLocaleString() + " VND";
				newItem.finalPrice = Number((parseFloat(item.finalPrice)).toFixed(1)).toLocaleString() + " VND";

				finalItem.newsNumber = finalItem.newsNumber + parseFloat(item.newsNumber);
				finalItem.price = finalItem.price + parseFloat(item.price);
				finalItem.vat = finalItem.vat + parseFloat(item.vat);
				finalItem.finalPrice = finalItem.finalPrice + parseFloat(item.finalPrice);

				let rendered = Mustache.render(tmpl, newItem);
				$("#content_data").append(rendered);
			});

			finalItem.newsNumber = finalItem.newsNumber + " bài";
			finalItem.price = Number((parseFloat(finalItem.price)).toFixed(1)).toLocaleString() + " VND";
			finalItem.vat = Number((parseFloat(finalItem.vat)).toFixed(1)).toLocaleString() + " VND";
			finalItem.finalPrice = Number((parseFloat(finalItem.finalPrice)).toFixed(1)).toLocaleString() + " VND";

			let rendered = Mustache.render(tmpl, finalItem);
			$("#content_data").append(rendered);
			$("#ladding_layer_id").hide(500);


			makeChart(res);
		}, 1000);
	});
}

function genDescription(data) {
	var labelList = makeLabel(data);
	var colorList = ['#4e73df', '#1cc88a', '#36b9cc', '#0B3B39', '#086A87', '#5F04B4', '#FF00FF', '#5882FA', '#01A9DB', '#04B404', '#01DFA5', '#2EFE64', '#088A08'];

	var html = "";
	for (var i = 0; i < labelList.length; i++) {
		html = html + "<span class=\"mr-2\"> <i style=\"color: " + colorList[i] + " \" class=\"fas fa-circle\"></i>" + labelList[i] + "</span>"
	}
	var parent = document.getElementById('description_id');
	parent.innerHTML = html;
}

function clearChart() {
	var parent = document.getElementById('chart-area');
	var child = document.getElementById('myPieChart');
	parent.removeChild(child);
	parent.innerHTML = '<canvas id="myPieChart" style="width: 800px"></canvas>';
}

function makeChart(data) {
	genDescription(data);
	// Set new default font family and font color to mimic Bootstrap's default styling
	Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
	Chart.defaults.global.defaultFontColor = '#858796';

	// Pie Chart Example
	var ctx = document.getElementById("myPieChart");
	var myPieChart = new Chart(ctx, {
		type: 'doughnut',
		data: {
			labels: makeLabel(data),
			datasets: [{
				data: makeData(data),
				backgroundColor: ['#4e73df', '#1cc88a', '#36b9cc', '#0B3B39', '#086A87', '#5F04B4', '#FF00FF', '#5882FA', '#01A9DB', '#04B404', '#01DFA5', '#2EFE64', '#088A08'],
				hoverBorderColor: "rgba(234, 236, 244, 1)",
			}],
		},
		options: {
			maintainAspectRatio: false,
			tooltips: {
				backgroundColor: "rgb(255,255,255)",
				bodyFontColor: "#858796",
				borderColor: '#dddfeb',
				borderWidth: 1,
				xPadding: 15,
				yPadding: 15,
				displayColors: false,
				caretPadding: 10,
			},
			legend: {
				display: false
			},
			cutoutPercentage: 80,
		},
	});
}

function makeLabel(data) {
	var labelList = [];
	for (var i = 0; i < data.length; i++) {
		var item = data[i];
		labelList.push(item.label);
	}
	return labelList;
}

function makeData(data) {
	var dataList = [];
	for (var i = 0; i < data.length; i++) {
		var item = data[i];
		dataList.push(parseFloat(item.price));
	}
	return dataList;
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
