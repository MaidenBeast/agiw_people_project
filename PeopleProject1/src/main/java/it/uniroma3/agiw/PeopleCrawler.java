package it.uniroma3.agiw;

import java.io.IOException;

import it.uniroma3.agiw.bing.BingClient;
import it.uniroma3.agiw.csv.CsvHandler;

public class PeopleCrawler {
	private String bingKey;
	private CsvHandler csvHandler;
	private BingClient bingClient;
	private PeopleDataHandler pdHandler;
	
	public PeopleCrawler() {
		this.bingClient = new BingClient();
		this.pdHandler = new PeopleDataHandler();
	}
	
	public PeopleCrawler(String bingKey) {
		this();
		this.setBingKey(bingKey);
	}

	public String getBingKey() {
		return bingKey;
	}

	public void setBingKey(String bingKey) {
		this.bingKey = bingKey;
	}

	public CsvHandler getCsvHandler() {
		return csvHandler;
	}

	public void setCsvHandler(CsvHandler csvHandler) {
		this.csvHandler = csvHandler;
	}
	
	public void execute() throws IOException {
		PersonEntry pEntry = this.csvHandler.readNextRow();
		
		while (pEntry != null) {
			if (!pEntry.isAlreadyFetched()) { //se la entry ancora non è stata analizzata
				
			} //altrimenti non fare niente
			
			pEntry = this.csvHandler.readNextRow();
		}
	}
	
}
