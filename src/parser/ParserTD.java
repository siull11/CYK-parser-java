package parser;

import grammar.Grammar;

// Abstract class for common methods for the both top-down algorithms
public abstract class ParserTD implements Parser {
    private long counter;
    protected Boolean[][][] table;
    private Grammar g;
    protected String s;

    protected int init(Grammar g, String s) {
        this.g = g;
        this.s = s;
        counter = 0;
        int n = s.length();
        table = new Boolean[n][n][g.getNumNT()];
        return n;
    }

    protected Boolean baseCase(int nt, int i, int j) {
        if (table[i][j][nt] != null) return table[i][j][nt]; // Return if val already calculated
        counter++; // Count number of recursive calls

        if (i == j) { // At bottom of tree, see if terminal can be produced
            boolean b = g.canProduceTerminal(nt, s.charAt(i));
            table[i][j][nt] = b;
            return b;
        }

        return null;
    }

    @Override
    public long getCounter() {
        return counter;
    }
}
