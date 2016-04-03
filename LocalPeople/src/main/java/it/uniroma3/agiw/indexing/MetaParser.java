package it.uniroma3.agiw.indexing;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MetaParser {
	
	private JSONObject jsonObject;
	
	public MetaParser(String json) throws Exception {
		this.jsonObject = this.prepareJson(json);
	}
	
	public Object getField(String fieldName) {
		Object value = this.jsonObject.get(fieldName);
		return value;
	}
	
	private JSONObject prepareJson(String json) throws Exception {
		FilePreparer fp = new MetaFilePreparer();
		return (JSONObject) fp.prepare(json);
	}
	
	
	

}
