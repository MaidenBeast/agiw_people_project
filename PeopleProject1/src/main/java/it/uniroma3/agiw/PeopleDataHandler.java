package it.uniroma3.agiw;

import java.io.File;
import java.io.IOException;
import java.util.List;

import it.uniroma3.agiw.bing.BingEntry;

public class PeopleDataHandler {

	//private static final Logger LOG = Logger.getLogger(PeopleDataHandler.class);
	
	public PeopleDataHandler() {
		new File("output").mkdir();
	}
	
	public void crawle(List<BingEntry> bingEntries, Person person) throws IOException {
		
		for (BingEntry entry : bingEntries) {
			PeopleDataHandlerThread thread = new PeopleDataHandlerThread(entry, person);
			thread.start();
		}
	}

}
