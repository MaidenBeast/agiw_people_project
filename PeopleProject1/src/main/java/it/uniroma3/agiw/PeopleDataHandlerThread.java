package it.uniroma3.agiw;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import it.uniroma3.agiw.bing.BingEntry;

public class PeopleDataHandlerThread extends Thread {
	private BingEntry entry;
	private PersonEntry pEntry;
	
	public PeopleDataHandlerThread(BingEntry entry, PersonEntry pEntry) {
		this.entry = entry;
		this.pEntry = pEntry;
	}
	
	public void run() {
		System.out.println("Fetching "+entry.getUrl());
		this.pEntry.increaseFetchedPages();
		
		String replacedName = pEntry.getPerson().getName().replaceAll("\\s|'", "_");
		String replacedSurname = pEntry.getPerson().getSurname().replaceAll("\\s|'", "_");
		String outFilename = replacedName+"_"+replacedSurname+"_"+DigestUtils.sha1Hex(entry.getUrl().toString());
		
		try {
			//FileUtils.copyURLToFile(entry.getUrl(), new File("output/"+entry.getBingQueryID()+".html"));
			Document doc = Jsoup.connect(entry.getUrl().toString()).get();
			String text = doc.text(); //solo testo, no tag (effettuo controllo su esso)
			
			String pToLowerCase1 = pEntry.getPerson().getName().toLowerCase()+" "
									+pEntry.getPerson().getSurname().toLowerCase(); //"<nome> <cognome>"
			
			String pToLowerCase2 = pEntry.getPerson().getSurname().toLowerCase()+" "
					+pEntry.getPerson().getName().toLowerCase(); //"<cognome> <nome>"
			
			/*
			 * nel caso in cui c'è l'EXACT MATCH o per il titolo,
			 * o per la descrizione, o per il body html
			 */
			if (StringUtils.containsIgnoreCase(entry.getTitle(), pToLowerCase1) ||
					StringUtils.containsIgnoreCase(entry.getDescription(), pToLowerCase1) ||
					StringUtils.containsIgnoreCase(text, pToLowerCase1) ||
				StringUtils.containsIgnoreCase(entry.getTitle(), pToLowerCase2) ||
					StringUtils.containsIgnoreCase(entry.getDescription(), pToLowerCase2) ||
					StringUtils.containsIgnoreCase(text, pToLowerCase2)) {
				String html = doc.html();
				//PrintWriter out = new PrintWriter("output/"+entry.getBingQueryID()+".html");
				PrintWriter out = new PrintWriter("output/"+outFilename+".html");
				out.println(html);
				out.close();
				this.pEntry.increaseSavedPages();
				//System.out.println("Saved "+entry.getUrl()+" to "+"output/"+entry.getBingQueryID()+".html "+pEntry.getPerson());
				System.out.println("Saved "+entry.getUrl()+" to "+"output/"+outFilename+".html "+pEntry.getPerson());
			} else {
				this.pEntry.increaseDroppedPages();
				System.out.println("Dropped "+entry.getUrl()+" "+pEntry.getPerson());
				return; //esco dalla funzione
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error while fetching "+entry.getUrl()+" "+pEntry.getPerson());
			this.pEntry.increaseErrorPages();
			e.printStackTrace();
			return;
		}
		
		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
		df.setTimeZone(tz);
		String fetchTime = df.format(new Date());
		
		JSONObject outObj = new JSONObject();
		
		outObj.put("bing_query_string", entry.getBingQueryString());
		outObj.put("fetch_date", fetchTime);
		outObj.put("name", pEntry.getPerson().getName());
		outObj.put("surname", pEntry.getPerson().getSurname());
		outObj.put("title", entry.getTitle());
		outObj.put("description", entry.getDescription());
		outObj.put("url", entry.getUrl().toString());
		
		FileWriter jsonFile;
		try {
			//jsonFile = new FileWriter("output/"+entry.getBingQueryID()+".meta.json");
			jsonFile = new FileWriter("output/"+outFilename+".meta.json");
			
			jsonFile.write(outObj.toString());
			//System.out.println("Saved metadata file to "+"output/"+entry.getBingQueryID()+".meta.json");
			System.out.println("Saved metadata file to "+"output/"+outFilename+".meta.json");
			
			jsonFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.pEntry.increaseErrorPages();
			return;
		}
	}
}
