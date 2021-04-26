function onInitScreen() {
	$("#googleMap").height($("#content1").height() + 2);

	if ($("#box-contact-list").find(".contact-box").length == 0) {
		$(".existed_contact_id").removeClass("active");
		$("#profile-tab").addClass("active");
		$("#profile").addClass("show active");
	}

	var credit = parseInt($("#credit_user").text()).toLocaleString(
		'en-US', {
		style: 'currency',
		currency: 'VND'
	});

	$("#credit_user").text(credit);
}


function youtubeVideoFocus(obj) {
	var val = $(obj).val();
	var rx = /^.*(?:(?:youtu\.be\/|v\/|vi\/|u\/\w\/|embed\/)|(?:(?:watch)?\?v(?:i)?=|\&v(?:i)?=))([^#\&\?]*).*/;
	var result = val.match(rx);

	var emberUrl = "https://www.youtube.com/embed/hjhj?autoplay=1";
	if (val.length == 0 || !result) {
		$("#youtube_preview").attr("src", emberUrl);
		$("#youtube_preview_bl").hide(500);
		return;
	}

	emberUrl = "https://www.youtube.com/embed/" + result[1]
		+ "?autoplay=1";
	$("#youtube_preview").attr("src", emberUrl);
	$("#youtube_preview_bl").show(500);
}

function save() {
	$("#error_contact").hide(100);
	if (!validateJsForPost()) {
		return;
	}
	
	var r = confirm("Lưu tin?");
	if (r == false) {
		return;
	}
	
	$("#ladding_layer_id").show();

	// get data
	var data = {
		title: $("#title").val(),
		formality: $("#formality").val(),
		province_id: $("#province_id_list option[value='" + $('#province_id').val() + "']").attr('key-data'),
		ward_id: $("#ward_id_list option[value='" + $('#ward_id').val() + "']").attr('key-data'),
		project_id: $("#project_id_list option[value='" + $('#project_id').val() + "']").attr('key-data'),
		price: $("#price").val(),
		category_id: $("#category_id_list option[value='" + $('#category_id').val() + "']").attr('key-data'),
		district_id: $("#district_id_list option[value='" + $('#district_id').val() + "']").attr('key-data'),
		street_id: $("#street_id_list option[value='" + $('#street_id').val() + "']").attr('key-data'),
		acreage: $("#acreage").val(),
		unit: $("#unit").val(),

		//desscription
		description: $("#description").val(),
		video: $("#video-youtube").val(),

		// other
		front_width: $("#front_width").val(),
		direction: $("#direction").val(),
		floors_num: $("#floors_num").val(),
		toilet_num: $("#toilet_num").val(),
		entrance_width: $("#entrance_width").val(),
		room_num: $("#room_num").val(),
		furniture: $("#furniture").val(),
		juridical_info: $("#juridical_info").val(),
		image: $("#image").val(),

		// schedule
		startDate: $("#startDate").val().replaceAll("-", "/"),
		endDate: $("#endDate").val().replaceAll("-", "/"),

		//contact
		contact_method: $("ul#myTab li button.active").attr("id") == "#existed_contact" ? "existed" : "new",
	}
	data = { ...data, ...makeContactData() };

	$(".validate").empty();
	ajaxRequest(
		"/post/save",
		"POST",
		JSON.stringify(data),
		function(res) {
			setTimeout(function(){ $("#ladding_layer_id").hide(); }, 1000);
			
			if (res.status == true) {
				alert("OK");
				return;
			}

			// display error
			for (const key in res.data) {
				$("#" + key).append(res.data[key]);
			}
			
			var validateList = document.getElementsByClassName("validate");
			var destItem = null;
			for (var i = 0; i < validateList.length; i++) {
				var item = validateList[i];
			
				if (item.textContent.length > 0) {
					destItem = item;
					item.parentElement.scrollIntoView();
					return;
				}
			}
		});
}

function validateJsForPost() {
	if ($("ul#myTab li button.active").attr("id") == "#existed_contact"
		&& typeof $('input[name=contact_bds]:checked').attr("id") === "undefined") {
		var elmnt = document.getElementById("thong-tin-lien-he");
		$("#error_contact").show(100);
		elmnt.scrollIntoView();
		return false;
	}
	return true;
}

function makeContactData() {

	if ($("ul#myTab li button.active").attr("id") == "#existed_contact") {
		return { contact_ind: $('input[name=contact_bds]:checked').attr("id") };
	} else {
		return {
			contact_name: $("#contact_name").val(),
			diachi: $("#diachi").val(),
			phone: $("#phone").val(),
			email: $("#email").val(),
		}
	}
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