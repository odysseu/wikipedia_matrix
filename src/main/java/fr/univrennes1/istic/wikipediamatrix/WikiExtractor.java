package fr.univrennes1.istic.wikipediamatrix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import bean.Table;

public class WikiExtractor {
	private static String[] ignoreType = { "navbox mw-collapsible autocollapse",
			"box-Update plainlinks metadata ambox ambox-content ambox-Update",
			"nowraplinks mw-collapsible mw-collapsed navbox-inner",
			"nowraplinks hlist mw-collapsible autocollapse navbox-inner", "nowraplinks navbox-subgroup" };

	public static List<Table> extractFromURL(String url) throws Exception {
		List<String> listIgnoreType = Arrays.asList(ignoreType);
		Document doc = Jsoup.connect(url).get();

//		Document doc = wiki.getDocument(url); // On cree un document avec l'url selectionne
		List<Table> res = new ArrayList<Table>();
		Elements tables = doc.select("table"); // On stock toutes les tables du document cree
		System.out.println("number of tables: " + tables.size());
		for (int i = 0; i < tables.size(); i++) { // On parcourt ces tables wikipedia
			Element htmltable = tables.get(i); // On selectionne une table wikipedia
			String tableType = htmltable.className();
			System.out.println(tableType);
			if (tableType.equals("sortable wikitable") || tableType.equals("wikitable sortable")
					|| tableType.equals("wikitable")) {
				Table wikitable = ParseWikitable.parseTable(htmltable);
				res.add(wikitable);
				System.out.println("Table found");
			} else if (listIgnoreType.contains(tableType)) {
				/* This represents the unacceptable types of table found in the wiki URLs. */
			} else {
				throw new Exception("Unknown Table Type: " + tableType);
			}
		}
		return res;
	}

	public static List<Table> extractComplexlyFromURL(String url) throws Exception {
		List<String> listIgnoreType = Arrays.asList(ignoreType);
		Document doc = Jsoup.connect(url).get();

//		Document doc = wiki.getDocument(url); // On cree un document avec l'url selectionne
		List<Table> res = new ArrayList<Table>();
		Elements tables = doc.select("table"); // On stock toutes les tables du document cree
		System.out.println("number of tables: " + tables.size());
		for (int i = 0; i < tables.size(); i++) { // On parcourt ces tables wikipedia
			Element htmltable = tables.get(i); // On selectionne une table wikipedia
			String tableType = htmltable.className();
			System.out.println(tableType);
			if (tableType.equals("sortable wikitable") || tableType.equals("wikitable sortable")) {
				Table wikitable = ParseWikitable.parseTable(htmltable);
				res.add(wikitable);
				System.out.println("Table found");
			} else if (tableType.equals("wikitable")) {
				Table wikitable = ParseWikitable.parseComplexTable(htmltable);
				res.add(wikitable);
				System.out.println("Table found");
			} else if (listIgnoreType.contains(tableType)) {
				/* This represents the unacceptable types of table found in the wiki URLs. */
			} else {
				throw new Exception("Unknown Table Type: " + tableType);
			}
		}
		return res;
	}

}
// TDD: Test Driven DEv