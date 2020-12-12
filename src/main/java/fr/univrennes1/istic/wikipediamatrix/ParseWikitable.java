package fr.univrennes1.istic.wikipediamatrix;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import bean.Table;

public class ParseWikitable {

//	private static Table fillContent(Table tableau, Elements lignes) {
//		for (int i = 1; i < lignes.size(); i++) {
//			Element tr = lignes.get(i);
//			Elements cellules = tr.select("td");
//
//			List<String> line = new ArrayList<String>();
//			for (Element td : cellules) {
//				line.add(td.text());
//			}
//			tableau.addLine(line.toArray(new String[0]));
//		}
//		return tableau;
//	}

//	private static Table createHeader(Table tableau, Elements lignes) {
//		Element firstTr = lignes.get(0);
//		Elements cellulesHeader = firstTr.select("th");
//		List<String> header = new ArrayList<String>();
//		for (Element td : cellulesHeader) {
//			header.add(td.text());
//		}
//		System.out.println(header);
//		tableau.setHeader(header.toArray(new String[0]));
//		return tableau;
//	}

	public static Table parseTable(Element htmltable) {
		Table tableau = new Table();
		try {
			Elements lignes = htmltable.select("tr");
			for (int i = 0; i < lignes.size(); i++) {
				Element tr = lignes.get(i);
				Elements cellules = tr.select("td,th");

				List<String> line = new ArrayList<String>();
				for (Element td : cellules) {
					line.add(td.text());
				}
				tableau.addLine(line.toArray(new String[0]));
			}
			return tableau;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	public static Table parseComplexTable(Element htmltable) {
		Table tableau = new Table();
		try {
			Elements lignes = htmltable.select("tr");
			int currentRaw = 0;
			for (Element tr : lignes) {
				int currentCol = 0;
				Elements cellules = tr.select("td,th");
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
}
