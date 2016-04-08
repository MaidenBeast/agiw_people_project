var elastic_query = {
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
	"query": {
		"multi_match": {
			"fields": ["title", "description", "html_text"],
			"query": ""	//da cambiare in runtime
		}
	}
};

//Sistemi di query alternativi: sostituire alla voce query di elastic_query
var standard = {
		"query:" {
			"multi_match": {
				"fields": ["title", "description", "html_text"],
				"query": ""
			}
		}
}

var relevance_byField = {
		"query:" {
			"multi_match": {
				"fields": ["title^1.5", "description^1.25", "html_text"],
				"query": "",
				"type": "best_fields"
			}
		}
}