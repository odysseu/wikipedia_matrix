package fr.univrennes1.istic.wikipediamatrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import bean.Table;

public class WikipediaHTMLExtractor {
	private static String[] relevantType = { "sortable wikitable", "wikitable", "wikitable sortable",
			"wikitable sortable mw-collapsible", "wikitable mw-collapsible mw-collapsed",
			"wikitable sortable collapsible", "wikitable sortable plainrowheaders", "wikitable sortable filterable",
			"wikitable center" };
	private static String[] ignoreType = { "navbox mw-collapsible autocollapse",
			"box-Update plainlinks metadata ambox ambox-content ambox-Update",
			"nowraplinks mw-collapsible mw-collapsed navbox-inner",
			"nowraplinks hlist mw-collapsible autocollapse navbox-inner", "nowraplinks navbox-subgroup",
			"box-Multiple_issues plainlinks metadata ambox ambox-content ambox-multiple_issues compact-ambox",
			"box-Weasel plainlinks metadata ambox ambox-content ambox-Weasel",
			"box-More_citations_needed plainlinks metadata ambox ambox-content ambox-Refimprove",
			"box-Notability plainlinks metadata ambox ambox-content ambox-Notability",
			"nowraplinks mw-collapsible autocollapse navbox-inner",
			"box-No_footnotes plainlinks metadata ambox ambox-style ambox-No_footnotes", "vertical-navbox nowraplinks",
			"box-Unreferenced_section plainlinks metadata ambox ambox-content ambox-Unreferenced",
			"mbox-small plainlinks sistersitebox", "vertical-navbox nowraplinks", "vertical-navbox nowraplinks",
			"vertical-navbox nowraplinks hlist", "box-Copy_edit plainlinks metadata ambox ambox-style ambox-Copy_edit",
			"box-Example_farm plainlinks metadata ambox ambox-content",
			"box-Expand_section plainlinks metadata ambox mbox-small-left ambox-content", "metadata plainlinks stub",
			"multicol", "toc" };

	public static List<Table> extractComplexlyFromURL(String url) throws Exception {
		List<String> listIgnoreType = Arrays.asList(ignoreType);
		List<String> listRelevantType = Arrays.asList(relevantType);
		Document doc = Jsoup.connect(url).get();

//		Document doc = wiki.getDocument(url); // On cree un document avec l'url selectionne
		List<Table> res = new ArrayList<Table>();
		Elements tables = doc.select("table"); // On stock toutes les tables du document cree
//		System.out.println("number of tables: " + tables.size());
		for (int i = 0; i < tables.size(); i++) { // On parcourt ces tables wikipedia
			Element htmltable = tables.get(i); // On selectionne une table wikipedia
			String tableType = htmltable.className();
//			System.out.println(tableType);
			if (listRelevantType.contains(tableType) || tableType.isEmpty()) {
				Table wikitable = ParseWikitable.parseComplexTable(htmltable);
				res.add(wikitable);
//				System.out.println("Sortable Table found");
			} else if (listIgnoreType.contains(tableType) || tableType.contains("box")) {
				/* This represents the unacceptable types of table found in the wiki URLs. */
			} else {
				throw new Exception("Unknown Table Type: " + tableType);
			}
		}
		return res;
	}

}
// TDD: Test Driven DEv