package parser;

import grammar.CNFGrammar;
import grammar.LinearGrammar;

// CYK algorithm top-down implementation for linear grammars
public class LinearParserTD implements Parser {
    private long counter;
    private LinearGrammar g;
    private String s;
    private Boolean[][][] table;

    @Override
    public boolean parse(CNFGrammar g, String s) {
        return false;
    }

    public boolean parse(LinearGrammar g, String s) {
        this.g = g;
        this.s = s;
        counter = 0;
        int n = s.length();
        table = new Boolean[n][n][g.getNumNT()];
        return recursive(g.getStart(), 0, n-1);
    }

    private boolean recursive(int nt, int i, int j) {
        if (table[i][j][nt] != null) return table[i][j][nt]; // Return if val already calculated
        counter++; // Count number of recursive calls

        if (i == j) { // At bottom of tree, see if terminal can be produced
            boolean b = g.canProduceTerminal(nt, s.charAt(i));
            table[i][j][nt] = b;
            return b;
        }

        LinearGrammar.PairNTLeft[] left = g.getNT_to_NTT()[nt];
        if (left != null) {
            for (LinearGrammar.PairNTLeft production: left) {
                if (s.charAt(j) == production.t && recursive(production.nt, i, j-1)) {
                    table[i][j][nt] = true;
                    return true;
                }
            }
        }

        LinearGrammar.PairNTRight[] right = g.getNT_to_TNT()[nt];
        if (right != null) {
            for (LinearGrammar.PairNTRight production: right) {
                if (s.charAt(i) == production.t && recursive(production.nt, i+1, j)) {;
                    table[i][j][nt] = true;
                    return true;
                }
            }
        }

        table[i][j][nt] = false;
        return false;
    }

    @Override
    public long getCounter() {
        return counter;
    }
}

