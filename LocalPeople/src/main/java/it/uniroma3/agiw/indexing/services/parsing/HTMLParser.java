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
		String text = "";
		if (this.HTMLDocument.body()!=null) {
			text = this.HTMLDocument.body().text();
		}
		return this.diacriticRemoval(text);
	}
	
	public String getBodyHTML() throws Exception {
		String html = ""; 
		if (this.HTMLDocument.body()!=null) {
			this.HTMLDocument.body().html();
		}
		return this.diacriticRemoval(html);
	}
	
	private Document prepareDocument(String html) throws Exception {
		FilePreparer fp = new HTMLFilePreparer();
		return (Document) fp.prepare(html);
	}
	
	private String diacriticRemoval(String str) throws Exception {
		DiacriticRemover dr = new DiacriticRemover();
		return dr.clean(str);
	}
}
