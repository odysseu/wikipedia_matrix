package bean;

import java.util.ArrayList;
import java.util.List;

public class Table {

//	private String[] header;
	private List<String[]> lines = new ArrayList<String[]>();

////	Header
//	public String[] getHeader() {
//		return header;
//	}
//
//	public void setHeader(String[] header) {
//		this.header = header;
//	}

// Lines
	public List<String[]> getLines() {
		return this.lines;
	}

	public void setLines(List<String[]> lines) {
		this.lines = lines;
	}

// Line
	public String[] getLine(int index) {
		return this.lines.get(index);
	}

	public void set(int rawIndex, int colIndex, String value) {
		int i = rawIndex;
		int j = colIndex;
		while (this.getLines().size() < i) {
			this.addLine(new String[0]);
		}
		String[] line1 = this.getLines().get(i);
		if (line1.length < j) {
			String[] line2 = new String[j];
			System.arraycopy(line1, 0, line2, 0, line1.length);
			this.lines.set(i, line2);
		}
		this.getLines().get(i)[j] = value;
	}

	public void addLine(String[] line) {
		this.lines.add(line);
	}

	public String get(int i, int j) {
		return this.getLines().get(i)[j];
	}

	public int getNbRaw() {
		return this.getLines().size();
	}

	public int getNbCol() {
		int nbCol = 0;
		for (String[] strings : lines) {
			int size = strings.length;
			if (nbCol > size) {
				nbCol = size;
			}
		}
		return nbCol;
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

}
