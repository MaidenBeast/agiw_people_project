package it.uniroma3.agiw.indexing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HTMLParser {
	
	private Document HTMLDocument;
	
	public HTMLParser(String html) throws IOException {
		this.HTMLDocument = this.prepareDocument(html);
	}
	
	public String getBodyText() {
		return this.HTMLDocument.body().text();
	}
	
	private Document prepareDocument(String html) throws IOException {
		File htmlFile = new File(html);
		Document doc = Jsoup.parse(htmlFile, "UTF-8");
		return doc;
	}

}
