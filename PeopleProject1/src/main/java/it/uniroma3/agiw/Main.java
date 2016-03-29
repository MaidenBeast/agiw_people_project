package it.uniroma3.agiw;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {

	public static void main(String[] args) {
		Options options = new Options();

		options.addOption("k", "bing-key", true, "The Bing Key for fetching results from the search engine");
		options.addOption("f", "csv-file", true, "The CSV file to be read");

		CommandLineParser parser = new DefaultParser();
		CommandLine cmd;
		
		String csvFile = null;
		String bingKey = null;
		
		final String PARSE_ERROR = "Usage: [-k|--bing-key] <bing-key> "
				+ "[-f|csv-file] <csv-file>";
		
		try {
			 cmd = parser.parse(options, args);
			 if (cmd.hasOption("k") && cmd.hasOption("f")) {
				 bingKey = cmd.getOptionValue("bing-key");
				 csvFile = cmd.getOptionValue("csv-file");
			 } else {
				 System.err.println(PARSE_ERROR);
				 System.exit(1);
			 }
		} catch (ParseException e) {
			System.err.println("Parsing failed. Reason: " + e.getMessage() );
			System.err.println(PARSE_ERROR);
		}
		
	}

}
