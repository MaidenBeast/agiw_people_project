package it.uniroma3.agiw.test;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

import it.uniroma3.agiw.Person;
import it.uniroma3.agiw.bing.BingClient;
import it.uniroma3.agiw.bing.BingEntry;

public class TestBingClient {
	
	public static void main(String[] args) {
		BingClient bingClient = new BingClient("b8rXeSU639rGO8IqB6ITd6Mk3vJXPcVYesgbvkwjxr8");
		List<BingEntry> bingEntryList = null;
		try {
			bingEntryList = bingClient.executeBingQuery(new Person("Valentino", "Rossi"), 0);
			//System.out.println(bingEntryList);
			System.out.println("Lunghezza lista: "+bingEntryList.size());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
