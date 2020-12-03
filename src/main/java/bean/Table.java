package bean;

import java.util.ArrayList;
import java.util.List;

public class Table {

	private String[] header;
	private List<String[]> lines = new ArrayList<String[]>();

//	Header
	public String[] getHeader() {
		return header;
	}

	public void setHeader(String[] header) {
		this.header = header;
	}

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

	public void addLine(String[] line) {
		this.lines.add(line);
	}
}
