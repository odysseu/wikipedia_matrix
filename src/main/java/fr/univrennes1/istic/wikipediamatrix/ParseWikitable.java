package fr.univrennes1.istic.wikipediamatrix;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import bean.Table;

public class ParseWikitable {

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
