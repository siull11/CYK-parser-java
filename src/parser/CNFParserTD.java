package parser;

import grammar.CNFGrammar;

// CYK algorithm top-down implementation
public class CNFParserTD extends ParserTD {
    private CNFGrammar g;

    public CNFParserTD(CNFGrammar g) {
        this.g = g;
    }

    @Override
    public boolean parse(String s) {
        int n = init(g, s);
        return recursive(g.getStart(), 0, n-1);
    }

    private boolean recursive(int nt, int i, int j) {
        Boolean res = baseCase(nt, i, j);
        if (res != null) return res;

        // Not at bottom of tree, see which non-terminals can be produced
        int[][] nts = g.getNT_to_NTs()[nt];
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
}
