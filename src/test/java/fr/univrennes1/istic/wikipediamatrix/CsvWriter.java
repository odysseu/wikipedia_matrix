package fr.univrennes1.istic.wikipediamatrix;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;

import bean.Table;

public class CsvWriter {
	static String outputDirWikitext = "C:\\Users\\Ulysse Boucherie\\eclipse-workspace\\wikipedia_matrix\\MesOutput\\wikiCSVs";

	public static void writeCsvFromTable(Table table, String csvName) {

		try {
			Writer writer = Files.newBufferedWriter(Paths.get(outputDirWikitext + csvName));

			ICSVWriter csvWriter = new CSVWriterBuilder(writer).withSeparator(';')
					// .withQuoteChar(CSVWriter.NO_QUOTE_CHARACTER)
					.withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER).withLineEnd(CSVWriter.DEFAULT_LINE_END).build();

			csvWriter.writeNext(table.getHeader());
			csvWriter.writeAll(table.getLines());
//			assertTrue();
			csvWriter.close();
			writer.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

}
