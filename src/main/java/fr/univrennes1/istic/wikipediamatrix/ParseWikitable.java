package fr.univrennes1.istic.wikipediamatrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import bean.Table;

public class ParseWikitable {

	static List<String> listRelevantType = Arrays.asList(WikipediaHTMLExtractor.relevantType);

	public static boolean isContainedInAnotherTable(Element htmltable) {
		for (Element elt : htmltable.parents()) {
			if (elt.tagName().equals("table") && listRelevantType.contains(elt.className()))
				return true;
		}
		return false;
	}

	public static Table parseComplexTable(Element htmltable) {
		Table tableau = new Table();
		String tableType = htmltable.className();
		tableau.setTableType(tableType);
		try {
			List<Element> trs = getChildAtSameLevel(htmltable, "tr");

			int currentRaw = 0;

			for (Element tr : trs) {
				int currentCol = 0;
				String[] tags = { "td", "th" };
				List<Element> cellules = getChildAtSameLevel(tr, tags);
				for (Element tdOuTh : cellules) {
					int colSpan = Integer.valueOf(tdOuTh.hasAttr("colspan") ? tdOuTh.attr("colspan") : "1");

					for (int j = 0; j < colSpan; j++) {
						int rawSpan = Integer.valueOf(tdOuTh.hasAttr("rowspan") ? tdOuTh.attr("rowspan") : "1");

						for (int h = 0; h < rawSpan; h++) {

							while (tableau.get(currentRaw + h, currentCol + j) != null) {
								currentCol = currentCol + 1;
							}
							tableau.set(currentRaw + h, currentCol + j, tdOuTh.text());
						}
					}
					currentCol = currentCol + 1;
				}
				currentRaw = currentRaw + 1;
			}
			return tableau;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static List<Element> getChildAtSameLevel(Element htmltable, String[] tags) {

		String selector = String.join(",", tags);

		Element firstLine = htmltable.selectFirst(selector);
		Elements siblings = firstLine.siblingElements();
		List<Element> trs = new ArrayList<Element>();
		trs.add(firstLine);
		for (Element element : siblings) {
			for (String tag : tags) {
				if (element.tagName().equals(tag))
					trs.add(element);
			}
		}
		return trs;
	}

	private static List<Element> getChildAtSameLevel(Element htmltable, String tagName) {
		Element firstLine = htmltable.selectFirst(tagName);
		Elements siblings = firstLine.siblingElements();
		List<Element> trs = new ArrayList<Element>();
		trs.add(firstLine);
		for (Element element : siblings) {
			if (element.tagName().equals(tagName))
				trs.add(element);
		}
		return trs;
	}

}
