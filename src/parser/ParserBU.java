package parser;

import java.util.Arrays;
import grammar.Grammar;

public class ParserBU implements Parser {
    private int counter;

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


    // Skapa v2 av denna med int arr / hashset ist f boolean arr
    public boolean parse2(Grammar g, String s) { // WIP!!!
        counter = 0;
        int n = s.length();
        Integer[][][] table = new Integer[n][n][];

        // Convert terminals to non-terminals (fill bottom row)
        for (int i = 0; i < n; i++) {
            char t = s.charAt(i);
            if (!g.T_to_NTs.containsKey(t)) return false;
            for (int nt: g.T_to_NTs.get(t)) {
                if (table[i][0] == null) table[i][0] = new Integer[]{nt};
                else table[i][0] = Arrays.copyOf(table[i][0], table[i][0].length+1);
            }
        }
        // Fill rest of the table
        for (int y = 1; y < n; y++) { // loop over rows
            for (int x = 0; x < n-y; x++) { // loop over columns
                Integer[] newCell = new Integer[0];
                for (int i = 0; i < y; i++) { // loop over child cells
                    Integer[] under = table[x][i];
                    Integer[] diagonal = table[x+i+1][y-i-1];
                    if (under == null || diagonal == null) continue;
//                    System.out.println(Arrays.toString(under) + Arrays.toString(diagonal)); // DEBUG
                    for (int u: under) { // loop over non-terminals under
                        for (int d: diagonal) { // loop over non-terminals diagonal
                            counter++; // Count number of loops
                            Integer[] nts = g.NTs_to_NT[u][d];
                            if (nts != null) {
                                newCell = new Integer[newCell.length+nts.length];
                                System.arraycopy(nts, 0, newCell, 0, newCell.length+nts.length);
                            }
                        }
                    }
                }
                if (newCell.length != 0) table[x][y] = newCell;
            }
        }

        for (Integer[][] row: table) {
            System.out.println(Arrays.deepToString(row)); // DEBUG
        }


        if (table[0][n-1] == null) return false;
        for (int nt: table[0][n-1]) {
            if (nt == g.start) return true;
        }
        return false;
    }

    @Override
    public int getCounter() {
        return counter;
    }
}
