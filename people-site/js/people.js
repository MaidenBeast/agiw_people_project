function render_search_bar() {
	$('.popup').css({ opacity: 0 });

	$('.form-container').submit(function(e) {
		e.preventDefault();
		var input = $('.form-container .search-field').val();

		if(!input || input == "Type search text here...") {
			$('.popup').css({ opacity: 0 });
			$('.popup').animate(
				{ opacity: 1 },
				{
					duration: 'slow',
					easing: 'easeOutBounce'
				});
			return false;
		} else {
			execute_query(input);
		}
	});

	$('.form-container .search-field').focus(function() {
		if($(this).val() == "Type search text here...") {
			this.value = "";
		}
	});

	$('.form-container .search-field').keydown(function() {
		$('.popup').css({ opacity: 0 });
	});
}

function print_index(query_string) {
	$.get("skeletons/first-page.html", function(data) {
		$("#container").html(data);

		if (query_string) {
			execute_query(query_string);
		} else {
			show_page();
		}
	});
}

function show_page() {
	render_search_bar();
	$("#container").show();
}

function execute_query(query_string) {
	var data_to_send = {
		"_source": false,
		"fields": ["title", "description"],
		"query": {
			"match": {
				//TODO da correggere
				//"title": query_string,
				//"description": query_string,
				"html_text": query_string
			}
		}
	};

	$.ajax({
		url: "http://"+window.hostname+":9200/people/page/_search", //endpoint elastic
		type: "GET",
		crossDomain: true,
        dataType: 'json',
        data: JSON.stringify(data_to_send),
        success: function(response) {
        	$("#container").append('<div id="output"></div>');
        	$("#output").append(JSON.stringify(response));
        	$("#container").addClass("searched");
        	show_page();
        }
	});
}