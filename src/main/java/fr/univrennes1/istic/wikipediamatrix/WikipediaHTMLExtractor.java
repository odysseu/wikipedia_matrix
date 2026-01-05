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
			"wikitable center", "multicol", "infobox wikitable", "wikitable sortable sort-under",
			"wikitable sortable sticky-header defaultcenter col2left", "sortable sort-under wikitable", "col-begin",
			"wikitable sortable sort-under sticky-header sticky-table-row1 sticky-table-col1 sticky-header-multi",
			"wikitable sticky-header-multi sortable", "vgr-aggregators wikitable mw-collapsible-content",
			"vgr-reviews wikitable mw-collapsible-content", "vgr-awards wikitable mw-collapsible-content",
			"wikitable sortable col6center col7center", "wikitable sticky-header", "wikitable sortable sticky-header",
			"wikitable sortable sort-under sticky-header",
			"sticky-header-multi wikitable sortable sort-under mw-collapsible", "sortable wikitable sort-under",
			"wikitable sticky-header sortable sort-under mw-datatable",
			"wikitable sortable sticky-header sort-under col4right col5right col6right col7right col8right",
			"wikitable sortable sticky-header-multi", "wikitable sortable sort-under collapsible",
			"nowraplinks hlist mw-collapsible mw-collapsed navbox-subgroup",
			"wikitable sortable sort-under sticky-header-multi", "sortable wikitable sticky-header",
			"sortable sort-under wikitable sticky-header", "sortable wikitable plainrowheaders",
			"sort-under sortable wikitable sticky-table-row1 sticky-table-col1",
			"wikitable sortable sticky-header-multi sort-under", "wikitable sortable sticky-header sort-under",
			"infobox vevent", "wikitable sortable mw-datatable mw-collapsible sticky-header",
			"wikitable sortable sort-under sticky-table-head",
			"wikitable sortable sort-under sticky-table-head sticky-table-col1", "wikitable sticky-header sortable",
			"infobox-subbox", "wikitable sticky-header sortable sort-under",
			"wikitable sticky-header sortable sort-under jquery-tablesorter", "sort-under sortable wikitable",
			"wikitable sortable sort-under plainrowheaders sticky-header-multi",
			"sortable sticky-header-multi wikitable jquery-tablesorter", "sortable sticky-header-multi wikitable",
			"wikitable sortable sort-under jquery-tablesorter", "wikitable sortable plainrowheaders sticky-header",
			"wikitable sortable sort-under sort-underfilterable sticky-header-multi",
			"wikitable sortable sort-under sort-understicky-header", "wikitable plainrowheaders",
			"mw-datatable wikitable sortable sort-under",
			"mw-datatable wikitable sortable sort-under jquery-tablesorter", "mw-datatable wikitable sortable",
			"wikitable sortable mw-datatable", "wikitable sortable mw-datatable jquery-tablesorter",
			"wikitable sortable mw-datatable sticky-header-multi sort-under",
			"wikitable sortable mw-datatable sticky-header-multi sort-under jquery-tablesorter",
			"wikitable sortable sort-under sticky-header-multi sticky-table-row1 sticky-table-col1 jquery-tablesorter",
			"wikitable sortable sort-under sticky-header-multi sticky-table-row1 sticky-table-col1",
			"wikitable sortable sort-under mw-collapsible", "wikitable sortable sort-under mw-datatable sticky-header",
			"wikitable sortable sort-under mw-datatable sticky-header jquery-tablesorter",
			"wikitable sortable sort-under mw-collapsible sticky-header", "sortable sort-under wikitable jquery-tablesorter", "sortable wikitable jquery-tablesorter"
	};
	public static String[] ignoreType = {
			"box-Disputed plainlinks metadata ambox ambox-content ambox-disputed",
			"box-Only_primary_sources plainlinks metadata ambox ambox-content",
			"box-Importance_section plainlinks metadata ambox ambox-content",
			"box-Overly_detailed plainlinks metadata ambox ambox-style ambox-overly_detailed",
			"box-Cleanup_rewrite plainlinks metadata ambox ambox-content",
			"sidebar nomobile hlist", "box-Howto plainlinks metadata ambox ambox-content",
			"sidebar nomobile vcard plainlist",
			"box-Prose plainlinks metadata ambox ambox-style ambox-Prose",
			"box-Condense plainlinks metadata ambox ambox-style ambox-condense",
			"box-Promotional plainlinks metadata ambox ambox-content ambox-Advert",
			"nowraplinks mw-collapsible expanded navbox-inner",
			"infobox ib-video-game hproduct",
			"sidebar nomobile nowraplinks plainlist",
			"plainlinks metadata ambox mbox-small-left ambox-notice",
			"box-Improve_categories plainlinks metadata ambox ambox-style ambox-cat_improve",
			"box-Independent_sources plainlinks metadata ambox ambox-content",
			"box-More_footnotes_needed plainlinks metadata ambox ambox-style ambox-More_footnotes_needed",
			"box-Cleanup-lang plainlinks metadata ambox ambox-style",
			"sidebar nomobile nowraplinks hlist",
			"sidebar nomobile nowraplinks",
			"wikitable wraplinks",
			"sidebar sidebar-collapse nomobile nowraplinks hlist",
			"sidebar sidebar-collapse nomobile nowraplinks",
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
			"box-Unreliable_sources plainlinks metadata ambox ambox-content ambox-unreliable_sources",
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
			"nowraplinks navbox-inner", "navbox mw-collapsible autocollapse",
			"box-Synthesis plainlinks metadata ambox ambox-content",
			"box-Example_farm plainlinks metadata ambox ambox-style" };

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
