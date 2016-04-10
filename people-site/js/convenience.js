/**
 * Funzioni ausiliarie ulteriori
 */
var stoptokens = /[':]/g;

//Costruttore di query
function submitted_query() {
	this.queryObject = {
			"from": 0,		//da cambiare in runtime
			"size": 10,
			"_source": false,
			"fields": ["title", "description", "url"],
			"highlight": {
				"fields": {
					"html_text": {
						"fragment_size" : 150,
						"number_of_fragments" : 3,
						"pre_tags" : ["<b>"],
						"post_tags" : ["</b>"]
					}
				}
			},
			"query": match_withKeywords
	}
}

function parseQueryString(parse_function, query_string, query_obj) {
	parse_function(query_string, query_obj);
}

function setUpQuery(qstr, qobj) {
	var cleanString = qstr.replace(stoptokens, "");
	qobj.query.bool.must[0].multi_match.query = cleanString;
}

var askCategory = function(qstr, qobj) {
	if (qstr.indexOf(":") < 0) {
		qobj.query.bool.must.splice(1, 1);
	}
	else {
		var category = qstr.split(":")[1];
		var cat_keywords = keywords[category];
		if (cat_keywords == undefined) {
			qobj.query.bool.must.splice(1, 1);
		}
		else {
			qobj.query.bool.must[1].match.html_text = keywords[category];
		}
	}
}

var askExactName = function(qstr, qobj) {
	if (qstr.indexOf("'") >= 0) {
		var exactName = qstr.split("'")[1].split(" ");
		
		qobj.query.bool.filter = {
				"multi_match" : {
					"query" : exactName.join(" "),
					"fields" : ["title", "description", "html_text"],
					"type" : "phrase"
				}
		};
	}
}