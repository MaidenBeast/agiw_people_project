function print_index() {
	$.get("skeletons/first-page.html", function(data) {
		$("#container").html(data);
	});
}