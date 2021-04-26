function dateTargetChange() {
	var categoryId = $(
		"#category_id_list option[value='" +
		$('#category_id').val() + "']").attr(
			'key-data');
	var startDate = $("#startDate").val().replaceAll("-", "/");
	var endDate = $("#endDate").val().replaceAll("-", "/");

	if (!categoryId || categoryId.length == 0) {
		alert("Chọn danh mục của bài đăng này trước.");

		var elmnt = document.getElementById("title");
		elmnt.scrollIntoView();


		$("#category_id").css("border", "solid 1px red");
		return;
	}

	if (startDate.trim().length == 0 || endDate.trim().length == 0) {
		return;
	}

	var data = {
		startDate: startDate,
		endDate: endDate
	}

	ajaxRequest("/post/calcalator/" + categoryId, "POST", JSON
		.stringify(data),
		function(response) {

			if (response.status == true) {
				var tongTien = 0;
				tongTien += parseInt(response.data);
				var price = parseInt(response.data).toLocaleString(
					'en-US', {
					style: 'currency',
					currency: 'VND'
				});
				$("#price_result").html(price);

				tongTien += parseInt(response.data * 0.1);
				var thue = parseInt(response.data * 0.1)
					.toLocaleString('en-US', {
						style: 'currency',
						currency: 'VND'
					});
				$("#thue_result").html(thue);

				var tong = tongTien.toLocaleString('en-US', {
					style: 'currency',
					currency: 'VND'
				});
				$("#tongtien_result").html(tong);

				if (tongTien > $("#credit_user_hidden").val()) {
					$("#box_credit").removeClass("border-success");
					$("#box_credit").addClass("border-danger");

					$("#box_credit_text").removeClass("text-success");
					$("#box_credit_text").addClass("text-danger");
					$("#napthem").show();
					$("#submit_form").prop('disabled', true);
				} else {
					$("#box_credit").removeClass("border-danger");
					$("#box_credit").addClass("border-success");

					$("#box_credit_text").removeClass("text-danger");
					$("#box_credit_text").addClass("text-success");
					$("#napthem").hide();
					$("#submit_form").prop('disabled', false);
				}
				return;
			} else {
				alert("Vui lòng chọn ngày hợp lệ.")
			}

			// display error
			for (const key in response.data) {
				$("#" + key).append(response.data[key]);
			}
		});

}

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

function napThemOnClick() {
	obj = $("#input_nap_them");
	if (obj.val().length == 0 || parseInt(obj.val()) < 50000) {
		alert("Số tiền cần nạp không hợp lệ.")
		return;
	}

	$("#modal").modal("show");
}

function napOnClick() {
	var txt;
	var r = confirm("Xác nhận thanh toán.");
	if (r == false) {
		return;
	}

	var creditAdd = $("#input_nap_them").val();
	var data = {
		credit: creditAdd
	}
	ajaxRequest("/post/add-credit", "POST", JSON.stringify(data),
		function(response) {
			var credit = parseInt(response).toLocaleString(
				'en-US', {
				style: 'currency',
				currency: 'VND'
			});
			$("#credit_user").text(credit);

			$("#modal").modal("hide");
		});
}