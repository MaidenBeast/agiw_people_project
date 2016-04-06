function getParameterByName(name, url) { //per prendere i parametri
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)", "i"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}

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
			history.pushState({}, null, "http://"+$.url("hostname")+$.url("path")+"/?query="+encodeURI(input));
			open_page();
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

function open_page() {
	$.get("skeletons/first-page.html", function(data) {
		$("#container").html(data);
		render_search_bar();

		if (getParameterByName("query")) {  //query parametrica via URL
			execute_query(decodeURI(getParameterByName("query")),
							(getParameterByName("page")) ? parseInt(getParameterByName("page")) : 1);
		} else {
			show_page();
		}
	});
}

/*function print_index(query_string, page) {
	$.get("skeletons/first-page.html", function(data) {
		$("#container").html(data);

		if (query_string) {
			execute_query(query_string, page);
		} else {
			show_page();
		}
	});
}*/

function show_page() {
	//render_search_bar();
	$("#container").show();
}

function execute_query(query_string, page) {
	$("#output").empty();
	$("#footer").empty();

	var data_to_send = {
		"from": 10*(page-1),
		"size": 10,
		"_source": false,
		"fields": ["title", "description", "url"],
		"query": {
			"multi_match": {
				"fields": ["title", "description", "html_text"],
				"query": query_string
			}
		}
	};

	$.ajax({
		url: "http://"+window.hostname+":9200/people/page/_search", //endpoint elastic
		type: "POST",
		crossDomain: true,
		contentType: "application/json",
        dataType: 'json',
        data: JSON.stringify(data_to_send),
        success: function(response) {
        	$("#container").append('<div id="output"></div><div id="footer"></div>');
        	//$("#output").append(JSON.stringify(response));

        	if (response && response.hits && response.hits.hits && response.hits.hits.length > 0) {
        		var hits = response.hits.hits;
        		for (var i = 0; i<hits.length; i++) {
        			var entry_to_append = '<div class="entry">'+
        								'<div><a class="link" href="'+hits[i].fields.url[0]+'">'+hits[i].fields.title[0]+'</a></div>'+
        								'<div class="url">'+hits[i].fields.url[0]+'</div>'+
        								'<div class="description">'+hits[i].fields.description[0]+'</div>'+
        								'</div>';

        			$("#output").append(entry_to_append);
        		}

        		var pages = Math.ceil(response.hits.total/10);

        		$("#footer").append('<table><tbody><tr></tr><tbody></table>');

        		for (var i = 1; i<=pages; i++) {
        			var page_to_append;

        			if (i==page) { //pagine corrente
        				page_to_append = '<td class="current">'+i+'</td>';
        			} else {
        				page_to_append = '<td>'+
        								'<a href="?query='+encodeURI(query_string)+'&page='+i+'">'+i+'</a></td>';
        			}

        			$("#footer tbody tr").append(page_to_append);      			

        		}

        		$("#footer tbody tr td a").click(function (event) {
					event.preventDefault();
					history.pushState({}, null, "http://"+$.url("hostname")+$.url("path")+"/"+$(this).attr("href"));
					open_page();
				});  

        	}

        	$("#container").addClass("searched");
        	show_page();
        }
	});
}