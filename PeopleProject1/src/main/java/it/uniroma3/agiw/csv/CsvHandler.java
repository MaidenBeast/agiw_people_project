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
	
	public CsvHandler(File inputFile) {
		this.inputFile = inputFile;
		this.actualRow = 0;
	}
	
	public PersonEntry readNextRow() throws IOException {
		PersonEntry entry = this.readRowByIndex(this.actualRow);
		this.actualRow++;
		return entry;
	}
	
	public PersonEntry readRowByIndex(int index) throws IOException {
		PersonEntry entry = null;
		
		CSVReader reader = new CSVReader(new FileReader(this.inputFile), ',');
		List<String[]> csvBody = reader.readAll();
		
		/*
		 * solo nel caso in cui l'index non sfori il numero di righe
		 * del file CSV leggi la entry, altrimenti ritorna null
		 */
		if (index < csvBody.size()) {
			String[] row = csvBody.get(index);
			entry = new PersonEntry(row[0], row[1], !row[2].equals("0"));
		}
		
		reader.close();
		return entry;
	}
	
	public void updateRow(PersonEntry entry) throws IOException {
		this.updateRowByIndex(entry, this.actualRow-1);
	}
	
	public void updateRowByIndex(PersonEntry entry, int row) throws IOException {
		String[] replace = {
				entry.getPerson().getName(),
				entry.getPerson().getSurname(),
				(entry.isAlreadyFetched()) ? "1" : "0"
			};
		
		this.updateCSVRow(replace, row);
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
	
}
