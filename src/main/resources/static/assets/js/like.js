function onSavePost(element, title, url, image, des, addess, format, category, newsId) {
	var clz = $(element).attr("class");
	var arr = clz.split(" ");
	var tmp = "";
	for (var i = 0; i < arr.length; i++) {
		if (arr[i].startsWith("liked_")) {
			tmp = arr[i];
		}
	}

	var elementList = $("." + tmp);
	if (isExisted(url)) {
		deletePostLocalStorage(url);
		deletePostFromServer(url);

		if (arr.length > 0) {
			$(elementList).removeClass("bxs-heart");
			$(elementList).addClass("bx-heart");
		}
		return;
	}

	if (arr.length > 0) {
		$(elementList).removeClass("bx-heart");
		$(elementList).addClass("bxs-heart");
	}

	saveToServer(url);
	saveToLocalStorage(title, url, image, des, addess, format, category, newsId);
}

function setNoficationNumber() {
	var list = getFromLocalStorage();

	if (list.length > 0) {
		$("#number_of_like_id").show(500);
		$("#number_of_like_id").text(list.length);
	} else {
		$("#number_of_like_id").hide(500);
	}

}

function deletePostFromServer(url) {
	ajaxRequest("/util/unlike", "POST", { url: url }, function(res) {
		loadMiniLikeBox();
		setNoficationNumber();
	});
}

function deletePostLocalStorage(url) {
	var itemsArray = getFromLocalStorage();

	const filteredItems = itemsArray.filter(item => item.urlPost !== url);
	localStorage.setItem('saveItems', JSON.stringify(filteredItems));
	const data = JSON.parse(localStorage.getItem('saveItems'));
	setNoficationNumber();
	return data;
}


function saveToServer(url) {
	ajaxRequest("/util/like", "POST", { url: url }, function(res) {
		loadMiniLikeBox();
		setNoficationNumber();
	});
}

function saveToLocalStorage(title, url, image, des, addess, format, category, newsId) {
	var obj = {
		title: title,
		urlPost: url,
		thumnail: image,
		description: des,
		address: addess,
		create_at: new Date(),
		format: format,
		category: category,
		newsId: newsId
	}

	var itemsArray = getFromLocalStorage();
	itemsArray.push(obj);
	localStorage.setItem('saveItems', JSON.stringify(itemsArray));
	setNoficationNumber();

	const data = JSON.parse(localStorage.getItem('saveItems'))
	return data;
}

function isExisted(url) {
	let itemsArray = localStorage.getItem('saveItems')
		? JSON.parse(localStorage.getItem('saveItems'))
		: []

	for (var i = 0; i < itemsArray.length; i++) {
		var item = itemsArray[i];
		if (item.urlPost == url)
			return true;
	}
	return false;
}

function getFromLocalStorage() {
	let itemsArray = localStorage.getItem('saveItems')
		? JSON.parse(localStorage.getItem('saveItems'))
		: []

	return itemsArray;
}

function loadMiniLikeBox() {
	var itemsArray = getFromLocalStorage();
	if (itemsArray.length == 0) {
		$("#like_mini_box_id").text("Không có bài viết nào");
		return;
	}

	$("#like_mini_box_id").empty();
	var x = 0;

	itemsArray = itemsArray.sort((a, b) => (a.create_at < b.create_at) ? 1 : -1)
	for (var i = 0; i < itemsArray.length; i++) {
		if (x == 3) return;
		var item = itemsArray[i];
		var timeA = timeAgo(new Date(item.create_at));
		var html = `<a href="${item.urlPost}" class="list-group-item list-group-item-action" aria-current="true"><div class="d-flex w-100 justify-content-between"><h6 style="font-weight: bold; color: #333; line-height: 1.5; text-transform: uppercase;" class="mb-1">${item.title}</h6></div><p style="padding: 0; margin:0;" class="mb-1 article-info-location">${item.address}<br></p><p style="padding: 0; margin:0;" class="mb-1 article-price">❖ ${item.description}  ~ 〄 ${timeA}<br></p></a>`;
		$("#like_mini_box_id").append(html);
		x++;
	}
}

function setLike() {
	var itemsArray = getFromLocalStorage();
	$('.checked_like').each(function(index) {
		if (isExistedInArr(itemsArray, $(this).attr("th-data"))) {
			$(this).removeClass("bx-heart");
			$(this).addClass("bxs-heart");
		}
	});
}

function isExistedInArr(arr, item) {
	for (var i = 0; i < arr.length; i++) {
		var obj = arr[i];
		if (obj.urlPost == item)
			return true;
	}
	return false;
}
