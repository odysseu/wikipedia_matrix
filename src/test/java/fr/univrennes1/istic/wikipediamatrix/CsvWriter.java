package fr.univrennes1.istic.wikipediamatrix;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;

import bean.Table;

public class CsvWriter {

	public static void writeCsvFromTable(Table table, String path) throws IOException {

		Writer writer = Files.newBufferedWriter(Paths.get(path));

		ICSVWriter csvWriter = new CSVWriterBuilder(writer).withSeparator(';')
				// .withQuoteChar(CSVWriter.NO_QUOTE_CHARACTER)
				.withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER).withLineEnd(CSVWriter.DEFAULT_LINE_END).build();

		csvWriter.writeAll(table.getLines());
		csvWriter.close();
		writer.close();

	}

}
