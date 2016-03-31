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
import org.odata4j.core.OClientBehaviors;
import org.odata4j.core.OEntity;
import org.odata4j.core.OQueryRequest;

import it.uniroma3.agiw.Person;

public class BingClient {
	private String bingKey;
	private final String SERVICE_ROOT_URI = "https://api.datamarket.azure.com/Bing/Search/v1/Web";

	public BingClient() {}
	
	public BingClient(String bingKey) {
		this();
		this.bingKey = bingKey;
	}

	public List<BingEntry> executeBingQuery(Person person) throws UnsupportedEncodingException, MalformedURLException {
		List<BingEntry> bingEntryList = new ArrayList<BingEntry>(50);
		
		String query = URLEncoder.encode("'"+person.getName()+" "
				+person.getSurname()+"'", "UTF-8");

		ODataConsumer c = ODataConsumers.dataMarket(this.SERVICE_ROOT_URI,
				this.bingKey);

		OQueryRequest<OEntity> oRequest = c.getEntities("Web")
				.custom("Query", query)
				.custom("Market", "it-IT")	//pagine in italiano
				.custom("Adult", "Strict");	//parental control

		Enumerable<OEntity> entities = oRequest.execute();
		
		for (OEntity entity : entities) {
			BingEntry bingEntry = new BingEntry();
			
			bingEntry.setBingIDEntry(entity.getProperty("ID", String.class).getValue());
			bingEntry.setTitle(entity.getProperty("Title", String.class).getValue());
			bingEntry.setDescription(entity.getProperty("Description", String.class).getValue());
			bingEntry.setDisplayUrl(entity.getProperty("DisplayUrl", String.class).getValue());
			bingEntry.setUrl(new URL(entity.getProperty("Url", String.class).getValue()));
			
			bingEntryList.add(bingEntry);
		}
		
		return bingEntryList;


	}
}
