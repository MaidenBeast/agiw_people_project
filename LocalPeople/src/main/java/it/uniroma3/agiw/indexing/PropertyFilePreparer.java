package it.uniroma3.agiw.indexing;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class PropertyFilePreparer implements FilePreparer {

	@Override
	public Object prepare(String path) throws Exception {
		Properties prop = new Properties();
		File file = new File(path);
		
		prop.load(new FileInputStream(file));
		return prop;
	}

}
