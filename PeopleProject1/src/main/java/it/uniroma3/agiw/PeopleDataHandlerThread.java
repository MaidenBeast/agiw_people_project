package it.uniroma3.agiw;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import it.uniroma3.agiw.bing.BingEntry;

public class PeopleDataHandlerThread extends Thread {
	private BingEntry entry;
	
	public PeopleDataHandlerThread(BingEntry entry) {
		this.entry = entry;
	}
	
	public void run() {
		System.out.println("Fetching "+entry.getUrl());
		try {
			FileUtils.copyURLToFile(entry.getUrl(),
									new File("output/"+entry.getBingQueryID()+".html"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		System.out.println("Saved "+entry.getUrl()+" to "+"output/"+entry.getBingQueryID()+".html");
		
		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
		df.setTimeZone(tz);
		String fetchTime = df.format(new Date());
		
		JSONObject outObj = new JSONObject();
		
		outObj.put("bing_query_string", entry.getBingQueryString());
		outObj.put("fetch_date", fetchTime);
		outObj.put("title", entry.getTitle());
		outObj.put("description", entry.getDescription());
		outObj.put("url", entry.getUrl().toString());
		
		FileWriter jsonFile;
		try {
			jsonFile = new FileWriter("output/"+entry.getBingQueryID()+".meta.json");
			
			jsonFile.write(outObj.toString());
			System.out.println("Saved metadata file to "+"output/"+entry.getBingQueryID()+".meta.json");
			
			jsonFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
	}
}
