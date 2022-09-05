
public class ParserBU implements Parser {

    private int counter;

    @Override
    public boolean parse(Grammar g, String s) {
        int len = s.length();
        boolean[][][] res = new boolean[len][len][g.numNT];

        // Convert terminals to non-terminals
        for (int i = 0; i < len; i++) {
            char t = s.charAt(i);
            if (!g.T_to_NT.containsKey(t)) {
                return false;
            }
            res[i][0][g.T_to_NT.get(t)] = true;
        }

        // Fill rest of the table
        for (int y = 1; y < len; y++) { // loop over rows
            for (int x = 0; x < len-y; x++) { // loop over columns
                boolean[] newCell = new boolean[g.numNT];
                for (int i = 0; i < y; i++) { // loop over child cells
                    boolean[] under = res[x][i];
                    boolean[] diagonal = res[x+i+1][y-i-1];
                    // loop over non-terminals
                    for (int u = 0; u < g.numNT; u++) {
                        for (int d = 0; d < g.numNT; d++) {
                            if (under[u] && diagonal[d]) {
                                int[] nts = g.NTs_to_NT[u][d];
                                if (nts == null) {
                                    continue;
                                }
                                for (int n: nts) {
                                    newCell[n] = true;
                                }
                            }
                            counter++; // Flytta innåt???
                        }
                    }
                }
                res[x][y] = newCell;
            }
        }
        return res[0][len-1][g.start];
    }

    @Override
    public int getCounter() {
        return counter;
    }
}
