package it.uniroma3.agiw;

import java.io.File;
import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import it.uniroma3.agiw.csv.CsvHandler;

public class Main {

	public static void main(String[] args) {
		Options options = new Options();

		options.addOption("k", "bing-key", true, "The Bing Key for fetching results from the search engine");
		options.addOption("f", "csv-file", true, "The CSV file to be read");
		options.addOption("r", "remaining-queries", true, "Remaining Bing transactions");

		CommandLineParser parser = new DefaultParser();
		CommandLine cmd;
		
		PeopleCrawler crawler = null;
		CsvHandler csvHandler = null;
		
		String csvFile = null;
		String bingKey = null;
		int remainingQueries = Integer.MAX_VALUE; 
		
		final String PARSE_ERROR = "Usage: [-k|--bing-key] <bing-key> "
				+ "[-f|csv-file] <csv-file> [-r <remaining-bing-transactions]";
		
		try {
			 cmd = parser.parse(options, args);
			 if (cmd.hasOption("k") && cmd.hasOption("f")) {
				 bingKey = cmd.getOptionValue("bing-key");
				 csvFile = cmd.getOptionValue("csv-file");
			 } else {
				 System.err.println(PARSE_ERROR);
				 System.exit(1);
			 }
			 
			 if (cmd.hasOption("r")) {
				 remainingQueries = Integer.parseInt(cmd.getOptionValue("r"));
			 }
			 
			 crawler = new PeopleCrawler(remainingQueries, bingKey);
			 csvHandler = new CsvHandler(new File(csvFile));
			 crawler.setCsvHandler(csvHandler);
			 
			 crawler.execute();
			 
		} catch (ParseException e) {
			System.err.println("Parsing failed. Reason: " + e.getMessage() );
			System.err.println(PARSE_ERROR);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
