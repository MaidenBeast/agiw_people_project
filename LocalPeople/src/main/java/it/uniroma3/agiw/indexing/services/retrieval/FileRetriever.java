package it.uniroma3.agiw.indexing.services.retrieval;

import java.io.FileFilter;
import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

//FileRetriever cerca tutti i file all'interno di una certa directory che matchano un determinato filtro
public class FileRetriever {
	
	private FileFilter filter;
	
	public FileRetriever(FileFilter f) {
		this.filter = f;
	}
	
	public Set<String> retrievePathsAbsolute(String directory) {
		File[] found = this.fetch(directory);
		Set<String> names = new TreeSet<>();
		
		for (File file : found) {
			String filename = file.getAbsolutePath();
			names.add(filename);
		}
		
		return names;
	}
	
	public Map<String, File> retrieve(String directory) {
		File[] found = this.fetch(directory);
		Map<String, File> name2path = new TreeMap<>();
		for (File file : found) {
			String filename = file.getAbsolutePath();
			name2path.put(filename, file);
		}
		return name2path;
	}
	
	private File[] fetch(String directory) {
		File dirPath = new File(directory);
		File[] found = dirPath.listFiles(this.filter);
		return found;
	}
}
