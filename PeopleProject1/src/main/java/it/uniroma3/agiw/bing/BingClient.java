package it.uniroma3.agiw.bing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import it.uniroma3.agiw.Person;

public class BingClient {
	private String bingKey;
	private final String SERVICE_ROOT_URI = "https://api.datamarket.azure.com/Bing/Search/";
	private final int OFFSET_PAGE = 50;
	//private static final Logger LOG = Logger.getLogger(BingClient.class);
	
	public BingClient() {}
	
	public BingClient(String bingKey) {
		this();
		this.bingKey = bingKey;
	}

	public List<BingEntry> executeBingQuery(Person person, int page) throws IOException {
		List<BingEntry> bingEntryList = new ArrayList<BingEntry>(50);
		
		String name = person.getName().replaceAll("'", "\\$\\$");
		String surname = person.getSurname().replaceAll("'", "\\$\\$");
		
		String query = URLEncoder.encode("'"+name+" "
				+surname+"'", "UTF-8");
		
		query = query.replaceAll("\\$\\$", "''");
		
		String market = URLEncoder.encode("'it-IT'", "UTF-8"); //pagine in italiano
		String adult = URLEncoder.encode("'Strict'", "UTF-8");
		String skip = String.valueOf(page*OFFSET_PAGE);
		
		String queryString = SERVICE_ROOT_URI+"Web?Query="+query+"&Market="+market+"&Adult="+adult+"&$skip="+skip+"&$format=JSON";
		
		String accountKeyEnc = new String(Base64.encodeBase64((this.bingKey + ":" + this.bingKey).getBytes()));
		URL url = new URL(queryString);
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("Authorization", "Basic " + accountKeyEnc);
        
        System.out.println("Getting results from "+queryString);
        try (final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            final StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            final JSONObject json = new JSONObject(response.toString());
            final JSONObject d = json.getJSONObject("d");
            final JSONArray results = d.getJSONArray("results");
            final int resultsLength = results.length();
            for (int i = 0; i < resultsLength; i++) {
                final JSONObject entity = results.getJSONObject(i);
                //System.out.println(aResult.get("Url"));
                
                BingEntry bingEntry = new BingEntry();
    			
    			bingEntry.setBingQueryString(queryString);
    			bingEntry.setBingQueryID(entity.getString("ID"));
    			bingEntry.setTitle(entity.getString("Title"));;
    			bingEntry.setDescription(entity.getString("Description"));
    			bingEntry.setDisplayUrl(entity.getString("DisplayUrl"));
    			bingEntry.setUrl(new URL(entity.getString("Url")));
    			
    			System.out.println(bingEntry); //debug
    			
    			bingEntryList.add(bingEntry);
            }
        }
		
		return bingEntryList;


	}
}
