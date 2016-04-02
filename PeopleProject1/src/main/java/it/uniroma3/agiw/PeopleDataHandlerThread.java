package it.uniroma3.agiw;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import it.uniroma3.agiw.bing.BingEntry;

public class PeopleDataHandlerThread extends Thread {
	private BingEntry entry;
	private Person person;
	
	public PeopleDataHandlerThread(BingEntry entry, Person person) {
		this.entry = entry;
		this.person = person;
	}
	
	public void run() {
		System.out.println("Fetching "+entry.getUrl());
		try {
			//FileUtils.copyURLToFile(entry.getUrl(), new File("output/"+entry.getBingQueryID()+".html"));
			Document doc = Jsoup.connect(entry.getUrl().toString()).get();
			String text = doc.text(); //solo testo, no tag (effettuo controllo su esso)
			
			String pToLowerCase = person.getName().toLowerCase()+" "
									+person.getSurname().toLowerCase();
			
			/*
			 * nel caso in cui c'è l'EXACT MATCH o per il titolo,
			 * o per la descrizione, o per il body html
			 */
			if (StringUtils.containsIgnoreCase(entry.getTitle(), pToLowerCase) ||
					StringUtils.containsIgnoreCase(entry.getDescription(), pToLowerCase) ||
					StringUtils.containsIgnoreCase(text, pToLowerCase)) {
				String html = doc.html();
				PrintWriter out = new PrintWriter("output/"+entry.getBingQueryID()+".html");
				out.println(html);
				out.close();
			} else {
				System.out.println("Dropped "+entry.getUrl());
				return; //esco dalla funzione
			}
			
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
