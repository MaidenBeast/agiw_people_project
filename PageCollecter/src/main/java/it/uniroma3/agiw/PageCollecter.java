package it.uniroma3.agiw;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class PageCollecter {
	private String inFolder;
	private String outFolder;
	
	public PageCollecter(String inFolder, String outFolder) {
		this.inFolder = inFolder;
		this.outFolder = outFolder;
	}
	
	public void collect() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = null;
		
		File inFolderFile = new File(this.inFolder);
		
		File outFolderFile = new File(this.inFolder);
		
		if (!outFolderFile.exists()) { //se non esiste la cartella di output
			outFolderFile.mkdirs(); //creala
		}
		
		for (File inFile : inFolderFile.listFiles()) {
			String inFileName = inFile.getName();
			
			if (inFileName.endsWith(".meta.json")) {
				mapper = new ObjectMapper();
				
				String prefixFile = inFileName.substring(0, inFileName.indexOf(".meta.json"));
				File inHtmlFile = new File(this.inFolder+"/"+prefixFile+".html");
				
				PageEntry pEntry = mapper.readValue(inFile, PageEntry.class);
				
				String replacedName = pEntry.getName().replaceAll("\\s|'", "_").toLowerCase();
				String replacedSurname = pEntry.getSurname().replaceAll("\\s|'", "_").toLowerCase();
				String outSubFolder = replacedName+"_"+replacedSurname;
				
				File outSubFolderFile = new File(this.outFolder+"/"+outSubFolder);
				
				if (!outSubFolderFile.exists()) { //se non esiste la cartella di output
					outSubFolderFile.mkdirs(); //creala
				}
				
				String url = pEntry.getUrl();
				String html = FileUtils.readFileToString(inHtmlFile, "utf-8");
				
				File jsonOutFile = new File(this.outFolder+"/"
											+outSubFolder+"/"
											+prefixFile
											+".json");
				this.writeToJsonFile(jsonOutFile, url, html);
				System.out.println("Salvato file "+jsonOutFile.getName());
				
			}
		}
		
	}
	
	private void writeToJsonFile(File jsonFile, String url, String html) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("url", url);
        node.put("html", html);
        mapper.writer().writeValue(jsonFile, node);
	}
}
