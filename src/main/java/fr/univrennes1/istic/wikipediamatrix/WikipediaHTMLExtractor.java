package fr.univrennes1.istic.wikipediamatrix;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;

import bean.Table;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WikipediaHTMLExtractor {

	private String BASE_WIKIPEDIA_URL;
	private String outputDirHtml;
	private String outputDirWikitext;

	public WikipediaHTMLExtractor(String BASE_WIKIPEDIA_URL, String outputDirHtml, String outputDirWikitext) {
		this.BASE_WIKIPEDIA_URL = BASE_WIKIPEDIA_URL;
		this.outputDirHtml = outputDirHtml;
		this.outputDirWikitext = outputDirWikitext;
	}

	public Document getDocument(String url) throws IOException {
		Document doc = Jsoup.connect(BASE_WIKIPEDIA_URL + url).get();
		return doc;
	}

	public Table fillContent(Table tableau, Elements lignes) {
		for (int i = 1; i < lignes.size(); i++) {
			Element tr = lignes.get(i);
			Elements cellules = tr.select("td");

			List<String> line = new ArrayList<String>();
			for (Element td : cellules) {
				line.add(td.text());
			}
			tableau.addLine(line.toArray(new String[0]));
		}
		return tableau;
	}

	public Table createHeader(Table tableau, Elements lignes) {
		Element firstTr = lignes.get(0);
		Elements cellulesHeader = firstTr.select("th");
		List<String> header = new ArrayList<String>();
		for (Element td : cellulesHeader) {
			header.add(td.text());
		}
		System.out.println(header);
//		tableau.setHeader(header.toArray(new String[0]));
		return tableau;
	}

	public Table getTable(Element htmlTable) {
		Table tableau = new Table();
		try {
			Elements lignes = htmlTable.select("tr");
			tableau = createHeader(tableau, lignes);
			tableau = fillContent(tableau, lignes);
			return tableau;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	public static Boolean isWikiTable(Element htmltable) {
		if (htmltable.className().equals("wikitable")) {
			return true;
		}
		return false;
	}

	public void writeCsvFromTable(Table table, String csvFileName, int nurl) {

		try {
			Writer writer = Files.newBufferedWriter(Paths.get(outputDirWikitext + csvFileName));

			ICSVWriter csvWriter = new CSVWriterBuilder(writer).withSeparator(CSVWriter.DEFAULT_SEPARATOR)
					.withQuoteChar(CSVWriter.NO_QUOTE_CHARACTER).withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
					.withLineEnd(CSVWriter.DEFAULT_LINE_END).build();
//			csvWriter.writeNext(table.getHeader());
			csvWriter.writeAll(table.getLines());
			csvWriter.close();
			writer.close();
			System.out.println("NEW FILE #" + nurl + "!\n" + "CSV file : " + csvFileName + " was written");

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private static String mkCSVFileName(String url, int n) {
		return url.trim() + "-" + n + ".csv";
	}

	public static void main(String[] args) throws IOException {

		String BASE_WIKIPEDIA_URL = "https://en.wikipedia.org/wiki/";
		String outputDirHtml = "MesOutput" + File.separator + "html" + File.separator;
		String outputDirWikitext = "MesOutput" + File.separator + "wikitext" + File.separator;

		WikipediaHTMLExtractor wiki = new WikipediaHTMLExtractor(BASE_WIKIPEDIA_URL, outputDirHtml, outputDirWikitext);
		File file = new File("inputdata" + File.separator + "wikiurls.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String url;
		int nurl = 0; // Pour compter le nombre de ligne qu'on a deja fait
		while ((url = br.readLine()) != null) { // On parcourt tous les lignes du fichier "wikiurls.txt"
			Document doc = wiki.getDocument(url); // On cree un document avec l'url selectionne
			System.out.println(doc);
			Elements tables = doc.select("table"); // On stock toutes les tables du document cree
			for (int i = 0; i < tables.size(); i++) { // On parcourt ces tables wikipedia
				Element htmltable = tables.get(i); // On selectionne une table wikipedia
				if (isWikiTable(htmltable)) { // Si la table est pertinente
					Table wikitable = wiki.getTable(htmltable); // On stock ce tableau
					String csvFileName = mkCSVFileName(url, i); // On cree alors le nom du nouveau fichier csv
					wiki.writeCsvFromTable(wikitable, csvFileName, nurl); // On rentre ce fichier csv dans notre system
																			// file.
					System.out.println("On est au " + nurl + i + "ieme tableau");
				}
			}
			nurl++;
		}
		br.close();
		System.out.println("Le programme a fini.");
	}

}
