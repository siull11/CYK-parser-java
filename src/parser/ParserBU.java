package parser;

import grammar.CNFGrammar;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

// CYK algorithm bottom-up implementation (with array of integers)
public class ParserBU implements Parser {
    private CNFGrammar g;
    private long counter;

    public ParserBU(CNFGrammar g) {
        this.g = g;
    }

    @Override
    public boolean parse(String s) {
        counter = 0;
        int n = s.length();
        HashMap<Character, Integer[]> T_to_NTs = g.getT_to_NTs();
        Integer[][][] NTs_to_NT = g.getNTs_to_NT();
        Integer[][][] table = new Integer[n][n][];

        // Convert terminals to non-terminals (fill bottom row)
        for (int i = 0; i < n; i++) {
            char t = s.charAt(i);
            if (!T_to_NTs.containsKey(t)) return false;
            table[i][0] = T_to_NTs.get(t);
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

                            Integer[] nts = NTs_to_NT[u][d];
                            if (nts != null) {
                                newCell.addAll(List.of(nts));
                            }
                        }
                    }
                }
                if (!newCell.isEmpty()) table[x][y] = newCell.toArray(new Integer[0]);
            }
        }

        if (table[0][n-1] == null) return false;
        for (int nt: table[0][n-1]) {
            if (nt == g.getStart()) return true;
        }
        return false;
    }

    @Override
    public long getCounter() {
        return counter;
    }
}
