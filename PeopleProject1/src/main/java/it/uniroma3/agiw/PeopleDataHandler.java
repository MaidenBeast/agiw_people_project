package it.uniroma3.agiw;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import it.uniroma3.agiw.bing.BingEntry;

public class PeopleDataHandler {

	//private static final Logger LOG = Logger.getLogger(PeopleDataHandler.class);
	
	public PeopleDataHandler() {
		new File("output").mkdir();
	}
	
	public void crawle(List<BingEntry> bingEntries) throws IOException {
		
		for (BingEntry entry : bingEntries) {
			PeopleDataHandlerThread thread = new PeopleDataHandlerThread(entry);
			thread.start();
		}
	}

}
