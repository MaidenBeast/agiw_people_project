package it.uniroma3.agiw;

import java.io.IOException;
import java.util.List;

import javax.swing.plaf.SliderUI;

import it.uniroma3.agiw.bing.BingClient;
import it.uniroma3.agiw.bing.BingEntry;
import it.uniroma3.agiw.csv.CsvHandler;

public class PeopleCrawler {
	//private String bingKey;
	private CsvHandler csvHandler;
	private BingClient bingClient;
	private PeopleDataHandler pdHandler;
	private int remainingQueries;
	
	private static final int TRANS_PER_QUERY = 5;
	
	public PeopleCrawler(int remainingQueries, String bingKey) {
		this.remainingQueries = remainingQueries/TRANS_PER_QUERY;
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
		PersonEntry pEntry = this.csvHandler.readNextRow();
		
		while (pEntry != null && this.remainingQueries>1) {
			if (!pEntry.isAlreadyFetched()) { //se la entry ancora non è stata analizzata
				
				for (int i = 0; i<10; i++) {
					List<BingEntry> page =
							this.bingClient.executeBingQuery(pEntry.getPerson(), i);
					pdHandler.crawle(page);
					Thread.sleep(1000); //attesa di un secondo
				}
				
			} //altrimenti non fare niente
			
			this.csvHandler.updateRow(new PersonEntry(pEntry.getPerson(), true));
			pEntry = this.csvHandler.readNextRow();
		}
	}
	
}
