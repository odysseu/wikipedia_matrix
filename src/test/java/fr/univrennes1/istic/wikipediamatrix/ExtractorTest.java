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
		System.out.println("number of lists: " + list.size());
		assertTrue(list.size() == 15);
		Table table1 = list.get(0);
		assertEquals("Kernel name", table1.get(0, 0));
		assertEquals("Amiga Exec", table1.get(1, 0));
		assertEquals("Type", table1.get(0, 5));
		assertEquals("Ipfirewall, PF", table1.get(3, 6));
		assertEquals("Can keep RTC in UT[2]", table1.get(0, 15));

		List<Table> list11 = WikiExtractor.extractComplexlyFromURL(url);
		System.out.println("number of lists: " + list11.size());
		assertTrue(list11.size() == 15);
		Table table11 = list11.get(0);
		assertEquals("Kernel name", table11.get(0, 0));
		assertEquals("Amiga Exec", table11.get(1, 0));
		assertEquals("Type", table11.get(0, 5));
		assertEquals("Ipfirewall, PF", table11.get(3, 6));
		assertEquals("Can keep RTC in UT[2]", table11.get(0, 15));
	}

	@Test
	public void testExtraction2() throws Exception {
		System.out.println("\n This is test Extractor #2.");
		String url = "https://en.wikipedia.org/wiki/Comparison_of_digital_SLRs";
		List<Table> list = WikiExtractor.extractFromURL(url);
		System.out.println("number of lists: " + list.size());
		assertTrue(list.size() == 8);

	}

}
