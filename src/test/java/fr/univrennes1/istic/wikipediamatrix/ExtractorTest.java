package fr.univrennes1.istic.wikipediamatrix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.HttpStatusException;
import org.junit.Test;

import bean.Table;

public class ExtractorTest {

	@Test
	public void testExtractAllUrl() throws Exception {

		String BASE_WIKIPEDIA_URL = "https://en.wikipedia.org/wiki/";
		String outputDirHtml = "/home/runner/work/wikipedia_matrix/wikipedia_matrix/target" + File.separator + "wikiCSVs" + File.separator;
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
			}
		}
	}

	@Test
	public void testExtraction_known_few_values() throws Exception {
		try {
			return Stream.of(new File("/home/runner/work/wikipedia_matrix/wikipedia_matrix/target" + File.separator + "wikiCSVs" + File.separator).listFiles())
			.filter(file -> !file.isDirectory())
			.map(File::getName)
			.collect(Collectors.toSet());
		}
		String url = "https://en.wikipedia.org/wiki/Comparison_of_operating_system_kernels";
		List<Table> list = WikipediaHTMLExtractor.extractComplexlyFromURL(url);
		assertTrue("In url " + url + " there were " + list.size() + " tables found but there should have been 15", list.size() == 15);
		Table table1 = list.get(0);
		assertTrue("table is not rectangulaire", table1.isRectangulaire());
		assertEquals("Kernel name", table1.get(0, 0));
		assertEquals("Amiga Exec", table1.get(1, 0));
		assertEquals("Type", table1.get(0, 5));
		assertEquals("Ipfirewall, PF", table1.get(3, 6));
		assertEquals("Can keep RTC in UT[2]", table1.get(0, 15));
	}

	@Test
	public void testExtraction_known_rectangular() throws Exception {
		String url = "https://en.wikipedia.org/wiki/Comparison_of_digital_SLRs";
		List<Table> list = WikipediaHTMLExtractor.extractComplexlyFromURL(url);
		assertTrue("In url " + url + " there were " + list.size() + " tables found but there should have been 8", list.size() == 8);
		Table table1 = list.get(1);
		assertTrue("table is not rectangulaire", table1.isRectangulaire());
		assertEquals("Expected number of Raws", 28, table1.getNbRaw());
		assertEquals("Expected number of Columns", 90, table1.getNbCol());
		assertEquals("Type", table1.get(0, 0));
	}

	@Test
	public void testExtraction_known_more_values() throws Exception {
		String url = "https://en.wikipedia.org/wiki/Comparison_of_digital_SLRs";
		List<Table> list = WikipediaHTMLExtractor.extractComplexlyFromURL(url);
		System.err.println("There are " + list.size() + " tables.");
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
	public void stats() throws Exception {
		File statsFile = new File("target" + File.separator + "statistics.txt");
		String BASE_WIKIPEDIA_URL = "https://en.wikipedia.org/wiki/";
		File file = new File("inputdata" + File.separator + "wikiurls.txt");
		List<String> listURLs = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(file));
		StringBuilder forFile = new StringBuilder();
		String url;

		while ((url = br.readLine()) != null) {
			listURLs.add(url);
		}
		br.close();
		List<Table> allTables = new ArrayList<Table>();
		int ArrayList_size = listURLs.size();
		for (String name : listURLs) {
			try {
				List<Table> listTables = WikipediaHTMLExtractor.extractComplexlyFromURL(BASE_WIKIPEDIA_URL + name);
				allTables.addAll(listTables);
				System.err.println("Ok for url : " + listURLs.indexOf(name) + "/" + ArrayList_size + " (" + BASE_WIKIPEDIA_URL + name + ")");
			} catch (HttpStatusException e) {
				System.err.println("Ignoring url " + listURLs.indexOf(name) + "/" + ArrayList_size + " : " + e.getMessage());
			}
		}
		System.err.println("There were " + allTables.size() + " tables found in total.");
		List<Integer> listColumns = new ArrayList<Integer>();
		List<Integer> listRows = new ArrayList<Integer>();
		Map<String, Integer> mapTableTypes = new HashMap<String, Integer>();
		Map<String, Integer> mapTableColumnsNames = new HashMap<String, Integer>();
		for (Table table : allTables) {
			listColumns.add(table.getNbCol());
			listRows.add(table.getNbRaw());
			Integer occur = mapTableTypes.getOrDefault(table.getTableType(), 0);
			mapTableTypes.put(table.getTableType(), occur + 1);
			for (int i = 0; i < table.getNbCol(); i++) {
				String columnName = table.get(0, i);
				Integer occurColumnName = mapTableColumnsNames.getOrDefault(columnName, 0);
				mapTableColumnsNames.put(columnName, occurColumnName + 1);
			}
		}

		forFile.append("Colonne Min: " + Statistiques.min(listColumns) + "\n");
		forFile.append("Colonne Max: " + Statistiques.max(listColumns) + "\n");
		forFile.append("Colonne moy: " + Statistiques.mean(listColumns) + "\n");
		forFile.append("Colonne Std: " + Statistiques.std(listColumns) + "\n");
		forFile.append("\n" + "\n");
		forFile.append("Ligne Min: " + Statistiques.min(listRows) + "\n");
		forFile.append("Ligne Max: " + Statistiques.max(listRows) + "\n");
		forFile.append("Ligne moy: " + Statistiques.mean(listRows) + "\n");
		forFile.append("Ligne Std: " + Statistiques.std(listRows) + "\n");
		forFile.append("\n" + "\n");
		forFile.append("TableTypes registered: " + mapTableTypes + "\n" + "\n");
		forFile.append("Relevant table types: " + WikipediaHTMLExtractor.listRelevantType.size() + "\n");
		forFile.append("Ignored table types: " + WikipediaHTMLExtractor.listIgnoreType.size() + "\n" + "\n");
		int n = 30;
		forFile.append("Number of different column names: " + mapTableColumnsNames.size() + "\n" + "\n");
		List<Entry<String, Integer>> topNColumns = findTopNColumns(n, mapTableColumnsNames);
		forFile.append("The top " + n + " most used columns are: " + "\n");
		for (Entry<String, Integer> string : topNColumns) {
			forFile.append("-> '" + string.getKey() + "' (" + string.getValue() + " occurences)" + "\n");
		}

		FileWriter fw = new FileWriter(statsFile);
		fw.write(forFile.toString());
		fw.close();
		System.out.println("Statistics file generated at : " + statsFile.getAbsolutePath());
		try (BufferedReader br2 = new BufferedReader(new FileReader(statsFile.getAbsolutePath()))) {
   			String line;
   			while ((line = br2.readLine()) != null) {
     	  		System.out.println(line);
				}
			br2.close();
		}

	}

	private List<Entry<String, Integer>> findTopNColumns(int n, Map<String, Integer> mapTableColumnsNames) {
		List<Entry<String, Integer>> res = new ArrayList<Entry<String, Integer>>();
		while (res.size() < n && mapTableColumnsNames.size() > 0) {

			Entry<String, Integer> ColumnMax = mapTableColumnsNames.entrySet().iterator().next();
			for (Entry<String, Integer> col : mapTableColumnsNames.entrySet()) {
				if (ColumnMax.getValue() < col.getValue())
					ColumnMax = col;
			}
			mapTableColumnsNames.remove(ColumnMax.getKey());
			res.add(ColumnMax);
		}
		return res;
	}
}
