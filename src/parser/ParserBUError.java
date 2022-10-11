package parser;

import grammar.CNFGrammar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

// Same algorithm as in ParserBU.java with error correction implemented as well
public class ParserBUError implements Parser {
    private final int lambda = -1; // Empty string nt (for deletion)
    private final CNFGrammar g;
    private long counter;
    private TableData[][][] table;

    public ParserBUError(CNFGrammar g) {
        this.g = g;
    }

    @Override
    public boolean parse(String s) {
        counter = 0;
        int n = s.length();
        HashMap<Character, Integer[]> T_to_NTs = g.getT_to_NTs();
        Integer[][][] NTs_to_NT = g.getNTs_to_NT();
        table = new TableData[n][n][];
        char[][] NT_to_Ts = g.getNT_to_Ts();

        // Convert terminals to non-terminals (fill bottom row)
        for (int i = 0; i < n; i++) {
            char t = s.charAt(i);
            if (!T_to_NTs.containsKey(t)) return false;

            ArrayList<TableData> newCell = new ArrayList<>();
            HashSet<Integer> usedNts = new HashSet<>();

            for (int nt: T_to_NTs.get(t)) { // Add all nts with error 0
                newCell.add(new TableData(nt, 0, String.valueOf(t)));
                usedNts.add(nt);
            }

            newCell.add(new TableData(lambda, 1, "")); // lambda -> ""

            for (int nt = 0; nt < NT_to_Ts.length; nt++) { // Add remaining nts that create terminals with error 1
                if (!usedNts.contains(nt) && NT_to_Ts[nt] != null && NT_to_Ts[nt].length > 0) {
                    newCell.add(new TableData(nt, 1, String.valueOf(NT_to_Ts[nt][0])));
                    usedNts.add(nt);
                }
            }

            table[i][0] = newCell.toArray(new TableData[0]);
        }

        // Fill rest of the table
        for (int y = 1; y < n; y++) { // Loop over rows
            for (int x = 0; x < n-y; x++) { // Loop over columns
                HashMap<Integer, TableData> newCell = new HashMap<>();

                for (int i = 0; i < y; i++) { // Loop over child cells
                    counter++; // Count number of loops
                    TableData[] under = table[x][i];
                    TableData[] diagonal = table[x+i+1][y-i-1];

                    if (under == null || diagonal == null) continue;

                    for (TableData u: under) { // Loop over non-terminals under
                        for (TableData d: diagonal) { // Loop over non-terminals diagonal

                            if (u.nt == lambda && d.nt == lambda) { // lambda -> lambda lambda
                                addNTtoCell(newCell, lambda, u, d);
                                continue;
                            }
                            if (u.nt == lambda) { // nt -> lambda nt
                                addNTtoCell(newCell, d.nt, u, d);
                                continue;
                            }
                            if (d.nt == lambda) { // nt -> nt lambda
                                addNTtoCell(newCell, u.nt, u, d);
                                continue;
                            }

                            Integer[] nts = NTs_to_NT[u.nt][d.nt];
                            if (nts != null) {
                                for (int nt: nts)
                                    addNTtoCell(newCell, nt, u, d);
                            }
                        }
                    }
                }
                if (!newCell.isEmpty()) table[x][y] = newCell.values().toArray(new TableData[0]);
            }
        }

        if (table[0][n-1] == null) return false;
        for (TableData cell: table[0][n-1]) {
            if (cell.nt == g.getStart() && cell.errors == 0) return true;
        }
        return false;
    }

    private void addNTtoCell(HashMap<Integer, TableData> cell, int nt, TableData left, TableData right) {
        int errors = left.errors + right.errors;
        if (!cell.containsKey(nt) || cell.get(nt).errors > errors) {
            cell.put(nt, new TableData(nt, errors, left.current + right.current));
        }
    }

    public ErrReturnData getErrorCorrections() {
        TableData[] topCell = table[0][table[0].length-1];
        if (topCell == null) return null;
        for (TableData cell: topCell) {
            if (cell.nt == g.getStart()) return new ErrReturnData(cell.errors, cell.current);
        }
        return null;
    }

    @Override
    public long getCounter() {
        return counter;
    }

    public static class TableData {
        public int nt;
        public int errors;
        public String current;

        public TableData(int nt, int errors, String current) {
            this.nt = nt;
            this.errors = errors;
            this.current = current;
        }
    }

    public static class ErrReturnData {
        public int corrections; // Min num of error-correcting changes
        public String corrected;

        public ErrReturnData(int corrections, String corrected) {
            this.corrections = corrections;
            this.corrected = corrected;
        }
    }
}
