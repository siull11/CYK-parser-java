package parser;

import java.util.HashSet;
import java.util.List;

import grammar.Grammar;

//FIXA INDEXIN SCHEMA FOR TABLE (ALL PARSE)???
// CYK algorithm bottom-up implementation
public class ParserBU implements Parser { // Splitta upp till ParserBUBool, ParserBUInt, ParserBUSet!!!
    private long counter;

    @Override
    public boolean parse(Grammar g, String s) {
        counter = 0;
        int n = s.length();
        boolean[][][] table = new boolean[n][n][g.numNT];

        // Convert terminals to non-terminals (fill bottom row)
        for (int i = 0; i < n; i++) {
            char t = s.charAt(i);
            if (!g.T_to_NTs.containsKey(t)) return false;
            for (int nt: g.T_to_NTs.get(t)) {
                table[i][0][nt] = true;
            }
        }

        // Fill rest of the table
        for (int y = 1; y < n; y++) { // loop over rows
            for (int x = 0; x < n-y; x++) { // loop over columns
                boolean[] newCell = new boolean[g.numNT];

                for (int i = 0; i < y; i++) { // loop over child cells
                    counter++; // Count number of loops
                    boolean[] under = table[x][i];
                    boolean[] diagonal = table[x+i+1][y-i-1];

                    for (int u = 0; u < g.numNT; u++) { // loop over non-terminals under
                        if (!under[u]) continue;

                        for (int d = 0; d < g.numNT; d++) { // loop over non-terminals diagonal
                            if (!diagonal[d]) continue;

                            Integer[] nts = g.NTs_to_NT[u][d]; // get non-terminals that produce u and d
                            if (nts == null) continue;

                            for (int nt: nts) newCell[nt] = true;
                        }
                    }
                }
                table[x][y] = newCell;
            }
        }
        return table[0][n-1][g.start];
    }

//    @Override
    public boolean parseIntArr(Grammar g, String s) {
        counter = 0;
        int n = s.length();
        Integer[][][] table = new Integer[n][n][];

        // Convert terminals to non-terminals (fill bottom row)
        for (int i = 0; i < n; i++) {
            char t = s.charAt(i);
            if (!g.T_to_NTs.containsKey(t)) return false;
            table[i][0] = g.T_to_NTs.get(t);
        }
        // Fill rest of the table
        for (int y = 1; y < n; y++) { // loop over rows
            for (int x = 0; x < n-y; x++) { // loop over columns
                HashSet<Integer> newCell = new HashSet<>();

                for (int i = 0; i < y; i++) { // loop over child cells
                    counter++; // Count number of loops
                    Integer[] under = table[x][i];
                    Integer[] diagonal = table[x+i+1][y-i-1];

                    if (under == null || diagonal == null) continue;

                    for (int u: under) { // loop over non-terminals under
                        for (int d: diagonal) { // loop over non-terminals diagonal

                            Integer[] nts = g.NTs_to_NT[u][d];
                            if (nts != null) {
                                newCell.addAll(List.of(nts));
                            }
                        }
                    }
                }
                if (!newCell.isEmpty()) table[x][y] = table[x][y] = newCell.toArray(new Integer[0]);
            }
        }

        if (table[0][n-1] == null) return false;
        for (int nt: table[0][n-1]) {
            if (nt == g.start) return true;
        }
        return false;
    }

    @Override
    public long getCounter() {
        return counter;
    }
}
