package bean;

import java.util.ArrayList;
import java.util.List;

public class Table {

	private List<String[]> lines = new ArrayList<String[]>();
	private String tableType;

	public List<String[]> getLines() {
		return this.lines;
	}

	public void setLines(List<String[]> lines) {
		this.lines = lines;
	}

	public String[] getLine(int index) {
		return this.lines.get(index);
	}

	public void set(int rawIndex, int colIndex, String value) {
		int i = rawIndex;
		int j = colIndex;

		while (this.lines.size() < i + 1) {
			this.lines.add(new String[0]);
		}
		String[] line1 = this.lines.get(i);

		if (line1.length < j + 1) {
			String[] line2 = new String[j + 1];
			System.arraycopy(line1, 0, line2, 0, line1.length);
			this.lines.set(i, line2);
		}
		this.lines.get(i)[j] = value;
	}

	public void addLine(String[] line) {
		this.lines.add(line);
	}

	public String get(int i, int j) {

		if (i + 1 > this.getLines().size()) {
			return null;
		}

		if (j + 1 > this.getLines().get(i).length) {
			return null;
		}
		return this.getLines().get(i)[j];
	}

	public int getNbRaw() {
		return this.getLines().size();
	}

	public int getNbCol() {
		int nbCol = 0;

		for (String[] strings : this.lines) {
			int size = strings.length;

			if (nbCol < size) {
				nbCol = size;
			}
		}
		return nbCol;
	}

	public boolean isRectangulaire() {
		int nbCol = this.lines.get(0).length;

		for (String[] strings : lines) {
			int size = strings.length;

			if (nbCol != size) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[\n");

		for (String[] line : lines) {
			sb.append("[");

			for (int i = 0; i < line.length; i++) {

				if (i > 0) {
					sb.append(" ; ");
				}
				sb.append(line[i]);
			}
			sb.append("]\n");
		}
		sb.append("]");

		return sb.toString();
	}

	public String getTableType() {
		return this.tableType;

	}

	public void setTableType(String tableType) {
		this.tableType = tableType;

	}

}
