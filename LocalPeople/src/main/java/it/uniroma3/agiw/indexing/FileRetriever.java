package it.uniroma3.agiw.indexing;

import java.io.FileFilter;
import java.io.File;
import java.util.Map;
import java.util.TreeMap;

//FileRetriever cerca tutti i file all'interno di una certa directory che matchano un determinato filtro
public class FileRetriever {
	
	private FileFilter filter;
	
	public FileRetriever(FileFilter f) {
		this.filter = f;
	}
	
	public Map<String, File> retrieve(String directory) {
		File dirPath = new File(directory);
		File[] found = dirPath.listFiles(this.filter);
		Map<String, File> name2path = new TreeMap<>();
		for (File file : found) {
			String filename = file.getName();
			name2path.put(filename, file);
		}
		return name2path;
	}
}
