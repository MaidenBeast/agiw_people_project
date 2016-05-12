package it.uniroma3.agiw;

import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class Main {

	public static void main(String[] args) {
		Options options = new Options();

		options.addOption("i", "in-folder", true, "Cartella di input degli HTML e dei json");
		options.addOption("o", "out-folder", true, "Cartella di output");

		CommandLineParser parser = new DefaultParser();
		CommandLine cmd;
		
		PageCollecter pCollect = null;

		final String PARSE_ERROR = "Usage: [-i|--in-folder] <in-folder> "
				+ "[-o|--out-folder] <out-folder>";

		try {
			cmd = parser.parse(options, args);

			if (cmd.hasOption("i") && cmd.hasOption("o")) {
				pCollect = new PageCollecter(cmd.getOptionValue("in-folder"),
											cmd.getOptionValue("out-folder"));
				pCollect.collect();
			} else {
				System.err.println(PARSE_ERROR);
				System.exit(1);
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
