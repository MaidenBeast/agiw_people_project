package it.uniroma3.agiw.indexing;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Map;

import javax.swing.filechooser.FileNameExtensionFilter;

public class BulkBuilder {
	
	private String sourcedir;
	private PrintStream destStream;
	
	public BulkBuilder(String sourcedir, String destFile) throws FileNotFoundException {
		this.sourcedir = sourcedir;
		this.destStream = new PrintStream(new File(destFile));
	}
	
	public void writeBulk(String index, String type) {
		FileRetriever htmlRetriever = new FileRetriever(new FileFilterExtension(".html"));
		FileRetriever jsonRetriever = new FileRetriever(new FileFilterExtension(".json"));
		
		Map<String, File> htmlFiles = htmlRetriever.retrieve(this.sourcedir);
		Map<String, File> jsonFiles = jsonRetriever.retrieve(this.sourcedir);
		
		for(String htmlName : htmlFiles.keySet()) {
			
		}
	}

}
