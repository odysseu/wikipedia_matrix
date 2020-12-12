package fr.univrennes1.istic.wikipediamatrix;

import static org.junit.Assert.*;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

import org.junit.Test;
import bean.Table;

public class ExtractorTest {

	@Test
	public void testExtraction() throws Exception {
		String url = "https://en.wikipedia.org/wiki/Comparison_of_operating_system_kernels";
		List<Table> list = WikiExtractor.extractFromURL(url);
//		System.out.println("number of lists: " + list.size());
		assertTrue(list.size() == 15);
		Table table1 = list.get(0);
//		System.out.println(table1);
		assertEquals("Kernel name", table1.get(0, 0));
		assertEquals("Amiga Exec", table1.get(1, 0));
		assertEquals("Type", table1.get(0, 5));
		assertEquals("Ipfirewall, PF", table1.get(3, 6));
		assertEquals("Can keep RTC in UT[2]", table1.get(0, 15));

		CsvWriter.writeCsvFromTable(table1, "Comparison_of_operating_system_kernels");
	}

	@Test
	public void testExtraction2() throws Exception {
//		System.out.println("\n This is test Extractor #2.");
		String url = "https://en.wikipedia.org/wiki/Comparison_of_digital_SLRs";
		List<Table> list = WikiExtractor.extractFromURL(url);
//		System.out.println("number of lists: " + list.size());
		assertTrue(list.size() == 8);

	}

	@Test
	public void testExtraction3() throws Exception {
//		System.out.println("\n This is test Extractor #3.");
		String url = "https://en.wikipedia.org/wiki/Comparison_of_digital_SLRs";
		List<Table> list = WikiExtractor.extractComplexlyFromURL(url);
		Table table1 = list.get(1);
//		System.out.println(table1);
		assertEquals("Expected number of Raws", 28, table1.getNbRaw());
		assertEquals("Expected number of Columns", 90, table1.getNbCol());
		assertEquals("Type", table1.get(0, 0));
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

}
