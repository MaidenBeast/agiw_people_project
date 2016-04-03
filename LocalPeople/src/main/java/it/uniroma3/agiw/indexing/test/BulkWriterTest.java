package it.uniroma3.agiw.indexing.test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import it.uniroma3.agiw.indexing.BulkWriter;

import org.junit.Before;
import org.junit.Test;

public class BulkWriterTest {
	
	private BulkWriter writer;
	
	private String file = "prova.txt";
	private String campoIndex = "indice";
	private String campoType = "tipo";
	private String campoId = "1000";
	private String fileMeta = "prova.meta.json";
	private String fileHTML = "prova.html";

	@Before
	public void setUp() throws Exception {
		this.writer = new BulkWriter(this.file);
	}

	@Test
	public void testWriteAction() throws Exception {
		this.writer.writeAction(this.campoIndex, this.campoType, this.campoId, this.fileHTML, this.fileMeta);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(this.file));
			String s;
			while ((s = reader.readLine()) != null) {
				System.out.println(s);
			}
		}
		catch(FileNotFoundException e) {
			fail("File non trovato o uso errato del metodo");
		}
		finally {
			if (reader != null) {
				reader.close();
			}
		}
	}
}
