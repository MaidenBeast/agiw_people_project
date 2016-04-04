package it.uniroma3.agiw.indexing;

import it.uniroma3.agiw.indexing.services.retrieval.FileFilterExtension;
import it.uniroma3.agiw.indexing.services.retrieval.FileRetriever;

import java.util.Set;

public class BulkBuilder {
	
	private String sourcedir;
	private BulkWriter writer;
	
	public BulkBuilder(String sourcedir, String destFile) throws Exception {
		this.sourcedir = sourcedir;
		this.writer = new BulkWriter(destFile);
	}
	
	public void buildBulk(String index, String type) throws Exception {
		FileRetriever htmlRetriever = new FileRetriever(new FileFilterExtension(".html"));
		
		Set<String> htmlFiles = htmlRetriever.retrievePathsAbsolute(this.sourcedir);
		
		int id = 1;
		for(String htmlPath : htmlFiles) {
			String[] nameExt = htmlPath.split("\\.");
			String jsonPath = nameExt[0] + ".meta.json";
			this.writer.writeAction(index, type, String.valueOf(id), htmlPath, jsonPath);
			id += 1;
		}
	}
}
