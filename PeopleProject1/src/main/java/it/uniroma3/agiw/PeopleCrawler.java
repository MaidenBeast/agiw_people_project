package it.uniroma3.agiw;

import java.io.IOException;
import java.util.List;

import it.uniroma3.agiw.bing.BingClient;
import it.uniroma3.agiw.bing.BingEntry;
import it.uniroma3.agiw.csv.CsvHandler;

public class PeopleCrawler {
	//private String bingKey;
	private CsvHandler csvHandler;
	private BingClient bingClient;
	private PeopleDataHandler pdHandler;
	private int remainingQueries;
	
	public PeopleCrawler(int remainingQueries, String bingKey) {
		this.remainingQueries = remainingQueries;
		this.bingClient = new BingClient(bingKey);
		this.pdHandler = new PeopleDataHandler();
	}

	public CsvHandler getCsvHandler() {
		return csvHandler;
	}

	public void setCsvHandler(CsvHandler csvHandler) {
		this.csvHandler = csvHandler;
	}
	
	public void execute() throws IOException, InterruptedException {
		int queryPerPerson = 10;
		int remainingEntries = this.csvHandler.getRemainingEntries();
		
		if (remainingQueries/remainingEntries < 10) {
			queryPerPerson = remainingQueries/remainingEntries;
		}
		
		PersonEntry pEntry = this.csvHandler.readNextRow();
		
		while (pEntry != null && this.remainingQueries>1) {
			if (!pEntry.isAlreadyFetched()) { //se la entry ancora non è stata analizzata
				
				for (int i = 0; i<queryPerPerson; i++) {
					List<BingEntry> page =
							this.bingClient.executeBingQuery(pEntry.getPerson(), i);
					pdHandler.crawle(page, pEntry);
					Thread.sleep(1000); //attesa di un secondo
				}
				
			} //altrimenti non fare niente
			
			pEntry.setAlreadyFetched(true);
			this.csvHandler.updateRow(pEntry);
			pEntry = this.csvHandler.readNextRow();
		}
	}
	
}
