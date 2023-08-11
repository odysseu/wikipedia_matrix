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
            List<Element> cells = getChildAtSameLevel(tr, tags);

            for (Element tdOrTh : cells) {
                int colSpan = 1;
                try {
                    String colSpanContent = tdOrTh.hasAttr("colspan") ? tdOrTh.attr("colspan") : "1";
                    colSpan = Integer.parseInt(colSpanContent);
                } catch (NumberFormatException e) {
                    System.err.println("td or th has an invalid colspan: " + tdOrTh);
                    e.printStackTrace();
                }
                int rawSpan = 1;
                try {
                    String rawSpanContent = tdOrTh.hasAttr("rowspan") ? tdOrTh.attr("rowspan") : "1";
                    rawSpan = Integer.parseInt(rawSpanContent);
                } catch (NumberFormatException e) {
                    System.err.println("td or th has an invalid rowspan: " + tdOrTh);
                    e.printStackTrace();
                }

                for (int h = 0; h < rawSpan; h++) {
                    while (tableau.get(currentRaw + h, currentCol) != null) {
                        currentCol++;
                    }
                    tableau.set(currentRaw + h, currentCol, tdOrTh.text());
                }
                currentCol += colSpan;
            }
            currentRaw++;
        }

        return tableau;
    } catch (Exception e) {
        System.err.println("Table type where problem appeared is : " + tableau.getTableType());
        e.printStackTrace();
        return null;
    }
}



	private static List<Element> getChildAtSameLevel(Element htmltable, String[] tags) {
		String selector = String.join(",", tags);
		try {
			Element firstLine = htmltable.selectFirst(selector);

			if (firstLine == null) {
				// Handle the case where no matching element was found.
				System.err.println("No element found with selector : " + selector);
				return new ArrayList<>(); // or return null if you prefer
			}

			List<Element> trs = new ArrayList<>();
			Elements siblings = firstLine.siblingElements();
			trs.add(firstLine);

			for (Element sibling : siblings) {
				for (String tag : tags) {
					if (sibling.tagName().equals(tag)) {
						trs.add(sibling);
					}
				}
			}

			return trs;
		} catch (Exception e) {
			System.err.println("HTML table where problem appeared is : " + htmltable);
			e.printStackTrace();
			return null;
		}
	}

private static List<Element> getChildAtSameLevel(Element htmltable, String tagName) {
		try	{
			Element firstLine = htmltable.selectFirst(tagName);
			List<Element> trs = new ArrayList<Element>();
			try{
				Elements siblings = firstLine.siblingElements();
				trs.add(firstLine);
				for (Element sibling : siblings) {
					if (sibling.tagName().equals(tagName))
						trs.add(sibling);
				}
			} catch (Exception e) {
			System.err.println("Il y n'y a pas de <tr>");
			e.printStackTrace();
			return null;
			}
			return trs;
		} catch (Exception e) {
			System.err.println("HTML table where problem appeared is : " + htmltable);
			e.printStackTrace();
			return null;
		}
	}

//	private static List<Element> getChildAtSameLevel(Element htmltable, String tagName) {
//		Element firstLine = htmltable.selectFirst(tagName);
//		List<Element> trs = new ArrayList<Element>();
//		if (firstLine != null){
//			Elements siblings = firstLine.siblingElements();
//			trs.add(firstLine);
//			for (Element sibling : siblings) {
//				if (sibling.tagName().equals(tagName))
//					trs.add(sibling);
//			}
//		}
//		return trs;
//	}

}
