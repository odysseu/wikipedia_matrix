package fr.univrennes1.istic.wikipediamatrix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Ignore;
import org.junit.Test;

import bean.Table;

public class ExtractorTest {

	@Test
	public void testExtraction() throws Exception {
		String url = "https://en.wikipedia.org/wiki/Comparison_of_operating_system_kernels";
		List<Table> list = WikipediaHTMLExtractor.extractComplexlyFromURL(url);
		assertTrue(list.size() == 15);
		Table table1 = list.get(0);
		assertTrue("table is not rectangulaire", table1.isRectangulaire());
		assertEquals("Kernel name", table1.get(0, 0));
		assertEquals("Amiga Exec", table1.get(1, 0));
		assertEquals("Type", table1.get(0, 5));
		assertEquals("Ipfirewall, PF", table1.get(3, 6));
		assertEquals("Can keep RTC in UT[2]", table1.get(0, 15));
	}

	@Test
	public void testExtraction2() throws Exception {
		String url = "https://en.wikipedia.org/wiki/Comparison_of_digital_SLRs";
		List<Table> list = WikipediaHTMLExtractor.extractComplexlyFromURL(url);
		assertTrue(list.size() == 8);
		Table table1 = list.get(1);
		assertTrue("table is not rectangulaire", table1.isRectangulaire());
		assertEquals("Expected number of Raws", 28, table1.getNbRaw());
		assertEquals("Expected number of Columns", 90, table1.getNbCol());
		assertEquals("Type", table1.get(0, 0));
	}

	@Test
	public void testExtraction3() throws Exception {
		String url = "https://en.wikipedia.org/wiki/Comparison_of_digital_SLRs";
		List<Table> list = WikipediaHTMLExtractor.extractComplexlyFromURL(url);
		Table table1 = list.get(7);
		assertTrue("table is not rectangulaire", table1.isRectangulaire());
		assertEquals("Expected number of Raws", 3, table1.getNbRaw());
		assertEquals("Expected number of Columns", 69, table1.getNbCol());
		assertEquals("", table1.get(0, 0));
		assertEquals("2003", table1.get(0, 7));
		assertEquals("2", table1.get(1, 2 + 4 * 5));
		assertEquals("SD1", table1.get(2, 1 + 4 * 10));

	}

	@Test
	public void testTable() throws Exception {
		Table table = new Table();
		assertEquals("Nb row for empty table", 0, table.getNbRaw());
		assertEquals("Nb column for empty table", 0, table.getNbCol());
		table.set(2, 1, "toto");
		assertEquals("Nb row after set", 3, table.getNbRaw());
		assertEquals("Nb column after set", 2, table.getNbCol());
		assertEquals("Content after set", "toto", table.get(2, 1));

		for (int i = 0; i < 3; i++) {

			for (int j = 0; j < 5; j++) {
				table.set(i, j, "line " + i + " col " + j);
			}
		}
		assertEquals("Nb row", 3, table.getNbRaw());
		assertEquals("Nb column", 5, table.getNbCol());

		for (int i = 0; i < 3; i++) {

			for (int j = 0; j < 5; j++) {
				assertEquals("Content after set", "line " + i + " col " + j, table.get(i, j));
			}
		}

	}

	@Test
	public void testExtractAllUrl() throws Exception {

		String BASE_WIKIPEDIA_URL = "https://en.wikipedia.org/wiki/";
		String outputDirHtml = "MesOutput" + File.separator + "wikiCSVs" + File.separator;
		File file = new File("inputdata" + File.separator + "wikiurls.txt");
		List<String> listURLs = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String url;

		while ((url = br.readLine()) != null) {
			listURLs.add(url);
		}
		br.close();

		for (String name : listURLs) {
			try {
				List<Table> listTables = WikipediaHTMLExtractor.extractComplexlyFromURL(BASE_WIKIPEDIA_URL + name);

				for (int i = 0; i < listTables.size(); i++) {
					Table table = listTables.get(i);
					String path = outputDirHtml + name + "_" + i + ".csv";
					CsvWriter.writeCsvFromTable(table, path);
				}
			} catch (HttpStatusException e) {
				System.err.println("Ignoring url at line " + listURLs.indexOf(name) + ": " + BASE_WIKIPEDIA_URL + name
						+ " : " + e.getMessage());
			} catch (Exception e) {
				throw new Exception("Error for page " + BASE_WIKIPEDIA_URL + name, e);
			}

		}
	}


}
