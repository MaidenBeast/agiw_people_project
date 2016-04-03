package it.uniroma3.agiw.indexing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class BulkWriter {
	
	private PrintStream destStream;
	private final String indexField = "\"_index\":";
	private final String typeField = "\"_type\":";
	private final String idField = "\"_id\":";
	private final String[] metaFields = {"bing_query_string", "title", "description", "url"};
	private FileOutputStream fs;
	
	public BulkWriter(String destination) throws FileNotFoundException {
		this.destStream = new PrintStream(new File(destination));
	}
	
	public void writeAction(String index, String type, String id, String htmlPath, String metaPath) throws Exception {
		this.writeIndexAction(index, type, id);
		this.writeSource(htmlPath, metaPath);
	}
	@SuppressWarnings("unchecked")
	private void writeIndexAction(String index, String type, String id) {
		JSONObject toWrite = new JSONObject();
		JSONObject actionParameters = new JSONObject();
		
		actionParameters.put("_index", index);
		actionParameters.put("_type", type);
		actionParameters.put("_id", id);
		toWrite.put("index", actionParameters);
		
		this.writeJSON(toWrite);
	}
	
	@SuppressWarnings("unchecked")
	private void writeSource(String htmlPath, String metaPath) throws Exception {
		HTMLParser htmlParser = new HTMLParser(htmlPath);
		MetaParser metaParser = new MetaParser(metaPath);
		
		JSONObject toWrite = new JSONObject();
		for (String mf : this.metaFields) {
			toWrite.put(mf, metaParser.getField(mf));
		}
		toWrite.put("html_text", htmlParser.getBodyText());
		
		this.writeJSON(toWrite);
	}
	
	private void writeJSON(JSONObject json) {
		this.destStream.println(json.toJSONString());
	}
}
