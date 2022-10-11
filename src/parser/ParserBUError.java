package parser;

import grammar.CNFGrammar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

// -1 is empty (lambda)!!!
// Same algorithm as in ParserBU.java with error correction implemented as well
public class ParserBUError implements Parser{
    private final CNFGrammar g;
    private long counter;
    ErrTableData[][][] table;

    public ParserBUError(CNFGrammar g) {
        this.g = g;
    }

    @Override
    public boolean parse(String s) {
        counter = 0;
        int n = s.length();
        HashMap<Character, Integer[]> T_to_NTs = g.getT_to_NTs();
        Integer[][][] NTs_to_NT = g.getNTs_to_NT();
        table = new ErrTableData[n][n][];
        char[][] NT_to_Ts = g.getNT_to_Ts();

        // Convert terminals to non-terminals (fill bottom row)
        for (int i = 0; i < n; i++) {
            char t = s.charAt(i);
            if (!T_to_NTs.containsKey(t)) return false;

            ArrayList<ErrTableData> newCell = new ArrayList<>();
            HashSet<Integer> usedNts = new HashSet<>();

            for (int nt: T_to_NTs.get(t)) { // Add all nts with error 0
                newCell.add(new ErrTableData(nt, 0, String.valueOf(t)));
                usedNts.add(nt);
            }

            //!!! newCell.add(new ErrTableData(-1, 1, "")); // Add lambda (deletion)

            for (int nt = 0; nt < NT_to_Ts.length; nt++) { // Add remaining nts that create terminals with error 1
                if (!usedNts.contains(nt) && NT_to_Ts[nt] != null && NT_to_Ts[nt].length > 0) {
                    newCell.add(new ErrTableData(nt, 1, String.valueOf(NT_to_Ts[nt][0])));
                    usedNts.add(nt);
                }
            }

            table[i][0] = newCell.toArray(new ErrTableData[0]);
        }

        // Fill rest of the table
        for (int y = 1; y < n; y++) { // loop over rows
            for (int x = 0; x < n-y; x++) { // loop over columns
                HashMap<Integer, ErrTableData> newCell = new HashMap<>();

                for (int i = 0; i < y; i++) { // loop over child cells
                    counter++; // Count number of loops
                    ErrTableData[] under = table[x][i];
                    ErrTableData[] diagonal = table[x+i+1][y-i-1];

                    if (under == null || diagonal == null) continue;

                    for (ErrTableData u: under) { // loop over non-terminals under
                        for (ErrTableData d: diagonal) { // loop over non-terminals diagonal

                            Integer[] nts = NTs_to_NT[u.nt][d.nt];
                            if (nts != null) {
                                for (int nt: nts) {
                                    int errors = u.errors + d.errors;
                                    if (!newCell.containsKey(nt) || newCell.get(nt).errors > errors) {
                                        newCell.put(nt, new ErrTableData(nt, errors, u.current + d.current));
                                    }
                                }
                            }
                        }
                    }
                }
                if (!newCell.isEmpty()) table[x][y] = newCell.values().toArray(new ErrTableData[0]);
            }
        }

        if (table[0][n-1] == null) return false;
        for (ErrTableData cell: table[0][n-1]) {
            if (cell.nt == g.getStart() && cell.errors == 0) return true;
        }
        return false;
    }

    public ErrReturnData getErrorCorrections() {
        ErrTableData[] topCell = table[0][table[0].length-1];
        if (topCell == null) return null;
        for (ErrTableData cell: topCell) {
            if (cell.nt == g.getStart()) return new ErrReturnData(cell.errors, cell.current);
        }
        return null;
    }

    @Override
    public long getCounter() {
        return counter;
    }

    public static class ErrTableData {
        public int nt;
        public int errors;
        public String current;

        public ErrTableData(int nt, int errors, String current) {
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

        public ErrReturnData() {
            corrections = 0;
            corrected = "";
        }
    }
}
