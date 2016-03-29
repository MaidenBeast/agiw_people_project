package it.uniroma3.agiw.csv;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import it.uniroma3.agiw.PersonEntry;

public class CsvHandler {
	
	private File inputFile;
	private int actualRow;
	private PersonEntry actualEntry;
	
	public CsvHandler(File inputFile) {
		this.inputFile = inputFile;
		this.actualRow = 0;
	}
	
	public void readNextRow() throws IOException {
		CSVReader reader = new CSVReader(new FileReader(this.inputFile), ',');
		List<String[]> csvBody = reader.readAll();
		String[] row = csvBody.get(this.actualRow);
		
		actualEntry = new PersonEntry(row[0], row[1], (row.equals("0")) ? true : false);
		
		reader.close();
	}
	
	public void updateRow() throws IOException {
		PersonEntry entry = this.getActualEntry();
		
		String[] replace = {
			entry.getPerson().getName(),
			entry.getPerson().getSurname(),
			"1" //alreadyFetched
		};
		
		this.updateCSVRow(replace, this.actualRow);
		this.actualRow++;
	}
	
	private void updateCSVRow(String[] replace, int row) throws IOException {
		// Read existing file 
		CSVReader reader = new CSVReader(new FileReader(this.inputFile), ',');
		List<String[]> csvBody = reader.readAll();
		
		csvBody.get(row)[0] = replace[0]; //name
		csvBody.get(row)[1] = replace[1]; //surname
		csvBody.get(row)[2] = replace[2]; //alreadyFetched
		
		reader.close();

		// Write to CSV file which is open
		CSVWriter writer = new CSVWriter(new FileWriter(this.inputFile), ',');
		writer.writeAll(csvBody);
		writer.flush();
		writer.close();
	}

	public PersonEntry getActualEntry() {
		return actualEntry;
	}
	
}
