package it.uniroma3.agiw.indexing.services.parsing;

import it.uniroma3.agiw.indexing.services.processing.FilePreparer;
import it.uniroma3.agiw.indexing.services.processing.HTMLFilePreparer;

import org.jsoup.nodes.Document;

public class HTMLParser {
	
	private Document HTMLDocument;
	
	public HTMLParser(String html) throws Exception {
		this.HTMLDocument = this.prepareDocument(html);
	}
	
	public String getBodyText() {
		return this.HTMLDocument.body().text();
	}
	
	private Document prepareDocument(String html) throws Exception {
		FilePreparer fp = new HTMLFilePreparer();
		return (Document) fp.prepare(html);
	}
}
