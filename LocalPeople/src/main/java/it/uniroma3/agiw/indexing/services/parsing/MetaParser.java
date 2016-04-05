package it.uniroma3.agiw.indexing.services.parsing;

import it.uniroma3.agiw.indexing.services.processing.FilePreparer;
import it.uniroma3.agiw.indexing.services.processing.MetaFilePreparer;

import org.json.simple.JSONObject;

public class MetaParser {
	
	private JSONObject jsonObject;
	
	public MetaParser(String json) throws Exception {
		this.jsonObject = this.prepareJson(json);
	}
	
	public Object getField(String fieldName) throws Exception {
		Object value = this.jsonObject.get(fieldName);
		return value;
	}
	
	public String getFieldAsString(String fieldName) throws Exception {
		String str = (String) this.getField(fieldName);
		DiacriticRemover dr = new DiacriticRemover();
		return dr.clean(str);
	}
	
	private JSONObject prepareJson(String json) throws Exception {
		FilePreparer fp = new MetaFilePreparer();
		return (JSONObject) fp.prepare(json);
	}
	
	
	

}
