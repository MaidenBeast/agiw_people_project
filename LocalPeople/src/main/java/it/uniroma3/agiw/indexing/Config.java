package it.uniroma3.agiw.indexing;

import it.uniroma3.agiw.indexing.services.processing.FilePreparer;
import it.uniroma3.agiw.indexing.services.processing.PropertyFilePreparer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class Config {
	
	final String delim = ",";
	private Properties bulkProperties;
	
	public Config(String propertyPath) throws Exception {
		this.bulkProperties = this.loadProperties(propertyPath);
	}
	
	public String getPropertyValue(String key) {
		return this.bulkProperties.getProperty(key);
	}
	
	public String[] getPropertyArray(String key) {
		String[] array = {};
		List<String> values = new ArrayList<>();
		
		Scanner s = new Scanner(this.bulkProperties.getProperty(key));
		s.useDelimiter(this.delim);
		while (s.hasNext()) {
			values.add(s.next());
		}
		s.close();
		return values.toArray(array);
	}
	
	private Properties loadProperties(String propertyPath) throws Exception {
		FilePreparer fp = new PropertyFilePreparer();
		return (Properties) fp.prepare(propertyPath);
	}

}
