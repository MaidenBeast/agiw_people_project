package it.uniroma3.agiw.indexing.services.processing;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HTMLFilePreparer implements FilePreparer {

	@Override
	public Object prepare(String path) throws IOException {
		File htmlFile = new File(path);
		Document doc = Jsoup.parse(htmlFile, "UTF-8");
		return doc;
	}

}
