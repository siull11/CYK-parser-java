package parser;

import grammar.CNFGrammar;

// CYK algorithm naive implementation
public class ParserNaive implements Parser {
    private final CNFGrammar g;
    private long counter;
    private String s;

    public ParserNaive(CNFGrammar g) {
        this.g = g;
    }

    @Override
    public boolean parse(String s) {
        this.s = s;
        counter = 0;
        return recursive(g.getStart(), 0, s.length()-1);
    }

    private boolean recursive(int nt, int i, int j) {
        counter++; // Count number of recursive calls
        if (i == j) { // At bottom of tree, see if terminal can be produced
            return g.canProduceTerminal(nt, s.charAt(i));
        }

        // Not at bottom of tree, see which non-terminals can be produced
        int[][] nts = g.getNT_to_NTs()[nt];
        if (nts != null) {
            for (int[] ntPair: nts) {
                for (int k = i; k < j; k++) {
                    if (recursive(ntPair[0], i, k) && recursive(ntPair[1], k+1, j)) return true;
                }
            }
        }
        return false;
    }

    @Override
    public long getCounter() {
        return counter;
    }
}
