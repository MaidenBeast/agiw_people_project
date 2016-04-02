package it.uniroma3.agiw;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.uniroma3.agiw.bing.BingEntry;

public class PeopleDataHandler {

	//private static final Logger LOG = Logger.getLogger(PeopleDataHandler.class);
	private List<Thread> threads;
	
	public PeopleDataHandler() {
		new File("output").mkdir();
		threads = new ArrayList<Thread>(50);
	}
	
	public void crawle(List<BingEntry> bingEntries, Person person) throws IOException {
		
		for (BingEntry entry : bingEntries) {
			PeopleDataHandlerThread thread = new PeopleDataHandlerThread(entry, person);
			thread.start();
			threads.add(thread);
			
		}
		
		for (Thread t : threads) {
			try {
				t.join(); //attendi che tutti i thread si completino
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
