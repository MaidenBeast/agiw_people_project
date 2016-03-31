package it.uniroma3.agiw.bing;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.core4j.Enumerable;
import org.odata4j.consumer.ODataConsumer;
import org.odata4j.consumer.ODataConsumers;
import org.odata4j.core.Guid;
import org.odata4j.core.OEntity;
import org.odata4j.core.OQueryRequest;

import it.uniroma3.agiw.Person;

public class BingClient {
	private String bingKey;
	private final String SERVICE_ROOT_URI = "https://api.datamarket.azure.com/Bing/Search/";

	public BingClient() {}
	
	public BingClient(String bingKey) {
		this();
		this.bingKey = bingKey;
	}

	public List<BingEntry> executeBingQuery(Person person, int page) throws UnsupportedEncodingException, MalformedURLException {
		List<BingEntry> bingEntryList = new ArrayList<BingEntry>(50);
		
		String query = URLEncoder.encode("'"+person.getName()+" "
				+person.getSurname()+"'", "UTF-8");

		ODataConsumer c = ODataConsumers.dataMarket(this.SERVICE_ROOT_URI,
				this.bingKey);
		
		//Pare che il client OData effettui 5 transazioni alla volta...
		OQueryRequest<OEntity> oRequest = c.getEntities("Web")
				.custom("Query", query)
				.custom("Market", URLEncoder.encode("'it-IT'", "UTF-8"))	//pagine in italiano
				.custom("Adult", URLEncoder.encode("'Strict'", "UTF-8"))
				//.custom("$top", "50")
				.custom("$skip", String.valueOf(page*500));

		Enumerable<OEntity> entities = oRequest.execute();
		
		for (OEntity entity : entities) {
			BingEntry bingEntry = new BingEntry();
			
			bingEntry.setBingIDEntry(entity.getProperty("ID", Guid.class).getValue().toString());
			bingEntry.setTitle(entity.getProperty("Title", String.class).getValue());
			bingEntry.setDescription(entity.getProperty("Description", String.class).getValue());
			bingEntry.setDisplayUrl(entity.getProperty("DisplayUrl", String.class).getValue());
			bingEntry.setUrl(new URL(entity.getProperty("Url", String.class).getValue()));
			
			System.out.println(bingEntry); //debug
			
			bingEntryList.add(bingEntry);
		}
		
		return bingEntryList;


	}
}
