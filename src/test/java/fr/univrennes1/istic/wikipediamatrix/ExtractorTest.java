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
		System.out.println("number of lists: "+ list.size());
		assertTrue(list.size() == 15);
		
//		https://en.wikipedia.org/wiki/Comparison_of_digital_SLRs
		
	}
	
	@Test
	public void testExtraction2() throws Exception {
		String url = "https://en.wikipedia.org/wiki/Comparison_of_digital_SLRs";
		List<Table> list = WikiExtractor.extractFromURL(url);
		System.out.println("number of lists: "+ list.size());
		assertTrue(list.size() == 1);
		
		
	}

}
