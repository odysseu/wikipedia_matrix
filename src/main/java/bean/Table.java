package bean;

import java.util.ArrayList;
import java.util.List;

public class Table {

    private List<String[]> lines = new ArrayList<String[]>();
    private String tableType;

    public List<String[]> getLines() {
        return this != null ? this.lines : new ArrayList<String[]>();
    }

    public void setLines(List<String[]> lines) {
        this.lines = lines != null ? lines : new ArrayList<String[]>();
    }

    public String[] getLine(int index) {
        if (this.lines.isEmpty() || index < 0 || index >= this.lines.size()) {
            return null;
        }
        return this.lines.get(index);
    }

    public void set(int rowIndex, int colIndex, String value) {
        if (rowIndex < 0 || colIndex < 0) {
            return;
        }

        while (this.lines.size() <= rowIndex) {
            this.lines.add(new String[0]);
        }

        String[] line = this.lines.get(rowIndex);
        if (line.length <= colIndex) {
            String[] newLine = new String[colIndex + 1];
            System.arraycopy(line, 0, newLine, 0, line.length);
            this.lines.set(rowIndex, newLine);
        }
        this.lines.get(rowIndex)[colIndex] = value;
    }

    public void addLine(String[] line) {
        if (line != null) {
            this.lines.add(line);
        }
    }

    public String get(int i, int j) {
        if (this.lines.isEmpty() || i < 0 || i >= this.lines.size()) {
            return null;
        }
        String[] line = this.lines.get(i);
        if (line == null || j < 0 || j >= line.length) {
            return null;
        }
        return line[j];
    }

    public int getNbRow() {
        return this.lines.size();
    }

    public int getNbCol() {
        if (this.lines.isEmpty()) {
            return 0;
        }

        int nbCol = 0;
        for (String[] strings : this.lines) {
            if (strings != null && strings.length > nbCol) {
                nbCol = strings.length;
            }
        }
        return nbCol;
    }

    public boolean isRectangulaire() {
        if (this.lines.isEmpty()) {
            return false; // or true, depending on your requirements
        }

        int nbCol = this.lines.get(0).length;
        for (String[] strings : this.lines) {
            if (strings == null || strings.length != nbCol) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        if (this.lines.isEmpty()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[\n");

        for (String[] line : this.lines) {
            if (line == null) {
                sb.append("[null]\n");
                continue;
            }

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
