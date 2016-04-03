package it.uniroma3.agiw.indexing.test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import it.uniroma3.agiw.indexing.BulkBuilder;

import org.junit.Before;
import org.junit.Test;

public class BulkBuilderTest {
	
	//Contiene 1 file
	private String directory_1 = "output_1";
	//Contiene 10 file
	private String directory_2 = "output_2";
	//Vuota
	private String directory_empty = "output_empty";
	
	private String file_1 = "bulk_1.txt";
	private String file_2 = "bulk_2.txt";
	private String file_empty = "bulk_empty.txt";
	
	private String index = "indice";
	private String type = "tipo";
	private BulkBuilder builder;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testbuildBulk_dir1() throws FileNotFoundException, IOException {
		executeBuild(this.directory_1, this.file_1);
		checkLines(this.file_1, 2);
	}
	
	@Test
	public void testbuildBulk_dir2() throws FileNotFoundException, IOException {
		executeBuild(this.directory_2, this.file_2);
		checkLines(this.file_2, 20);
	}
	
	@Test
	public void testbuildBulk_dirEmpty() throws FileNotFoundException, IOException {
		executeBuild(this.directory_empty, this.file_empty);
		checkLines(this.file_empty, 0);
	}
	
	private void executeBuild(String sourcedir, String outputFile) throws FileNotFoundException, IOException {
		try {
			this.builder = new BulkBuilder(sourcedir, outputFile);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("Problemi nella creazione del builder");
		}
		
		try {
			this.builder.buildBulk(this.index, this.type);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("Problemi nella scrittura delle azioni bulk");
		}
	}
	
	private void checkLines(String outputFile, int lines) throws IOException {
		BufferedReader reader = null;
		String s;
		int linecount = 0;
		try {
			reader = new BufferedReader(new FileReader(outputFile));
			while ((s = reader.readLine()) != null) {
				linecount += 1;
			}
			assertEquals(lines, linecount);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			fail("File dei risultati non creato");
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("Errore nel test");
		}
		finally {
			if (reader != null) {
				reader.close();
			}
		}
			
	}
}

