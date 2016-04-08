package it.uniroma3.agiw.indexing;

import it.uniroma3.agiw.indexing.services.parsing.HTMLParser;
import it.uniroma3.agiw.indexing.services.parsing.MetaParser;

import java.io.File;
import java.io.PrintStream;

import org.json.simple.JSONObject;

public class BulkWriter {
	
	private PrintStream destStream;
	private Config config;
	
	public BulkWriter(String destination) throws Exception {
		this.destStream = new PrintStream(new File(destination), "UTF-8");
		this.config = new Config("bulkwriter.properties");
	}
	
	public void writeAction(String index, String type, String id, String htmlPath, String metaPath) throws Exception {
		this.writeIndexAction(index, type, id);
		this.writeSource(htmlPath, metaPath);
	}
	
	public void writeAction(String index, String type, String htmlPath, String metaPath) throws Exception {
		this.writeIndexAction(index, type);
		this.writeSource(htmlPath, metaPath);
	}
	@SuppressWarnings("unchecked")
	private void writeIndexAction(String index, String type, String id) {
		JSONObject toWrite = new JSONObject();
		JSONObject actionParameters = new JSONObject();
		
		actionParameters.put(this.config.getPropertyValue("indexIndexField"), index);
		actionParameters.put(this.config.getPropertyValue("typeIndexField"), type);
		actionParameters.put(this.config.getPropertyValue("idIndexField"), id);
		toWrite.put("index", actionParameters);
		
		this.writeJSON(toWrite);
	}
	
	@SuppressWarnings("unchecked")
	private void writeIndexAction(String index, String type) {
		JSONObject toWrite = new JSONObject();
		JSONObject actionParameters = new JSONObject();
		
		actionParameters.put(this.config.getPropertyValue("indexIndexField"), index);
		actionParameters.put(this.config.getPropertyValue("typeIndexField"), type);
		toWrite.put("index", actionParameters);
		
		this.writeJSON(toWrite);
	}
	
	@SuppressWarnings("unchecked")
	private void writeSource(String htmlPath, String metaPath) throws Exception {
		HTMLParser htmlParser = new HTMLParser(htmlPath);
		MetaParser metaParser = new MetaParser(metaPath);
		
		JSONObject toWrite = new JSONObject();
		for (String mf : this.config.getPropertyArray("metaFields")) {
			toWrite.put(mf, metaParser.getFieldAsString(mf));
		}
		toWrite.put("html_text", htmlParser.getBodyText());
		
		this.writeJSON(toWrite);
	}
	
	private void writeJSON(JSONObject json) {
		this.destStream.println(json.toJSONString());
	}
}
