package parser;

import grammar.CNFGrammar;

import java.util.HashMap;

// CYK algorithm bottom-up implementation (with array of booleans)
public class ParserBUBool implements Parser {
    private CNFGrammar g;
    private long counter;

    public ParserBUBool(CNFGrammar g) {
        this.g = g;
    }

    @Override
    public boolean parse(String s) { // Denna ineffektiv (skapa en verision som loopar över alla regler?)???
        counter = 0;
        int n = s.length();
        int numNT = g.getNumNT();
        HashMap<Character, Integer[]> T_to_NTs = g.getT_to_NTs();
        Integer[][][] NTs_to_NT = g.getNTs_to_NT();
        boolean[][][] table = new boolean[n][n][numNT];

        // Convert terminals to non-terminals (fill bottom row)
        for (int i = 0; i < n; i++) {
            char t = s.charAt(i);
            if (!T_to_NTs.containsKey(t)) return false;
            for (int nt: T_to_NTs.get(t)) {
                table[i][0][nt] = true;
            }
        }

        // Fill rest of the table
        for (int y = 1; y < n; y++) { // loop over rows
            for (int x = 0; x < n-y; x++) { // loop over columns
                boolean[] newCell = new boolean[numNT];

                for (int i = 0; i < y; i++) { // loop over child cells
                    counter++; // Count number of loops
                    boolean[] under = table[x][i];
                    boolean[] diagonal = table[x+i+1][y-i-1];

                    for (int u = 0; u < numNT; u++) { // loop over non-terminals under
                        if (!under[u]) continue;

                        for (int d = 0; d < numNT; d++) { // loop over non-terminals diagonal
                            if (!diagonal[d]) continue;

                            Integer[] nts = NTs_to_NT[u][d]; // get non-terminals that produce u and d
                            if (nts == null) continue;

                            for (int nt: nts) newCell[nt] = true;
                        }
                    }
                }
                table[x][y] = newCell;
            }
        }
        return table[0][n-1][g.getStart()];
    }

    @Override
    public long getCounter() {
        return counter;
    }
}
