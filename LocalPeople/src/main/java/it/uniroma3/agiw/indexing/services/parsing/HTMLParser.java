package it.uniroma3.agiw.indexing.services.parsing;

import it.uniroma3.agiw.indexing.services.processing.FilePreparer;
import it.uniroma3.agiw.indexing.services.processing.HTMLFilePreparer;

import org.jsoup.nodes.Document;

public class HTMLParser {
	
	private Document HTMLDocument;
	
	public HTMLParser(String html) throws Exception {
		this.HTMLDocument = this.prepareDocument(html);
	}
	
	public String getBodyText() throws Exception {
		String text = this.HTMLDocument.body().text();
		DiacriticRemover dr = new DiacriticRemover();
		return dr.clean(text);
	}
	
	private Document prepareDocument(String html) throws Exception {
		FilePreparer fp = new HTMLFilePreparer();
		return (Document) fp.prepare(html);
	}
}
