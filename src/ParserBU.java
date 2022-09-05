
public class ParserBU implements Parser {

    private int counter;

    // Skapa v2 av denna med int arr ist f bool & ksk v3 med HashSet !!!
    @Override
    public boolean parse(Grammar g, String s) {
        int n = s.length();
        boolean[][][] res = new boolean[n][n][g.numNT];

        // Convert terminals to non-terminals (fill bottom row)
        for (int i = 0; i < n; i++) {
            char t = s.charAt(i);
            if (!g.T_to_NT.containsKey(t)) return false;
            for (int nt: g.T_to_NT.get(t)) {
                res[i][0][nt] = true;
            }
        }

        // Fill rest of the table
        for (int y = 1; y < n; y++) { // loop over rows
            for (int x = 0; x < n-y; x++) { // loop over columns
                boolean[] newCell = new boolean[g.numNT];

                for (int i = 0; i < y; i++) { // loop over child cells
                    boolean[] under = res[x][i];
                    boolean[] diagonal = res[x+i+1][y-i-1];

                    for (int u = 0; u < g.numNT; u++) { // loop over non-terminals under
                        if (!under[u]) continue;

                        for (int d = 0; d < g.numNT; d++) { // loop over non-terminals diagonal
                            if (!diagonal[d]) continue;

                            int[] nts = g.NTs_to_NT[u][d]; // get non-terminals that produce u and d
                            if (nts == null) continue;

                            for (int nt: nts) {
                                newCell[nt] = true;
                                counter++; // Flytta ???
                            }
                        }
                    }
                }
                res[x][y] = newCell;
            }
        }
        return res[0][n-1][g.start];
    }

    @Override
    public int getCounter() {
        return counter;
    }
}
