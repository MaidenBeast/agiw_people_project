package it.uniroma3.agiw.csv;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import it.uniroma3.agiw.PersonEntry;

public class TestCSVHandler {
	private CsvHandler csvHandler;
	
	@Before
	public void setUp() throws Exception {
		URL url = this.getClass().getResource("/test_csv.csv");
		File testCSV = new File(url.getFile());
		this.csvHandler = new CsvHandler(testCSV);
	}
	
	@Test
	public void testReadNextRow() {
		try {
			PersonEntry entry1 = csvHandler.readNextRow();
			assertEquals(new PersonEntry("Pinco", "Pallino", false), entry1);
			
			PersonEntry entry2 = csvHandler.readNextRow();
			assertEquals(new PersonEntry("Mario", "Rossi", false), entry2);
			
			PersonEntry entry3 = csvHandler.readNextRow();
			assertEquals(new PersonEntry("Riccardo", "Bruni", false), entry3);
			
			PersonEntry entry4 = csvHandler.readNextRow();
			assertEquals(new PersonEntry("Marco", "Verdi", false), entry4);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	@Test
	public void testUpdateCSVRow() {
		try {
			//entry 1
			PersonEntry entry1 = csvHandler.readNextRow();			
			assertEquals(new PersonEntry("Pinco", "Pallino", false), entry1);
			
			entry1.setAlreadyFetched(true);
			csvHandler.updateRow(entry1);
			entry1 = csvHandler.readRowByIndex(0);
			assertEquals(new PersonEntry("Pinco", "Pallino", true), entry1);
			
			entry1.setAlreadyFetched(false);
			csvHandler.updateRowByIndex(entry1, 0);
			entry1 = csvHandler.readRowByIndex(0);
			assertEquals(new PersonEntry("Pinco", "Pallino", false), entry1);
			
			//entry 2
			PersonEntry entry2 = csvHandler.readNextRow();			
			assertEquals(new PersonEntry("Mario", "Rossi", false), entry2);
			
			entry2.setAlreadyFetched(true);
			csvHandler.updateRow(entry2);
			entry2 = csvHandler.readRowByIndex(1);
			assertEquals(new PersonEntry("Mario", "Rossi", true), entry2);
			
			entry2.setAlreadyFetched(false);
			csvHandler.updateRowByIndex(entry2, 1);
			entry2 = csvHandler.readRowByIndex(1);
			assertEquals(new PersonEntry("Mario", "Rossi", false), entry2);
			
			//entry 3
			PersonEntry entry3 = csvHandler.readNextRow();			
			assertEquals(new PersonEntry("Riccardo", "Bruni", false), entry3);
			
			entry3.setAlreadyFetched(true);
			csvHandler.updateRow(entry3);
			entry3 = csvHandler.readRowByIndex(2);
			assertEquals(new PersonEntry("Riccardo", "Bruni", true), entry3);
			
			entry3.setAlreadyFetched(false);
			csvHandler.updateRowByIndex(entry3, 2);
			entry3 = csvHandler.readRowByIndex(2);
			assertEquals(new PersonEntry("Riccardo", "Bruni", false), entry3);
			
			//entry 4
			PersonEntry entry4 = csvHandler.readNextRow();			
			assertEquals(new PersonEntry("Marco", "Verdi", false), entry4);
			
			entry4.setAlreadyFetched(true);
			csvHandler.updateRow(entry4);
			entry4 = csvHandler.readRowByIndex(3);
			assertEquals(new PersonEntry("Marco", "Verdi", true), entry4);
			
			entry4.setAlreadyFetched(false);
			csvHandler.updateRowByIndex(entry4, 3);
			entry4 = csvHandler.readRowByIndex(3);
			assertEquals(new PersonEntry("Marco", "Verdi", false), entry4);
			
			//entry null
			PersonEntry entryNull = csvHandler.readNextRow();
			assertNull(entryNull);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
