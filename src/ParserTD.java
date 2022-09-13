public class ParserTD implements Parser {
    private int counter;
    private Grammar g;
    private String s;
    private Boolean[][][] table;

    @Override
    public boolean parse(Grammar g, String s) {
        this.g = g;
        this.s = s;
        counter = 0;
        int n = s.length();
        table = new Boolean[n][n][g.numNT];
        return recursive(g.start, 0, n-1);
    }

    private boolean recursive(int nt, int i, int j) {
        if (table[i][j][nt] != null) return table[i][j][nt]; // Return if val already calculated
        counter++; // Count number of recursive calls

        if (i == j) { // At bottom of tree, see if terminal can be produced
            boolean b = g.canProduceTerminal(nt, s.charAt(i));
            table[i][j][nt] = b;
            return b;
        }

        // Not at bottom of tree, see which non-terminals can be produced
        int[][] nts = g.NT_to_NTs[nt];
        if (nts != null) {
            for (int[] ntPair: nts) {
                for (int k = i; k < j; k++) {
                    if (recursive(ntPair[0], i, k) && recursive(ntPair[1], k+1, j)) {
                        table[i][j][nt] = true;
                        return true;
                    }
                }
            }
        }
        table[i][j][nt] = false;
        return false;
    }

    @Override
    public int getCounter() {
        return counter;
    }
}
