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
	$.get("/skeletons/first-page.html", function(data) {
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

	/*var data_to_send = {
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
	};*/

	elastic_query["from"] = 10*(page-1);
	//Settaggio query
	match_withKeywords["bool"]["must"][0]["multi_match"]["query"] = query_string;
	//TEST - Parte in automatico per ogni query. Nella versione definitiva deve essere innescato da un trigger nella query string
	match_withKeywords["bool"]["must"][1]["match"]["html_text"] = pallavolo_words;
	//Selezione tipo di query
	elastic_query["query"] = match_withKeywords;
	
	

	$.ajax({
		url: "http://"+window.hostname+":9200/people/page/_search", //endpoint elastic
		type: "POST",
		crossDomain: true,
		contentType: "application/json",
        dataType: 'json',
        data: JSON.stringify(elastic_query),
        success: function(response) {
        	$("#container").append('<div id="output"><div id="counter"></div></div><div id="footer"></div>');
        	//$("#output").append(JSON.stringify(response));

        	if (response && response.hits && response.hits.hits && response.hits.hits.length > 0) {
        		var pages = Math.ceil(response.hits.total/10);

        		$("#counter").append('Pagina '+page+' di '+response.hits.total+' risultati');

        		var hits = response.hits.hits;
        		for (var i = 0; i<hits.length; i++) {
        			var scoring = Math.round(hits[i]._score*1000);

        			var entry_to_append = '<div class="entry">'+
        								'<div><a class="link" href="'+hits[i].fields.url[0]+'">'+hits[i].fields.title[0]+'</a>'+
        								'<span>Score: '+hits[i]._score+'<progress value="'+scoring+'" max="1000"></progress></span></div>'+
        								'<div class="url">'+hits[i].fields.url[0]+'</div>'+
        								'<div class="description">'+hits[i].fields.description[0]+'</div>';
        			
        			for (var j = 0; hits[i].highlight && hits[i].highlight.html_text && j<hits[i].highlight.html_text.length; j++) {
        				entry_to_append += 	'<div class="description">'+hits[i].highlight.html_text[j]+'</div>';
        			}

        								
        			entry_to_append += '</div>';

        			$("#output").append(entry_to_append);
        		}

        		$("#footer").append('<table><tbody><tr></tr><tbody></table>');

        		var left_limit_page = (page>=7 && pages>10) ? (page-5) : 1;
        		var right_limit_page = (page<left_limit_page+5 && pages>10) ? left_limit_page+9 : (page<=pages-7 && pages>10) ? page+5 : pages;
        		
        		if (page>1) {
        			$("#footer tbody tr").append('<td class="current"><a href="?query='+encodeURI(query_string)+'&page='+(page-1)+'">&lt</a></td>');  
        		}

        		for (var i = left_limit_page; i<=right_limit_page; i++) {
        			var page_to_append;

        			if (i==page) { //pagina corrente
        				page_to_append = '<td class="current">'+i+'</td>';
        			} else {
        				page_to_append = '<td>'+
        								'<a href="?query='+encodeURI(query_string)+'&page='+i+'">'+i+'</a></td>';
        			}

        			$("#footer tbody tr").append(page_to_append);      			

        		}

        		if (page<pages) {
        			$("#footer tbody tr").append('<td class="current"><a href="?query='+encodeURI(query_string)+'&page='+(page+1)+'">&gt</a></td>'); 
        		}

        		$("#footer tbody tr td a").click(function (event) {
					event.preventDefault();
					history.pushState({}, null, "http://"+$.url("hostname")+$.url("path")+"/"+$(this).attr("href"));
					open_page();
				});  

        	} else {
        		$("#output").append('<h3>Non è stato trovato alcun risultato per </h3>'+query_string);
        	}

        	$("#container").addClass("searched");
        	show_page();
        }
	});
}