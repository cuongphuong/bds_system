function onSavePost(element, title, url, image, des, addess) {
	if (isExisted(url)) {
		deletePostLocalStorage(url);
		deletePostFromServer(url);
		$(element).removeClass("bxs-heart");
		$(element).addClass("bx-heart");
		return;
	}

	$(element).removeClass("bx-heart");
	$(element).addClass("bxs-heart");
	saveToServer(url);
	saveToLocalStorage(title, url, image, des, addess);
}

function deletePostFromServer(url) {
	ajaxRequest("/util/unlike", "POST", { url: url }, function(res) {
		loadMiniLikeBox();
	});
}

function deletePostLocalStorage(url) {
	var itemsArray = getFromLocalStorage();

	const filteredItems = itemsArray.filter(item => item.urlPost !== url);
	localStorage.setItem('saveItems', JSON.stringify(filteredItems));
	const data = JSON.parse(localStorage.getItem('saveItems'))
	return data;
}


function saveToServer(url) {
	ajaxRequest("/util/like", "POST", { url: url }, function(res) {
		loadMiniLikeBox();
	});
}

function saveToLocalStorage(title, url, image, des, addess) {
	var obj = {
		title: title,
		urlPost: url,
		thumnail: image,
		description: des,
		address: addess,
		create_at: new Date()
	}

	var itemsArray = getFromLocalStorage();
	itemsArray.push(obj);
	localStorage.setItem('saveItems', JSON.stringify(itemsArray))

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
		console.log(timeA);
		var html = `<a href="${item.urlPost}" class="list-group-item list-group-item-action" aria-current="true"><div class="d-flex w-100 justify-content-between"><h6 style="font-weight: bold; color: #333; line-height: 1.5; text-transform: uppercase;" class="mb-1">${item.title}</h6></div><p style="padding: 0; margin:0;" class="mb-1 article-info-location">${item.address}<br></p><p style="padding: 0; margin:0;" class="mb-1 article-price">❖ ${item.description}  ~ 〄 ${timeA}<br></p></a>`;
		$("#like_mini_box_id").append(html);
		x ++;
	}







}