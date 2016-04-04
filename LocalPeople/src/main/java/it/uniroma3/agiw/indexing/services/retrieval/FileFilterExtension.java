package it.uniroma3.agiw.indexing.services.retrieval;

import java.io.File;
import java.io.FileFilter;

public class FileFilterExtension implements FileFilter {
	
	private String extension;
	
	public FileFilterExtension(String ext) {
		this.extension = ext;
	}

	@Override
	public boolean accept(File pathname) {
		String filename = pathname.getName();
		return filename.endsWith(this.extension);
	}

}
