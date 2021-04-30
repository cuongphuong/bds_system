function provinceChange() {
	var id = $("#province_id_list option[value='" + $('#province_id').val() + "']").attr('key-data');

	ajaxRequest(
		"/util/get-district/" + id,
		"GET",
		null,
		function(data) {
			var html = "";
			data
				.forEach(function(item) {
					html = html +
						"<option value=\"";
					html = html + item.name;
					html = html + "\" key-data=\"";
					html = html + item.id;
					html = html + "\"></option>";
				});

			$("#district_id").val("");
			$("#ward_id").val("");
			$("#street_id").val("");
			$("#project_id").val("");
			$("#district_id_list").empty();
			$("#district_id_list").append(html);
		});
}

function districtChange() {
	var id = $(
		"#district_id_list option[value='" +
		$('#district_id').val() + "']").attr(
			'key-data');

	$("#ward_id").val("");
	$("#street_id").val("");
	$("#project_id").val("");

	$("#project_id_list").empty();
	$("#ward_id_list").empty();
	$("#street_id_list").empty();

	ajaxRequest(
		"/util/get-ward/" + id,
		"GET",
		null,
		function(data) {
			var html = "";
			data
				.forEach(function(item) {
					html = html +
						"<option value=\"";
					html = html + item.name;
					html = html + "\" key-data=\"";
					html = html + item.id;
					html = html + "\"></option>";
				});
			$("#ward_id_list").append(html);
		});

	ajaxRequest(
		"/util/get-street/" + id,
		"GET",
		null,
		function(data) {
			var html = "";
			data
				.forEach(function(item) {
					html = html +
						"<option value=\"";
					html = html + item.name;
					html = html + "\" key-data=\"";
					html = html + item.id;
					html = html + "\"></option>";
				});

			$("#street_id_list").append(html);
		});

	ajaxRequest(
		"/util/get-project/" + id,
		"GET",
		null,
		function(data) {
			var html = "";
			data
				.forEach(function(item) {
					html = html +
						"<option value=\"";
					html = html + item.name;
					html = html + "\" key-data=\"";
					html = html + item.id;
					html = html + "\"></option>";
				});
			$("#project_id_list").append(html);
		});
}

function changePriceInput(obj) {
	var t = docso($("#price").val()) + " " +
		$("#unit option:selected").text();
	t = t.trim().toLowerCase();
	t = t.charAt(0).toUpperCase() + t.slice(1);
	$("#thanhchu").val(t);
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