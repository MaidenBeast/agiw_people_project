package it.uniroma3.agiw.indexing;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Main {
	
	/*
	 * L'indexer genererà una sequenza di comandi bulk a partire dai file contenuti in una cartella passata come parametro. Tali comandi dovranno poi essere
	 * sottoposti a ElasticSearch per l'indicizzazione.
	 * 
	 * Uso dell'indexer da riga di comando:
	 * java -jar indexer.jar <directory sorgente> <destinazione bulk> <indice> <tipo>
	 */
	
	public static void main(String[] args) throws FileNotFoundException {
		if (args.length != 4) {
			System.out.println("Numero di parametri non valido");
			System.out.println("Uso: java -jar indexer.jar <directory sorgente> <destinazione bulk> <indice> <tipo>");
		}
		else {
			String sourcedir = args[0];
			String destfile = args[1];
			String index = args[2];
			String type = args[3];
			BulkBuilder builder = null;
			try {
				builder = new BulkBuilder(sourcedir, destfile);
			}
			catch (Exception e) {
				System.out.println("Errore BulkBuilder: controllare che la directory sorgente specificata esista.");
				e.printStackTrace(new PrintStream("errortrace.txt"));
				System.exit(1);
			}
			
			try {
				builder.buildBulk(index, type);
				System.out.println("Indicizzazione completata. Sottoporre il file a ElasticSearch.");
				System.out.println("Sintassi: curl -s -X POST http://localhost:9200/_bulk --data-binary @<posizione file>");
			}
			catch (Exception e) {
				System.out.println("Errore durante la scrittura dei comandi bulk");
				e.printStackTrace(new PrintStream("errortrace.txt"));
				System.exit(1);
			}
		}
	}
}
