package it.uniroma3.agiw.indexing;

import org.json.simple.JSONObject;

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
