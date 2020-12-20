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
	public static String[] relevantType = { "sortable wikitable", "wikitable", "wikitable sortable",
			"wikitable sortable mw-collapsible", "wikitable mw-collapsible mw-collapsed",
			"wikitable sortable collapsible", "wikitable sortable plainrowheaders", "wikitable sortable filterable",
			"wikitable center", "multicol", "infobox wikitable" };
	public static String[] ignoreType = {
			"box-Multiple_issues plainlinks metadata ambox ambox-content ambox-multiple_issues compact-ambox",
			"box-Weasel plainlinks metadata ambox ambox-content ambox-Weasel",
			"box-More_citations_needed plainlinks metadata ambox ambox-content ambox-Refimprove",
			"vertical-navbox nowraplinks", "mbox-small plainlinks sistersitebox",
			"nowraplinks hlist mw-collapsible autocollapse navbox-inner", "nowraplinks navbox-subgroup",
			"box-Unreferenced_section plainlinks metadata ambox ambox-content ambox-Unreferenced",
			"vertical-navbox nowraplinks hlist",
			"box-Notability plainlinks metadata ambox ambox-content ambox-Notability",
			"nowraplinks mw-collapsible autocollapse navbox-inner",
			"box-No_footnotes plainlinks metadata ambox ambox-style ambox-No_footnotes",
			"box-Copy_edit plainlinks metadata ambox ambox-style ambox-Copy_edit",
			"box-Example_farm plainlinks metadata ambox ambox-content",
			"box-Update plainlinks metadata ambox ambox-content ambox-Update",
			"box-Expand_section plainlinks metadata ambox mbox-small-left ambox-content", "metadata plainlinks stub",
			"box-Unreferenced plainlinks metadata ambox ambox-content ambox-Unreferenced",
			"box-Globalize plainlinks metadata ambox ambox-content ambox-globalize",
			"box-Unreliable_sources plainlinks metadata ambox ambox-content ambox-unreliable_sources",
			"box-Unbalanced plainlinks metadata ambox ambox-content ambox-unbalanced",
			"box-Self-published plainlinks metadata ambox ambox-content ambox-self-published",
			"box-Original_research plainlinks metadata ambox ambox-content ambox-Original_research",
			"box-Cleanup plainlinks metadata ambox ambox-style ambox-Cleanup",
			"box-More_citations_needed_section plainlinks metadata ambox ambox-content ambox-Refimprove",
			"metadata mbox-small noprint selfref", "nowraplinks mw-collapsible autocollapse navbox-subgroup",
			"nowraplinks navbox-vertical mw-collapsible uncollapsed navbox-inner",
			"nowraplinks hlist mw-collapsible expanded navbox-inner",
			"nowraplinks hlist mw-collapsible mw-collapsed navbox-inner",
			"nowraplinks mw-collapsible mw-collapsed navbox-subgroup", "infobox",
			"box-Primary_sources plainlinks metadata ambox ambox-content ambox-Primary_sources",
			"box-Third-party plainlinks metadata ambox ambox-content", "vertical-navbox nowraplinks plainlist",
			"box-Refexample plainlinks metadata ambox ambox-content ambox-Refimprove",
			"box-External_links plainlinks metadata ambox ambox-style ambox-external_links",
			"nowraplinks mw-collapsible uncollapse navbox-subgroup",
			"nowraplinks mw-collapsible uncollapsed navbox-subgroup", "infobox hproduct",
			"nowraplinks hlist navbox-inner", "box-Advert plainlinks metadata ambox ambox-content ambox-Advert",
			"box-Lead_too_short plainlinks metadata ambox ambox-content ambox-lead_too_short",
			"box-Orphan plainlinks metadata ambox ambox-style ambox-Orphan",
			"box-Empty_section plainlinks metadata ambox mbox-small-left ambox-content",
			"box-One_source plainlinks metadata ambox ambox-content ambox-one_source",
			"box-More_footnotes plainlinks metadata ambox ambox-style ambox-More_footnotes",
			"box-Essay-like plainlinks metadata ambox ambox-style ambox-essay-like",
			"box-Very_long plainlinks metadata ambox ambox-style ambox-very_long",
			"nowraplinks hlist mw-collapsible uncollapsed navbox-inner",
			"box-Missing_information plainlinks metadata ambox ambox-content",
			"box-Confusing plainlinks metadata ambox ambox-style ambox-confusing",
			"box-COI plainlinks metadata ambox ambox-content ambox-COI",
			"box-Context plainlinks metadata ambox ambox-style ambox-Context",
			"box-Condense plainlinks metadata ambox ambox-content ambox-condense",
			"box-Example_farm plainlinks metadata ambox ambox-content",
			"box-Citation_style plainlinks metadata ambox ambox-style ambox-citation_style",
			"nowraplinks hlist navbox-subgroup", "nowraplinks navbox-vertical mw-collapsible mw-collapsed navbox-inner",
			"box-Cleanup_HTML plainlinks metadata ambox ambox-style", "toc",
			"nowraplinks mw-collapsible mw-collapsed navbox-inner",
			"box-Cleanup_reorganize plainlinks metadata ambox ambox-style", "vertical-navbox hlist",
			"box-Manual plainlinks metadata ambox ambox-style",
			"box-Technical plainlinks metadata ambox ambox-style ambox-technical",
			"box-Undue_weight plainlinks metadata ambox ambox-content ambox-undue-weight",
			"box-POV plainlinks metadata ambox ambox-content ambox-POV",
			"box-Importance-section plainlinks metadata ambox ambox-content ambox-importance-section",
			"nowraplinks navbox-inner", "navbox mw-collapsible autocollapse" };

	static List<String> listIgnoreType = Arrays.asList(ignoreType);
	static List<String> listRelevantType = Arrays.asList(relevantType);

	public static List<Table> extractComplexlyFromURL(String url) throws Exception {
		Document doc = Jsoup.connect(url).get();
		List<Table> res = new ArrayList<Table>();
		Elements tables = doc.select("table");

		for (int i = 0; i < tables.size(); i++) {
			Element htmltable = tables.get(i);
			String tableType = htmltable.className();

			if (listRelevantType.contains(tableType) || tableType.isEmpty()) {

				if (!ParseWikitable.isContainedInAnotherTable(htmltable)) {
					Table wikitable = ParseWikitable.parseComplexTable(htmltable);
					res.add(wikitable);
				}
			} else if (listIgnoreType.contains(tableType)) {

			} else {
				throw new Exception("Unknown Table Type: " + tableType);
			}
		}
		return res;
	}

}
