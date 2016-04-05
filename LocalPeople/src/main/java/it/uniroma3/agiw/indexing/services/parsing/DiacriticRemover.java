package it.uniroma3.agiw.indexing.services.parsing;

import it.uniroma3.agiw.indexing.Config;

public class DiacriticRemover {
	
	private Config diacritic;
	
	public DiacriticRemover() throws Exception {
		this.diacritic = new Config("diacritic.properties");
	}
	
	public String clean(String input) {
		StringBuffer str = new StringBuffer(input);
		for (int i = 0; i < str.length(); i +=1) {
			String diacr = String.valueOf(str.charAt(i));
			String correction = this.diacritic.getPropertyValue(diacr);
			if (correction != null) {
				str.replace(i, i+1, correction);
			}
		}
		return str.toString();
	}

}
