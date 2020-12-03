package fr.univrennes1.istic.wikipediamatrix;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.junit.Test;

public class BenchTest {

	/*
	 * the challenge is to extract as many relevant tables as possible and save them
	 * into CSV files from the 300+ Wikipedia URLs given see below for more details
	 **/
	@Test
	public void testBenchExtractors() throws Exception {

		String BASE_WIKIPEDIA_URL = "https://en.wikipedia.org/wiki/";
		String outputDirHtml = "output" + File.separator + "html" + File.separator;
		assertTrue(new File(outputDirHtml).isDirectory());
		String outputDirWikitext = "output" + File.separator + "wikitext" + File.separator;
		assertTrue(new File(outputDirWikitext).isDirectory());

		File file = new File("inputdata" + File.separator + "wikiurls.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String url;
		int nurl = 0;

		while ((url = br.readLine()) != null) {
			String wurl = BASE_WIKIPEDIA_URL + url;
			System.out.println("Wikipedia url: " + wurl);
			String csvFileName = mkCSVFileName(url, nurl);
			System.out.println("CSV file name: " + csvFileName);

			nurl++;
		}

		br.close();
//		assertEquals(nurl, 336);

	}

	@Test
	public void testExtraction() throws Exception {

	}

	public String mkCSVFileName(String url, int n) {
		return url.trim() + "-" + n + ".csv";
	}

}
