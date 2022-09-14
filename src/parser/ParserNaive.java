package parser;

import grammar.Grammar;

public class ParserNaive implements Parser {
    private int counter;
    private Grammar g;
    private String s;

    @Override
    public boolean parse(Grammar g, String s) {
        this.g = g;
        this.s = s;
        counter = 0;
        return recursive(g.start, 0, s.length()-1);
    }

    private boolean recursive(int nt, int i, int j) {
        counter++; // Count number of recursive calls
        if (i == j) { // At bottom of tree, see if terminal can be produced
            return g.canProduceTerminal(nt, s.charAt(i));
        }

        // Not at bottom of tree, see which non-terminals can be produced
        int[][] nts = g.NT_to_NTs[nt];
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
    public int getCounter() {
        return counter;
    }
}